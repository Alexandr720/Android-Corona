package com.stepin.coronaapp.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.reflect.TypeToken;
import com.stepin.coronaapp.App;
import com.stepin.coronaapp.R;
import com.stepin.coronaapp.adapter.TracingAdapter;
import com.stepin.coronaapp.adapter.TracingPassengerAdapter;
import com.stepin.coronaapp.api.Api;
import com.stepin.coronaapp.api.NetworkRequest;
import com.stepin.coronaapp.model.APIResponse;
import com.stepin.coronaapp.model.CollectionResponse;
import com.stepin.coronaapp.model.TracingListResponse;
import com.stepin.coronaapp.model.TracingOfficer;
import com.stepin.coronaapp.model.TracingPassenger;
import com.stepin.coronaapp.model.TracingPassengerListResponse;
import com.stepin.coronaapp.model.User;
import com.stepin.coronaapp.sqlite.DatabaseQueryClass;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Objects;

public class TracingActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private LinearLayout add_btn ;
    private ProgressDialog progressDialog;

    ListView informationListView,infromationListView_passenger;
    TracingAdapter adapter;
    TracingPassengerAdapter passenger_adapter;

    ArrayList<TracingListResponse.Data> items;
    ArrayList<TracingPassengerListResponse.Data> items_passenger;
    private ArrayList<CollectionResponse.Data> collections = new ArrayList<>();
    private String tracing_border;
    private int collection_id;
    String type = "1";
    User user;
    private Bitmap selectedImage = null;
    String tracing_val = "gover";
    String history_last = "Yes";
    String infect_str = "";
    private TextView government_txt, passenger_txt, government_line, passenger_line;
    LinearLayout government_lay, passenger_lay;
    private DatabaseQueryClass databaseQueryClass = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracing);
        user = new User().getUser(this);
        databaseQueryClass = new DatabaseQueryClass(TracingActivity.this);

        add_btn = findViewById(R.id.add_btn);
        items = new ArrayList<>();
        items.clear();
        items_passenger = new ArrayList<>();
        items_passenger.clear();
        government_lay = findViewById(R.id.government_layout);
        passenger_lay = findViewById(R.id.passenger_layout);
        government_lay.setVisibility(View.VISIBLE);
        passenger_lay.setVisibility(View.GONE);
        informationListView = findViewById(R.id.informationListView);
        infromationListView_passenger = findViewById(R.id.informationListView1);
        adapter = new TracingAdapter(this, items);
        passenger_adapter = new TracingPassengerAdapter(this,items_passenger);

        collection_id = user.getData().getCollection_id();

        informationListView.setAdapter(adapter);
        informationListView.setOnItemClickListener(this);

        infromationListView_passenger.setAdapter(passenger_adapter);;
        infromationListView_passenger.setOnItemClickListener(this);

        government_txt = findViewById(R.id.government_txt);
        government_line = findViewById(R.id.government_line);
        passenger_txt = findViewById(R.id.passenger_txt);
        passenger_line = findViewById(R.id.passenger_line);

        if(user.getData().getGover_level().equalsIgnoreCase("1") && user.getData().getBorder_level().equalsIgnoreCase("1")){
            government_lay.setVisibility(View.VISIBLE);
            passenger_lay.setVisibility(View.VISIBLE);
        }else if(user.getData().getGover_level().equalsIgnoreCase("0") && user.getData().getBorder_level().equalsIgnoreCase("1")){
            passenger_txt.setTextColor(getResources().getColor(R.color.white));
            government_txt.setTextColor(getResources().getColor(R.color.white_5));
            passenger_line.setVisibility(View.VISIBLE);
            government_line.setVisibility(View.GONE);
            government_lay.setVisibility(View.GONE);
            passenger_lay.setVisibility(View.VISIBLE);
            Toast.makeText(TracingActivity.this,"You can't see government officer data",Toast.LENGTH_LONG).show();
        }else if(user.getData().getBorder_level().equalsIgnoreCase("0") && user.getData().getGover_level().equalsIgnoreCase("1")){
            Toast.makeText(TracingActivity.this,"You can't see passenger data",Toast.LENGTH_LONG).show();
        }else if(user.getData().getBorder_level().equalsIgnoreCase("0") && user.getData().getGover_level().equalsIgnoreCase("0")){
            Toast.makeText(TracingActivity.this,"You can't see data",Toast.LENGTH_LONG).show();
        }

        government_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getData().getGover_level().equalsIgnoreCase("1")){
                    government_txt.setTextColor(getResources().getColor(R.color.white));
                    passenger_txt.setTextColor(getResources().getColor(R.color.white_5));
                    government_line.setVisibility(View.VISIBLE);
                    passenger_line.setVisibility(View.GONE);
                    government_lay.setVisibility(View.VISIBLE);
                    passenger_lay.setVisibility(View.GONE);
                }else{
                    Toast.makeText(TracingActivity.this,"You can't see government officer data",Toast.LENGTH_LONG).show();
                }
            }
        });

        passenger_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user.getData().getBorder_level().equalsIgnoreCase("1")){
                    passenger_txt.setTextColor(getResources().getColor(R.color.white));
                    government_txt.setTextColor(getResources().getColor(R.color.white_5));
                    passenger_line.setVisibility(View.VISIBLE);
                    government_line.setVisibility(View.GONE);
                    government_lay.setVisibility(View.GONE);
                    passenger_lay.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(TracingActivity.this,"You can't see passenger",Toast.LENGTH_LONG).show();
                }
            }
        });
        setProgressDialog();
        getInformationDetails();
        getPassengerDetails();

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View dialogView = getLayoutInflater().inflate(R.layout.dlg_tracing, null);
                AlertDialog emailDlg = new android.app.AlertDialog.Builder(TracingActivity.this)
                        .setView(dialogView)
                        .setCancelable(true)
                        .create();
                final RadioButton goverRadio = dialogView.findViewById(R.id.radioButton3);
                final RadioButton passengerRadio = dialogView.findViewById(R.id.radioButton4);
                final Button ok = dialogView.findViewById(R.id.ok);
                goverRadio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goverRadio.setChecked(true);
                        passengerRadio.setChecked(false);
                        tracing_val = "gover";
                    }
                });

                passengerRadio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goverRadio.setChecked(false);
                        passengerRadio.setChecked(true);
                        tracing_val = "passenger";
                    }
                });

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(tracing_val.equalsIgnoreCase("gover")){
                            if(user.getData().getGover_level().equalsIgnoreCase("1")){
                                createOfficerDlg();
                                tracing_val = "gover";
                                goverRadio.setChecked(true);
                                passengerRadio.setChecked(false);
                            }else{
                                Toast.makeText(TracingActivity.this,"You can't access this page",Toast.LENGTH_LONG).show();
                            }

                        }else if(tracing_val.equalsIgnoreCase("passenger")){
                            if(user.getData().getBorder_level().equalsIgnoreCase("1")){
                                if(user.getData().getCollection_id() != 0) {
                                    createPassengerDlg();
                                    tracing_val = "gover";
                                    goverRadio.setChecked(true);
                                    passengerRadio.setChecked(false);
                                } else {
                                    Toast.makeText(TracingActivity.this,"You can't access this page",Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(TracingActivity.this,"You can't access this page",Toast.LENGTH_LONG).show();
                            }
                        }
                        emailDlg.dismiss();
                    }
                });
                emailDlg.setCanceledOnTouchOutside(true);
                emailDlg.setCancelable(true);
                emailDlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                emailDlg.show();
            }
        });
    }

    private void setProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Submitting Report ...");
    }

    private void getInformationDetails() {
        if(user.getData().getGover_level().equalsIgnoreCase("1")){
            String url = new Api().generateUrl(Api.tracing_list, new LinkedHashMap<>());
            Type type = new TypeToken<TracingListResponse>(){}.getType();
            if(App.isNetworkAvailable(TracingActivity.this)){
                new NetworkRequest<TracingListResponse>().fetch(this, type, url, (obj, error) -> {
                    if (error == null && obj != null) {
                        items = (ArrayList<TracingListResponse.Data>) obj.getTracingList();
                        adapter.updateItems(items);
                    }
                });
            }else{
                Toast.makeText(TracingActivity.this,"You are working offline",Toast.LENGTH_LONG).show();
            }
        }else{
            items.clear();
            adapter.updateItems(items);
        }
    }

    private void getPassengerDetails(){
        if(user.getData().getBorder_level().equalsIgnoreCase("1")){
            String url = new Api().generateUrl(Api.passenger_list, new LinkedHashMap<>());
            Type type = new TypeToken<TracingPassengerListResponse>(){}.getType();
            if(App.isNetworkAvailable(TracingActivity.this)){
                new NetworkRequest<TracingPassengerListResponse>().fetch(this, type, url, (obj, error) -> {
                    if (error == null && obj != null) {
                        items_passenger = (ArrayList<TracingPassengerListResponse.Data>) obj.getPassengerTracingList();
                        passenger_adapter.updateItems(items_passenger);
                    }
                });
            }else{
                Toast.makeText(TracingActivity.this,"You are working offline",Toast.LENGTH_LONG).show();
            }
        }else{
            items_passenger.clear();
            passenger_adapter.updateItems(items_passenger);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(government_lay.getVisibility() == View.VISIBLE){
            try {
                String id_str = String.valueOf(items.get(position).getId());
                String name = String.valueOf(items.get(position).getPassenger_name());
                String card_num = items.get(position).getUnique_card_number();
                String type = items.get(position).getService_type();
                String tel = items.get(position).getTel_number();
                String home = items.get(position).getHome_address();
                String vehicle_num = items.get(position).getVehicle_number();
                String destination = items.get(position).getDestination();
                String title = items.get(position).getTracing_title();

                Intent intent = new Intent(TracingActivity.this,TracingDetailActivity.class);
                intent.putExtra("id",id_str);
                intent.putExtra("name",name);
                intent.putExtra("card_num",card_num);
                intent.putExtra("tel",tel);
                intent.putExtra("type",type);
                intent.putExtra("home",home);
                intent.putExtra("vehicle_num",vehicle_num);
                intent.putExtra("destination",destination);
                intent.putExtra("title",title);
                startActivity(intent);
            }catch (Exception e){
                e.printStackTrace();
            }

        }else{
            String id_str = String.valueOf(items_passenger.get(position).getId());
            String name = String.valueOf(items_passenger.get(position).getPassenger_name());
            String temp = items_passenger.get(position).getTemp();
            String vehicle_num = items_passenger.get(position).getVehicle_num();
            String tel = items_passenger.get(position).getTel_number();
            String seat_num = items_passenger.get(position).getSeat_num();
            String from_village = items_passenger.get(position).getFrom_village();
            String to_village = items_passenger.get(position).getTo_village();
            String title = items_passenger.get(position).getTracing_title();
            String location = items_passenger.get(position).getLocation();
            String contact = items_passenger.get(position).getContact();
            String contact_num = items_passenger.get(position).getContact_num();
            String history_last = items_passenger.get(position).getHistory_last();
            String infect = items_passenger.get(position).getInfect_str();
            String id_num = items_passenger.get(position).getId_num();

            Intent intent = new Intent(TracingActivity.this,TracingPassengerDetailActivity.class);
            intent.putExtra("id",id_str);
            intent.putExtra("name",name);
            intent.putExtra("id_num",id_num);
            intent.putExtra("vehicle_num",vehicle_num);
            intent.putExtra("tel",tel);
            intent.putExtra("temp",temp);
            intent.putExtra("seat_num",seat_num);
            intent.putExtra("from_village",from_village);
            intent.putExtra("to_village",to_village);
            intent.putExtra("title",title);
            intent.putExtra("location",location);
            intent.putExtra("contact",contact);
            intent.putExtra("contact_num",contact_num);
            intent.putExtra("infect",infect);
            intent.putExtra("history_last",history_last);
            startActivity(intent);
        }
    }

    private void createOfficerDlg(){
        View dialogView = getLayoutInflater().inflate(R.layout.dlg_tracing_government, null);

        AlertDialog officierDlg = new android.app.AlertDialog.Builder(TracingActivity.this)
                .setView(dialogView)
                .setCancelable(true)
                .create();
        String time = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy @ HH:mm", Locale.getDefault());
        time = sdf.format(new Date());

        final TextView name = dialogView.findViewById(R.id.name);
        final TextView date = dialogView.findViewById(R.id.date);
        name.setText(user.getData().getFirstName() + user.getData().getLastName());
        date.setText(time);
        final EditText title = dialogView.findViewById(R.id.title);
        final EditText card_num = dialogView.findViewById(R.id.card_num);
        final EditText service = dialogView.findViewById(R.id.service_type);
        final EditText tel_num = dialogView.findViewById(R.id.tel_num);
        final EditText home_address = dialogView.findViewById(R.id.address);
        final EditText reg_num = dialogView.findViewById(R.id.reg_num);
        final EditText destination = dialogView.findViewById(R.id.destination);
        final Button report = dialogView.findViewById(R.id.report);
        String finalTime = time;

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(title.getText().toString())) {
                    title.setError("Please enter title");
                } else if (TextUtils.isEmpty(card_num.getText().toString())){
                    card_num.setError("Please enter Identification Card Number");
                } else if (TextUtils.isEmpty(service.getText().toString())){
                    service.setError("Please enter Service seeking");
                } else if (TextUtils.isEmpty(tel_num.getText().toString())){
                    tel_num.setError("Please enter Telephone Number");
                } else if (TextUtils.isEmpty(home_address.getText().toString())){
                    home_address.setError("Please enter Home address");
                } else if (TextUtils.isEmpty(reg_num.getText().toString())){
                    reg_num.setError("Please enter Registration Number");
                }  else if (TextUtils.isEmpty(destination.getText().toString())){
                    destination.setError("Please enter destination");
                } else {
                    if(App.isNetworkAvailable(TracingActivity.this)){
                        LinkedHashMap<String, String> map = new LinkedHashMap<>();
                        map.put("user_id", String.valueOf(user.getData().getId()));
                        map.put("name", user.getData().getFirstName() + user.getData().getLastName());
                        map.put("title", title.getText().toString());
                        map.put("card_num", card_num.getText().toString());
                        map.put("service_type",service.getText().toString());
                        map.put("tel_num",tel_num.getText().toString());
                        map.put("home_address",home_address.getText().toString());
                        map.put("reg_num",reg_num.getText().toString());
                        map.put("destination",destination.getText().toString());
                        map.put("date", finalTime);
                        map.put("collection_id", String.valueOf(collection_id));

                        progressDialog.show();

                        String url = new Api().generateUrl(Api.tracing_submit, map);
                        Type type = new TypeToken<APIResponse>(){}.getType();

                        new NetworkRequest<APIResponse>().post(TracingActivity.this, type, url, (status, error) -> {
                            progressDialog.dismiss();
                            if (error != null && status == null) {
                                showError(error.getMessage());
                            } else if (status.getStatus().equals("failed")) {
                                showError(status.getMsg());
                            } else {
                                Toast.makeText(TracingActivity.this, status.getMsg(), Toast.LENGTH_LONG).show();
                                getInformationDetails();
                                government_txt.setTextColor(getResources().getColor(R.color.white));
                                passenger_txt.setTextColor(getResources().getColor(R.color.white_5));
                                government_line.setVisibility(View.VISIBLE);
                                passenger_line.setVisibility(View.GONE);
                                passenger_lay.setVisibility(View.GONE);
                                government_lay.setVisibility(View.VISIBLE);
                            }

                            title.setText("");
                            card_num.setText("");
                            service.setText("");
                            tel_num.setText("");
                            home_address.setText("");
                            reg_num.setText("");
                            destination.setText("");
                        });
                    } else {
                        Toast.makeText(TracingActivity.this,"You are working offline",Toast.LENGTH_LONG).show();
                        String created_time =  new SimpleDateFormat("MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                        String user_id_local = String.valueOf(user.getData().getId());
                        String local_name = user.getData().getFirstName() + user.getData().getLastName();
                        String local_title = title.getText().toString();
                        String local_card_num = card_num.getText().toString();
                        String service_type = service.getText().toString();
                        String local_tel_num = tel_num.getText().toString();
                        String local_home_address = home_address.getText().toString();
                        String local_reg_num = reg_num.getText().toString();
                        String local_destination = destination.getText().toString();
                        String local_date =  finalTime;

                        TracingOfficer tempdata = new TracingOfficer(-1, user_id_local, local_name, local_title, local_card_num, service_type, local_tel_num, local_home_address, local_reg_num,local_destination,local_date,created_time);

                        long id = databaseQueryClass.insertOfficerData(tempdata);

                        if(id!=-1){
                            getInformationDetails();
                            Toast.makeText(TracingActivity.this,"Your data is saved in your phone",Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(TracingActivity.this,"Local Database error",Toast.LENGTH_LONG).show();
                        }

                        title.setText("");
                        card_num.setText("");
                        service.setText("");
                        tel_num.setText("");
                        home_address.setText("");
                        reg_num.setText("");
                        destination.setText("");
                    }
                }
            }
        });

        officierDlg.setCanceledOnTouchOutside(true);
        officierDlg.setCancelable(true);
        Objects.requireNonNull(officierDlg.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        officierDlg.show();
    }

    @SuppressLint("SetTextI18n")
    private void createPassengerDlg(){
        View dialogView = getLayoutInflater().inflate(R.layout.dlg_tracing_passenger, null);

        AlertDialog passengerdlg = new android.app.AlertDialog.Builder(TracingActivity.this)
                .setView(dialogView)
                .setCancelable(true)
                .create();
        String time = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy @ HH:mm", Locale.getDefault());
        time = sdf.format(new Date());

        final TextView title = dialogView.findViewById(R.id.title);
        final TextView date = dialogView.findViewById(R.id.date);
        title.setText(user.getData().getFirstName() + user.getData().getLastName());
        date.setText(time);
        final EditText temp = dialogView.findViewById(R.id.temp);
        final EditText name = dialogView.findViewById(R.id.name);
        final EditText id_num = dialogView.findViewById(R.id.id_num);
        final EditText phone_num = dialogView.findViewById(R.id.phone_num);

        final EditText vehicle_num = dialogView.findViewById(R.id.vehicle_num);
        final EditText seat_num = dialogView.findViewById(R.id.seat_num);
        final EditText from_village = dialogView.findViewById(R.id.from_village);
        final EditText to_village = dialogView.findViewById(R.id.to_village);
        final EditText location = dialogView.findViewById(R.id.location);
        final EditText contact = dialogView.findViewById(R.id.contact);
        final EditText contact_num = dialogView.findViewById(R.id.contact_num);

        final RadioButton radioButton1 = dialogView.findViewById(R.id.radioButton1);
        final RadioButton radioButton2 = dialogView.findViewById(R.id.radioButton2);
        final CheckBox radioButton3 = dialogView.findViewById(R.id.radioButton3);
        final CheckBox radioButton4 = dialogView.findViewById(R.id.radioButton4);
        final CheckBox radioButton5 = dialogView.findViewById(R.id.radioButton5);
        final CheckBox radioButton6 = dialogView.findViewById(R.id.radioButton6);

        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history_last = "Yes";
            }
        });
        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history_last = "No";
            }
        });
        radioButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(infect_str.equalsIgnoreCase("")){
                    infect_str = "Cough";
                }else{
                    infect_str = infect_str + ", " + "Cough";
                }
            }
        });
        radioButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(infect_str.equalsIgnoreCase("")){
                    infect_str = "Upper Respiratory";
                } else {
                    infect_str = infect_str + ", " + "Upper Respiratory";
                }
            }
        });
        radioButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(infect_str.equalsIgnoreCase("")){
                    infect_str = "Track";
                }else{
                    infect_str = infect_str + ", " + "Track";
                }
            }
        });
        radioButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(infect_str.equalsIgnoreCase("")){
                    infect_str = "None";
                }else{
                    infect_str = infect_str + ", " + "None";
                }
            }
        });

        final Button report = dialogView.findViewById(R.id.report);
        String finalTime = time;
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String h_temp = temp.getText().toString();
                if(TextUtils.isEmpty(temp.getText().toString())){
                    temp.setError("Please input temp reading");
                } else if(Float.parseFloat(h_temp) > 45.0){
                    temp.setError("Temperature must be less than 45.0");
                } else if(Float.parseFloat(h_temp) < 30.0){
                    temp.setError("Temperature must be more than 30.0");
                } else if (TextUtils.isEmpty(name.getText().toString())) {
                    name.setError("Please input Name");
                } else if(TextUtils.isEmpty(id_num.getText().toString())){
                    id_num.setError("Please input ID Number");
                } else if(TextUtils.isEmpty(phone_num.getText().toString())){
                    phone_num.setError("Please input Phone Number");
                }else if (TextUtils.isEmpty(vehicle_num.getText().toString())){
                    vehicle_num.setError("Please input Vehicle Number");
                } else if (TextUtils.isEmpty(seat_num.getText().toString())){
                    seat_num.setError("Please input Seat Number");
                } else if (TextUtils.isEmpty(from_village.getText().toString())){
                    from_village.setError("Please input From village");
                }  else if (TextUtils.isEmpty(to_village.getText().toString())){
                    to_village.setError("Please input To village");
                } else if (TextUtils.isEmpty(location.getText().toString())){
                    location.setError("Please input location");
                } else if (TextUtils.isEmpty(contact.getText().toString())){
                    contact.setError("Please input Next of Kin Contact");
                } else if (TextUtils.isEmpty(contact_num.getText().toString())){
                    contact_num.setError("Please input Next of Kin Phone number");
                } else{
                    if(history_last.equalsIgnoreCase("0")){
                        startActivity(new Intent(TracingActivity.this, IamAtRiskActivity.class));
                    } else {
                        if(App.isNetworkAvailable(TracingActivity.this)){
                            LinkedHashMap<String, String> map = new LinkedHashMap<>();
                            map.put("user_id", String.valueOf(user.getData().getId()));
                            map.put("name", name.getText().toString());
                            map.put("id_num",id_num.getText().toString());
                            map.put("temp",temp.getText().toString());
                            map.put("phone_num", phone_num.getText().toString());
                            map.put("vehicle_num",vehicle_num.getText().toString());
                            map.put("seat_num",seat_num.getText().toString());
                            map.put("from_village",from_village.getText().toString());
                            map.put("to_village",to_village.getText().toString());
                            map.put("location",location.getText().toString());
                            map.put("date", finalTime);
                            map.put("infect_str",infect_str);
                            map.put("history_last",history_last);
                            map.put("contact",contact.getText().toString());
                            map.put("contact_num",contact_num.getText().toString());
                            map.put("collection_id", String.valueOf(collection_id));

                            progressDialog.show();

                            String url = new Api().generateUrl(Api.tracing_passenger_submit, map);
                            Type type = new TypeToken<APIResponse>(){}.getType();

                            new NetworkRequest<APIResponse>().post(TracingActivity.this, type, url, (status, error) -> {
                                progressDialog.dismiss();
                                if (error != null && status == null) {
                                    showError(error.getMessage());
                                } else if (status.getStatus().equals("failed")) {
                                    showError(status.getMsg());
                                } else {
                                    Toast.makeText(TracingActivity.this, status.getMsg(), Toast.LENGTH_LONG).show();
                                    getPassengerDetails();
                                    infect_str = "";
                                    history_last = "";
                                    passenger_txt.setTextColor(getResources().getColor(R.color.white));
                                    government_txt.setTextColor(getResources().getColor(R.color.white_5));
                                    passenger_line.setVisibility(View.VISIBLE);
                                    government_line.setVisibility(View.GONE);
                                    government_lay.setVisibility(View.GONE);
                                    passenger_lay.setVisibility(View.VISIBLE);
                                }

                                id_num.setText("");
                                temp.setText("");
                                name.setText("");
                                phone_num.setText("");
                                vehicle_num.setText("");
                                seat_num.setText("");
                                from_village.setText("");
                                to_village.setText("");
                                location.setText("");
                                contact.setText("");
                                contact_num.setText("");
                                radioButton1.setChecked(true);
                                radioButton2.setChecked(false);
                                radioButton3.setChecked(false);
                                radioButton4.setChecked(false);
                                radioButton5.setChecked(false);
                                radioButton6.setChecked(false);
                                infect_str = "";
                                history_last = "";
                            });
                        } else {
                            Toast.makeText(TracingActivity.this,"You are working offline",Toast.LENGTH_LONG).show();
                            String created_time =  new SimpleDateFormat("MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                            String user_id = String.valueOf(user.getData().getId());
                            String lname = name.getText().toString();
                            String lid_num = id_num.getText().toString();
                            String ltemp = temp.getText().toString();
                            String ltitle = title.getText().toString();
                            String lphone_num = phone_num.getText().toString();
                            String lvehicle_num = vehicle_num.getText().toString();
                            String lseat_num = seat_num.getText().toString();
                            String lfrom_village = from_village.getText().toString();
                            String lto_village= to_village.getText().toString();
                            String llocation = location.getText().toString();
                            String ldate = finalTime;
                            String linfect_str = infect_str;
                            String lhistory_last = history_last;
                            String lcontact = contact.getText().toString();
                            String lcontact_num = contact_num.getText().toString();

                            TracingPassenger tempdata = new TracingPassenger(-1, user_id, ltemp, lname, ltitle, lvehicle_num, lphone_num, lseat_num, ldate, lfrom_village, lto_village, lcontact, lcontact_num, llocation, linfect_str, lhistory_last, lid_num, created_time);

                            long id = databaseQueryClass.insertPassengerData(tempdata);

                            if(id != -1){
                                getInformationDetails();
                                Toast.makeText(TracingActivity.this,"Your data is saved in your phone",Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(TracingActivity.this,"Local Database error",Toast.LENGTH_LONG).show();
                            }

                            id_num.setText("");
                            temp.setText("");
                            name.setText("");
                            phone_num.setText("");
                            vehicle_num.setText("");
                            seat_num.setText("");
                            from_village.setText("");
                            to_village.setText("");
                            location.setText("");
                            contact.setText("");
                            contact_num.setText("");
                            radioButton1.setChecked(true);
                            radioButton2.setChecked(false);
                            radioButton3.setChecked(false);
                            radioButton4.setChecked(false);
                            radioButton5.setChecked(false);
                            radioButton6.setChecked(false);
                            infect_str = "";
                            history_last = "Yes";
                        }
                    }
                }
            }
        });

        passengerdlg.setCanceledOnTouchOutside(true);
        passengerdlg.setCancelable(true);
        passengerdlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        passengerdlg.show();
    }

    private void showError(String message) {
        new AlertDialog.Builder(this).setMessage(message)
                .setPositiveButton("Ok", (dialog, which) -> { })
                .show();
    }

    public void backBtnTapped(View view) {
        finish();
    }
}