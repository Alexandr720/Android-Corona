package com.stepin.coronaapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.stepin.coronaapp.App;
import com.stepin.coronaapp.R;
import com.stepin.coronaapp.api.Api;
import com.stepin.coronaapp.api.NetworkRequest;
import com.stepin.coronaapp.model.APIResponse;
import com.stepin.coronaapp.model.User;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_activity);


        startTimer();

//        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(SplashActivity.this, instanceIdResult -> {
//            String token = instanceIdResult.getToken();
//            SharedPreferences.Editor editor = getSharedPreferences(Utils.sharedPerfName, MODE_PRIVATE).edit();
//            editor.putString("token", token);
//            editor.apply();
//
//            saveToken(token);
//        });

    }

    private void saveToken(String token) {
        if(App.isNetworkAvailable(SplashActivity.this)){
            User user = new User().getUser(this);
            if (user == null) { return; }
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            map.put("user_id", String.valueOf(user.getData().getId()));
            map.put("token", token);

            Log.d("token", token);
            String url = new Api().generateUrl(Api.token, map);

            Type type = new TypeToken<APIResponse>(){}.getType();
            new NetworkRequest<APIResponse>().post(this, type, url, (obj, error) -> {
            });
        }else{
            Toast.makeText(SplashActivity.this,"You are working offline", Toast.LENGTH_LONG).show();
        }

    }

    private void startTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, CoronaSelectScreen.class));
                finish();
            }
        },4000);

    }
}
