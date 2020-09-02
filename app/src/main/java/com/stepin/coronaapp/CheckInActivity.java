package com.stepin.coronaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mikhaellopez.circularimageview.CircularImageView;

public class CheckInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);


        CircularImageView imageView = findViewById(R.id.circularImageView);
        imageView.bringToFront();
    }
}
