package com.stepin.coronaapp.activities;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.stepin.coronaapp.App;
import com.stepin.coronaapp.R;
import com.stepin.coronaapp.api.Api;
import com.stepin.coronaapp.api.NetworkRequest;
import com.stepin.coronaapp.model.APIResponse;
import com.stepin.coronaapp.model.User;
import com.stepin.coronaapp.model.Utils;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;

public class LoginActivity extends AppCompatActivity {

    EditText emailEdt, passwordEdt;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setProgressDialog();

        emailEdt = findViewById(R.id.loginEmailEdt);
        passwordEdt = findViewById(R.id.passwordLoginEdt);

    }

    private void setProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Logging in");
    }

    private void showError(String message) {
        new AlertDialog.Builder(this).setMessage(message)
                .setPositiveButton("Ok", (dialog, which) -> { })
                .show();
    }

    private boolean validateLogin(String email, String password) {

        if (email.isEmpty()) {
            emailEdt.setError("Please enter email");
            emailEdt.requestFocus();
        } else if (!new Utils().isValidEmail(email)) {
            emailEdt.setError("Please enter valid email");
            emailEdt.requestFocus();
        } else if (password.isEmpty()) {
            passwordEdt.setError("Please enter password");
            passwordEdt.requestFocus();
        } else {
            return false;
        }

        return true;
    }

    public void continueBtnTapped(View view) {

        if(App.isNetworkAvailable(LoginActivity.this)){
            String email = emailEdt.getText().toString().trim();
            String password = passwordEdt.getText().toString().trim();

            if (validateLogin(email, password)) { return; }

            LinkedHashMap<String, String> params = new LinkedHashMap<>();
            params.put("email", email);
            params.put("password", password);

            String url = new Api().generateUrl(Api.login, params);
            Type type = new TypeToken<User>(){}.getType();

            progressDialog.setMessage("Logging in");
            progressDialog.show();
            new NetworkRequest<User>().post(this, type, url, (user, error) -> {
                progressDialog.dismiss();
                if (error != null && user == null) {
                    showError(error.getMessage());
                } else if (user.getStatus().equals("failed")) {
                    showError(user.getMsg());
                } else {
                    saveUserData(user);
                    SharedPreferences.Editor editor = getSharedPreferences(Utils.sharedPerfName, MODE_PRIVATE).edit();
                    editor.putString("password",password);
                    editor.apply();

                }
            });
        }else{
            Toast.makeText(LoginActivity.this,"You are working offline",Toast.LENGTH_LONG).show();
        }

    }

    private void saveUserData(User user) {
        SharedPreferences.Editor editor = getSharedPreferences(Utils.sharedPerfName, MODE_PRIVATE).edit();
        editor.putInt("id", user.getData().getId());
        editor.putString("first_name", user.getData().getFirstName());
        editor.putString("last_name", user.getData().getLastName());
        editor.putString("email", user.getData().getEmail());
        editor.putString("gender", user.getData().getGender());
        editor.putInt("age", user.getData().getAge());
        editor.putString("enforce_level",user.getData().getEnforce_level());
        editor.putString("gover_level",user.getData().getGover_level());
        editor.putString("border_level",user.getData().getBorder_level());
        editor.putString("gbv_level",user.getData().getGbv_level());
        editor.putString("chv_level",user.getData().getChv_level());
        editor.putString("state", user.getData().getState());
        editor.putString("profile_image", user.getData().getProfileImage());
        editor.putString("conversation_id", user.getConversationId());
        editor.putInt("collection_id", user.getData().getCollection_id());

        editor.apply();

        Intent i = new Intent(this, ProfileActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);

        SharedPreferences prefs = getSharedPreferences(Utils.sharedPerfName, MODE_PRIVATE);
        String token = prefs.getString("token", null);

        if (token != null) {  saveToken(token, String.valueOf(user.getData().getId()));  }
    }

    private void saveToken(String token, String id) {
        if(App.isNetworkAvailable(LoginActivity.this)){
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            map.put("user_id", id);
            map.put("token", token);

            Log.d("token", token);
            String url = new Api().generateUrl(Api.token, map);

            Type type = new TypeToken<APIResponse>(){}.getType();
            new NetworkRequest<APIResponse>().post(this, type, url, (obj, error) -> {
            });
        }else{
            Toast.makeText(LoginActivity.this,"You are working offline",Toast.LENGTH_LONG).show();
        }

    }

    public void forgotBtnTapped(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter email");

        final View customLayout = getLayoutInflater().inflate(R.layout.alert_dialog_input_layout, null);
        builder.setView(customLayout);

        builder.setPositiveButton("Ok", (dialog, which) -> {
            EditText forgotEmailEdt = customLayout.findViewById(R.id.alertEditText);
            String forgotEmail = forgotEmailEdt.getText().toString().trim();
            if (forgotEmail.isEmpty()) {
                forgotEmailEdt.setError("Please enter your email");
            } else if (!new Utils().isValidEmail(forgotEmail)){
                forgotEmailEdt.setError("Please enter valid email");
            } else {
                dialog.dismiss();
                sendForgotEmailRequest(forgotEmail);
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        builder.show();

    }

    private void sendForgotEmailRequest(String email) {
        if(App.isNetworkAvailable(LoginActivity.this)){
            LinkedHashMap<String, String> params = new LinkedHashMap<>();
            params.put("email", email);

            String url = new Api().generateUrl(Api.forgot, params);
            Type type = new TypeToken<APIResponse>(){}.getType();

            progressDialog.setMessage("Sending email ...");
            progressDialog.show();

            new NetworkRequest<APIResponse>().post(this, type, url, (status, error) -> {
                progressDialog.dismiss();
                if (error != null && status == null) {
                    showError(error.getMessage());
                } else {
                    showError(status.getMsg());
                }
            });
        }else{
            Toast.makeText(LoginActivity.this,"You are working offline",Toast.LENGTH_LONG).show();
        }


    }

    public void registerBtnTapped(View view) {
        finish();
    }

    public void btnTappedGoogle(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO,Uri.parse("mailto:"+Api.email));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        startActivity(Intent.createChooser(emailIntent,"Choose Title"));
    }

    public void btnTappedTwiter(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Api.twitter)));
    }
    public void btnTappedFacebook(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Api.facebook)));
    }
}
