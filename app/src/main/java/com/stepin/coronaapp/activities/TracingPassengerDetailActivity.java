package com.stepin.coronaapp.activities;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.stepin.coronaapp.R;

public class TracingPassengerDetailActivity  extends AppCompatActivity {

    private String id_num_str,title_str,temp_str,vehicle_num_str,tel_num_str,seat_nums_str,from_village_str,to_village_str,location_str,contact_str,contact_num_str,history_last_str,infect_str;
    private TextView id_num,title,temp,vehicle_num,tel_num,seat_num,from_village,to_village,location,contact,contact_num,history,infect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracing_passenger_detail);
        title = findViewById(R.id.title);
        vehicle_num = findViewById(R.id.vehicle_num);
        tel_num = findViewById(R.id.phone_num);
        seat_num = findViewById(R.id.seat_num);
        from_village = findViewById(R.id.from_village);
        to_village = findViewById(R.id.to_village);
        location = findViewById(R.id.location);
        contact = findViewById(R.id.contact);
        contact_num = findViewById(R.id.contact_num);
        id_num = findViewById(R.id.id_num);
        history = findViewById(R.id.history);
        infect = findViewById(R.id.infect);
        temp = findViewById(R.id.temp);

        try{
            title_str = getIntent().getStringExtra("title");
        }catch (Exception e){
            title_str = "";
        }
        try{
            temp_str = getIntent().getStringExtra("temp");
        }catch (Exception e){
            temp_str = "";
        }
        try{
            id_num_str = getIntent().getStringExtra("id_num");
        }catch (Exception e){
            id_num_str = "";
        }
        try{
            vehicle_num_str = getIntent().getStringExtra("vehicle_num");
        }catch (Exception e){
            vehicle_num_str = "";
        }

        try{
            tel_num_str = getIntent().getStringExtra("tel");
        }catch (Exception e){
            tel_num_str = "";
        }
        try{
            seat_nums_str = getIntent().getStringExtra("seat_num");
        }catch (Exception e){
            seat_nums_str = "";
        }
        try{
            from_village_str = getIntent().getStringExtra("from_village");
        }catch (Exception e){
            from_village_str = "";
        }
        try{
            to_village_str = getIntent().getStringExtra("to_village");
        }catch (Exception e){
            to_village_str = "";
        }



        try{
            location_str = getIntent().getStringExtra("location");
        }catch (Exception e){
            location_str = "";
        }
        try{
            contact_str = getIntent().getStringExtra("contact");
        }catch (Exception e){
            contact_str = "";
        }
        try{
            contact_num_str = getIntent().getStringExtra("contact_num");
        }catch (Exception e){
            contact_num_str = "";
        }
        try{
            infect_str = getIntent().getStringExtra("infect");
        }catch (Exception e){
            infect_str = "";
        }
        try{
            history_last_str = getIntent().getStringExtra("history_last");
        }catch (Exception e){
            history_last_str = "";
        }

        title.setText(title_str);
        tel_num.setText(tel_num_str);
        vehicle_num.setText(vehicle_num_str);
        seat_num.setText(seat_nums_str);
        from_village.setText(from_village_str);
        to_village.setText(to_village_str);
        location.setText(location_str);
        contact.setText(contact_str);
        contact_num.setText(contact_num_str);
        id_num.setText(id_num_str);
        history.setText(history_last_str);
        infect.setText(infect_str);
        temp.setText(temp_str);

    }
    public void backBtnTapped(View v){
        finish();
    }
    @Override
    public void onBackPressed(){
        finish();
    }
}