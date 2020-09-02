package com.stepin.coronaapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.stepin.coronaapp.R;

public class ChvVisitDetailActivity  extends AppCompatActivity {

    private String id_num_str,title_str,name_str,age_str,nhif_str,village_str,ward_str,nearname_str,mask_str,provide_str,mal_str,diabet_str,hyper_str,tb_str,indicate_str,remark_str;
    private TextView id_num,title,name,age,nhif,village,ward,nearname,mask,provide,mal,diabet,hyper,tb,indicate,remark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chv_visit_detail);
        title = findViewById(R.id.title);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        nhif = findViewById(R.id.nhif);
        village = findViewById(R.id.village);
        ward = findViewById(R.id.ward);
        nearname = findViewById(R.id.nearname);
        mask = findViewById(R.id.mask);
        provide = findViewById(R.id.provide);
        mal = findViewById(R.id.mal);
        diabet = findViewById(R.id.diabet);
        hyper = findViewById(R.id.hyper);
        tb = findViewById(R.id.tb);
        indicate = findViewById(R.id.indicate);
        remark = findViewById(R.id.remark);
        id_num = findViewById(R.id.id_num);

        try{
            title_str = getIntent().getStringExtra("title");
        }catch (Exception e){
            title_str = "";
        }
        try{
            id_num_str = getIntent().getStringExtra("id_num");
        }catch (Exception e){
            id_num_str = "";
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
            nhif_str = getIntent().getStringExtra("nhif");
        }catch (Exception e){
            nhif_str = "";
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
            nearname_str = getIntent().getStringExtra("nearname");
        }catch (Exception e){
            nearname_str = "";
        }
        try{
            mask_str = getIntent().getStringExtra("mask");
        }catch (Exception e){
            mask_str = "";
        }



        try{
            provide_str = getIntent().getStringExtra("provide");
        }catch (Exception e){
            provide_str = "";
        }
        try{
            mal_str = getIntent().getStringExtra("mal");
        }catch (Exception e){
            mal_str = "";
        }
        try{
            diabet_str = getIntent().getStringExtra("diabet");
        }catch (Exception e){
            diabet_str = "";
        }
        try{
            hyper_str = getIntent().getStringExtra("hyper");
        }catch (Exception e){
            hyper_str = "";
        }
        try{
            tb_str = getIntent().getStringExtra("tb");
        }catch (Exception e){
            tb_str = "";
        }
        try{
            indicate_str = getIntent().getStringExtra("indicate");
        }catch (Exception e){
            indicate_str = "";
        }
        try{
            remark_str = getIntent().getStringExtra("remark");
        }catch (Exception e){
            remark_str = "";
        }

        title.setText(title_str);
        name.setText(name_str);
        age.setText(age_str);
        nhif.setText(nhif_str);
        village.setText(village_str);
        nearname.setText(nearname_str);
        mask.setText(mask_str);
        provide.setText(provide_str);
        mal.setText(mal_str);
        diabet.setText(diabet_str);
        hyper.setText(hyper_str);
        tb.setText(tb_str);
        indicate.setText(indicate_str);
        remark.setText(remark_str);
        id_num.setText(id_num_str);
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