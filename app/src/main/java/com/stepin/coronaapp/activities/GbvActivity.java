package com.stepin.coronaapp.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stepin.coronaapp.App;
import com.stepin.coronaapp.R;
import com.stepin.coronaapp.adapter.EnforceAdapter;
import com.stepin.coronaapp.adapter.GbvAdapter;
import com.stepin.coronaapp.api.Api;
import com.stepin.coronaapp.api.NetworkRequest;
import com.stepin.coronaapp.model.APIResponse;
import com.stepin.coronaapp.model.ChvVisitLocal;
import com.stepin.coronaapp.model.EnforceListResponse;
import com.stepin.coronaapp.model.GbvListResponse;
import com.stepin.coronaapp.model.GbvLocal;
import com.stepin.coronaapp.model.State;
import com.stepin.coronaapp.model.User;
import com.stepin.coronaapp.model.Utils;
import com.stepin.coronaapp.sqlite.DatabaseQueryClass;

import java.io.FileNotFoundException;
import java.io.InputStream;
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

public class GbvActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private LinearLayout add_btn ;
    private ProgressDialog progressDialog;

    ListView informationListView;
    GbvAdapter adapter;

    ArrayList<GbvListResponse.Data> items;
    User user;
    String realdate = "";
    private int mYear,mMonth,mDay;
    private DatabaseQueryClass databaseQueryClass = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gbv);

        databaseQueryClass = new DatabaseQueryClass(GbvActivity.this);

        user = new User().getUser(this);
        add_btn = findViewById(R.id.add_btn);
        items = new ArrayList<>();
        informationListView = findViewById(R.id.informationListView);
        adapter = new GbvAdapter(this, items);
        informationListView.setAdapter(adapter);
        informationListView.setOnItemClickListener(this);

        setProgressDialog();
        getInformationDetails();
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
        if(App.isNetworkAvailable(GbvActivity.this)){
            String url = new Api().generateUrl(Api.gbv_list, new LinkedHashMap<>());
            Type type = new TypeToken<GbvListResponse>(){}.getType();

            new NetworkRequest<GbvListResponse>().fetch(this, type, url, (obj, error) -> {
                if (error == null && obj != null) {
                    items = (ArrayList<GbvListResponse.Data>) obj.getGbvList();
                    adapter.updateItems(items);
                }
            });
        }else{
            Toast.makeText(GbvActivity.this,"You are working offline",Toast.LENGTH_LONG).show();
        }

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String title = items.get(position).getTitle();
        String county = items.get(position).getCounty();
        String nature = items.get(position).getNature();
        String gender = items.get(position).getGender();
        String age = items.get(position).getAge();
        String report_date = items.get(position).getReport_date();
        String report_place = items.get(position).getReport_place();
        String status = items.get(position).getStatus();
        String officer_name = items.get(position).getOfficer_name();
        String remark = items.get(position).getRemark();

        Intent intent = new Intent(GbvActivity.this,GbvDetailActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("county",county);
        intent.putExtra("nature",nature);
        intent.putExtra("gender",gender);
        intent.putExtra("age",age);
        intent.putExtra("report_date",report_date);
        intent.putExtra("report_place",report_place);
        intent.putExtra("status",status);
        intent.putExtra("officer_name",officer_name);
        intent.putExtra("remark",remark);
        startActivity(intent);

    }

    private void createAddDlg(){
        View dialogView = getLayoutInflater().inflate(R.layout.dlg_gbv, null);


        AlertDialog gbvDlg = new android.app.AlertDialog.Builder(GbvActivity.this)
                .setView(dialogView)
                .setCancelable(true)
                .create();

        final EditText title = dialogView.findViewById(R.id.title);
        final EditText nature = dialogView.findViewById(R.id.nature);
        final Button report = dialogView.findViewById(R.id.report);
        final Spinner county = dialogView.findViewById(R.id.county);
        final Spinner genderSpinner = dialogView.findViewById(R.id.genderSpinner);
        final Spinner ageSpinner = dialogView.findViewById(R.id.ageSpinner);
        final TextView report_date = dialogView.findViewById(R.id.report_date);
        final EditText county_txt = dialogView.findViewById(R.id.county_txt);
        final EditText age_txt = dialogView.findViewById(R.id.age);
        final EditText gender_txt = dialogView.findViewById(R.id.gender);
        final EditText status = dialogView.findViewById(R.id.status);
        final EditText report_place = dialogView.findViewById(R.id.report_place);
        final EditText remark = dialogView.findViewById(R.id.remark);


        report_date.setOnClickListener(new View.OnClickListener() {
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(GbvActivity.this,
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
                                report_date.setText(realdate);

                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });


        String citiesJson = new Utils().inputStreamToString(getResources().openRawResource(R.raw.cities));
        Type listType = new TypeToken<List<State>>(){}.getType();
        List<State> states = new Gson().fromJson(citiesJson, listType);

        List<String> categories = new ArrayList<>(Collections.singletonList("County"));
        for (State state: states) { categories.add(state.getName()); }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        county.setAdapter(dataAdapter);

        county.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                county_txt.setText(parent.getItemAtPosition(position).toString());
                ((TextView) parent.getChildAt(0)).setTextColor(Color.TRANSPARENT);

                if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("County")) {
                    county_txt.setTextColor(getResources().getColor(R.color.light_gray));
                } else {
                    county_txt.setTextColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<String> ages = new ArrayList<>(Collections.singletonList("Age"));
        for (int i=0;i<120;i++) { ages.add(""+i); }

        ArrayAdapter<String> ageAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ages);
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(ageAdapter);

        ageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                age_txt.setText(parent.getItemAtPosition(position).toString());
                ((TextView) parent.getChildAt(0)).setTextColor(Color.TRANSPARENT);

                if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Age")) {
                    age_txt.setTextColor(getResources().getColor(R.color.light_gray));
                } else {
                    age_txt.setTextColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        List<String> genders = new ArrayList<>(Arrays.asList("Gender", "Male", "Female", "Other"));

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genders);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender_txt.setText(parent.getItemAtPosition(position).toString());
                ((TextView) parent.getChildAt(0)).setTextColor(Color.TRANSPARENT);

                if (parent.getItemAtPosition(position).toString().equalsIgnoreCase("Gender")) {
                    gender_txt.setTextColor(getResources().getColor(R.color.light_gray));
                } else {
                    gender_txt.setTextColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(title.getText().toString())){
                  title.setError("Please input title");
                } else if (TextUtils.isEmpty(county_txt.getText().toString())) {
                    county_txt.setError("Please select county");
                }else if (TextUtils.isEmpty(nature.getText().toString())) {
                    nature.setError("Please input nature");
                }else if(TextUtils.isEmpty(gender_txt.getText().toString())){
                    gender_txt.setError("Please select gender");
                }else if(TextUtils.isEmpty(age_txt.getText().toString())){
                    age_txt.setError("Please select age");
                }else if (TextUtils.isEmpty(realdate)){
                    Toast.makeText(GbvActivity.this,"Please input date",Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(status.getText().toString())){
                    status.setError("Please input status");
                } else if (TextUtils.isEmpty(report_place.getText().toString())){
                    report_place.setError("Please input place");
                } else if (TextUtils.isEmpty(remark.getText().toString())){
                    remark.setError("Please input remarks");
                }else{

                    if(App.isNetworkAvailable(GbvActivity.this)){
                        LinkedHashMap<String, String> map = new LinkedHashMap<>();
                        map.put("user_id", String.valueOf(user.getData().getId()));
                        map.put("county",county_txt.getText().toString());
                        map.put("title",title.getText().toString());
                        map.put("nature", nature.getText().toString());
                        map.put("gender",gender_txt.getText().toString());
                        map.put("age",age_txt.getText().toString());
                        map.put("report_date", realdate);
                        map.put("status", status.getText().toString());
                        map.put("report_place",report_place.getText().toString());
                        map.put("remark",remark.getText().toString());
                        progressDialog.show();


                        String url = new Api().generateUrl(Api.gbv_submit, map);
                        Type type = new TypeToken<APIResponse>(){}.getType();

                        Log.d("json", url);
                        new NetworkRequest<APIResponse>().post(GbvActivity.this, type, url, (status, error) -> {
                            progressDialog.dismiss();
                            if (error != null && status == null) {
                                showError(error.getMessage());
                            } else if (status.getStatus().equals("failed")) {
                                showError(status.getMsg());
                            } else {
                                Toast.makeText(GbvActivity.this, status.getMsg(), Toast.LENGTH_LONG).show();
                                getInformationDetails();
                                gbvDlg.dismiss();
                            }
                        });
                    }else{
                        Toast.makeText(GbvActivity.this,"You are working offline",Toast.LENGTH_LONG).show();
                        String created_time =  new SimpleDateFormat("MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                        String luser_id = String.valueOf(user.getData().getId());
                        String lcounty = county_txt.getText().toString();
                        String ltitle = title.getText().toString();
                        String lnature = nature.getText().toString();
                        String gender = gender_txt.getText().toString();
                        String lage = age_txt.getText().toString();
                        String report_dates = realdate;
                        String lstatus = status.getText().toString();
                        String lreport_place = report_place.getText().toString();
                        String lremark = remark.getText().toString();

                        GbvLocal tempdata = new GbvLocal(-1, luser_id, ltitle, lcounty,  gender, lage,report_dates,lstatus, lreport_place, lremark,lnature,created_time);

                        long id = databaseQueryClass.insertGbvData(tempdata);
                        if(id!=-1){
                            county_txt.setText("Country");
                            title.setText("");
                            nature.setText("");
                            gender_txt.setText("Gender");
                            age_txt.setText("Age");
                            status.setText("");
                            report_date.setText("");
                            realdate = "";
                            report_place.setText("");
                            remark.setText("");
                            Toast.makeText(GbvActivity.this,"Your data is saved in your phone",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(GbvActivity.this,"Local Database error",Toast.LENGTH_LONG).show();
                        }
                    }





                }
            }
        });

        gbvDlg.setCanceledOnTouchOutside(true);
        gbvDlg.setCancelable(true);
        gbvDlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        gbvDlg.show();


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