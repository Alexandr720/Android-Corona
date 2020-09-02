package com.stepin.coronaapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.stepin.coronaapp.R;

public class GbvDetailActivity  extends AppCompatActivity {

    private String title_str,county_str,gender_str,age_str,date_str,place_str,status_str,remark_str,officer_str,nature_str;
    private TextView title,county,gender,age,date,place,status,remark,officer,nature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gbv_detail);
        title = findViewById(R.id.title);
        county = findViewById(R.id.county);
        gender = findViewById(R.id.gender);
        age = findViewById(R.id.age);
        date = findViewById(R.id.date);
        place = findViewById(R.id.place);
        status = findViewById(R.id.status);
        remark = findViewById(R.id.remark);
        officer = findViewById(R.id.officer);
        nature = findViewById(R.id.nature);


        try{
            title_str = getIntent().getStringExtra("title");
        }catch (Exception e){
            title_str = "";
        }
        try{
            county_str = getIntent().getStringExtra("county");
        }catch (Exception e){
            county_str = "";
        }
        try {
            nature_str = getIntent().getStringExtra("nature");
        }catch (Exception e){
            nature_str = "";
        }
        try{
            gender_str = getIntent().getStringExtra("gender");
        }catch (Exception e){
            gender_str = "";
        }
        try{
            age_str = getIntent().getStringExtra("age");
        }catch (Exception e){
            age_str = "";
        }
        try{
            date_str = getIntent().getStringExtra("report_date");
        }catch (Exception e){
            date_str = "";
        }
        try{
            status_str = getIntent().getStringExtra("status");
        }catch (Exception e){
            status_str = "";
        }
        try{
            place_str = getIntent().getStringExtra("report_place");
        }catch (Exception e){
            place_str = "";
        }
        try{
            remark_str = getIntent().getStringExtra("remark");
        }catch (Exception e){
            remark_str = "";
        }try{
            officer_str = getIntent().getStringExtra("officer_name");
        }catch (Exception e){
            officer_str = "";
        }
        title.setText(title_str);
        county.setText(county_str);
        gender.setText(gender_str);
        age.setText(age_str);
        date.setText(date_str);
        place.setText(place_str);
        status.setText(status_str);
        remark.setText(remark_str);
        officer.setText(officer_str);
        nature.setText(nature_str);

    }
    public void backBtnTapped(View v){
        finish();
    }
    @Override
    public void onBackPressed(){
        finish();
    }
}
