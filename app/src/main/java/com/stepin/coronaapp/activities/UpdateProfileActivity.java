package com.stepin.coronaapp.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.stepin.coronaapp.App;
import com.stepin.coronaapp.R;
import com.stepin.coronaapp.api.Api;
import com.stepin.coronaapp.api.NetworkRequest;
import com.stepin.coronaapp.model.APIResponse;
import com.stepin.coronaapp.model.State;
import com.stepin.coronaapp.model.UploadImageResponse;
import com.stepin.coronaapp.model.User;
import com.stepin.coronaapp.model.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class UpdateProfileActivity extends AppCompatActivity {

    EditText firstNameEdt, lastNameEdt, emailEdt, passwordEdt, stateEdt, genderEdt, ageEdt;
    Spinner stateSpinner, genderSpinner, ageSpinner;
    private ProgressDialog progressDialog;

    private CircularImageView circularImageView;
    private ProgressBar bar;
    private int RESULT_LOAD_IMG = 121;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        settingIds();
        setProgressDialog();
        setStateSpinner();
        setAgeSpinner();
        setGenderSpinner();
        setUserInformation();

        requestCameraPermission();
    }

    private void settingIds() {
        firstNameEdt = findViewById(R.id.editText2);
        lastNameEdt = findViewById(R.id.editText);
        emailEdt = findViewById(R.id.editText3);
        passwordEdt = findViewById(R.id.editText4);
        stateEdt = findViewById(R.id.editText5);
        genderEdt = findViewById(R.id.editText6);
        ageEdt = findViewById(R.id.editText9);

        circularImageView = findViewById(R.id.circularImageView);
        bar = findViewById(R.id.circularProgress);
        stateSpinner = findViewById(R.id.updateStateSpinner);
        genderSpinner = findViewById(R.id.updateGenderState);
        ageSpinner = findViewById(R.id.updateAgeSpinner);
    }

    private void setProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Updating ...");
    }

    private void setUserInformation() {
        TextView nameTxt = findViewById(R.id.textView47);
        TextView stateTxt = findViewById(R.id.textView48);

        User user = new User().getUser(this);
        nameTxt.setText(user.getData().getFirstName() + " " + user.getData().getLastName());
        stateTxt.setText(user.getData().getState());

        firstNameEdt.setText(user.getData().getFirstName());
        lastNameEdt.setText(user.getData().getLastName());
        emailEdt.setText(user.getData().getEmail());
        stateEdt.setText(user.getData().getState());
//        genderEdt.setText(user.getData().getGender());
//        ageEdt.setText(""+user.getData().getAge());

        CircularImageView circularImageView = findViewById(R.id.circularImageView);
        if (user.getData().getProfileImage() != null && !user.getData().getProfileImage().isEmpty()) {
            String url = Api.profileImageUrl + user.getData().getProfileImage();
            Picasso.get().load(url).placeholder(R.drawable.profile_pic).into(circularImageView);
        }
    }

    private void setStateSpinner() {

        String citiesJson = new Utils().inputStreamToString(getResources().openRawResource(R.raw.cities));
        Type listType = new TypeToken<List<State>>(){}.getType();
        List<State> states = new Gson().fromJson(citiesJson, listType);

        List<String> categories = new ArrayList<>(Collections.singletonList("State Of Residence"));
        for (State state: states) { categories.add(state.getName()); }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(dataAdapter);

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stateEdt.setText(parent.getItemAtPosition(position).toString());
                ((TextView) parent.getChildAt(0)).setTextColor(Color.TRANSPARENT);

                if (parent.getItemAtPosition(position).toString().equals("State Of Residence")) {
                    stateEdt.setTextColor(getResources().getColor(R.color.light_gray));
                } else {
                    stateEdt.setTextColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setAgeSpinner() {
        List<String> ages = new ArrayList<>(Collections.singletonList("Select Age"));
        for (int i=0;i<120;i++) { ages.add(""+i); }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ages);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(dataAdapter);

        ageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ageEdt.setText(parent.getItemAtPosition(position).toString());
                ((TextView) parent.getChildAt(0)).setTextColor(Color.TRANSPARENT);

                if (parent.getItemAtPosition(position).toString().equals("Select Age")) {
                    ageEdt.setTextColor(getResources().getColor(R.color.light_gray));
                } else {
                    ageEdt.setTextColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setGenderSpinner() {
        List<String> genders = new ArrayList<>(Arrays.asList("Select Gender", "Male", "Female", "Other"));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genders);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(dataAdapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genderEdt.setText(parent.getItemAtPosition(position).toString());
                ((TextView) parent.getChildAt(0)).setTextColor(Color.TRANSPARENT);

                if (parent.getItemAtPosition(position).toString().equals("Select Gender")) {
                    genderEdt.setTextColor(getResources().getColor(R.color.light_gray));
                } else {
                    genderEdt.setTextColor(getResources().getColor(R.color.black));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private boolean validateRegister(String firstName, String lastName, String email, String password, String state, String gender, String age) {

        if (firstName.isEmpty()) {
            firstNameEdt.setError("Please enter first name");
            firstNameEdt.requestFocus();
        } else if (lastName.isEmpty()) {
            lastNameEdt.setError("Please enter last name");
            lastNameEdt.requestFocus();
        } else if (email.isEmpty()) {
            emailEdt.setError("Please enter email");
            emailEdt.requestFocus();
        } else if (!new Utils().isValidEmail(email)) {
            emailEdt.setError("Please enter valid email");
            emailEdt.requestFocus();
        } else if (password.isEmpty()) {
            passwordEdt.setError("Please enter password");
            passwordEdt.requestFocus();
        } else if (password.length() < 8) {
            passwordEdt.setError("Password is short, at least 7 characters");
            passwordEdt.requestFocus();
        } else if (state.isEmpty() || state.equals("State Of Residence")) {
            showError("Please select state of residence");
        } else if (gender.isEmpty() || gender.equals("Select Gender")) {
            showError("Please select gender");
        } else if (age.isEmpty() || age.equals("Select Age")) {
            showError("Please select age");
        } else {
            return false;
        }

        return true;
    }

    private LinkedHashMap<String, String> getParameters(String firstName, String lastName, String email, String password, String state, String gender, String age) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("first_name", firstName);
        map.put("last_name", lastName);
        map.put("email", email);
        map.put("password", password);
        map.put("gender", gender);
        map.put("age", age);
        map.put("state", state);
        map.put("user_type_id", "1");
        return map;
    }

    public void finishedProfile(View view) {
        finish();
    }

    public void saveChangesBtnTapped(View view) {

        if(App.isNetworkAvailable(UpdateProfileActivity.this)){
            String firstName = firstNameEdt.getText().toString().trim();
            String lastName = lastNameEdt.getText().toString().trim();
            String email = emailEdt.getText().toString().trim();
            String password = passwordEdt.getText().toString().trim();
            String state = stateEdt.getText().toString().trim();
            String gender = genderEdt.getText().toString().trim();
            String age = ageEdt.getText().toString().trim();

            if (validateRegister(firstName, lastName, email, password, state, gender, age)) { return; }

            progressDialog.show();

            LinkedHashMap<String, String> params = getParameters(firstName, lastName, email, password, state, gender, age);
            String url = new Api().generateUrl(Api.update, params);
            Type type = new TypeToken<APIResponse>(){}.getType();

            Log.d("update", url);
            new NetworkRequest<APIResponse>().post(this, type, url, (status, error) -> {
                progressDialog.dismiss();
                if (error != null && status == null) {
                    showError(error.getMessage());
                } else if (status.getStatus().equals("failed")) {
                    showError(status.getMsg());
                } else {
                    Toast.makeText(this, "Successfully Updated", Toast.LENGTH_LONG).show();
                    saveUserData(params);
                }
            });
        }else{
            Toast.makeText(UpdateProfileActivity.this,"You are working offline",Toast.LENGTH_LONG).show();
        }

    }

    private void showError(String message) {
        new AlertDialog.Builder(this).setMessage(message)
                .setPositiveButton("Ok", (dialog, which) -> { })
                .show();
    }

    private void saveUserData(HashMap<String, String> map) {
        SharedPreferences.Editor editor = getSharedPreferences(Utils.sharedPerfName, MODE_PRIVATE).edit();
        editor.putString("name", map.get("name"));
        editor.putString("email", map.get("email"));
        editor.putString("gender", map.get("gender"));
        editor.putString("state", map.get("state"));
        editor.putInt("age", Integer.parseInt(map.get("age")));
        editor.apply();
    }

    private void requestCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
            }
        }
    }

    public void circularImageTapped(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMG);
    }

    public void uploadImage(Bitmap bitmap) {
        if(App.isNetworkAvailable(UpdateProfileActivity.this)){
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            map.put("user_id", String.valueOf(new User().getUser(this).getData().getId()));
            String url = new Api().generateUrl(Api.profileImage, map);
            Type type = new TypeToken<UploadImageResponse>(){}.getType();
            bar.setVisibility(View.VISIBLE);

            Log.d("json image url", url);
            new NetworkRequest<UploadImageResponse>().uploadImage(this, type, bitmap, url, "profile_image", (obj, error) -> {
                bar.setVisibility(View.INVISIBLE);
                if (obj == null && error != null) {
                    Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
                } else if (obj != null) {
                    SharedPreferences.Editor editor = getSharedPreferences(Utils.sharedPerfName, MODE_PRIVATE).edit();
                    editor.putString("profile_image", obj.getImageName());
                    editor.apply();
                    Toast.makeText(this, obj.getMsg(), Toast.LENGTH_LONG).show();
                }
            });
        }else{
            Toast.makeText(UpdateProfileActivity.this,"You are working offline",Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == RESULT_LOAD_IMG) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                circularImageView.setImageBitmap(selectedImage);
                uploadImage(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong while picking the image", Toast.LENGTH_LONG).show();
            }
        }
    }
}
