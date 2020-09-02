package com.stepin.coronaapp.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stepin.coronaapp.App;
import com.stepin.coronaapp.R;
import com.stepin.coronaapp.adapter.ChvAdapter;
import com.stepin.coronaapp.adapter.ChvMotherAdapter;
import com.stepin.coronaapp.adapter.TracingAdapter;
import com.stepin.coronaapp.adapter.TracingPassengerAdapter;
import com.stepin.coronaapp.api.Api;
import com.stepin.coronaapp.api.NetworkRequest;
import com.stepin.coronaapp.model.APIResponse;
import com.stepin.coronaapp.model.ChvListResponse;
import com.stepin.coronaapp.model.ChvMotherListResponse;
import com.stepin.coronaapp.model.ChvMotherLocal;
import com.stepin.coronaapp.model.ChvVisitLocal;
import com.stepin.coronaapp.model.State;
import com.stepin.coronaapp.model.TracingListResponse;
import com.stepin.coronaapp.model.TracingPassenger;
import com.stepin.coronaapp.model.TracingPassengerListResponse;
import com.stepin.coronaapp.model.User;
import com.stepin.coronaapp.model.Utils;
import com.stepin.coronaapp.sqlite.DatabaseQueryClass;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class ChvActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private LinearLayout add_btn ;
    private ProgressDialog progressDialog;
    private DatabaseQueryClass databaseQueryClass = null;

    ListView informationListView,infromationListView_passenger;
    ChvAdapter adapter;
    ChvMotherAdapter passenger_adapter;

    ArrayList<ChvListResponse.Data> items;
    ArrayList<ChvMotherListResponse.Data> items_passenger;
    String type = "1";
    User user;
    private Bitmap selectedImage = null;
    String tracing_val = "visit";
    String nhif_str = "Yes",nhif_mother_str = "Yes",clinic_str = "Yes",mask_str = "Yes",mal_str = "", diabet_str = "", hyper_str = "", tb_str = "";
    String folic_str = "Yes", mary_str = "Married";
    String realdate = "";
    private int mYear,mMonth,mDay;

    private TextView government_txt, passenger_txt, government_line, passenger_line;
    LinearLayout government_lay, passenger_lay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chv);
        user = new User().getUser(this);
        databaseQueryClass = new DatabaseQueryClass(ChvActivity.this);
        add_btn = findViewById(R.id.add_btn);
        items = new ArrayList<>();
        items.clear();
        items_passenger = new ArrayList<>();
        items_passenger.clear();
        government_lay = findViewById(R.id.government_layout);
        passenger_lay = findViewById(R.id.passenger_layout);
        informationListView = findViewById(R.id.informationListView);
        infromationListView_passenger = findViewById(R.id.informationListView1);
        adapter = new ChvAdapter(this, items);
        passenger_adapter = new ChvMotherAdapter(this,items_passenger);

        informationListView.setAdapter(adapter);
        informationListView.setOnItemClickListener(this);

        infromationListView_passenger.setAdapter(passenger_adapter);;
        infromationListView_passenger.setOnItemClickListener(this);

        government_txt = findViewById(R.id.government_txt);
        government_line = findViewById(R.id.government_line);
        passenger_txt = findViewById(R.id.passenger_txt);
        passenger_line = findViewById(R.id.passenger_line);

        government_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                government_txt.setTextColor(getResources().getColor(R.color.white));
                passenger_txt.setTextColor(getResources().getColor(R.color.white_5));
                government_line.setVisibility(View.VISIBLE);
                passenger_line.setVisibility(View.GONE);
                government_lay.setVisibility(View.VISIBLE);
                passenger_lay.setVisibility(View.GONE);
            }
        });

        passenger_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passenger_txt.setTextColor(getResources().getColor(R.color.white));
                government_txt.setTextColor(getResources().getColor(R.color.white_5));
                passenger_line.setVisibility(View.VISIBLE);
                government_line.setVisibility(View.GONE);
                government_lay.setVisibility(View.GONE);
                passenger_lay.setVisibility(View.VISIBLE);

            }
        });
        setProgressDialog();
        getInformationDetails();
        getChvMotherList();
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAddDlg();
            }
        });
    }
    private void setProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Submitting Report ...");
    }
    private void getInformationDetails() {
        String url = new Api().generateUrl(Api.chv_list, new LinkedHashMap<>());
        Type type = new TypeToken<ChvListResponse>(){}.getType();
        if(App.isNetworkAvailable(ChvActivity.this)){
            new NetworkRequest<ChvListResponse>().fetch(this, type, url, (obj, error) -> {
                if (error == null && obj != null) {
                    items = (ArrayList<ChvListResponse.Data>) obj.getChvList();
                    adapter.updateItems(items);
                }
            });
        }else{
            Toast.makeText(ChvActivity.this,"You are working offline",Toast.LENGTH_LONG).show();
        }

    }

    private void getChvMotherList(){
        String url = new Api().generateUrl(Api.chv_mother_list, new LinkedHashMap<>());
        Type type = new TypeToken<ChvMotherListResponse>(){}.getType();
        if(App.isNetworkAvailable(ChvActivity.this)){
            new NetworkRequest<ChvMotherListResponse>().fetch(this, type, url, (obj, error) -> {
                if (error == null && obj != null) {
                    items_passenger = (ArrayList<ChvMotherListResponse.Data>) obj.getChvMotherList();
                    passenger_adapter.updateItems(items_passenger);
                }
            });
        }else{
            Toast.makeText(ChvActivity.this,"You are working offline",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if(passenger_lay.getVisibility() == View.GONE){
            String id_str = String.valueOf(items.get(position).getId());
            String name = String.valueOf(items.get(position).getName());
            String title = items.get(position).getTitle();
            String age = items.get(position).getAge();
            String id_num = items.get(position).getId_num();
            String nhif = items.get(position).getNhif();
            String village = items.get(position).getVillage();
            String ward = items.get(position).getWard();
            String nearname = items.get(position).getNearname();
            String mask = items.get(position).getMask();
            String provide = items.get(position).getProvide();
            String mal = items.get(position).getMal();
            String diabet = items.get(position).getDiabet();
            String hyper = items.get(position).getHyper();
            String tb = items.get(position).getTb();
            String indicate = items.get(position).getIndicate();
            String remark = items.get(position).getRemark();

            Intent intent = new Intent(ChvActivity.this,ChvVisitDetailActivity.class);
            intent.putExtra("id",id_str);
            intent.putExtra("name",name);
            intent.putExtra("title",title);
            intent.putExtra("age",age);
            intent.putExtra("id_num",id_num);
            intent.putExtra("nhif",nhif);
            intent.putExtra("village",village);
            intent.putExtra("ward",ward);
            intent.putExtra("nearname",nearname);
            intent.putExtra("provide",provide);
            intent.putExtra("mask",mask);
            intent.putExtra("mal",mal);
            intent.putExtra("diabet",diabet);
            intent.putExtra("hyper",hyper);
            intent.putExtra("tb",tb);
            intent.putExtra("indicate",indicate);
            intent.putExtra("remark",remark);
            startActivity(intent);
        }else{
            String id_str = String.valueOf(items_passenger.get(position).getId());
            String name = String.valueOf(items_passenger.get(position).getName());
            String title = items_passenger.get(position).getTitle();
            String nhif = items_passenger.get(position).getNhif();
            String age = items_passenger.get(position).getAge();
            String mother_id = items_passenger.get(position).getMother_id();
            String village = items_passenger.get(position).getVillage();
            String ward = items_passenger.get(position).getWard();
            String tel_num = items_passenger.get(position).getTel_num();
            String clinic = items_passenger.get(position).getClinic();
            String due_date = items_passenger.get(position).getDue_date();
            String folic = items_passenger.get(position).getFolic();
            String mary = items_passenger.get(position).getMary();
            String children = items_passenger.get(position).getChildren();
            String remark = items_passenger.get(position).getRemark();
            Intent intent = new Intent(ChvActivity.this,ChvMotherDetailActivity.class);
            intent.putExtra("id",id_str);
            intent.putExtra("name",name);
            intent.putExtra("title",title);
            intent.putExtra("nhif",nhif);
            intent.putExtra("age",age);
            intent.putExtra("mother_id",mother_id);
            intent.putExtra("village",village);
            intent.putExtra("ward",ward);
            intent.putExtra("tel_num",tel_num);
            intent.putExtra("clinic",clinic);
            intent.putExtra("due_date",due_date);
            intent.putExtra("folic",folic);
            intent.putExtra("mary",mary);
            intent.putExtra("children",children);
            intent.putExtra("remark",remark);
            startActivity(intent);
        }


    }

    private void createAddDlg(){
        View dialogView = getLayoutInflater().inflate(R.layout.dlg_chv, null);


        AlertDialog emailDlg = new android.app.AlertDialog.Builder(ChvActivity.this)
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
                tracing_val = "visit";
            }
        });

        passengerRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goverRadio.setChecked(false);
                passengerRadio.setChecked(true);
                tracing_val = "mother";
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tracing_val.equalsIgnoreCase("visit")){
                    createVisitDlg();
                    tracing_val = "visit";
                    goverRadio.setChecked(true);
                    passengerRadio.setChecked(false);
                }else if(tracing_val.equalsIgnoreCase("mother")){
                    createMotherDlg();
                    tracing_val = "visit";

                    goverRadio.setChecked(true);
                    passengerRadio.setChecked(false);
                }
                emailDlg.dismiss();
            }
        });

        emailDlg.setCanceledOnTouchOutside(true);
        emailDlg.setCancelable(true);
        emailDlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        emailDlg.show();


    }
    private void createVisitDlg(){
        View dialogView = getLayoutInflater().inflate(R.layout.dlg_chv_visit, null);


        AlertDialog officierDlg = new android.app.AlertDialog.Builder(ChvActivity.this)
                .setView(dialogView)
                .setCancelable(true)
                .create();
        final EditText title = dialogView.findViewById(R.id.title);
        final TextView name = dialogView.findViewById(R.id.name);
        final EditText age = dialogView.findViewById(R.id.age);
        final EditText id_num = dialogView.findViewById(R.id.id_num);
        final EditText ward = dialogView.findViewById(R.id.ward);
        final RadioButton nhif1 = dialogView.findViewById(R.id.nhif1);
        final RadioButton nhif2 = dialogView.findViewById(R.id.nhif2);
        nhif1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nhif1.setChecked(true);
                nhif2.setChecked(false);
                nhif_str = "Yes";
            }
        });
        nhif2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nhif2.setChecked(true);
                nhif1.setChecked(false);
                nhif_str = "No";
            }
        });
        final EditText village = dialogView.findViewById(R.id.village);
        final EditText nearname = dialogView.findViewById(R.id.nearname);
        final RadioButton mask1 = dialogView.findViewById(R.id.mask1);
        final RadioButton mask2 = dialogView.findViewById(R.id.mask2);
        mask1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mask1.setChecked(true);
                mask2.setChecked(false);
                mask_str = "Yes";
            }
        });
        mask2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mask1.setChecked(true);
                mask2.setChecked(false);
                mask_str = "Yes";
            }
        });
        final EditText provide = dialogView.findViewById(R.id.provide);
        final CheckBox mal1 = dialogView.findViewById(R.id.mal1);
        final CheckBox mal2 = dialogView.findViewById(R.id.mal2);

        final CheckBox diabet1 = dialogView.findViewById(R.id.diabet1);
        final CheckBox diabet2 = dialogView.findViewById(R.id.diabet2);
        final CheckBox hyper1 = dialogView.findViewById(R.id.hyper1);
        final CheckBox hyper2 = dialogView.findViewById(R.id.hyper2);
        final CheckBox tb = dialogView.findViewById(R.id.tb);
        final CheckBox asthma = dialogView.findViewById(R.id.asthma);
        final EditText indicate = dialogView.findViewById(R.id.indicate);
        final EditText remark = dialogView.findViewById(R.id.remark);

        diabet1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(diabet_str.equalsIgnoreCase("")){
                        diabet_str = "Severe";
                    }else {
                        diabet_str = "Severe, Acute";
                    }
                }else{
                    if(diabet_str.equalsIgnoreCase("Severe")){
                        diabet_str = "";
                    }else {
                        diabet_str = "Acute";
                    }
                }
            }
        });
        diabet2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(diabet_str.equalsIgnoreCase("")){
                        diabet_str = "Acute";
                    }else {
                        diabet_str = "Severe, Acute";
                    }
                }else{
                    if(diabet_str.equalsIgnoreCase("Acute")){
                        diabet_str = "";
                    }else {
                        diabet_str = "Severe";
                    }
                }
            }
        });

        hyper1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(hyper_str.equalsIgnoreCase("")){
                        hyper_str = "Severe";
                    }else {
                        hyper_str = "Severe, Acute";
                    }
                }else{
                    if(hyper_str.equalsIgnoreCase("Severe")){
                        hyper_str = "";
                    }else {
                        hyper_str = "Acute";
                    }
                }
            }
        });
        hyper2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(hyper_str.equalsIgnoreCase("")){
                        hyper_str = "Acute";
                    }else {
                        hyper_str = "Severe, Acute";
                    }
                }else{
                    if(hyper_str.equalsIgnoreCase("Acute")){
                        hyper_str = "";
                    }else {
                        hyper_str = "Severe";
                    }
                }
            }
        });
        mal1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(mal_str.equalsIgnoreCase("")){
                        mal_str = "Severe";
                    }else {
                        mal_str = "Severe, Acute";
                    }
                }else{
                    if(mal_str.equalsIgnoreCase("Severe")){
                        mal_str = "";
                    }else {
                        mal_str = "Acute";
                    }
                }
            }
        });
        mal2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(mal_str.equalsIgnoreCase("")){
                        mal_str = "Acute";
                    }else {
                        mal_str = "Severe, Acute";
                    }
                }else{
                    if(mal_str.equalsIgnoreCase("Acute")){
                        mal_str = "";
                    }else {
                        mal_str = "Severe";
                    }
                }
            }
        });
        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(tb_str.equalsIgnoreCase("")){
                        tb_str = "TB";
                    }else {
                        tb_str = "TB, Asthma";
                    }
                }else{
                    if(tb_str.equalsIgnoreCase("TB")){
                        tb_str = "";
                    }else {
                        tb_str = "Asthma";
                    }
                }
            }
        });
        asthma.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(tb_str.equalsIgnoreCase("")){
                        tb_str = "Asthma";
                    }else {
                        tb_str = "TB, Asthma";
                    }
                }else{
                    if(tb_str.equalsIgnoreCase("Asthma")){
                        tb_str = "";
                    }else {
                        tb_str = "TB";
                    }
                }
            }
        });



        final Button report = dialogView.findViewById(R.id.report);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (TextUtils.isEmpty(title.getText().toString())) {
                    title.setError("Please input title");
                } else if (TextUtils.isEmpty(name.getText().toString())){
                    name.setError("Please input name of person visited");
                } else if (TextUtils.isEmpty(age.getText().toString())){
                    age.setError("Please input age of person visited");
                } else if (TextUtils.isEmpty(id_num.getText().toString())){
                    id_num.setError("Please input ID Number");
                } else if (TextUtils.isEmpty(village.getText().toString())){
                    village.setError("Please input Village");
                } else if(TextUtils.isEmpty(ward.getText().toString())){
                    ward.setError("Please input Ward");
                }else if (TextUtils.isEmpty(nearname.getText().toString())){
                    nearname.setError("Please input name of nearest Health facility");
                }  else if (TextUtils.isEmpty(provide.getText().toString())){
                    provide.setError("Please input pre-existing condition");
                } else if(TextUtils.isEmpty(indicate.getText().toString())){
                    indicate.setError("Please input form of disability");
                }else if(TextUtils.isEmpty(remark.getText().toString())){
                    remark.setError("Please input remarks");
                }else{

                    if(App.isNetworkAvailable(ChvActivity.this)){
                        LinkedHashMap<String, String> map = new LinkedHashMap<>();
                        map.put("user_id", String.valueOf(user.getData().getId()));
                        map.put("title", title.getText().toString());
                        map.put("name", name.getText().toString());
                        map.put("age", age.getText().toString());
                        map.put("id_num",id_num.getText().toString());
                        map.put("nhif", nhif_str);
                        map.put("village",village.getText().toString());
                        map.put("nearname",nearname.getText().toString());
                        map.put("mask",mask_str);
                        map.put("ward",ward.getText().toString());
                        map.put("provide",provide.getText().toString());
                        map.put("mal",mal_str);
                        map.put("diabet",diabet_str);
                        map.put("hyper",hyper_str);
                        map.put("tb",tb_str);
                        map.put("indicate", indicate.getText().toString());
                        map.put("remark", remark.getText().toString());

                        progressDialog.show();


                        String url = new Api().generateUrl(Api.chv_submit, map);
                        Type type = new TypeToken<APIResponse>(){}.getType();

                        Log.d("json", url);
                        new NetworkRequest<APIResponse>().post(ChvActivity.this, type, url, (status, error) -> {
                            progressDialog.dismiss();
                            if (error != null && status == null) {
                                showError(error.getMessage());
                            } else if (status.getStatus().equals("failed")) {
                                showError(status.getMsg());
                            } else {
                                Toast.makeText(ChvActivity.this, status.getMsg(), Toast.LENGTH_LONG).show();
                                getInformationDetails();
                                officierDlg.dismiss();
                                government_txt.setTextColor(getResources().getColor(R.color.white));
                                passenger_txt.setTextColor(getResources().getColor(R.color.white_5));
                                government_line.setVisibility(View.VISIBLE);
                                passenger_line.setVisibility(View.GONE);
                                passenger_lay.setVisibility(View.GONE);
                                government_lay.setVisibility(View.VISIBLE);

                            }
                        });
                    }else{
                        Toast.makeText(ChvActivity.this,"You are working offline",Toast.LENGTH_LONG).show();
                        String created_time =  new SimpleDateFormat("MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                        String luser_id = String.valueOf(user.getData().getId());
                        String ltitle = title.getText().toString();
                        String lname = name.getText().toString();
                        String lage = age.getText().toString();
                        String lid_num = id_num.getText().toString();
                        String lnhif = nhif_str;
                        String lvillage = village.getText().toString();
                        String lnearname = nearname.getText().toString();
                        String lmask = mask_str;
                        String lward = ward.getText().toString();
                        String lprovide = provide.getText().toString();
                        String lmal = mal_str;
                        String ldiabet = diabet_str;
                        String lhyper = hyper_str;
                        String ltb = tb_str;
                        String lindicate = indicate.getText().toString();
                        String lremark = remark.getText().toString();

                        ChvVisitLocal tempdata = new ChvVisitLocal(-1, luser_id,ltitle, lname, lage, lid_num, lnhif,
                                lvillage, lnearname, lmask,lward,lprovide,lmal, ldiabet,lhyper,ltb,lindicate,lremark,created_time);


                        long id = databaseQueryClass.insertVisitData(tempdata);
                        if(id!=-1){
                            mal_str = "";diabet_str = "" ; hyper_str = ""; tb_str = "";
                            mask1.setChecked(true);
                            mask2.setChecked(false);
                            mal1.setChecked(false);
                            mal2.setChecked(false);
                            diabet1.setChecked(false);
                            diabet2.setChecked(false);
                            hyper1.setChecked(false);
                            hyper2.setChecked(false);
                            tb.setChecked(false);
                            asthma.setChecked(false);
                            title.setText("");name.setText("");age.setText("");id_num.setText("");village.setText("");nearname.setText("");
                            ward.setText("");provide.setText("");indicate.setText("");remark.setText("");
                            Toast.makeText(ChvActivity.this,"Your data is saved in your phone",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(ChvActivity.this,"Local Database error",Toast.LENGTH_LONG).show();
                        }
                    }

                }
            }
        });

        officierDlg.setCanceledOnTouchOutside(true);
        officierDlg.setCancelable(true);
        officierDlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        officierDlg.show();
    }
    private void createMotherDlg(){
        View dialogView = getLayoutInflater().inflate(R.layout.dlg_chv_mother, null);


        AlertDialog passengerdlg = new android.app.AlertDialog.Builder(ChvActivity.this)
                .setView(dialogView)
                .setCancelable(true)
                .create();

        final EditText title = dialogView.findViewById(R.id.title);
        final TextView name = dialogView.findViewById(R.id.name);
        final EditText age = dialogView.findViewById(R.id.age);
        final EditText village = dialogView.findViewById(R.id.village);
        final EditText ward = dialogView.findViewById(R.id.ward);
        final RadioButton nhif1 = dialogView.findViewById(R.id.nhif1);
        final RadioButton nhif2 = dialogView.findViewById(R.id.nhif2);
        final RadioButton clinic1 = dialogView.findViewById(R.id.clinic1);
        final RadioButton clinic2 = dialogView.findViewById(R.id.clinic2);
        nhif1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nhif1.setChecked(true);
                nhif2.setChecked(false);
                nhif_mother_str = "Yes";
            }
        });
        nhif2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nhif2.setChecked(true);
                nhif1.setChecked(false);
                nhif_mother_str = "No";
            }
        });
        clinic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clinic1.setChecked(true);
                clinic2.setChecked(false);
                clinic_str = "Yes";
            }
        });
        clinic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clinic2.setChecked(true);
                clinic1.setChecked(false);
                clinic_str = "No";
            }
        });
        final EditText phone_num = dialogView.findViewById(R.id.phone_num);
        final EditText mother_id = dialogView.findViewById(R.id.mother_id);
        final TextView due_date = dialogView.findViewById(R.id.due_date);

        due_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }catch (Exception e){
                    e.printStackTrace();
                }
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ChvActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String day = "";
                                String month = "";
                                if(dayOfMonth<9)
                                    day = "0" + dayOfMonth;
                                else
                                    day = String.valueOf(dayOfMonth);
                                if(monthOfYear+1<9)
                                    month = "0" + String.valueOf(monthOfYear+1);
                                else
                                    month = String.valueOf(monthOfYear + 1);
                                realdate = year + "-" + month + "-" + day;
                                due_date.setText(realdate);

                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });

        final RadioButton folic1 = dialogView.findViewById(R.id.folic1);
        final RadioButton folic2 = dialogView.findViewById(R.id.folic2);
        final RadioButton mary1 = dialogView.findViewById(R.id.mary1);
        final RadioButton mary2 = dialogView.findViewById(R.id.mary2);
        final RadioButton mary3 = dialogView.findViewById(R.id.mary3);
        final RadioButton mary4 = dialogView.findViewById(R.id.mary4);

        folic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                folic_str = "Yes";
            }
        });
        folic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                folic_str = "No";
            }
        });
        mary1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mary_str = "Married";
            }
        });
        mary2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mary_str = "Single";
            }
        });
        mary3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mary_str = "Divorced";
            }
        });
        mary4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mary_str = "Widowed";
            }
        });

        final EditText children = dialogView.findViewById(R.id.children);
        final EditText remark = dialogView.findViewById(R.id.remark);

        final Button report = dialogView.findViewById(R.id.report);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(title.getText().toString())) {
                    title.setError("Please input title");
                } else if(TextUtils.isEmpty(name.getText().toString())){
                    name.setError("Please input mother name");
                }else if (TextUtils.isEmpty(age.getText().toString())){
                    age.setError("Please input age of mother");
                } else if (TextUtils.isEmpty(phone_num.getText().toString())){
                    phone_num.setError("Please input Telephone Number");
                } else if (TextUtils.isEmpty(mother_id.getText().toString())){
                    mother_id.setError("Please input mother ID");
                } else if (TextUtils.isEmpty(village.getText().toString())){
                    village.setError("Please input village");
                } else if(TextUtils.isEmpty(ward.getText().toString())){
                    ward.setError("Please input ward");
                }else if (realdate.equalsIgnoreCase("")){
                    Toast.makeText(ChvActivity.this,"Please input due date",Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(children.getText().toString())){
                    children.setError("Please input children number");
                }  else if (TextUtils.isEmpty(remark.getText().toString())){
                    remark.setError("Please input remarks");
                }else{
                    if(App.isNetworkAvailable(ChvActivity.this)){
                        LinkedHashMap<String, String> map = new LinkedHashMap<>();
                        map.put("user_id", String.valueOf(user.getData().getId()));
                        map.put("title", title.getText().toString());
                        map.put("name",name.getText().toString());
                        map.put("age", age.getText().toString());
                        map.put("nhif",nhif_str);
                        map.put("mother_id",mother_id.getText().toString());
                        map.put("tel_num",phone_num.getText().toString());
                        map.put("clinic",clinic_str);
                        map.put("due_date",realdate);
                        map.put("folic",folic_str);
                        map.put("mary",mary_str);
                        map.put("village",village.getText().toString());
                        map.put("ward",ward.getText().toString());
                        map.put("children",children.getText().toString());
                        map.put("remark",remark.getText().toString());
                        progressDialog.show();


                        String url = new Api().generateUrl(Api.chv_mother_submit, map);
                        Type type = new TypeToken<APIResponse>(){}.getType();

                        Log.d("json", url);
                        new NetworkRequest<APIResponse>().post(ChvActivity.this, type, url, (status, error) -> {
                            progressDialog.dismiss();
                            if (error != null && status == null) {
                                showError(error.getMessage());
                            } else if (status.getStatus().equals("failed")) {
                                showError(status.getMsg());
                            } else {
                                Toast.makeText(ChvActivity.this, status.getMsg(), Toast.LENGTH_LONG).show();
                                getChvMotherList();
                                passengerdlg.dismiss();

                                passenger_txt.setTextColor(getResources().getColor(R.color.white));
                                government_txt.setTextColor(getResources().getColor(R.color.white_5));
                                passenger_line.setVisibility(View.VISIBLE);
                                government_line.setVisibility(View.GONE);
                                government_lay.setVisibility(View.GONE);
                                passenger_lay.setVisibility(View.VISIBLE);

                            }
                        });
                    }else{
                        Toast.makeText(ChvActivity.this,"You are working offline",Toast.LENGTH_LONG).show();
                        String created_time =  new SimpleDateFormat("MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                        String luser_id = String.valueOf(user.getData().getId());
                        String ltitle = title.getText().toString();
                        String lname = name.getText().toString();
                        String lage = age.getText().toString();
                        String lnhif = nhif_str;
                        String lmother_id = mother_id.getText().toString();
                        String ltel_num = phone_num.getText().toString();
                        String lclinic = clinic_str;
                        String ldue_date = realdate;
                        String lfolic = folic_str;
                        String lmary = mary_str;
                        String lvillage = village.getText().toString();
                        String lward = ward.getText().toString();
                        String lchildren = children.getText().toString();
                        String lremark = remark.getText().toString();
                        ChvMotherLocal tempdata = new ChvMotherLocal(-1, luser_id,ltitle, lname, lage, lnhif, lmother_id,
                                ltel_num, lclinic, ldue_date,lfolic,lmary,lvillage, lward,lchildren,lremark,created_time);

                        long id = databaseQueryClass.insertMotherData(tempdata);
                        if(id!=-1){
                            title.setText("");
                            realdate = "";due_date.setText("");clinic1.setChecked(true);clinic2.setChecked(false);nhif1.setChecked(true);nhif2.setChecked(false);
                            folic1.setChecked(true);folic2.setChecked(false);mary1.setChecked(true);mary2.setChecked(false);mary3.setChecked(false);mary4.setChecked(false);
                            name.setText("");age.setText("");mother_id.setText("");phone_num.setText("");village.setText("");ward.setText("");
                            children.setText("");remark.setText("");
                            Toast.makeText(ChvActivity.this,"Your data is saved in your phone",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(ChvActivity.this,"Local Database error",Toast.LENGTH_LONG).show();
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