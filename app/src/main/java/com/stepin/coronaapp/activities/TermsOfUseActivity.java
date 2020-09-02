package com.stepin.coronaapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.stepin.coronaapp.R;
import com.stepin.coronaapp.api.Api;
import com.stepin.coronaapp.model.User;
import com.stepin.coronaapp.model.Utils;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

public class TermsOfUseActivity extends AppCompatActivity {

    Intent intent;
    TextView mainTitle, contentTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_use);

        mainTitle = findViewById(R.id.title);
        contentTxt = findViewById(R.id.text2);
        setUserInformation();


        intent = getIntent();
        String pagetitle= intent.getStringExtra("type");
        mainTitle.setText(pagetitle);

        if (!TextUtils.isEmpty(getIntent().getStringExtra("content"))) {
            String content = getIntent().getStringExtra("content");
            contentTxt.setText(TextUtils.isEmpty(content) ? "" : content);
        }
    }

    private void setUserInformation() {
        TextView nameTxt = findViewById(R.id.textView114);
        TextView timeTxt = findViewById(R.id.textView115);

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

    public void backActivity(View view) {
        finish();
    }
}
