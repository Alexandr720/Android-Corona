package com.stepin.coronaapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
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
import com.stepin.coronaapp.model.State;
import com.stepin.coronaapp.model.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    Spinner stateSpinner, ageSpinner, genderSpinner;
    EditText firstNameEdt, lastNameEdt, emailEdt, passwordEdt, stateEditText, ageEditText, genderEditText;
    private ProgressDialog progressDialog;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerBtn = findViewById(R.id.button);
        firstNameEdt = findViewById(R.id.firstNameEditText);
        lastNameEdt = findViewById(R.id.lastNameEditText);
        emailEdt = findViewById(R.id.emailEditText);
        passwordEdt = findViewById(R.id.passwordEditText);

        setStateSpinner();
        setAgeSpinner();
        setGenderSpinner();
        setProgressDialog();

        TextView textView = findViewById(R.id.textView5);

        String sourceString = "Registered? " + "<u> Login Now </u> ";
        textView.setText(Html.fromHtml(sourceString));
    }

    private void setStateSpinner() {

        stateSpinner = findViewById(R.id.residenceSpinner);
        stateEditText = findViewById(R.id.stateEditText);

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
                stateEditText.setText(parent.getItemAtPosition(position).toString());
                ((TextView) parent.getChildAt(0)).setTextColor(Color.TRANSPARENT);

                if (parent.getItemAtPosition(position).toString().equals("State Of Residence")) {
                    stateEditText.setTextColor(getResources().getColor(R.color.light_gray));
                } else {
                    stateEditText.setTextColor(getResources().getColor(R.color.white));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setAgeSpinner() {
        ageEditText = findViewById(R.id.ageEditText);
        ageSpinner = findViewById(R.id.ageSpinner);

        List<String> ages = new ArrayList<>(Collections.singletonList("Age"));
        for (int i=0;i<120;i++) { ages.add(""+i); }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ages);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(dataAdapter);

        ageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ageEditText.setText(parent.getItemAtPosition(position).toString());
                ((TextView) parent.getChildAt(0)).setTextColor(Color.TRANSPARENT);

                if (parent.getItemAtPosition(position).toString().equals("Age")) {
                    ageEditText.setTextColor(getResources().getColor(R.color.light_gray));
                } else {
                    ageEditText.setTextColor(getResources().getColor(R.color.white));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setGenderSpinner() {
        genderEditText = findViewById(R.id.genderEditText);
        genderSpinner = findViewById(R.id.genderSpinner);

        List<String> genders = new ArrayList<>(Arrays.asList(" Gender", "Male", "Female", "Other"));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genders);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(dataAdapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genderEditText.setText(parent.getItemAtPosition(position).toString());
                ((TextView) parent.getChildAt(0)).setTextColor(Color.TRANSPARENT);

                if (parent.getItemAtPosition(position).toString().equals("Gender")) {
                    genderEditText.setTextColor(getResources().getColor(R.color.light_gray));
                } else {
                    genderEditText.setTextColor(getResources().getColor(R.color.white));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Registering ...");
    }

    public void loginBtnTapped(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void showSpinnerError(String message) {
        new AlertDialog.Builder(this).setMessage(message)
                .setPositiveButton("Ok", (dialog, which) -> { })
                .show();
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
            showSpinnerError("Please select state of residence");
        } else if (gender.isEmpty() || gender.equals("Select Gender")) {
            showSpinnerError("Please select gender");
        } else if (age.isEmpty() || age.equals("Select Age")) {
            showSpinnerError("Please select age");
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

    public void registerContinueBtnTapped(View view) {

        if(App.isNetworkAvailable(RegisterActivity.this)){
            String firstName = firstNameEdt.getText().toString().trim();
            String lastName = lastNameEdt.getText().toString().trim();
            String email = emailEdt.getText().toString().trim();
            String password = passwordEdt.getText().toString().trim();
            String state = stateEditText.getText().toString().trim();
            String gender = genderEditText.getText().toString().trim();
            String age = ageEditText.getText().toString().trim();

            if (validateRegister(firstName, lastName, email, password, state, gender, age)) { return; }

            LinkedHashMap<String, String> params = getParameters(firstName, lastName, email, password, state, gender, age);
            String url = new Api().generateUrl(Api.register, params);
            Type type = new TypeToken<APIResponse>(){}.getType();

            progressDialog.show();
            new NetworkRequest<APIResponse>().post(this, type, url, (status, error) -> {
                progressDialog.dismiss();
                if (error != null && status == null) {
                    showError(error.getMessage());
                } else if (status.getStatus().equals("failed")) {
                    showError(status.getMsg());
                } else {
                    Toast.makeText(this, "Successfully Registered", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this, LoginActivity.class));
                }
            });
        }else{
            Toast.makeText(RegisterActivity.this,"You are working offline",Toast.LENGTH_LONG).show();
        }

    }

    private void showError(String message) {
        new AlertDialog.Builder(this).setMessage(message)
                .setPositiveButton("Ok", (dialog, which) -> { })
                .show();
    }
}
