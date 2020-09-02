package com.stepin.coronaapp.activities;

import android.Manifest;
import android.app.AlertDialog;
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
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.reflect.TypeToken;
import com.stepin.coronaapp.App;
import com.stepin.coronaapp.R;
import com.stepin.coronaapp.adapter.ExpandableListViewAdapter;
import com.stepin.coronaapp.api.Api;
import com.stepin.coronaapp.api.NetworkRequest;
import com.stepin.coronaapp.model.APIResponse;
import com.stepin.coronaapp.model.CheckData;
import com.stepin.coronaapp.model.ChvMotherLocal;
import com.stepin.coronaapp.model.ChvVisitLocal;
import com.stepin.coronaapp.model.EnforceLocal;
import com.stepin.coronaapp.model.GbvLocal;
import com.stepin.coronaapp.model.ReportLocal;
import com.stepin.coronaapp.model.TracingOfficer;
import com.stepin.coronaapp.model.TracingPassenger;
import com.stepin.coronaapp.model.User;
import com.stepin.coronaapp.sqlite.DatabaseQueryClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LocalActivity extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private ExpandableListViewAdapter expandableListViewAdapter;
    private List<String> listDataGroup;
    private HashMap<String, List<String>> listDataChild;
    private List<CheckData> check_list_all = new ArrayList<>();
    private List<TracingOfficer> officer_list_all = new ArrayList<>();
    private List<TracingPassenger> passenger_list_all = new ArrayList<>();
    private List<ChvVisitLocal> visit_list_all = new ArrayList<>();
    private List<ChvMotherLocal> mother_list_all = new ArrayList<>();
    private List<GbvLocal> gbv_list_all = new ArrayList<>();
    private List<EnforceLocal> enforce_list_all = new ArrayList<>();
    private List<ReportLocal> report_list_all = new ArrayList<>();
    private DatabaseQueryClass databaseQueryClass = null;
    private ProgressDialog progressDialog;
    private String type = "";
    private Bitmap selectedEnforceImage = null;
    private int RESULT_LOAD_IMG_LOCAL = 125, CAMERA_LOCAL = 126;
    User user;
    private String collection_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_local);
        databaseQueryClass = new DatabaseQueryClass(LocalActivity.this);

        // initializing the views
        initViews();

        report_list_all.clear();
        check_list_all.clear();
        officer_list_all.clear();
        passenger_list_all.clear();
        visit_list_all.clear();
        mother_list_all.clear();
        gbv_list_all.clear();
        enforce_list_all.clear();
        // initializing the listeners
        requestCameraPermission();
        initListeners();

        // initializing the objects
        initObjects();

        setProgressDialog();

        user = new User().getUser(this);
        if (user == null) { return; }
        collection_id = String.valueOf(user.getData().getCollection_id());

        Button btnSendAll = findViewById(R.id.btnSendAll);
        btnSendAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!App.isNetworkAvailable(LocalActivity.this)) {
                    Toast.makeText(LocalActivity.this, "You are working offline. You can't upload data.", Toast.LENGTH_LONG).show();
                } else {
                    if((report_list_all.size() == 0) && (check_list_all.size() == 0) && (officer_list_all.size() == 0) && (passenger_list_all.size() == 0)
                            && (visit_list_all.size() == 0) && (mother_list_all.size() == 0) && (gbv_list_all.size() == 0) && (enforce_list_all.size() == 0)) {
                        Toast.makeText(LocalActivity.this,"You can't upload data.Data is empty",Toast.LENGTH_LONG).show();
                    } else {
                        View dialogView = getLayoutInflater().inflate(R.layout.dlg_upload_all, null);
                        AlertDialog dlg = new android.app.AlertDialog.Builder(LocalActivity.this)
                                .setView(dialogView)
                                .setCancelable(true)
                                .create();

                        final TextView ok = dialogView.findViewById(R.id.ok);
                        final TextView cancel = dialogView.findViewById(R.id.cancel);
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dlg.dismiss();
                            }
                        });
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (report_list_all.size() != 0) {
                                    progressDialog.show();
                                    sendReport();
                                }

                                if (check_list_all.size() != 0) {
                                    progressDialog.show();
                                    sendCheckIn();
                                }

                                if (officer_list_all.size() != 0) {
                                    progressDialog.show();
                                    sendOfficer();
                                }

                                if (passenger_list_all.size() != 0) {
                                    try {
                                        progressDialog.show();
                                        sendPassenger();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                if (visit_list_all.size() != 0) {
                                    progressDialog.show();
                                    sendVisit();
                                }

                                if (mother_list_all.size() != 0) {
                                    progressDialog.show();
                                    sendMother();
                                }

                                if (gbv_list_all.size() != 0) {
                                    progressDialog.show();
                                    sendGbv();
                                }

                                if (enforce_list_all.size() != 0) {
                                    progressDialog.show();
                                    sendEnforce();
                                }

                                dlg.dismiss();
                            }
                        });
                        dlg.setCanceledOnTouchOutside(true);
                        dlg.setCancelable(true);
                        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dlg.show();
                    }
                }
            }
        });
    }

    public void sendReport() {
        JSONArray array = new JSONArray();
        for (int i =0; i < report_list_all.size() ; i++ ) {
            JSONObject map = new JSONObject();
            try {
                map.put("user_id", report_list_all.get(i).getUserid());
                map.put("report_type", report_list_all.get(i).getReport_type());
                map.put("symptom", report_list_all.get(i).getSyptom());
                map.put("latitude", report_list_all.get(i).getLan());
                map.put("longitude", report_list_all.get(i).getLon());
                map.put("city", report_list_all.get(i).getCity());
                map.put("state", report_list_all.get(i).getState());
                map.put("address", report_list_all.get(i).getAddress());
                map.put("country", report_list_all.get(i).getCountry());
                map.put("additional_info", report_list_all.get(i).getAddition());
                map.put("collection_id", collection_id);

                array.put(map);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(App.isNetworkAvailable(LocalActivity.this)){
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            map.put("report_data", array.toString());

            String url = new Api().generateUrl(Api.report_all, map);
            Type type = new TypeToken<APIResponse>(){}.getType();

            new NetworkRequest<APIResponse>().post(LocalActivity.this, type, url, (status, error) -> {
                progressDialog.dismiss();
                if (error != null && status == null) {
                    showError(error.getMessage());
                } else if (status.getStatus().equals("failed")) {
                    showError(status.getMsg());
                } else {
                    Toast.makeText(LocalActivity.this, status.getMsg(), Toast.LENGTH_LONG).show();
                    databaseQueryClass.deleteReportAll();
                    expandableListViewAdapter.reportAllRemoved(listDataGroup.get(0));
                    report_list_all.clear();
                    expandableListViewAdapter.notifyDataSetChanged();
                }
            });
        } else {
            Toast.makeText(LocalActivity.this,"You are working offline.You can't upload data.",Toast.LENGTH_LONG).show();
        }
    }

    private void sendCheckIn() {
        JSONArray array = new JSONArray();
        for (int i =0; i < check_list_all.size() ; i++ ) {
            JSONObject map = new JSONObject();
            try {
                map.put("peaple", check_list_all.get(i).getPeople());
                map.put("time", check_list_all.get(i).getTime());
                map.put("utilities", check_list_all.get(i).getUtil());
                map.put("additional_info", check_list_all.get(i).getAddition());
                map.put("city", check_list_all.get(i).getCity());
                map.put("state", check_list_all.get(i).getState());
                map.put("address", check_list_all.get(i).getAddress());
                map.put("country", check_list_all.get(i).getCountry());
                map.put("user_id", check_list_all.get(i).getUser_id());
                map.put("longitude", check_list_all.get(i).getLon());
                map.put("latitude", check_list_all.get(i).getLan());
                array.put(map);
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(App.isNetworkAvailable(LocalActivity.this)){
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            map.put("report_data", array.toString());

            String url = new Api().generateUrl(Api.checkin_all, map);
            Type type = new TypeToken<APIResponse>(){}.getType();

            Log.d("json", url);
            new NetworkRequest<APIResponse>().post(LocalActivity.this, type, url, (status, error) -> {
                progressDialog.dismiss();
                if (error != null && status == null) {
                    showError(error.getMessage());
                } else if (status.getStatus().equals("failed")) {
                    showError(status.getMsg());
                } else {
                    Toast.makeText(LocalActivity.this, status.getMsg(), Toast.LENGTH_LONG).show();
                    databaseQueryClass.deleteCheckAll();
                    expandableListViewAdapter.reportAllRemoved(listDataGroup.get(1));
                    check_list_all.clear();
                    expandableListViewAdapter.notifyDataSetChanged();
                }
            });
        } else {
            Toast.makeText(LocalActivity.this,"You are working offline.You can't upload data.",Toast.LENGTH_LONG).show();
        }
    }

    private void sendOfficer() {
        JSONArray array = new JSONArray();
        for (int i =0; i < officer_list_all.size() ; i++ ) {
            JSONObject map = new JSONObject();
            try {
                map.put("user_id", officer_list_all.get(i).getUser_id_local());
                map.put("name", officer_list_all.get(i).getLocal_name());
                map.put("title", officer_list_all.get(i).getLocal_title());
                map.put("card_num", officer_list_all.get(i).getLocal_card_num());
                map.put("service_type",officer_list_all.get(i).getService_type());
                map.put("tel_num",officer_list_all.get(i).getLocal_tel_num());
                map.put("home_address",officer_list_all.get(i).getLocal_home_address());
                map.put("reg_num",officer_list_all.get(i).getLocal_reg_num());
                map.put("destination",officer_list_all.get(i).getLocal_destination());
                map.put("date", officer_list_all.get(i).getLocal_date());
                array.put(map);
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(App.isNetworkAvailable(LocalActivity.this)){
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            map.put("report_data", array.toString());

            String url = new Api().generateUrl(Api.office_all, map);
            Type type = new TypeToken<APIResponse>(){}.getType();

            new NetworkRequest<APIResponse>().post(LocalActivity.this, type, url, (status, error) -> {
                progressDialog.dismiss();
                if (error != null && status == null) {
                    showError(error.getMessage());
                } else if (status.getStatus().equals("failed")) {
                    showError(status.getMsg());
                } else {
                    Toast.makeText(LocalActivity.this, status.getMsg(), Toast.LENGTH_LONG).show();
                    databaseQueryClass.deleteOfficeAll();
                    expandableListViewAdapter.reportAllRemoved(listDataGroup.get(3));
                    officer_list_all.clear();
                    expandableListViewAdapter.notifyDataSetChanged();

                }
            });
        } else {
            Toast.makeText(LocalActivity.this,"You are working offline.You can't upload data.",Toast.LENGTH_LONG).show();
        }
    }

    private void sendPassenger() throws JSONException {
        int length = passenger_list_all.size() / 10;
        JSONArray jsonArray = new JSONArray();
        for (int j = 0; j <= length; j++) {
            JSONArray array = new JSONArray();
            JSONObject map = new JSONObject();

            for (int k = 0; k < 10; k++) {
                int i = j * 10 + k;

                if (i == passenger_list_all.size()) {
                    break;
                }
                try {
                    map = new JSONObject();

                    map.put("user_id", passenger_list_all.get(i).getUser_id());
                    map.put("name", passenger_list_all.get(i).getPassenger_name());
                    map.put("id_num", passenger_list_all.get(i).getId_num());
                    map.put("temp", passenger_list_all.get(i).getTemp());
                    map.put("title", passenger_list_all.get(i).getTracing_title());
                    map.put("phone_num", passenger_list_all.get(i).getTel_number());
                    map.put("vehicle_num", passenger_list_all.get(i).getVehicle_num());
                    map.put("seat_num", passenger_list_all.get(i).getSeat_num());
                    map.put("from_village", passenger_list_all.get(i).getFrom_village());
                    map.put("to_village", passenger_list_all.get(i).getTo_village());
                    map.put("location", passenger_list_all.get(i).getLocation());
                    map.put("date", passenger_list_all.get(i).getPublish_date());
                    map.put("infect_str", passenger_list_all.get(i).getInfect_str());
                    map.put("history_last", passenger_list_all.get(i).getHistory_last());
                    map.put("contact", passenger_list_all.get(i).getContact());
                    map.put("contact_num", passenger_list_all.get(i).getContact_num());
                    map.put("collection_id", collection_id);

                    array.put(map);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            jsonArray.put(array);
        }

        if(App.isNetworkAvailable(LocalActivity.this)) {
            for (int i = 0; i < jsonArray.length(); i ++) {
                LinkedHashMap<String, String> map = new LinkedHashMap<>();
                map.put("report_data", jsonArray.get(i).toString());

                String url = new Api().generateUrl(Api.passenger_all, map);
                Type type = new TypeToken<APIResponse>() {}.getType();

                int finalI = i;
                new NetworkRequest<APIResponse>().post(LocalActivity.this, type, url, (status, error) -> {
                    progressDialog.dismiss();
                    if (error != null && status == null) {
                        Toast.makeText(LocalActivity.this, ""+error.getMessage(), Toast.LENGTH_LONG).show();
                    } else if (status.getStatus().equals("failed")) {
                        Toast.makeText(LocalActivity.this, "Tracing Passenger Data "+status.getMsg(), Toast.LENGTH_LONG).show();
                    } else {
                        if(finalI == jsonArray.length()-1) {
                            Toast.makeText(LocalActivity.this, status.getMsg(), Toast.LENGTH_LONG).show();
                        }
                    }

                    databaseQueryClass.deletePassengerAll();
                    expandableListViewAdapter.reportAllRemoved(listDataGroup.get(4));
                    passenger_list_all.clear();
                    expandableListViewAdapter.notifyDataSetChanged();
                });
            }
        } else {
            Toast.makeText(LocalActivity.this,"You are working offline.You can't upload data.",Toast.LENGTH_LONG).show();
        }
    }

    private void sendVisit() {
        JSONArray array = new JSONArray();
        for (int i =0; i < visit_list_all.size() ; i++ ) {
            JSONObject map = new JSONObject();
            try {
                map.put("user_id", visit_list_all.get(i).getUser_id());
                map.put("title", visit_list_all.get(i).getTitle());
                map.put("name",visit_list_all.get(i).getName());
                map.put("age", visit_list_all.get(i).getAge());
                map.put("id_num",visit_list_all.get(i).getId_num());
                map.put("nhif", visit_list_all.get(i).getNhif());
                map.put("village",visit_list_all.get(i).getVillage());
                map.put("nearname",visit_list_all.get(i).getVillage());
                map.put("mask",visit_list_all.get(i).getMask());
                map.put("ward",visit_list_all.get(i).getWard());
                map.put("provide",visit_list_all.get(i).getProvide());
                map.put("mal",visit_list_all.get(i).getMal());
                map.put("diabet",visit_list_all.get(i).getDiabet());
                map.put("hyper",visit_list_all.get(i).getHyper());
                map.put("tb",visit_list_all.get(i).getTb());
                map.put("indicate", visit_list_all.get(i).getIndicate());
                map.put("remark", visit_list_all.get(i).getRemark());

                array.put(map);
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(App.isNetworkAvailable(LocalActivity.this)){
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            map.put("report_data", array.toString());


            String url = new Api().generateUrl(Api.visit_all, map);
            Type type = new TypeToken<APIResponse>(){}.getType();

            Log.d("json", url);
            new NetworkRequest<APIResponse>().post(LocalActivity.this, type, url, (status, error) -> {
                progressDialog.dismiss();
                if (error != null && status == null) {
                    showError(error.getMessage());
                } else if (status.getStatus().equals("failed")) {
                    showError(status.getMsg());
                } else {
                    Toast.makeText(LocalActivity.this, status.getMsg(), Toast.LENGTH_LONG).show();
                    databaseQueryClass.deleteVisitAll();
                    expandableListViewAdapter.reportAllRemoved(listDataGroup.get(5));
                    visit_list_all.clear();
                    expandableListViewAdapter.notifyDataSetChanged();
                }
            });


        }else{
            Toast.makeText(LocalActivity.this,"You are working offline.You can't upload data.",Toast.LENGTH_LONG).show();
        }
    }

    private void sendMother() {
        JSONArray array = new JSONArray();
        for (int i =0; i < mother_list_all.size() ; i++ ) {
            JSONObject map = new JSONObject();
            try {
                map.put("user_id", mother_list_all.get(i).getUser_id());
                map.put("title", mother_list_all.get(i).getTitle());
                map.put("name",mother_list_all.get(i).getAge());
                map.put("age", mother_list_all.get(i).getAge());
                map.put("nhif",mother_list_all.get(i).getNhif());
                map.put("mother_id",mother_list_all.get(i).getMother_id());
                map.put("tel_num",mother_list_all.get(i).getTel_num());
                map.put("clinic",mother_list_all.get(i).getClinic());
                map.put("due_date",mother_list_all.get(i).getDue_date());
                map.put("folic",mother_list_all.get(i).getFolic());
                map.put("mary",mother_list_all.get(i).getMary());
                map.put("village",mother_list_all.get(i).getWard());
                map.put("ward",mother_list_all.get(i).getUser_id());
                map.put("children",mother_list_all.get(i).getChildren());
                map.put("remark",mother_list_all.get(i).getRemark());

                array.put(map);
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(App.isNetworkAvailable(LocalActivity.this)){
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            map.put("report_data", array.toString());


            String url = new Api().generateUrl(Api.mother_all, map);
            Type type = new TypeToken<APIResponse>(){}.getType();

            Log.d("json", url);
            new NetworkRequest<APIResponse>().post(LocalActivity.this, type, url, (status, error) -> {
                progressDialog.dismiss();
                if (error != null && status == null) {
                    showError(error.getMessage());
                } else if (status.getStatus().equals("failed")) {
                    showError(status.getMsg());
                } else {
                    Toast.makeText(LocalActivity.this, status.getMsg(), Toast.LENGTH_LONG).show();
                    databaseQueryClass.deleteMotherAll();
                    expandableListViewAdapter.reportAllRemoved(listDataGroup.get(6));
                    mother_list_all.clear();
                    expandableListViewAdapter.notifyDataSetChanged();
                }
            });


        }else{
            Toast.makeText(LocalActivity.this,"You are working offline.You can't upload data.",Toast.LENGTH_LONG).show();
        }
    }

    private void sendGbv() {
        JSONArray array = new JSONArray();
        for (int i =0; i < gbv_list_all.size() ; i++ ) {
            JSONObject map = new JSONObject();
            try {
                map.put("user_id", gbv_list_all.get(i).getUser_id());
                map.put("county",gbv_list_all.get(i).getCounty());
                map.put("title",gbv_list_all.get(i).getTitle());
                map.put("nature", gbv_list_all.get(i).getNature());
                map.put("gender",gbv_list_all.get(i).getGender());
                map.put("age",gbv_list_all.get(i).getAge());
                map.put("report_date", gbv_list_all.get(i).getReport_date());
                map.put("status", gbv_list_all.get(i).getStatus());
                map.put("report_place",gbv_list_all.get(i).getReport_place());
                map.put("remark",gbv_list_all.get(i).getRemark());

                array.put(map);
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(App.isNetworkAvailable(LocalActivity.this)){
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            map.put("report_data", array.toString());


            String url = new Api().generateUrl(Api.gbv_all, map);
            Type type = new TypeToken<APIResponse>(){}.getType();

            Log.d("json", url);
            new NetworkRequest<APIResponse>().post(LocalActivity.this, type, url, (status, error) -> {
                progressDialog.dismiss();
                if (error != null && status == null) {
                    showError(error.getMessage());
                } else if (status.getStatus().equals("failed")) {
                    showError(status.getMsg());
                } else {
                    Toast.makeText(LocalActivity.this, status.getMsg(), Toast.LENGTH_LONG).show();
                    databaseQueryClass.deleteGbvAll();
                    expandableListViewAdapter.reportAllRemoved(listDataGroup.get(7));
                    gbv_list_all.clear();
                    expandableListViewAdapter.notifyDataSetChanged();

                }
            });


        }else{
            Toast.makeText(LocalActivity.this,"You are working offline.You can't upload data.",Toast.LENGTH_LONG).show();
        }
    }

    private void sendEnforce() {
        JSONArray array = new JSONArray();
        for (int i =0; i < enforce_list_all.size() ; i++ ) {
            JSONObject map = new JSONObject();
            try {
                map.put("user_id", enforce_list_all.get(i).getUser_id());
                map.put("enforce_type", enforce_list_all.get(i).getUser_id());
                map.put("title",enforce_list_all.get(i).getTitle());
                map.put("description", enforce_list_all.get(i).getDescription());
                map.put("file_type","image");
                array.put(map);
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(App.isNetworkAvailable(LocalActivity.this)){
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            map.put("report_data", array.toString());


            String url = new Api().generateUrl(Api.enforce_all, map);
            Type type = new TypeToken<APIResponse>(){}.getType();

            Log.d("json", url);
            new NetworkRequest<APIResponse>().post(LocalActivity.this, type, url, (status, error) -> {
                progressDialog.dismiss();
                if (error != null && status == null) {
                    showError(error.getMessage());
                } else if (status.getStatus().equals("failed")) {
                    showError(status.getMsg());
                } else {
                    Toast.makeText(LocalActivity.this, status.getMsg(), Toast.LENGTH_LONG).show();
                    databaseQueryClass.deleteEnforceAll();
                    expandableListViewAdapter.reportAllRemoved(listDataGroup.get(2));
                    enforce_list_all.clear();
                    expandableListViewAdapter.notifyDataSetChanged();

                }
            });


        }else{
            Toast.makeText(LocalActivity.this,"You are working offline.You can't upload data.",Toast.LENGTH_LONG).show();
        }
    }

    private void initViews() {
        expandableListView = findViewById(R.id.expandableListView);
    }

    private void setProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Adding Information ...");
    }

    private void showError(String message) {
        new AlertDialog.Builder(this).setMessage(message)
                .setPositiveButton("Ok", (dialog, which) -> { })
                .show();
    }

    private void initListeners() {
        // ExpandableListView on child click listener
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if(listDataGroup.get(groupPosition).equalsIgnoreCase("Report")) {
                    View dialogView = getLayoutInflater().inflate(R.layout.dlg_report_local, null);

                    AlertDialog emailDlg = new android.app.AlertDialog.Builder(LocalActivity.this)
                            .setView(dialogView)
                            .setCancelable(true)
                            .create();

                    final TextView additional = dialogView.findViewById(R.id.additional);
                    final TextView symptom = dialogView.findViewById(R.id.symptom);
                    final Button report = dialogView.findViewById(R.id.report);
                    final Button photo = dialogView.findViewById(R.id.photo);
                    final Button gallery = dialogView.findViewById(R.id.gallery);

                    try {
                        ArrayList<String>list = new ArrayList<>();

                        additional.setText(report_list_all.get(childPosition).getAddition());
                        String[] a = report_list_all.get(childPosition).getSyptom().split(",");
                        for(int i = 0; i < a.length; i++){
                            if(a[i].equalsIgnoreCase("1")){
                                list.add("Running Nose");
                            }else if(a[i].equalsIgnoreCase("2")){
                                list.add("Coughing");
                            }else if(a[i].equalsIgnoreCase("3")){
                                list.add("Fever");
                            }else if(a[i].equalsIgnoreCase("4")){
                                list.add("Sore Throat");
                            }else if(a[i].equalsIgnoreCase("5")){
                                list.add("Sneezing");
                            }else if(a[i].equalsIgnoreCase("6")){
                                list.add("Headache");
                            }else if(a[i].equalsIgnoreCase("7")){
                                list.add("Short Breath");
                            }else if(a[i].equalsIgnoreCase("8")){
                                list.add("Int'l Arrival");
                            }
                        }
                        String symptom_str = TextUtils.join(",", list);

                        symptom.setText(symptom_str);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    report.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(App.isNetworkAvailable(LocalActivity.this)){
                                if(selectedEnforceImage == null){
                                    Toast.makeText(LocalActivity.this,"Please select image",Toast.LENGTH_LONG).show();
                                } else {
                                    LinkedHashMap<String, String> map = new LinkedHashMap<>();
                                    map.put("user_id", report_list_all.get(childPosition).getUserid());
                                    map.put("report_type", report_list_all.get(childPosition).getReport_type());
                                    map.put("symptom", report_list_all.get(childPosition).getSyptom());
                                    map.put("latitude", report_list_all.get(childPosition).getLan());
                                    map.put("longitude", report_list_all.get(childPosition).getLon());
                                    map.put("city", report_list_all.get(childPosition).getCity());
                                    map.put("state", report_list_all.get(childPosition).getState());
                                    map.put("address", report_list_all.get(childPosition).getAddress());
                                    map.put("country", report_list_all.get(childPosition).getCountry());
                                    map.put("additional_info", report_list_all.get(childPosition).getAddition());

                                    String url = new Api().generateUrl(Api.report, map);
                                    Type type = new TypeToken<APIResponse>(){}.getType();

                                    progressDialog.show();

                                    new NetworkRequest<APIResponse>().uploadImage(LocalActivity.this, type, selectedEnforceImage, url, Api.reportImage, (status, error) -> {
                                        progressDialog.dismiss();
                                        if (error != null && status == null) {
                                            showError(error.getMessage());
                                        } else if (status.getStatus().equals("failed")) {
                                            showError(status.getMsg());
                                        } else {
                                            Toast.makeText(LocalActivity.this, status.getMsg(), Toast.LENGTH_LONG).show();
                                            emailDlg.dismiss();
                                            databaseQueryClass.deleteReportTable(report_list_all.get(childPosition).getId());
                                            selectedEnforceImage = null;
                                            expandableListViewAdapter.notifyDataSetChanged();
                                            expandableListViewAdapter.reportitemRemoved(listDataGroup.get(0), report_list_all, childPosition);
                                            report_list_all.remove(childPosition);
                                        }
                                    });
                                }

                            } else {
                                Toast.makeText(LocalActivity.this,"You are working offline.You can't upload data.",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    photo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            photoBtnTapped();
                        }
                    });
                    gallery.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            browseBtnTapped();
                        }
                    });
                    emailDlg.setCanceledOnTouchOutside(true);
                    emailDlg.setCancelable(true);
                    emailDlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    emailDlg.show();
                } else if(listDataGroup.get(groupPosition).equalsIgnoreCase("Check In")) {
                    View dialogView = getLayoutInflater().inflate(R.layout.dlg_check_item, null);
                    AlertDialog dlg = new android.app.AlertDialog.Builder(LocalActivity.this)
                            .setView(dialogView)
                            .setCancelable(true)
                            .create();
                    final TextView people = dialogView.findViewById(R.id.people);
                    final TextView time = dialogView.findViewById(R.id.time);
                    final TextView util = dialogView.findViewById(R.id.util);
                    final TextView addition = dialogView.findViewById(R.id.addition);
                    try {
                        people.setText(check_list_all.get(childPosition).getPeople());
                        time.setText(check_list_all.get(childPosition).getTime());
                        util.setText(check_list_all.get(childPosition).getUtil());
                        addition.setText(check_list_all.get(childPosition).getAddition());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    final Button submit = dialogView.findViewById(R.id.submit);

                    LinkedHashMap<String, String> map = new LinkedHashMap<>();
                    map.put("peaple", check_list_all.get(childPosition).getPeople());
                    map.put("time", check_list_all.get(childPosition).getTime());
                    map.put("utilities", check_list_all.get(childPosition).getUtil());
                    map.put("additional_info", check_list_all.get(childPosition).getAddition());
                    map.put("city", check_list_all.get(childPosition).getCity());
                    map.put("state", check_list_all.get(childPosition).getState());
                    map.put("address", check_list_all.get(childPosition).getAddress());
                    map.put("country", check_list_all.get(childPosition).getCountry());
                    map.put("user_id", check_list_all.get(childPosition).getUser_id());
                    map.put("longitude", check_list_all.get(childPosition).getLon());
                    map.put("latitude", check_list_all.get(childPosition).getLan());

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(App.isNetworkAvailable(LocalActivity.this)){
                                String url = new Api().generateUrl(Api.checkIn, map);
                                Type type = new TypeToken<APIResponse>(){}.getType();

                                Log.d("json", url);
                                progressDialog.show();

                                new NetworkRequest<APIResponse>().post(LocalActivity.this, type, url, (status, error) -> {
                                    progressDialog.dismiss();
                                    if (error != null && status == null) {
                                        showError(error.getMessage());
                                    } else if (status.getStatus().equals("failed")) {
                                        showError(status.getMsg());
                                    } else {
                                        Toast.makeText(LocalActivity.this, status.getMsg(), Toast.LENGTH_LONG).show();
                                        databaseQueryClass.deleteCheckTable(check_list_all.get(childPosition).getId());
                                        expandableListViewAdapter.notifyDataSetChanged();
                                        expandableListViewAdapter.checkitemRemoved(listDataGroup.get(1), check_list_all, childPosition);
                                        check_list_all.remove(childPosition);
                                        dlg.dismiss();
                                    }
                                });
                            }else{
                                Toast.makeText(LocalActivity.this,"You are working offline.You can't upload data.",Toast.LENGTH_LONG).show();
                            }


                        }
                    });
                    dlg.setCanceledOnTouchOutside(true);
                    dlg.setCancelable(true);
                    dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dlg.show();

                } else if(listDataGroup.get(groupPosition).equalsIgnoreCase("Enforcement")) {
                    View dialogView = getLayoutInflater().inflate(R.layout.dlg_enforce_local, null);

                    AlertDialog emailDlg = new android.app.AlertDialog.Builder(LocalActivity.this)
                            .setView(dialogView)
                            .setCancelable(true)
                            .create();

                    final TextView title = dialogView.findViewById(R.id.title);
                    final TextView description = dialogView.findViewById(R.id.description);
                    final TextView type = dialogView.findViewById(R.id.type);
                    final Button report = dialogView.findViewById(R.id.report);
                    final Button photo = dialogView.findViewById(R.id.photo);
                    final Button gallery = dialogView.findViewById(R.id.gallery);

                    try {
                        title.setText(enforce_list_all.get(childPosition).getTitle());
                        String str_type = enforce_list_all.get(childPosition).getEnforce_type();
                        if(str_type.equalsIgnoreCase("1")){
                            str_type = "People gathering in large numbers";
                        }else if(str_type.equalsIgnoreCase("2")){
                            str_type = "People not following orders";
                        }else if(str_type.equalsIgnoreCase("3")){
                            str_type = "People not wearing face mask";
                        }else if(str_type.equalsIgnoreCase("4")){
                            str_type = "People not observing social distance";
                        }

                        type.setText(str_type);
                        description.setText(enforce_list_all.get(childPosition).getDescription());
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    report.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (TextUtils.isEmpty(title.getText().toString())) {
                                title.setError("Please enter title");
                            } else if (TextUtils.isEmpty(description.getText().toString())){
                                description.setError("Please enter description");
                            } else if(selectedEnforceImage == null){
                                Toast.makeText(LocalActivity.this,"Please select image",Toast.LENGTH_LONG).show();
                            }else{
                                if(App.isNetworkAvailable(LocalActivity.this)){
                                    LinkedHashMap<String, String> map = new LinkedHashMap<>();
                                    map.put("user_id", enforce_list_all.get(childPosition).getUser_id());
                                    map.put("enforce_type", enforce_list_all.get(childPosition).getUser_id());
                                    map.put("title",enforce_list_all.get(childPosition).getTitle());
                                    map.put("description", enforce_list_all.get(childPosition).getDescription());
                                    map.put("file_type","image");

                                    String url = new Api().generateUrl(Api.enforce, map);
                                    Type type = new TypeToken<APIResponse>(){}.getType();


                                    progressDialog.show();

                                    new NetworkRequest<APIResponse>().uploadImage(LocalActivity.this, type, selectedEnforceImage, url, Api.reportImage, (status, error) -> {
                                        progressDialog.dismiss();
                                        if (error != null && status == null) {
                                            showError(error.getMessage());
                                        } else if (status.getStatus().equals("failed")) {
                                            showError(status.getMsg());
                                        } else {
                                            Toast.makeText(LocalActivity.this, status.getMsg(), Toast.LENGTH_LONG).show();
                                            emailDlg.dismiss();
                                            databaseQueryClass.deleteEnforceTable(enforce_list_all.get(childPosition).getId());
                                            expandableListViewAdapter.notifyDataSetChanged();
                                            expandableListViewAdapter.enforceitemRemoved(listDataGroup.get(2), enforce_list_all, childPosition);
                                            enforce_list_all.remove(childPosition);
                                            selectedEnforceImage = null;
                                        }
                                    });
                                }else{
                                    Toast.makeText(LocalActivity.this,"You are working offline.You can't upload data.",Toast.LENGTH_LONG).show();
                                }


                            }
                        }
                    });
                    photo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            photoBtnTapped();
                        }
                    });
                    gallery.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            browseBtnTapped();
                        }
                    });
                    emailDlg.setCanceledOnTouchOutside(true);
                    emailDlg.setCancelable(true);
                    emailDlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    emailDlg.show();
                } else if(listDataGroup.get(groupPosition).equalsIgnoreCase("Tracing Government Offices")) {
                    View dialogView = getLayoutInflater().inflate(R.layout.dlg_officer_local, null);
                    AlertDialog dlg = new android.app.AlertDialog.Builder(LocalActivity.this)
                            .setView(dialogView)
                            .setCancelable(true)
                            .create();

                    final TextView title = dialogView.findViewById(R.id.title);
                    final TextView card_num = dialogView.findViewById(R.id.card_num);
                    final TextView service_type = dialogView.findViewById(R.id.service_type);
                    final TextView tel_num = dialogView.findViewById(R.id.tel_num);
                    final TextView address = dialogView.findViewById(R.id.address);
                    final TextView reg_num = dialogView.findViewById(R.id.reg_num);
                    final TextView destination = dialogView.findViewById(R.id.destination);
                    try {
                        title.setText(officer_list_all.get(childPosition).getLocal_title());
                        card_num.setText(officer_list_all.get(childPosition).getLocal_card_num());
                        service_type.setText(officer_list_all.get(childPosition).getService_type());
                        tel_num.setText(officer_list_all.get(childPosition).getLocal_tel_num());
                        address.setText(officer_list_all.get(childPosition).getLocal_home_address());
                        reg_num.setText(officer_list_all.get(childPosition).getLocal_reg_num());
                        destination.setText(officer_list_all.get(childPosition).getLocal_destination());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    final Button submit = dialogView.findViewById(R.id.submit);

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                if(App.isNetworkAvailable(LocalActivity.this)){
                                    LinkedHashMap<String, String> map = new LinkedHashMap<>();
                                    map.put("user_id", officer_list_all.get(childPosition).getUser_id_local());
                                    map.put("name", officer_list_all.get(childPosition).getLocal_name());
                                    map.put("title", officer_list_all.get(childPosition).getLocal_title());
                                    map.put("card_num", officer_list_all.get(childPosition).getLocal_card_num());
                                    map.put("service_type",officer_list_all.get(childPosition).getService_type());
                                    map.put("tel_num",officer_list_all.get(childPosition).getLocal_tel_num());
                                    map.put("home_address",officer_list_all.get(childPosition).getLocal_home_address());
                                    map.put("reg_num",officer_list_all.get(childPosition).getLocal_reg_num());
                                    map.put("destination",officer_list_all.get(childPosition).getLocal_destination());
                                    map.put("date", officer_list_all.get(childPosition).getLocal_date());
                                    progressDialog.show();

                                    String url = new Api().generateUrl(Api.tracing_submit, map);
                                    Type type = new TypeToken<APIResponse>(){}.getType();

                                    new NetworkRequest<APIResponse>().post(LocalActivity.this, type, url, (status, error) -> {
                                        progressDialog.dismiss();
                                        if (error != null && status == null) {
                                            showError(error.getMessage());
                                        } else if (status.getStatus().equals("failed")) {
                                            showError(status.getMsg());
                                        } else {
                                            Toast.makeText(LocalActivity.this, status.getMsg(), Toast.LENGTH_LONG).show();
                                            databaseQueryClass.deleteOfficerTable(officer_list_all.get(childPosition).getId());
                                            expandableListViewAdapter.notifyDataSetChanged();
                                            expandableListViewAdapter.officeritemRemoved(listDataGroup.get(3), officer_list_all, childPosition);
                                            officer_list_all.remove(childPosition);
                                            dlg.dismiss();

                                        }
                                    });
                            }else{
                                Toast.makeText(LocalActivity.this,"You are working offline.You can't upload data.",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    dlg.setCanceledOnTouchOutside(true);
                    dlg.setCancelable(true);
                    dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dlg.show();
                } else if(listDataGroup.get(groupPosition).equalsIgnoreCase("Tracing Passengers")) {
                    View dialogView = getLayoutInflater().inflate(R.layout.dlg_passenger_local, null);
                    AlertDialog dlg = new android.app.AlertDialog.Builder(LocalActivity.this)
                            .setView(dialogView)
                            .setCancelable(true)
                            .create();

                    final TextView title = dialogView.findViewById(R.id.title);
                    final TextView vehicle_num = dialogView.findViewById(R.id.vehicle_num);
                    final TextView tel_num = dialogView.findViewById(R.id.phone_num);
                    final TextView seat_num = dialogView.findViewById(R.id.seat_num);
                    final TextView from_village = dialogView.findViewById(R.id.from_village);
                    final TextView to_village = dialogView.findViewById(R.id.to_village);
                    final TextView location = dialogView.findViewById(R.id.location);
                    final TextView contact = dialogView.findViewById(R.id.contact);
                    final TextView contact_num = dialogView.findViewById(R.id.contact_num);
                    final TextView id_num = dialogView.findViewById(R.id.id_num);
                    final TextView history = dialogView.findViewById(R.id.history);
                    final TextView infect = dialogView.findViewById(R.id.infect);
                    final TextView temp = dialogView.findViewById(R.id.temp);
                    try {
                        title.setText(passenger_list_all.get(childPosition).getTracing_title());
                        tel_num.setText(passenger_list_all.get(childPosition).getTel_number());
                        vehicle_num.setText(passenger_list_all.get(childPosition).getVehicle_num());
                        seat_num.setText(passenger_list_all.get(childPosition).getSeat_num());
                        from_village.setText(passenger_list_all.get(childPosition).getFrom_village());
                        to_village.setText(passenger_list_all.get(childPosition).getTo_village());
                        location.setText(passenger_list_all.get(childPosition).getLocation());
                        contact.setText(passenger_list_all.get(childPosition).getContact());
                        contact_num.setText(passenger_list_all.get(childPosition).getContact_num());
                        id_num.setText(passenger_list_all.get(childPosition).getId_num());
                        history.setText(passenger_list_all.get(childPosition).getHistory_last());
                        infect.setText(passenger_list_all.get(childPosition).getInfect_str());
                        temp.setText(passenger_list_all.get(childPosition).getTemp());

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    final Button submit = dialogView.findViewById(R.id.submit);

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(App.isNetworkAvailable(LocalActivity.this)){
                                LinkedHashMap<String, String> map = new LinkedHashMap<>();
                                map.put("user_id", passenger_list_all.get(childPosition).getUser_id());
                                map.put("name", passenger_list_all.get(childPosition).getPassenger_name());
                                map.put("id_num",passenger_list_all.get(childPosition).getId_num());
                                map.put("temp",passenger_list_all.get(childPosition).getTemp());
                                map.put("title", passenger_list_all.get(childPosition).getTracing_title());
                                map.put("phone_num", passenger_list_all.get(childPosition).getTel_number());
                                map.put("vehicle_num",passenger_list_all.get(childPosition).getVehicle_num());
                                map.put("seat_num",passenger_list_all.get(childPosition).getSeat_num());
                                map.put("from_village",passenger_list_all.get(childPosition).getFrom_village());
                                map.put("to_village",passenger_list_all.get(childPosition).getTo_village());
                                map.put("location",passenger_list_all.get(childPosition).getLocation());
                                map.put("date", passenger_list_all.get(childPosition).getPublish_date());
                                map.put("infect_str",passenger_list_all.get(childPosition).getInfect_str());
                                map.put("history_last",passenger_list_all.get(childPosition).getHistory_last());
                                map.put("contact",passenger_list_all.get(childPosition).getContact());
                                map.put("contact_num",passenger_list_all.get(childPosition).getContact_num());


                                String url = new Api().generateUrl(Api.tracing_passenger_submit, map);
                                Type type = new TypeToken<APIResponse>(){}.getType();

                                Log.d("json", url);
                                new NetworkRequest<APIResponse>().post(LocalActivity.this, type, url, (status, error) -> {
                                    progressDialog.dismiss();
                                    if (error != null && status == null) {
                                        showError(error.getMessage());
                                    } else if (status.getStatus().equals("failed")) {
                                        showError(status.getMsg());
                                    } else {
                                        Toast.makeText(LocalActivity.this, status.getMsg(), Toast.LENGTH_LONG).show();
                                        databaseQueryClass.deletePassengerTable(passenger_list_all.get(childPosition).getId());
                                        expandableListViewAdapter.notifyDataSetChanged();
                                        expandableListViewAdapter.passengeritemRemoved(listDataGroup.get(4), passenger_list_all, childPosition);
                                        passenger_list_all.remove(childPosition);
                                        dlg.dismiss();

                                    }
                                });


                            }else{
                                Toast.makeText(LocalActivity.this,"You are working offline.You can't upload data.",Toast.LENGTH_LONG).show();
                            }


                        }
                    });

                    dlg.setCanceledOnTouchOutside(true);
                    dlg.setCancelable(true);
                    dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dlg.show();
                } else if(listDataGroup.get(groupPosition).equalsIgnoreCase("CHV Household")) {
                    View dialogView = getLayoutInflater().inflate(R.layout.dlg_visit_local, null);
                    AlertDialog dlg = new android.app.AlertDialog.Builder(LocalActivity.this)
                            .setView(dialogView)
                            .setCancelable(true)
                            .create();

                    final TextView title = dialogView.findViewById(R.id.title);
                    final TextView name = dialogView.findViewById(R.id.name);
                    final TextView age = dialogView.findViewById(R.id.age);
                    final TextView nhif = dialogView.findViewById(R.id.nhif);
                    final TextView village = dialogView.findViewById(R.id.village);
                    final TextView ward = dialogView.findViewById(R.id.ward);
                    final TextView nearname = dialogView.findViewById(R.id.nearname);
                    final TextView mask = dialogView.findViewById(R.id.mask);
                    final TextView provide = dialogView.findViewById(R.id.provide);
                    final TextView mal = dialogView.findViewById(R.id.mal);
                    final TextView diabet = dialogView.findViewById(R.id.diabet);
                    final TextView hyper = dialogView.findViewById(R.id.hyper);
                    final TextView tb = dialogView.findViewById(R.id.tb);
                    final TextView indicate = dialogView.findViewById(R.id.indicate);
                    final TextView remark = dialogView.findViewById(R.id.remark);
                    final TextView id_num = dialogView.findViewById(R.id.id_num);

                    try {
                        title.setText(visit_list_all.get(childPosition).getTitle());
                        name.setText(visit_list_all.get(childPosition).getName());
                        age.setText(visit_list_all.get(childPosition).getAge());
                        nhif.setText(visit_list_all.get(childPosition).getNhif());
                        village.setText(visit_list_all.get(childPosition).getVillage());
                        nearname.setText(visit_list_all.get(childPosition).getNearname());
                        mask.setText(visit_list_all.get(childPosition).getMask());
                        provide.setText(visit_list_all.get(childPosition).getProvide());
                        mal.setText(visit_list_all.get(childPosition).getMal());
                        diabet.setText(visit_list_all.get(childPosition).getDiabet());
                        hyper.setText(visit_list_all.get(childPosition).getHyper());
                        tb.setText(visit_list_all.get(childPosition).getTb());
                        indicate.setText(visit_list_all.get(childPosition).getIndicate());
                        remark.setText(visit_list_all.get(childPosition).getRemark());
                        id_num.setText(visit_list_all.get(childPosition).getId_num());
                        ward.setText(visit_list_all.get(childPosition).getWard());

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    final Button submit = dialogView.findViewById(R.id.submit);

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(App.isNetworkAvailable(LocalActivity.this)){
                                LinkedHashMap<String, String> map = new LinkedHashMap<>();
                                map.put("user_id", visit_list_all.get(childPosition).getUser_id());
                                map.put("title", visit_list_all.get(childPosition).getTitle());
                                map.put("name",visit_list_all.get(childPosition).getName());
                                map.put("age", visit_list_all.get(childPosition).getAge());
                                map.put("id_num",visit_list_all.get(childPosition).getId_num());
                                map.put("nhif", visit_list_all.get(childPosition).getNhif());
                                map.put("village",visit_list_all.get(childPosition).getVillage());
                                map.put("nearname",visit_list_all.get(childPosition).getVillage());
                                map.put("mask",visit_list_all.get(childPosition).getMask());
                                map.put("ward",visit_list_all.get(childPosition).getWard());
                                map.put("provide",visit_list_all.get(childPosition).getProvide());
                                map.put("mal",visit_list_all.get(childPosition).getMal());
                                map.put("diabet",visit_list_all.get(childPosition).getDiabet());
                                map.put("hyper",visit_list_all.get(childPosition).getHyper());
                                map.put("tb",visit_list_all.get(childPosition).getTb());
                                map.put("indicate", visit_list_all.get(childPosition).getIndicate());
                                map.put("remark", visit_list_all.get(childPosition).getRemark());

                                String url = new Api().generateUrl(Api.chv_submit, map);
                                Type type = new TypeToken<APIResponse>(){}.getType();

                                Log.d("json", url);
                                new NetworkRequest<APIResponse>().post(LocalActivity.this, type, url, (status, error) -> {
                                    progressDialog.dismiss();
                                    if (error != null && status == null) {
                                        showError(error.getMessage());
                                    } else if (status.getStatus().equals("failed")) {
                                        showError(status.getMsg());
                                    } else {
                                        Toast.makeText(LocalActivity.this, status.getMsg(), Toast.LENGTH_LONG).show();
                                        databaseQueryClass.deleteVisitTable(visit_list_all.get(childPosition).getId());
                                        expandableListViewAdapter.notifyDataSetChanged();
                                        expandableListViewAdapter.visititemRemoved(listDataGroup.get(5), visit_list_all, childPosition);
                                        visit_list_all.remove(childPosition);
                                        dlg.dismiss();

                                    }
                                });


                            }else{
                                Toast.makeText(LocalActivity.this,"You are working offline.You can't upload data.",Toast.LENGTH_LONG).show();
                            }


                        }
                    });

                    dlg.setCanceledOnTouchOutside(true);
                    dlg.setCancelable(true);
                    dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dlg.show();
                } else if(listDataGroup.get(groupPosition).equalsIgnoreCase("CHV Mother")) {
                    View dialogView = getLayoutInflater().inflate(R.layout.dlg_mother_local, null);
                    AlertDialog dlg = new android.app.AlertDialog.Builder(LocalActivity.this)
                            .setView(dialogView)
                            .setCancelable(true)
                            .create();

                    final TextView title = dialogView.findViewById(R.id.title);
                    final TextView name = dialogView.findViewById(R.id.name);
                    final TextView tel_num = dialogView.findViewById(R.id.tel_num);
                    final TextView age = dialogView.findViewById(R.id.age);
                    final TextView nhif = dialogView.findViewById(R.id.nhif);
                    final TextView mother_id = dialogView.findViewById(R.id.mother_id);
                    final TextView village = dialogView.findViewById(R.id.village);
                    final TextView ward = dialogView.findViewById(R.id.ward);
                    final TextView due_date = dialogView.findViewById(R.id.due_date);
                    final TextView folic = dialogView.findViewById(R.id.folic);
                    final TextView mary = dialogView.findViewById(R.id.mary);
                    final TextView children = dialogView.findViewById(R.id.children);
                    final TextView remark = dialogView.findViewById(R.id.remark);
                    final RadioButton clinic1 = dialogView.findViewById(R.id.clinic1);
                    final RadioButton clinic2 = dialogView.findViewById(R.id.clinic2);
                    try {
                        String clinic_str = mother_list_all.get(childPosition).getClinic();
                        if(clinic_str.equalsIgnoreCase("Yes")){
                            clinic1.setChecked(true);
                            clinic2.setChecked(false);
                        }else{
                            clinic1.setChecked(false);
                            clinic2.setChecked(true);
                        }
                        title.setText(mother_list_all.get(childPosition).getTitle());
                        tel_num.setText(mother_list_all.get(childPosition).getTel_num());
                        name.setText(mother_list_all.get(childPosition).getName());
                        age.setText(mother_list_all.get(childPosition).getAge());
                        nhif.setText(mother_list_all.get(childPosition).getNhif());
                        mother_id.setText(mother_list_all.get(childPosition).getMother_id());
                        due_date.setText(mother_list_all.get(childPosition).getDue_date());
                        folic.setText(mother_list_all.get(childPosition).getFolic());
                        mary.setText(mother_list_all.get(childPosition).getMary());
                        children.setText(mother_list_all.get(childPosition).getChildren());
                        remark.setText(mother_list_all.get(childPosition).getRemark());
                        village.setText(mother_list_all.get(childPosition).getVillage());
                        ward.setText(mother_list_all.get(childPosition).getWard());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    final Button submit = dialogView.findViewById(R.id.submit);

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(App.isNetworkAvailable(LocalActivity.this)){
                                LinkedHashMap<String, String> map = new LinkedHashMap<>();
                                map.put("user_id", mother_list_all.get(childPosition).getUser_id());
                                map.put("title", mother_list_all.get(childPosition).getTitle());
                                map.put("name",mother_list_all.get(childPosition).getAge());
                                map.put("age", mother_list_all.get(childPosition).getAge());
                                map.put("nhif",mother_list_all.get(childPosition).getNhif());
                                map.put("mother_id",mother_list_all.get(childPosition).getMother_id());
                                map.put("tel_num",mother_list_all.get(childPosition).getTel_num());
                                map.put("clinic",mother_list_all.get(childPosition).getClinic());
                                map.put("due_date",mother_list_all.get(childPosition).getDue_date());
                                map.put("folic",mother_list_all.get(childPosition).getFolic());
                                map.put("mary",mother_list_all.get(childPosition).getMary());
                                map.put("village",mother_list_all.get(childPosition).getWard());
                                map.put("ward",mother_list_all.get(childPosition).getUser_id());
                                map.put("children",mother_list_all.get(childPosition).getChildren());
                                map.put("remark",mother_list_all.get(childPosition).getRemark());


                                String url = new Api().generateUrl(Api.chv_mother_submit, map);
                                Type type = new TypeToken<APIResponse>(){}.getType();

                                Log.d("json", url);
                                new NetworkRequest<APIResponse>().post(LocalActivity.this, type, url, (status, error) -> {
                                    progressDialog.dismiss();
                                    if (error != null && status == null) {
                                        showError(error.getMessage());
                                    } else if (status.getStatus().equals("failed")) {
                                        showError(status.getMsg());
                                    } else {
                                        Toast.makeText(LocalActivity.this, status.getMsg(), Toast.LENGTH_LONG).show();
                                        databaseQueryClass.deleteMotherTable(mother_list_all.get(childPosition).getId());
                                        expandableListViewAdapter.notifyDataSetChanged();
                                        expandableListViewAdapter.motheritemRemoved(listDataGroup.get(6), mother_list_all, childPosition);
                                        mother_list_all.remove(childPosition);
                                        dlg.dismiss();

                                    }
                                });


                            }else{
                                Toast.makeText(LocalActivity.this,"You are working offline.You can't upload data.",Toast.LENGTH_LONG).show();
                            }


                        }
                    });

                    dlg.setCanceledOnTouchOutside(true);
                    dlg.setCancelable(true);
                    dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dlg.show();
            } else if(listDataGroup.get(groupPosition).equalsIgnoreCase("GBV")) {
                    View dialogView = getLayoutInflater().inflate(R.layout.dlg_gbv_local, null);
                    AlertDialog dlg = new android.app.AlertDialog.Builder(LocalActivity.this)
                            .setView(dialogView)
                            .setCancelable(true)
                            .create();

                    final TextView title = dialogView.findViewById(R.id.title);
                    final TextView county = dialogView.findViewById(R.id.county);
                    final TextView gender = dialogView.findViewById(R.id.gender);
                    final TextView age = dialogView.findViewById(R.id.age);
                    final TextView date = dialogView.findViewById(R.id.date);
                    final TextView place = dialogView.findViewById(R.id.place);
                    final TextView status = dialogView.findViewById(R.id.status);
                    final TextView remark = dialogView.findViewById(R.id.remark);
                    final TextView nature = dialogView.findViewById(R.id.nature);
                    try {
                        title.setText(gbv_list_all.get(childPosition).getTitle());
                        county.setText(gbv_list_all.get(childPosition).getCounty());
                        gender.setText(gbv_list_all.get(childPosition).getGender());
                        age.setText(gbv_list_all.get(childPosition).getAge());
                        date.setText(gbv_list_all.get(childPosition).getReport_date());
                        place.setText(gbv_list_all.get(childPosition).getReport_place());
                        status.setText(gbv_list_all.get(childPosition).getStatus());
                        remark.setText(gbv_list_all.get(childPosition).getRemark());
                        nature.setText(gbv_list_all.get(childPosition).getNature());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    final Button submit = dialogView.findViewById(R.id.submit);

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(App.isNetworkAvailable(LocalActivity.this)){
                                LinkedHashMap<String, String> map = new LinkedHashMap<>();
                                map.put("user_id", gbv_list_all.get(childPosition).getUser_id());
                                map.put("county",gbv_list_all.get(childPosition).getCounty());
                                map.put("title",gbv_list_all.get(childPosition).getTitle());
                                map.put("nature", gbv_list_all.get(childPosition).getNature());
                                map.put("gender",gbv_list_all.get(childPosition).getGender());
                                map.put("age",gbv_list_all.get(childPosition).getAge());
                                map.put("report_date", gbv_list_all.get(childPosition).getReport_date());
                                map.put("status", gbv_list_all.get(childPosition).getStatus());
                                map.put("report_place",gbv_list_all.get(childPosition).getReport_place());
                                map.put("remark",gbv_list_all.get(childPosition).getRemark());


                                String url = new Api().generateUrl(Api.gbv_submit, map);
                                Type type = new TypeToken<APIResponse>(){}.getType();

                                Log.d("json", url);
                                new NetworkRequest<APIResponse>().post(LocalActivity.this, type, url, (status, error) -> {
                                    progressDialog.dismiss();
                                    if (error != null && status == null) {
                                        showError(error.getMessage());
                                    } else if (status.getStatus().equals("failed")) {
                                        showError(status.getMsg());
                                    } else {
                                        Toast.makeText(LocalActivity.this, status.getMsg(), Toast.LENGTH_LONG).show();
                                        databaseQueryClass.deleteGbvTable(gbv_list_all.get(childPosition).getId());
                                        expandableListViewAdapter.notifyDataSetChanged();
                                        expandableListViewAdapter.gbvitemRemoved(listDataGroup.get(7), gbv_list_all, childPosition);
                                        gbv_list_all.remove(childPosition);
                                        dlg.dismiss();

                                    }
                                });


                            }else{
                                Toast.makeText(LocalActivity.this,"You are working offline.You can't upload data.",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    dlg.setCanceledOnTouchOutside(true);
                    dlg.setCancelable(true);
                    dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dlg.show();
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            listDataGroup.get(groupPosition) + " : "
                                    + listDataChild.get(listDataGroup.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT)
                            .show();
                }
                return false;
            }
        });

        // ExpandableListView Group expanded listener
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (!App.isNetworkAvailable(LocalActivity.this)){
                    Toast.makeText(LocalActivity.this,"You are working offline. You can't upload data.",Toast.LENGTH_LONG).show();
                } else {
                    if(groupPosition == 0 && report_list_all.size() !=0 ){
                        View dialogView = getLayoutInflater().inflate(R.layout.dlg_upload_all, null);
                        AlertDialog dlg = new android.app.AlertDialog.Builder(LocalActivity.this)
                                .setView(dialogView)
                                .setCancelable(true)
                                .create();

                        final TextView ok = dialogView.findViewById(R.id.ok);
                        final TextView cancel = dialogView.findViewById(R.id.cancel);
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dlg.dismiss();
                            }
                        });
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dlg.dismiss();
                                progressDialog.show();
                                sendReport();
                            }
                        });
                        dlg.setCanceledOnTouchOutside(true);
                        dlg.setCancelable(true);
                        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dlg.show();
                    } else if(groupPosition == 1&& check_list_all.size()!=0){
                        View dialogView = getLayoutInflater().inflate(R.layout.dlg_upload_all, null);
                        AlertDialog dlg = new android.app.AlertDialog.Builder(LocalActivity.this)
                                .setView(dialogView)
                                .setCancelable(true)
                                .create();

                        final TextView ok = dialogView.findViewById(R.id.ok);
                        final TextView cancel = dialogView.findViewById(R.id.cancel);
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dlg.dismiss();
                            }
                        });
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendCheckIn();
                                progressDialog.show();
                                dlg.dismiss();
                            }
                        });
                        dlg.setCanceledOnTouchOutside(true);
                        dlg.setCancelable(true);
                        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dlg.show();
                    }else if(groupPosition == 2&& enforce_list_all.size()!=0){
                        View dialogView = getLayoutInflater().inflate(R.layout.dlg_upload_all, null);
                        AlertDialog dlg = new android.app.AlertDialog.Builder(LocalActivity.this)
                                .setView(dialogView)
                                .setCancelable(true)
                                .create();

                        final TextView ok = dialogView.findViewById(R.id.ok);
                        final TextView cancel = dialogView.findViewById(R.id.cancel);
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dlg.dismiss();
                            }
                        });
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendEnforce();
                                progressDialog.show();
                                dlg.dismiss();
                            }
                        });
                        dlg.setCanceledOnTouchOutside(true);
                        dlg.setCancelable(true);
                        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dlg.show();
                    }else if(groupPosition == 3&& officer_list_all.size()!=0){
                        View dialogView = getLayoutInflater().inflate(R.layout.dlg_upload_all, null);
                        AlertDialog dlg = new android.app.AlertDialog.Builder(LocalActivity.this)
                                .setView(dialogView)
                                .setCancelable(true)
                                .create();

                        final TextView ok = dialogView.findViewById(R.id.ok);
                        final TextView cancel = dialogView.findViewById(R.id.cancel);
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dlg.dismiss();
                            }
                        });
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendOfficer();
                                progressDialog.show();
                                dlg.dismiss();
                            }
                        });
                        dlg.setCanceledOnTouchOutside(true);
                        dlg.setCancelable(true);
                        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dlg.show();
                    }else if(groupPosition == 4&& passenger_list_all.size()!=0){
                        View dialogView = getLayoutInflater().inflate(R.layout.dlg_upload_all, null);
                        AlertDialog dlg = new android.app.AlertDialog.Builder(LocalActivity.this)
                                .setView(dialogView)
                                .setCancelable(true)
                                .create();

                        final TextView ok = dialogView.findViewById(R.id.ok);
                        final TextView cancel = dialogView.findViewById(R.id.cancel);
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dlg.dismiss();
                            }
                        });
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    sendPassenger();
                                    progressDialog.show();
                                    dlg.dismiss();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        dlg.setCanceledOnTouchOutside(true);
                        dlg.setCancelable(true);
                        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dlg.show();
                    }else if(groupPosition == 5&& visit_list_all.size()!=0){
                        View dialogView = getLayoutInflater().inflate(R.layout.dlg_upload_all, null);
                        AlertDialog dlg = new android.app.AlertDialog.Builder(LocalActivity.this)
                                .setView(dialogView)
                                .setCancelable(true)
                                .create();

                        final TextView ok = dialogView.findViewById(R.id.ok);
                        final TextView cancel = dialogView.findViewById(R.id.cancel);
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dlg.dismiss();
                            }
                        });
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendVisit();
                                progressDialog.show();
                                dlg.dismiss();
                            }
                        });
                        dlg.setCanceledOnTouchOutside(true);
                        dlg.setCancelable(true);
                        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dlg.show();
                    }else if(groupPosition == 6&& mother_list_all.size()!=0){
                        View dialogView = getLayoutInflater().inflate(R.layout.dlg_upload_all, null);
                        AlertDialog dlg = new android.app.AlertDialog.Builder(LocalActivity.this)
                                .setView(dialogView)
                                .setCancelable(true)
                                .create();

                        final TextView ok = dialogView.findViewById(R.id.ok);
                        final TextView cancel = dialogView.findViewById(R.id.cancel);
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dlg.dismiss();
                            }
                        });
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendMother();
                                progressDialog.show();
                                dlg.dismiss();
                            }
                        });
                        dlg.setCanceledOnTouchOutside(true);
                        dlg.setCancelable(true);
                        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dlg.show();
                    }else if(groupPosition == 7&& gbv_list_all.size()!=0){
                        View dialogView = getLayoutInflater().inflate(R.layout.dlg_upload_all, null);
                        AlertDialog dlg = new android.app.AlertDialog.Builder(LocalActivity.this)
                                .setView(dialogView)
                                .setCancelable(true)
                                .create();

                        final TextView ok = dialogView.findViewById(R.id.ok);
                        final TextView cancel = dialogView.findViewById(R.id.cancel);
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dlg.dismiss();
                            }
                        });
                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendGbv();
                                progressDialog.show();
                                dlg.dismiss();
                            }
                        });
                        dlg.setCanceledOnTouchOutside(true);
                        dlg.setCancelable(true);
                        dlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dlg.show();
                    }else{
                        Toast.makeText(LocalActivity.this,"You can't upload data.Data is empty",Toast.LENGTH_LONG).show();
                    }
                }


            }
        });

        // ExpandableListView Group collapsed listener
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataGroup.get(groupPosition) + " " + "selected",
//                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initObjects() {
        // initializing the list of groups
        listDataGroup = new ArrayList<>();
        // initializing the list of child
        listDataChild = new HashMap<>();
        initListData();
    }

    private void initListData() {
        report_list_all.addAll(databaseQueryClass.getReportData());
        check_list_all.addAll(databaseQueryClass.getCheckData());
        officer_list_all.addAll(databaseQueryClass.getOfficerData());
        passenger_list_all.addAll(databaseQueryClass.getPassengerData());
        visit_list_all.addAll(databaseQueryClass.getVisitData());
        mother_list_all.addAll(databaseQueryClass.getMotherData());
        gbv_list_all.addAll(databaseQueryClass.getGbvData());
        enforce_list_all.addAll(databaseQueryClass.getEnforceData());

        // Adding group data
        listDataGroup.add(getString(R.string.report));
        listDataGroup.add(getString(R.string.checkin));
        listDataGroup.add(getString(R.string.enforcement));
        listDataGroup.add(getString(R.string.tracing_gover));
        listDataGroup.add(getString(R.string.tracing_passenger));
        listDataGroup.add(getString(R.string.chv_house));
        listDataGroup.add(getString(R.string.chv_mother));
        listDataGroup.add(getString(R.string.gbv));

        // array of strings
        String[] array;

        // list of alcohol
        List<String> report_list = new ArrayList<>();
        for(int i = 0; i < report_list_all.size(); i ++){
            report_list.add(report_list_all.get(i).getCreated());
        }

        List<String> check_list = new ArrayList<>();
        for(int i = 0; i < check_list_all.size(); i ++){
            check_list.add(check_list_all.get(i).getCreated_time());
        }
        List<String> enforce_list = new ArrayList<>();
        for(int i = 0; i < enforce_list_all.size(); i ++){
            enforce_list.add(enforce_list_all.get(i).getCreated());
        }

        List<String> officer_list = new ArrayList<>();
        for(int i = 0; i < officer_list_all.size(); i ++){
            officer_list.add(officer_list_all.get(i).getCreated_time());
        }
        List<String> passenger_list = new ArrayList<>();
        for(int i = 0; i < passenger_list_all.size(); i ++){
            passenger_list.add(passenger_list_all.get(i).getCreated());
        }

        List<String> visit_list = new ArrayList<>();
        for(int i = 0; i < visit_list_all.size(); i ++){
            visit_list.add(visit_list_all.get(i).getCreated_time());
        }
        List<String> mother_list = new ArrayList<>();
        for(int i = 0; i < mother_list_all.size(); i ++){
            mother_list.add(mother_list_all.get(i).getCreated_time());
        }

        List<String> gbv_list = new ArrayList<>();
        for(int i = 0; i < gbv_list_all.size(); i ++){
            gbv_list.add(gbv_list_all.get(i).getCreated());
        }

        // Adding child data
        listDataChild.put(listDataGroup.get(0), report_list);
        listDataChild.put(listDataGroup.get(1), check_list);
        listDataChild.put(listDataGroup.get(2), enforce_list);
        listDataChild.put(listDataGroup.get(3), officer_list);
        listDataChild.put(listDataGroup.get(4), passenger_list);
        listDataChild.put(listDataGroup.get(5), visit_list);
        listDataChild.put(listDataGroup.get(6), mother_list);
        listDataChild.put(listDataGroup.get(7), gbv_list);

        // notify the adapter
        expandableListViewAdapter = new ExpandableListViewAdapter(this, listDataGroup, listDataChild);

        // setting list adapter
        expandableListView.setAdapter(expandableListViewAdapter);
        expandableListViewAdapter.notifyDataSetChanged();

    }

    public void backBtnTapped(View view){
        finish();
    }

    private void browseBtnTapped() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[] {"image/*", "video/*"});
        startActivityForResult(intent, RESULT_LOAD_IMG_LOCAL);
    }

    private void requestCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
            }
        }
    }

    private void photoBtnTapped() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_LOCAL);
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
            }
        } else {
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA_LOCAL);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == RESULT_LOAD_IMG_LOCAL) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                selectedEnforceImage = BitmapFactory.decodeStream(imageStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong while picking the image", Toast.LENGTH_LONG).show();
            }
        } else if (resultCode == RESULT_OK && requestCode == CAMERA_LOCAL) {
            selectedEnforceImage = (Bitmap) data.getExtras().get("data");
        }
    }
}
