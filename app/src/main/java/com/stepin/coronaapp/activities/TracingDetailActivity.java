package com.stepin.coronaapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.stepin.coronaapp.R;

public class TracingDetailActivity  extends AppCompatActivity {

    private String title_str,card_num_str,service_type_str,tel_num_str,address_str,reg_num_str,destination_str;
    private TextView title,card_num,service_type,tel_num,address,reg_num,destination;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracing_detail);
        title = findViewById(R.id.title);
        card_num = findViewById(R.id.card_num);
        service_type = findViewById(R.id.service_type);
        tel_num = findViewById(R.id.tel_num);
        address = findViewById(R.id.address);
        reg_num = findViewById(R.id.reg_num);
        destination = findViewById(R.id.destination);

        try{
            title_str = getIntent().getStringExtra("title");
        }catch (Exception e){
            title_str = "";
        }
        try{
            card_num_str = getIntent().getStringExtra("card_num");
        }catch (Exception e){
            card_num_str = "";
        }
        try{
            service_type_str = getIntent().getStringExtra("type");
        }catch (Exception e){
            service_type_str = "";
        }
        try{
            tel_num_str = getIntent().getStringExtra("tel");
        }catch (Exception e){
            tel_num_str = "";
        }
        try{
            address_str = getIntent().getStringExtra("home");
        }catch (Exception e){
            address_str = "";
        }
        try{
            reg_num_str = getIntent().getStringExtra("vehicle_num");
        }catch (Exception e){
            reg_num_str = "";
        }
        try{
            destination_str = getIntent().getStringExtra("destination");
        }catch (Exception e){
            destination_str = "";
        }

        title = findViewById(R.id.title);
        card_num = findViewById(R.id.card_num);
        service_type = findViewById(R.id.service_type);
        tel_num = findViewById(R.id.tel_num);
        address = findViewById(R.id.address);
        reg_num = findViewById(R.id.reg_num);
        destination = findViewById(R.id.destination);

        title.setText(title_str);
        card_num.setText(card_num_str);
        service_type.setText(service_type_str);
        tel_num.setText(tel_num_str);
        address.setText(address_str);
        reg_num.setText(reg_num_str);
        destination.setText(destination_str);

    }
    public void backBtnTapped(View v){
        finish();
    }
    @Override
    public void onBackPressed(){
        finish();
    }
}
