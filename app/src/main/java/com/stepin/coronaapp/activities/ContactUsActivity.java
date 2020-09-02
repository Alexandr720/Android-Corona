package com.stepin.coronaapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.stepin.coronaapp.R;
import com.stepin.coronaapp.api.Api;
import com.stepin.coronaapp.model.User;
import com.stepin.coronaapp.model.Utils;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

public class ContactUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        setUserInformation();
    }

    private void setUserInformation() {
        TextView nameTxt = findViewById(R.id.textView1410);
        TextView timeTxt = findViewById(R.id.textView1511);

        User user = new User().getUser(this);
        timeTxt.setText(user.getData().getState() + " " + new Utils().getTime());

        String sourceString = "Welcome " + "<b>" + user.getData().getFirstName() + " " + user.getData().getLastName() + "</b> ";
        nameTxt.setText(Html.fromHtml(sourceString));

        CircularImageView circularImageView = findViewById(R.id.circularImageView2);
        if (user.getData().getProfileImage() != null && !user.getData().getProfileImage().isEmpty()) {
            String url = Api.profileImageUrl + user.getData().getProfileImage();
            Picasso.get().load(url).placeholder(R.drawable.profile_pic).into(circularImageView);
        }
    }

    public void bactTo(View view) {
        finish();
    }

    public void fbImageTapped(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Api.facebook)));
    }

    public void twitterImageTapped(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Api.twitter)));
    }

    public void gPlusBtnTapped(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Api.googlePlus)));
    }
}
