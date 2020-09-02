package com.stepin.coronaapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stepin.coronaapp.R;
import com.stepin.coronaapp.api.Api;
import com.stepin.coronaapp.model.User;
import com.stepin.coronaapp.model.Utils;
import com.google.android.material.navigation.NavigationView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView nameTxt, stateTxt, timeTxt, navTime;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    String state = "";
    User user;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        user = new User().getUser(this);

        toolbar = findViewById(R.id.toolbar2);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigationView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        navigationView.setItemTextColor(ColorStateList.valueOf(Color.BLACK));
        navigationView.setItemIconTintList(null);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerOpen,R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //makeResponsive();
        setUserInformation();
    }

    private double getInch() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        double wi=(double)width/(double)dm.xdpi;
        double hi=(double)height/(double)dm.ydpi;
        double x = Math.pow(wi,2);
        double y = Math.pow(hi,2);
        return Math.sqrt(x+y);
    }

    private void makeResponsive() {

        ImageView infImageView = findViewById(R.id.imageView46);
        ImageView reportImageView = findViewById(R.id.imageView47);
        ImageView locationView = findViewById(R.id.imageView44);
        ImageView supportView = findViewById(R.id.imageView45);

        if (getInch() > 5.5) {

            LinearLayout.LayoutParams infConst = (LinearLayout.LayoutParams) infImageView.getLayoutParams();
            infConst.height = 400;
            infImageView.setLayoutParams(infConst);

            LinearLayout.LayoutParams reportConst = (LinearLayout.LayoutParams) reportImageView.getLayoutParams();
            reportConst.height = 400;
            reportImageView.setLayoutParams(reportConst);

            LinearLayout.LayoutParams locConst = (LinearLayout.LayoutParams) locationView.getLayoutParams();
            locConst.height = 400;
            locationView.setLayoutParams(locConst);

            LinearLayout.LayoutParams supportConst = (LinearLayout.LayoutParams) supportView.getLayoutParams();
            supportConst.height = 400;
            supportView.setLayoutParams(supportConst);

        }

    }

    private void setUserInformation() {
        nameTxt = findViewById(R.id.textView24);
        stateTxt = findViewById(R.id.textView26);
        timeTxt = findViewById(R.id.profileStateTxt);

        TextView navName = navigationView.getHeaderView(0).findViewById(R.id.navNameTxt);
        navTime = navigationView.getHeaderView(0).findViewById(R.id.navTimeTxt);

        User user = new User().getUser(this);
        nameTxt.setText(user.getData().getFirstName() + " " + user.getData().getLastName());
        stateTxt.setText(user.getData().getState());
        timeTxt.setText(user.getData().getState() + " " + new Utils().getTime());

        navName.setText(user.getData().getFirstName() + " " + user.getData().getLastName());
        navTime.setText(user.getData().getState() + " " + new Utils().getTime());
        state = user.getData().getState();

        CircularImageView navCircularImageView = navigationView.getHeaderView(0).findViewById(R.id.profilePic);
        CircularImageView circularImageView = findViewById(R.id.circularImageView);
        if (user.getData().getProfileImage() != null && !user.getData().getProfileImage().isEmpty()) {
            String url = Api.profileImageUrl + user.getData().getProfileImage();
            Picasso.get().load(url).placeholder(R.drawable.profile_pic).into(circularImageView);
            Picasso.get().load(url).placeholder(R.drawable.profile_pic).into(navCircularImageView);
        }
    }

    public void gotoEnforce(View view){
        String level = user.getData().getEnforce_level();
        if(level.equalsIgnoreCase("0")){
            Toast.makeText(ProfileActivity.this,"You can't access this page",Toast.LENGTH_LONG).show();
        }else if(level.equalsIgnoreCase("1")){
            startActivity(new Intent(this, EnforceActivity.class));
        }
    }
    public void gotoTracing(View view){
        startActivity(new Intent(this, TracingActivity.class));
    }
    public void informationCenterTapped(View view) {
        startActivity(new Intent(this, InformationCenterActivity.class));
    }

    public void reportCenterTapped(View view) {
        Intent intent = new Intent(this, ShowCurrentLocationActivity.class);
        intent.putExtra("type", "Report");
        startActivity(intent);
    }

    public void locationCheckInTapped(View view) {
        Intent intent = new Intent(this, ShowCurrentLocationActivity.class);
        intent.putExtra("type", "Check In");
        startActivity(intent);
    }

    public void onChv(View view){
        if(user.getData().getChv_level().equalsIgnoreCase("0")){
            Toast.makeText(ProfileActivity.this,"You can't access this page",Toast.LENGTH_LONG).show();
        }else if(user.getData().getChv_level().equalsIgnoreCase("1")){
            startActivity(new Intent(this, ChvActivity.class));
        }
    }
    public void onGbv(View view){
        if(user.getData().getGbv_level().equalsIgnoreCase("0")){
            Toast.makeText(ProfileActivity.this,"You can't access this page",Toast.LENGTH_LONG).show();
        }else if(user.getData().getGbv_level().equalsIgnoreCase("1")){
            startActivity(new Intent(this, GbvActivity.class));
        }
    }
    public void supportCenterTapped(View view) {
        startActivity(new Intent(this, SupportActivity.class));
    }

    public void profileAboutUsTapped(View view) {
        startActivity(new Intent(ProfileActivity.this,AboutUsActivity.class));
    }

    public void profileContactUsTapped(View view) {
        startActivity(new Intent(ProfileActivity.this,ContactUsActivity.class));
    }

    public void profileTermsOfUseTapped(View view) {
        Intent intent = new Intent(ProfileActivity.this,TermsOfUseActivity.class);
        intent.putExtra("type", "Terms Of Services");
        startActivity(intent);
    }

    public void profileFacebookTapped(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Api.facebook)));
    }

    public void profileTwitterTapped(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Api.twitter)));
    }

    public void profileInstaTapped(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Api.insta)));
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                startActivity(new Intent(ProfileActivity.this, UpdateProfileActivity.class));
                break;
            case R.id.setting:
                startActivity(new Intent(ProfileActivity.this, SettingActivity.class));
                break;
            case R.id.local:
                startActivity(new Intent(ProfileActivity.this, LocalActivity.class));
                break;
//            case R.id.Message:
//                startActivity(new Intent(ProfileActivity.this, MessageActivity.class));
//                break;
//            case R.id.notification:
//                startActivity(new Intent(ProfileActivity.this,Notification.class));
//                break;
            case R.id.logout:
                SharedPreferences preferences = getSharedPreferences(Utils.sharedPerfName, MODE_PRIVATE);
                preferences.edit().remove("id").apply();
                preferences.edit().remove("enforce_level").apply();
                preferences.edit().remove("gover_level").apply();
                preferences.edit().remove("border_level").apply();
                preferences.edit().remove("gbv_level").apply();
                preferences.edit().remove("chv_level").apply();

                Intent i = new Intent(this, CoronaSelectScreen.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            default:
                break;
        }
        return false;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.profile)
        {
            startActivity(new Intent(ProfileActivity.this, UpdateProfileActivity.class));

        }
        if(item.getItemId()==R.id.setting)
        {
            startActivity(new Intent(ProfileActivity.this, SettingActivity.class));

        }
        if(item.getItemId()==R.id.local)
        {
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));

        }

//        if(item.getItemId()==R.id.Message)
//        {
//            startActivity(new Intent(ProfileActivity.this, MessageActivity.class));
//        }

//        if(item.getItemId()==R.id.notification)
//        {
//            Intent i = new Intent(ProfileActivity.this, Notification.class);
//            startActivity(i);
//
//        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!state.isEmpty()) {
            timeTxt.setText(state + " " + new Utils().getTime());
            navTime.setText(state + " " + new Utils().getTime());
        }
    }
}
