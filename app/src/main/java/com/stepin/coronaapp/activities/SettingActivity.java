package com.stepin.coronaapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.stepin.coronaapp.R;
import com.stepin.coronaapp.api.Api;
import com.stepin.coronaapp.model.User;
import com.stepin.coronaapp.model.Utils;
import com.google.android.material.navigation.NavigationView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

public class SettingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {


    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
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


        setUserInformation();
    }

    private void setUserInformation() {
        TextView nameTxt = findViewById(R.id.textView47);
        TextView timeTxt = findViewById(R.id.textView48);

        TextView navName = navigationView.getHeaderView(0).findViewById(R.id.navNameTxt);
        TextView navTime = navigationView.getHeaderView(0).findViewById(R.id.navTimeTxt);

        User user = new User().getUser(this);
        nameTxt.setText(user.getData().getFirstName() + " " + user.getData().getLastName());
        timeTxt.setText(user.getData().getState() + " " + new Utils().getTime());

        navName.setText(user.getData().getFirstName() + " " + user.getData().getLastName());
        navTime.setText(user.getData().getState() + " " + new Utils().getTime());

        CircularImageView navCircularImageView = navigationView.getHeaderView(0).findViewById(R.id.profilePic);
        CircularImageView circularImageView = findViewById(R.id.circularImageView3);
        if (user.getData().getProfileImage() != null && !user.getData().getProfileImage().isEmpty()) {
            String url = Api.profileImageUrl + user.getData().getProfileImage();
            Picasso.get().load(url).placeholder(R.drawable.profile_pic).into(circularImageView);
            Picasso.get().load(url).placeholder(R.drawable.profile_pic).into(navCircularImageView);
        }
    }

//    public void goToNotification(View view) {
//        startActivity( new Intent(SettingActivity.this,Notification.class));
//    }

    public void settingAboutUsTapped(View view) {
        startActivity(new Intent(this, AboutUsActivity.class));
    }

    public void settingContactUsTapped(View view) {
        startActivity(new Intent(this, ContactUsActivity.class));
    }

    public void settingTermsOfUseTapped(View view) {
        startActivity(new Intent(this, TermsOfUseActivity.class));
    }

    public void settingFacebookTapped(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Api.facebook)));
    }

    public void settingTwitterTapped(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Api.twitter)));
    }

    public void settingInstaTapped(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Api.insta)));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                finish();
                break;
            case R.id.setting:
                startActivity(new Intent(SettingActivity.this, SettingActivity.class));
                break;
            case R.id.local:
                startActivity(new Intent(SettingActivity.this, LocalActivity.class));
                break;
//            case R.id.Message:
//                startActivity(new Intent(SettingActivity.this, MessageActivity.class));
//                break;
//            case R.id.notification:
//                startActivity(new Intent(SettingActivity.this,Notification.class));
//                break;
            case R.id.logout:
                SharedPreferences preferences = getSharedPreferences(Utils.sharedPerfName, 0);
                preferences.edit().remove("enforce_level").apply();
                preferences.edit().remove("gover_level").apply();
                preferences.edit().remove("border_level").apply();
                preferences.edit().remove("gbv_level").apply();
                preferences.edit().remove("chv_level").apply();
                preferences.edit().remove("id").apply();
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
            startActivity(new Intent(SettingActivity.this, ProfileActivity.class));
        }
        if(item.getItemId()==R.id.setting)
        {
            startActivity(new Intent(SettingActivity.this, SettingActivity.class));

        }
        if(item.getItemId()==R.id.local)
        {
            startActivity(new Intent(SettingActivity.this, LoginActivity.class));

        }

//        if(item.getItemId()==R.id.Message)
//        {
//            startActivity(new Intent(SettingActivity.this, MessageActivity.class));
//        }
//        if(item.getItemId()==R.id.notification)
//        {
//            Intent i = new Intent(SettingActivity.this, Notification.class);
//            startActivity(i);
//
//        }

        return super.onOptionsItemSelected(item);
    }


    public void editProfileTapped(View view) {
        startActivity(new Intent(this, UpdateProfileActivity.class));
    }

    public void blockTapped(View view) {
        intent = new Intent(this, TermsOfUseActivity.class);
        intent.putExtra("help","Block");
        startActivity(intent);
    }

    public void helpTapped(View view) {
        intent = new Intent(this, TermsOfUseActivity.class);
        intent.putExtra("help","Help");
        startActivity(intent);
    }

    public void generaTapeed(View view) {
        intent = new Intent(this, TermsOfUseActivity.class);
        intent.putExtra("help","General");
        startActivity(intent);
    }

    public void privacyTapped(View view) {
        intent = new Intent(this, TermsOfUseActivity.class);
        intent.putExtra("help","Privacy");
        startActivity(intent);
    }

    public void accountTapped(View view) {
        intent = new Intent(this, TermsOfUseActivity.class);
        intent.putExtra("help","Account");
        startActivity(intent);
    }
}
