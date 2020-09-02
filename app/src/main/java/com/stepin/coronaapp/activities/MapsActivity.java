package com.stepin.coronaapp.activities;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.stepin.coronaapp.App;
import com.stepin.coronaapp.R;
import com.stepin.coronaapp.api.Api;
import com.stepin.coronaapp.api.NetworkRequest;
import com.stepin.coronaapp.model.APIResponse;
import com.stepin.coronaapp.model.CheckData;
import com.stepin.coronaapp.model.User;
import com.stepin.coronaapp.model.Utils;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.reflect.TypeToken;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.stepin.coronaapp.sqlite.DatabaseQueryClass;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Button utilityBtn;
    private Spinner utilitiesSpinner;
    private Button lessThan10Btn, lessThan50Btn, fiftyPeoplePlusBtn;
    private Button lessThan1HourBtn, lessThan6HourBtn, fixHoursPlusBtn;
    private EditText additionalInfEdt;

    private String people = "less than 10 people";
    private String hours = "less than 1 hour";
    private ArrayList<String> utilities;
    private ProgressDialog progressDialog;

    User user;
    private String longitude = "1", latitude = "1", address = "", city = "", country = "", state = "";
    private TextView addressTxt, stateTxt;

    private DatabaseQueryClass databaseQueryClass = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        databaseQueryClass = new DatabaseQueryClass(MapsActivity.this);
        addressTxt = findViewById(R.id.textView27);
        stateTxt = findViewById(R.id.textView29);
        utilityBtn = findViewById(R.id.button9);
        utilitiesSpinner = findViewById(R.id.utilitiesSpinner);
        lessThan10Btn = findViewById(R.id.people10);
        lessThan50Btn = findViewById(R.id.button14);
        fiftyPeoplePlusBtn = findViewById(R.id.button15);
        lessThan1HourBtn = findViewById(R.id.button11);
        lessThan6HourBtn = findViewById(R.id.button10);
        fixHoursPlusBtn = findViewById(R.id.button12);
        additionalInfEdt = findViewById(R.id.editText7);

        setProgressDialog();
        setUserInformation();
        setStateSpinner();

        getLatLong();


        if (getInch() >= 6) {
            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) additionalInfEdt.getLayoutParams();
            lp.height = 400;
            additionalInfEdt.setLayoutParams(lp);

            Button submitBtn = findViewById(R.id.button8);
            ConstraintLayout.LayoutParams lp1 = (ConstraintLayout.LayoutParams) submitBtn.getLayoutParams();
            lp1.height = 150;
            submitBtn.setLayoutParams(lp1);
        }
    }

    private int getInch() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        double wi=(double)width/(double)dm.xdpi;
        double hi=(double)height/(double)dm.ydpi;
        double x = Math.pow(wi,2);
        double y = Math.pow(hi,2);
        return (int) Math.round(Math.sqrt(x+y));
    }

    private void getLatLong() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            latitude = bundle.getString("lat", "1");
            longitude = bundle.getString("long", "1");

            if (!latitude.equals("1") && !longitude.equals("1")) { getAddress(); }
        }
    }

    private void getAddress() {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(latitude), Double.parseDouble(longitude), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            address = addresses.get(0).getAddressLine(0);
            city = addresses.get(0).getLocality();
            country = addresses.get(0).getCountryName();
            state = addresses.get(0).getAdminArea();

            addressTxt.setText(address);
            stateTxt.setText(state + " " + new Utils().getTime());

            Log.d("json address", address);
            Log.d("json city", city);
            Log.d("json country", country);
            Log.d("json state", state);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setUserInformation() {
        TextView nameTxt = findViewById(R.id.textView28);

        user = new User().getUser(this);
        nameTxt.setText(user.getData().getFirstName() + " " + user.getData().getLastName());

        CircularImageView circularImageView = findViewById(R.id.circularImageView);
        if (user.getData().getProfileImage() != null && !user.getData().getProfileImage().isEmpty()) {
            String url = Api.profileImageUrl + user.getData().getProfileImage();
            Picasso.get().load(url).placeholder(R.drawable.profile_pic).into(circularImageView);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if ((latitude != null && longitude != null) && (!latitude.equals("1") && !longitude.equals("1"))) {
            LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
            mMap.animateCamera(cameraUpdate);
        }
    }

    private void setStateSpinner() {

        utilities = new ArrayList<>(Arrays.asList("vehicle", "work", "wedding", "party", "shopping", "bus stage", "soko others", "ferry", "airport"));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, utilities);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        utilitiesSpinner.setAdapter(dataAdapter);

        utilitiesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                utilityBtn.setText(parent.getItemAtPosition(position).toString());
                utilityBtn.setTextColor(getResources().getColor(R.color.black));
                ((TextView) parent.getChildAt(0)).setTextColor(Color.TRANSPARENT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void closeCheckIn(View view) {
        finish();
    }

    public void lessThan10BtnTapped(View view) {
        selectLessThan("less than 10");
    }

    public void lessThan50BtnTapped(View view) {
        selectLessThan("less than 50");
    }

    public void FiftyPlusBtnTapped(View view) {
        selectLessThan("more than 50");
    }

    public void lessThan1HourBtnTapped(View view) {
        selectLessThanHours("less than 1");
    }

    public void lessThan6HourBtnTapped(View view) {
        selectLessThanHours("less than 6");
    }

    public void fixHourPlusBtnTapped(View view) {
        selectLessThanHours("more than 6");
    }

    private void selectLessThan(String type) {
        int whiteColor = getResources().getColor(R.color.white);
        int blackColor = getResources().getColor(R.color.black);
        Drawable selected = getResources().getDrawable(R.drawable.buttonforcheckinselected);
        Drawable unselected = getResources().getDrawable(R.drawable.buttonsforcheckin);

        lessThan10Btn.setBackground(unselected);
        lessThan10Btn.setTextColor(blackColor);

        lessThan50Btn.setBackground(unselected);
        lessThan50Btn.setTextColor(blackColor);

        fiftyPeoplePlusBtn.setBackground(unselected);
        fiftyPeoplePlusBtn.setTextColor(blackColor);

        switch (type) {
            case "less than 10":
                lessThan10Btn.setBackground(selected);
                lessThan10Btn.setTextColor(whiteColor);
                people = "less than 10 people";
                break;
            case "less than 50":
                lessThan50Btn.setBackground(selected);
                lessThan50Btn.setTextColor(whiteColor);
                people = "less than 50 people";
                break;
            case  "more than 50":
                fiftyPeoplePlusBtn.setBackground(selected);
                fiftyPeoplePlusBtn.setTextColor(whiteColor);
                people = "more than 50 people";
                break;
        }
    }


    private void selectLessThanHours(String type) {
        int whiteColor = getResources().getColor(R.color.white);
        int blackColor = getResources().getColor(R.color.black);
        Drawable selected = getResources().getDrawable(R.drawable.buttonforcheckinselected);
        Drawable unselected = getResources().getDrawable(R.drawable.buttonsforcheckin);

        lessThan1HourBtn.setBackground(unselected);
        lessThan1HourBtn.setTextColor(blackColor);

        lessThan6HourBtn.setBackground(unselected);
        lessThan6HourBtn.setTextColor(blackColor);

        fixHoursPlusBtn.setBackground(unselected);
        fixHoursPlusBtn.setTextColor(blackColor);

        switch (type) {
            case "less than 1":
                lessThan1HourBtn.setBackground(selected);
                lessThan1HourBtn.setTextColor(whiteColor);
                hours = "less than 1 hours";
                break;
            case "less than 6":
                lessThan6HourBtn.setBackground(selected);
                lessThan6HourBtn.setTextColor(whiteColor);
                hours = "less than 6 hours";
                break;
            case  "more than 6":
                fixHoursPlusBtn.setBackground(selected);
                fixHoursPlusBtn.setTextColor(whiteColor);
                hours = "more than 6 hours";
                break;
        }
    }

    private LinkedHashMap<String, String> getParameters() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("peaple", people);
        map.put("time", hours);
        map.put("utilities", utilityBtn.getText().toString());
        if (!additionalInfEdt.getText().toString().isEmpty()) {
            map.put("additional_info", additionalInfEdt.getText().toString());
        }else{
            map.put("additional_info", "");
        }

        map.put("city", city.isEmpty() ? user.getData().getState() : city);
        map.put("state", state.isEmpty() ? user.getData().getState() : state);
        map.put("address", address.isEmpty() ? user.getData().getState() : address);
        map.put("country", country.isEmpty() ? user.getData().getState() : country);
        map.put("user_id", String.valueOf(user.getData().getId()));
        map.put("longitude", longitude);
        map.put("latitude", latitude);

        return map;
    }

    public void submitBtnTapped(View view) {
        if(App.isNetworkAvailable(MapsActivity.this)){
            String url = new Api().generateUrl(Api.checkIn, getParameters());
            Type type = new TypeToken<APIResponse>(){}.getType();

            Log.d("json", url);
            progressDialog.show();

            new NetworkRequest<APIResponse>().post(this, type, url, (status, error) -> {
                progressDialog.dismiss();
                if (error != null && status == null) {
                    showError(error.getMessage());
                } else if (status.getStatus().equals("failed")) {
                    showError(status.getMsg());
                } else {
                    Toast.makeText(this, status.getMsg(), Toast.LENGTH_LONG).show();
                    resetForm();
                }
            });
        }else{
            Toast.makeText(MapsActivity.this,"You are working offline",Toast.LENGTH_LONG).show();
            try {
                String time = hours;
                String util = utilityBtn.getText().toString();
                String addition = "";
                if (!additionalInfEdt.getText().toString().isEmpty()) {
                    addition =  additionalInfEdt.getText().toString();
                }else{
                    addition = "";
                }

                String city_str =  city.isEmpty() ? user.getData().getState() : city;
                String state_str = state.isEmpty() ? user.getData().getState() : state;
                String address_str = address.isEmpty() ? user.getData().getState() : address;
                String country_str = country.isEmpty() ? user.getData().getState() : country;
                String userid_str = String.valueOf(user.getData().getId());
                String created_time =  new SimpleDateFormat("MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                CheckData tempdata = new CheckData(-1, people, time, util, addition, city_str, state_str, address_str, country_str,userid_str,longitude,latitude,created_time);

                long id = databaseQueryClass.insertCheckData(tempdata);
                if(id!=-1){
                    resetForm();
                    additionalInfEdt.setText("");
                    Toast.makeText(MapsActivity.this,"Your data is saved in your phone",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MapsActivity.this,"Local Database error",Toast.LENGTH_LONG).show();
                }
            }catch (Exception e){

            }

        }

    }

    private void showError(String message) {
        new AlertDialog.Builder(this).setMessage(message)
                .setPositiveButton("Ok", (dialog, which) -> { })
                .show();
    }

    private void resetForm() {
        selectLessThan("less than 10");
        selectLessThanHours("less than 1");
        utilitiesSpinner.setSelection(0);
        additionalInfEdt.setText("");
    }

    private void setProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Adding Information ...");
    }
}
