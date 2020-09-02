package com.stepin.coronaapp.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.stepin.coronaapp.App;
import com.stepin.coronaapp.R;
import com.stepin.coronaapp.adapter.RiskAdapter;
import com.stepin.coronaapp.api.Api;
import com.stepin.coronaapp.api.NetworkRequest;
import com.stepin.coronaapp.model.InformationCenterRiskData;
import com.stepin.coronaapp.model.User;
import com.stepin.coronaapp.model.Utils;
import com.google.gson.reflect.TypeToken;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class IamAtRiskActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Button view2,noButton;
    TextView yesTxt,noTxt;
    ListView riskListView;

    RiskAdapter adapter;
    ArrayList<InformationCenterRiskData.Data> items;
    ProgressBar bar;
    Button riskBtn;

    private FusedLocationProviderClient mFusedLocationClient;
    private LatLng latLng = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iam_at_risk);

        riskBtn = findViewById(R.id.button4);
        bar = findViewById(R.id.riskProgressBar);
        view2 = findViewById(R.id.view2);
        noButton = findViewById(R.id.view);
        yesTxt = findViewById(R.id.yes);
        noTxt = findViewById(R.id.no);

        setUserInformation();
        setListViewAndAdapter();
        getRiskData();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            currentLocation();
        }
    }

    private void setUserInformation() {
        TextView nameTxt = findViewById(R.id.textView14);
        TextView timeTxt = findViewById(R.id.textView15);

        User user = new User().getUser(this);
        timeTxt.setText(user.getData().getState() + " " + new Utils().getTime());

        String sourceString = "Welcome " + "<b>" + user.getData().getFirstName() + " " + user.getData().getLastName() + "</b> ";
        nameTxt.setText(Html.fromHtml(sourceString));
    }

    private void currentLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location == null) { return; }
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
        });
    }

    private void setListViewAndAdapter() {
        riskListView = findViewById(R.id.riskListView);
        riskListView.bringToFront();
        riskBtn.bringToFront();

        items = new ArrayList<>();
        adapter = new RiskAdapter(this, items);
        riskListView.setAdapter(adapter);
        riskListView.setOnItemClickListener(this);
    }

    private void showError(String message) {
        new AlertDialog.Builder(this).setMessage(message)
                .setPositiveButton("Ok", (dialog, which) -> { })
                .show();
    }

    private void getRiskData() {
        if(App.isNetworkAvailable(IamAtRiskActivity.this)){
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            map.put("information_center_id", "1");

            String url = new Api().generateUrl(Api.risk, map);
            Type type = new TypeToken<InformationCenterRiskData>(){}.getType();

            new NetworkRequest<InformationCenterRiskData>().fetch(this, type, url, (obj, error) -> {
                bar.setVisibility(View.GONE);
                if (error != null && obj == null) {
                    showError(error.getMessage());
                } else if (obj.getStatus().equals("failed")) {
                    showError(obj.getMsg());
                } else {
                    adapter.updateData((ArrayList<InformationCenterRiskData.Data>) obj.getRiskData());
                }
            });
        }else{
            Toast.makeText(IamAtRiskActivity.this,"You are working offline",Toast.LENGTH_LONG).show();
        }


    }


    public void btnTappedyes(View view) {
        view2.setVisibility(View.VISIBLE);
        noButton.setVisibility(View.INVISIBLE);
    }

    public void btntappedNo(View view) {
        view2.setVisibility(View.INVISIBLE);
        noButton.setVisibility(View.VISIBLE);
    }

    public void closeRisk(View view) {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private double getInch() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        double wi=(double)width/(double)dm.xdpi;
        double hi=(double)height/(double)dm.ydpi;
        double x = Math.pow(wi,2);
        double y = Math.pow(hi,2);
        return Math.sqrt(x+y);
    }

    public void reportBtnTapped(View view) {
        if (getInch() > 5.5) {
            Intent intent = new Intent(this, NewReportCenterActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            if (latLng != null) {
                intent.putExtra("lat", String.valueOf(latLng.latitude));
                intent.putExtra("long", String.valueOf(latLng.longitude));
            }
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, ReportActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            if (latLng != null) {
                intent.putExtra("lat", String.valueOf(latLng.latitude));
                intent.putExtra("long", String.valueOf(latLng.longitude));
            }
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            currentLocation();
        }
    }
}
