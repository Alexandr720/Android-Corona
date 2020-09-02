package com.stepin.coronaapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.stepin.coronaapp.R;

public class ChvMotherDetailActivity  extends AppCompatActivity {

    private String title_str,name_str,age_str,tel_num_str,nhif_str,mother_id_str,village_str,ward_str,due_date_str,folic_str,mary_str,children_str,remark_str;
    private TextView title,name,age,tel_num,nhif,mother_id,village,ward,due_date,folic,mary,children,remark;
    private RadioButton clinic1,clinic2;
    private String clinic_str = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chv_mother_detail);
        title = findViewById(R.id.title);
        name = findViewById(R.id.name);
        tel_num = findViewById(R.id.tel_num);
        age = findViewById(R.id.age);
        nhif = findViewById(R.id.nhif);
        mother_id = findViewById(R.id.mother_id);
        village = findViewById(R.id.village);
        ward = findViewById(R.id.ward);
        due_date = findViewById(R.id.due_date);
        folic = findViewById(R.id.folic);
        mary = findViewById(R.id.mary);
        children = findViewById(R.id.children);
        remark = findViewById(R.id.remark);

        clinic1 = findViewById(R.id.clinic1);
        clinic2 = findViewById(R.id.clinic2);

        try{
            title_str = getIntent().getStringExtra("title");
        }catch (Exception e){
            title_str = "";
        }
        try{
            name_str = getIntent().getStringExtra("name");
        }catch (Exception e){
            name_str = "";
        }
        try{
            age_str = getIntent().getStringExtra("age");
        }catch (Exception e){
            age_str = "";
        }
        try{
            tel_num_str = getIntent().getStringExtra("tel_num");
        }catch (Exception e){
            tel_num_str = "";
        }
        try{
            nhif_str = getIntent().getStringExtra("nhif");
        }catch (Exception e){
            nhif_str = "";
        }
        try{
            mother_id_str = getIntent().getStringExtra("mother_id");
        }catch (Exception e){
            mother_id_str = "";
        }
        try{
            village_str = getIntent().getStringExtra("village");
        }catch (Exception e){
            village_str = "";
        }
        try{
            ward_str = getIntent().getStringExtra("ward");
        }catch (Exception e){
            ward_str = "";
        }
        try{
            due_date_str = getIntent().getStringExtra("due_date");
        }catch (Exception e){
            due_date_str = "";
        }


        try{
            folic_str = getIntent().getStringExtra("folic");
        }catch (Exception e){
            folic_str = "";
        }
        try{
            mary_str = getIntent().getStringExtra("mary");
        }catch (Exception e){
            mary_str = "";
        }
        try{
            children_str = getIntent().getStringExtra("children");
        }catch (Exception e){
            children_str = "";
        }
        try{
            clinic_str = getIntent().getStringExtra("clinic");
        }catch (Exception e){
            clinic_str = "";
        }
        if(clinic_str.equalsIgnoreCase("Yes")){
            clinic1.setChecked(true);
            clinic2.setChecked(false);
        }else{
            clinic1.setChecked(false);
            clinic2.setChecked(true);
        }

        try{
            remark_str = getIntent().getStringExtra("remark");
        }catch (Exception e){
            remark_str = "";
        }



        title.setText(title_str);
        name.setText(name_str);
        tel_num.setText(tel_num_str);
        age.setText(age_str);
        nhif.setText(nhif_str);
        mother_id.setText(mother_id_str);
        due_date.setText(due_date_str);
        folic.setText(folic_str);
        mary.setText(mary_str);
        children.setText(children_str);
        remark.setText(remark_str);
        village.setText(village_str);
        ward.setText(ward_str);

    }
    public void backBtnTapped(View v){
        finish();
    }
    @Override
    public void onBackPressed(){
        finish();
    }
}