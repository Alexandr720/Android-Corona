package com.stepin.coronaapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.stepin.coronaapp.App;
import com.stepin.coronaapp.R;
import com.stepin.coronaapp.api.Api;
import com.stepin.coronaapp.api.NetworkRequest;
import com.stepin.coronaapp.model.APIResponse;
import com.stepin.coronaapp.model.User;
import com.stepin.coronaapp.model.Utils;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;

public class CoronaSelectScreen extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corona_select_screen);
        user = new User().getUser(this);
    }

    public void citizenBtnClicked(View view) {
        if (user == null) {
            startActivity(new Intent(this, RegisterActivity.class));
        } else {
            if(App.isNetworkAvailable(CoronaSelectScreen.this)){
                SharedPreferences prefs = getSharedPreferences(Utils.sharedPerfName, MODE_PRIVATE);

                String email = user.getData().getEmail();
                String password = prefs.getString("password", "");

                LinkedHashMap<String, String> params = new LinkedHashMap<>();
                params.put("email", email);
                params.put("password", password);

                String url = new Api().generateUrl(Api.login, params);
                Type type = new TypeToken<User>(){}.getType();

                new NetworkRequest<User>().post(this, type, url, (user, error) -> {
                    if (error != null && user == null) {
                        showError(error.getMessage());
                    } else if (user.getStatus().equals("failed")) {
                        showError(user.getMsg());
                    } else {
                        saveUserData(user);
                    }
                });
            } else {
                Toast.makeText(CoronaSelectScreen.this,"You are working offline",Toast.LENGTH_LONG).show();
                Intent i = new Intent(this, ProfileActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }

        }
    }

    private void showError(String message) {
        new AlertDialog.Builder(this).setMessage(message)
                .setPositiveButton("Ok", (dialog, which) -> { })
                .show();
    }

    private void saveUserData(User user) {
        SharedPreferences.Editor editor = getSharedPreferences(Utils.sharedPerfName, MODE_PRIVATE).edit();
        editor.putString("first_name", user.getData().getFirstName());
        editor.putString("last_name", user.getData().getLastName());
        editor.putString("email", user.getData().getEmail());
        editor.putString("gender", user.getData().getGender());
        editor.putString("state", user.getData().getState());
        editor.putInt("age", user.getData().getAge());
        editor.putInt("id", user.getData().getId());
        editor.putString("conversation_id", user.getConversationId());
        editor.putString("profile_image", user.getData().getProfileImage());
        editor.putString("enforce_level",user.getData().getEnforce_level());
        editor.putString("gover_level",user.getData().getGover_level());
        editor.putString("border_level",user.getData().getBorder_level());
        editor.putString("gbv_level",user.getData().getGbv_level());
        editor.putString("chv_level",user.getData().getChv_level());
        editor.apply();

        Intent i = new Intent(this, ProfileActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);

        SharedPreferences prefs = getSharedPreferences(Utils.sharedPerfName, MODE_PRIVATE);
        String token = prefs.getString("token", null);

        if (token != null) {  saveToken(token, String.valueOf(user.getData().getId()));  }
    }

    private void saveToken(String token, String id) {
        if(App.isNetworkAvailable(CoronaSelectScreen.this)){
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            map.put("user_id", id);
            map.put("token", token);

            Log.d("token", token);
            String url = new Api().generateUrl(Api.token, map);

            Type type = new TypeToken<APIResponse>(){}.getType();
            new NetworkRequest<APIResponse>().post(this, type, url, (obj, error) -> {
            });
        }else{
            Toast.makeText(CoronaSelectScreen.this,"You are working offline",Toast.LENGTH_LONG).show();
        }

    }

    public void supportBtnClicked(View view) {
        startActivity(new Intent(this, SupportActivity.class));
    }
}
