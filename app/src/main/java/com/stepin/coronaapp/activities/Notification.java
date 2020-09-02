package com.stepin.coronaapp.activities;

import android.os.Bundle;
import android.view.View;

import com.stepin.coronaapp.R;

import androidx.appcompat.app.AppCompatActivity;

public class Notification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
    }

    public void closeNotification(View view) {
        finish();
    }
}
