package com.stepin.coronaapp.activities;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.stepin.coronaapp.App;
import com.stepin.coronaapp.R;
import com.stepin.coronaapp.api.Api;
import com.stepin.coronaapp.api.NetworkRequest;
import com.stepin.coronaapp.model.APIResponse;
import com.stepin.coronaapp.model.CollectionResponse;
import com.stepin.coronaapp.model.SymptomsResponse;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class NewReportCenterActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    View self, third;
    Button selfbtn, thirdbtn;
    EditText firstname,lastname, ageEdt;
    EditText infEdt;

    CardView runningNoseCardView, coughingCardView, feverCardView, soreThroatCardView, sneezingCardView, headacheCardView, shortBreathCardView, arrivalCardView;
    ImageView runningNoseImageView, coughingImageView, feverImageView, soreThroatImageView, sneezingImageView, headacheImageView, shortBreathImageView, arrivalImageView;
    TextView runningNoseTextView, coughingTextView, feverTextView, soreThroatTextView, sneezingTextView, headacheTextView, shortBreathTextView, arrivalTextView;

    ArrayList<String> list = new ArrayList<>();
    String report = "self report", latitude = "1", longitude = "1";
    User user;

    private int RESULT_LOAD_IMG = 123, CAMERA = 124;
    private Bitmap selectedImage = null;
    private ProgressDialog progressDialog;
    private ArrayList<SymptomsResponse.Data> symptoms = new ArrayList<>();
    private ArrayList<CollectionResponse.Data> collections = new ArrayList<>();
    private int collection_id;
    private String address = "", city = "", country = "", state = "";
    private TextView addressTxt, stateTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_report_center);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        addressTxt = findViewById(R.id.textView27);
        stateTxt = findViewById(R.id.textView29);
        self = findViewById(R.id.view5);
        third = findViewById(R.id.view4);

        selfbtn = findViewById(R.id.button5);
        thirdbtn = findViewById(R.id.button121);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        infEdt = findViewById(R.id.editText7);
        ageEdt = findViewById(R.id.ageEditText);

        selfbtn.setOnClickListener(v -> {
            firstname.setVisibility(View.GONE);
            lastname.setVisibility(View.GONE);
            ageEdt.setVisibility(View.GONE);

            self.setVisibility(View.VISIBLE);
            selfbtn.setTextColor(getResources().getColor(R.color.red));

            third.setVisibility(View.INVISIBLE);
            thirdbtn.setTextColor(getResources().getColor(R.color.black));

            report = "self report";
        });

        thirdbtn.setOnClickListener(v -> {
            firstname.setVisibility(View.VISIBLE);
            lastname.setVisibility(View.VISIBLE);
            ageEdt.setVisibility(View.VISIBLE);

            self.setVisibility(View.INVISIBLE);
            selfbtn.setTextColor(getResources().getColor(R.color.black));

            third.setVisibility(View.VISIBLE);
            thirdbtn.setTextColor(getResources().getColor(R.color.red));

            report = "third party report";
        });

        getLatLong();
        requestCameraPermission();
        settingIds();
        setUserInformation();
        self.performClick();
        setProgressDialog();

        getSymptoms();
        getAddress();
        getCollections();

        fixLayout();
    }

    private void fixLayout() {
        double inch = getInch();

        if (Math.round(inch) >= 6) {
            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) infEdt.getLayoutParams();
            lp.height = 200;
            infEdt.setLayoutParams(lp);

            Button submitBtn = findViewById(R.id.button8);
            ConstraintLayout.LayoutParams lp1 = (ConstraintLayout.LayoutParams) submitBtn.getLayoutParams();
            lp1.height = 110;
            submitBtn.setLayoutParams(lp1);
        }

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

    private void getSymptoms() {
        if(App.isNetworkAvailable(NewReportCenterActivity.this)){
            String url = new Api().generateUrl(Api.symptoms, new LinkedHashMap<>());
            Type type = new TypeToken<SymptomsResponse>(){}.getType();

            new NetworkRequest<SymptomsResponse>().fetch(this, type, url, (obj, error) -> {
                if (error == null && obj != null) {
                    symptoms = (ArrayList<SymptomsResponse.Data>) obj.getData();
                }
            });
        }else{
            Toast.makeText(NewReportCenterActivity.this,"You are working offline",Toast.LENGTH_LONG).show();
        }


    }

    private void getCollections() {
        if(App.isNetworkAvailable(NewReportCenterActivity.this)){
            String url = new Api().generateUrl(Api.collection, new LinkedHashMap<>());
            Type type = new TypeToken<CollectionResponse>(){}.getType();

            new NetworkRequest<CollectionResponse>().fetch(this, type, url, (obj, error) -> {
                if (error == null && obj != null) {
                    collections = (ArrayList<CollectionResponse.Data>) obj.getData();
                }
            });
        } else {
            Toast.makeText(NewReportCenterActivity.this,"You are working offline",Toast.LENGTH_LONG).show();
        }
    }

    private void requestCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
            }
        }
    }

    private void setProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Submitting Report ...");
    }

    private void settingIds() {
        runningNoseCardView = findViewById(R.id.cardView121);
        runningNoseImageView = findViewById(R.id.imageView31);
        runningNoseTextView = findViewById(R.id.runningNoseTxt);

        coughingCardView = findViewById(R.id.cardView151);
        coughingImageView = findViewById(R.id.coughing);
        coughingTextView = findViewById(R.id.coughingTxt);

        feverCardView = findViewById(R.id.cardView141);
        feverImageView = findViewById(R.id.fever);
        feverTextView = findViewById(R.id.feverTxt);

        soreThroatCardView = findViewById(R.id.cardView161);
        soreThroatImageView = findViewById(R.id.sore);
        soreThroatTextView = findViewById(R.id.soreTxt);

        sneezingCardView = findViewById(R.id.cardView8);
        sneezingImageView = findViewById(R.id.sneezing);
        sneezingTextView = findViewById(R.id.snezzingTxt);

        headacheCardView = findViewById(R.id.cardView7);
        headacheImageView = findViewById(R.id.headache);
        headacheTextView = findViewById(R.id.headacheTxt);

        shortBreathCardView = findViewById(R.id.cardView9);
        shortBreathImageView = findViewById(R.id.shortbreath);
        shortBreathTextView = findViewById(R.id.shortbreathTxt);

        arrivalCardView = findViewById(R.id.cardView26);
        arrivalImageView = findViewById(R.id.arrival);
        arrivalTextView = findViewById(R.id.arrivalTxt);
    }

    private void setUserInformation() {
        TextView nameTxt = findViewById(R.id.textView28);
        user = new User().getUser(this);
        if (user == null) { return; }
        nameTxt.setText(user.getData().getFirstName() + " " + user.getData().getLastName());

        CircularImageView circularImageView = findViewById(R.id.circularImageView);
        if (user.getData().getProfileImage() != null && !user.getData().getProfileImage().isEmpty()) {
            String url = Api.profileImageUrl + user.getData().getProfileImage();
            Picasso.get().load(url).placeholder(R.drawable.profile_pic).into(circularImageView);
        }
    }

    private void getLatLong() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            latitude = bundle.getString("lat", "1");
            longitude = bundle.getString("long", "1");
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

    public void closeReport(View view) {
        finish();
    }

    public void runningNoseBtnTapped(View view) {
        selectType(runningNoseCardView, runningNoseImageView, runningNoseTextView, "Running Nose", "1");
    }

    public void coughingNoseBtnTapped(View view) {
        selectType(coughingCardView, coughingImageView, coughingTextView, "Coughing", "2");
    }

    public void feverBtnTapped(View view) {
        selectType(feverCardView, feverImageView, feverTextView, "Fever", "3");
    }

    public void soreThroatTapped(View view) {
        selectType(soreThroatCardView, soreThroatImageView, soreThroatTextView, "Sore Throat", "4");
    }

    public void headacheBtnTapped(View view) {
        selectType(headacheCardView, headacheImageView, headacheTextView, "Headache", "6");
    }

    public void shortBreathBtnTapped(View view) {
        selectType(shortBreathCardView, shortBreathImageView, shortBreathTextView, "Short Breath", "7");
    }

    public void sneezingBtnTapped(View view) {
        selectType(sneezingCardView, sneezingImageView, sneezingTextView, "Sneezing", "5");
    }

    public void intArrivalBtnTapped(View view) {
        selectType(arrivalCardView, arrivalImageView, arrivalTextView, "Int'l Arrival", "8");
    }

    private String getSymptomsId(String type) {
        for (SymptomsResponse.Data data: symptoms) {
            if (data.getSymptom().toLowerCase().equals(type)) {
                return String.valueOf(data.getId());
            }
        }
        return "-1";
    }

    private void selectType(CardView cardView, ImageView imageView, TextView textView, String type, String id) {
        int redColor = getResources().getColor(R.color.red);
        int whiteColor = getResources().getColor(R.color.white);
        int blackColor = getResources().getColor(R.color.black);
        String symid = getSymptomsId(type.toLowerCase());

        if (!symid.equals("-1")) { id = symid; }

        if (list.contains(id)) {
            cardView.setCardBackgroundColor(whiteColor);
            imageView.setColorFilter(null);
            textView.setTextColor(blackColor);
            list.remove(id);
        } else {
            cardView.setCardBackgroundColor(redColor);
            imageView.setColorFilter(whiteColor);
            textView.setTextColor(whiteColor);
            list.add(id);
        }
    }

    private void getAddress() {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(latitude), Double.parseDouble(longitude), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addresses.isEmpty()) { return; }
            address = addresses.get(0).getAddressLine(0);
            city = addresses.get(0).getLocality();
            country = addresses.get(0).getCountryName();
            state = addresses.get(0).getAdminArea();

            addressTxt.setText(address);
            stateTxt.setText(state + " " + new Utils().getTime());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // http://corona.stepinnsolution.com/api/report?symptom=coughing&report_type=self%20report&user_id=16&latitude=1123123&longitude=12313
    private LinkedHashMap<String, String> getParameters() {

        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("user_id", String.valueOf(user.getData().getId()));
        map.put("report_type", report);
        map.put("symptom", TextUtils.join(",", list));
        map.put("latitude", latitude);
        map.put("longitude", longitude);
        map.put("city", city.isEmpty() ? user.getData().getState() : city);
        map.put("state", state.isEmpty() ? user.getData().getState() : state);
        map.put("address", address.isEmpty() ? user.getData().getState() : address);
        map.put("country", country.isEmpty() ? user.getData().getState() : country);
        map.put("collection_id", String.valueOf(collection_id));

        if (report.equals("third party report")) {
            map.put("first_name", firstname.getText().toString());
            map.put("second_name", lastname.getText().toString());
            map.put("age", ageEdt.getText().toString());
        }

        if (!infEdt.getText().toString().isEmpty()) {
            map.put("additional_info", infEdt.getText().toString());
        }

        return map;
    }

    private boolean validate() {
        if (list.isEmpty()) {
            showError("Please select symptom");
        } else if (report.equals("third party report") && firstname.getText().toString().isEmpty()){
            firstname.setError("Please enter first name");
        } else if (report.equals("third party report") && lastname.getText().toString().isEmpty()) {
            lastname.setError("Please enter second name");
        } else if (report.equals("third party report") && ageEdt.getText().toString().isEmpty()) {
            lastname.setError("Please enter age");
        } else {
            return false;
        }

        return true;
    }

//    public void reportSubmitBtnTapped(View view) {
//        if(App.isNetworkAvailable(NewReportCenterActivity.this)){
//            String url = new Api().generateUrl(Api.report, getParameters());
//            Type type = new TypeToken<APIResponse>(){}.getType();
//
//            Log.d("json", url);
//            if (validate()) { return; }
//            progressDialog.show();
//
//            new NetworkRequest<APIResponse>().uploadImage(this, type, selectedImage, url, Api.reportImage, (status, error) -> {
//                progressDialog.dismiss();
//                if (error != null && status == null) {
//                    showError(error.getMessage());
//                } else if (status.getStatus().equals("failed")) {
//                    showError(status.getMsg());
//                } else {
//                    Toast.makeText(this, status.getMsg(), Toast.LENGTH_LONG).show();
//                    resetForm();
//                }
//            });
//        }else{
//            Toast.makeText(NewReportCenterActivity.this,"You are working offline",Toast.LENGTH_LONG).show();
//        }
//
//    }

    public void reportSubmitBtnTapped(View view) {
        if(App.isNetworkAvailable(NewReportCenterActivity.this)){

            if (validate()) { return; }

            View dialogView = getLayoutInflater().inflate(R.layout.dlg_report_collection, null);

            AlertDialog emailDlg = new android.app.AlertDialog.Builder(NewReportCenterActivity.this)
                    .setView(dialogView)
                    .setCancelable(true)
                    .create();

            RadioGroup rg_collection = dialogView.findViewById(R.id.rg_collection);

            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{-android.R.attr.state_enabled},
                            new int[]{android.R.attr.state_enabled}
                    },
                    new int[] {
                            Color.BLACK,
                            getResources().getColor(R.color.global_color_orange_primary_dark) //enabled
                    }
            );

            for (int i = 0; i < collections.size(); i ++) {
                RadioButton radioButton = new RadioButton(NewReportCenterActivity.this);
                radioButton.setId(collections.get(i).getId());
                radioButton.setText(collections.get(i).getCollection_name());
                radioButton.setTextSize(14);
                radioButton.setButtonTintList(colorStateList);

                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                rg_collection.addView(radioButton, params);
            }

            rg_collection.check(collections.get(0).getId());

            rg_collection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    collection_id = checkedId;

                }
            });

            final Button ok = dialogView.findViewById(R.id.ok);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    emailDlg.dismiss();
                    progressDialog.show();

                    String url = new Api().generateUrl(Api.report, getParameters());
                    Type type = new TypeToken<APIResponse>(){}.getType();

                    new NetworkRequest<APIResponse>().uploadImage(NewReportCenterActivity.this, type, selectedImage, url, Api.reportImage, (status, error) -> {
                        progressDialog.dismiss();
                        if (error != null && status == null) {
                            showError(error.getMessage());
                        } else if (status.getStatus().equals("failed")) {
                            showError(status.getMsg());
                        } else {
                            Toast.makeText(NewReportCenterActivity.this, status.getMsg(), Toast.LENGTH_LONG).show();
                            resetForm();
                        }
                    });
                }
            });
            emailDlg.setCanceledOnTouchOutside(true);
            emailDlg.setCancelable(true);
            emailDlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            emailDlg.show();
        } else {
            Toast.makeText(NewReportCenterActivity.this,"You are working offline",Toast.LENGTH_LONG).show();
        }
    }

    private void resetForm() {
        ArrayList<CardView> cardViews = new ArrayList<>(Arrays.asList(runningNoseCardView, coughingCardView, feverCardView, soreThroatCardView, sneezingCardView, headacheCardView, shortBreathCardView, arrivalCardView));
        ArrayList<ImageView> imageViews = new ArrayList<>(Arrays.asList(runningNoseImageView, coughingImageView, feverImageView, soreThroatImageView, sneezingImageView, headacheImageView, shortBreathImageView, arrivalImageView));
        ArrayList<TextView> textViews = new ArrayList<>(Arrays.asList(runningNoseTextView, coughingTextView, feverTextView, soreThroatTextView, sneezingTextView, headacheTextView, shortBreathTextView, arrivalTextView));

        int whiteColor = getResources().getColor(R.color.white);
        int blackColor = getResources().getColor(R.color.black);

        for (int i=0;i<cardViews.size();i++) {
            cardViews.get(i).setCardBackgroundColor(whiteColor);
            imageViews.get(i).setColorFilter(null);
            textViews.get(i).setTextColor(blackColor);
        }

        list.clear();
        infEdt.setText("");
        selectedImage = null;
        firstname.setText("");
        lastname.setText("");
        ageEdt.setText("");
    }

    private void showError(String message) {
        new AlertDialog.Builder(this).setMessage(message)
                .setPositiveButton("Ok", (dialog, which) -> { })
                .show();
    }

    public void photoBtnTapped(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA);
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
            }
        } else {
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA);
        }
    }

    @SuppressLint("IntentReset")
    public void browseBtnTapped(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[] {"image/*", "video/*"});
        startActivityForResult(intent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == RESULT_LOAD_IMG) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                selectedImage = BitmapFactory.decodeStream(imageStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong while picking the image", Toast.LENGTH_LONG).show();
            }
        } else if (resultCode == RESULT_OK && requestCode == CAMERA) {
            selectedImage = (Bitmap) data.getExtras().get("data");
        }
    }

}
