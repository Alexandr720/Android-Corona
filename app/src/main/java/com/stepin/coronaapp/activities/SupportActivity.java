package com.stepin.coronaapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.stepin.coronaapp.R;
import com.stepin.coronaapp.api.Api;
import com.stepin.coronaapp.model.User;
import com.stepin.coronaapp.model.Utils;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class SupportActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        TextView emailTxt = findViewById(R.id.supportEmailTxt);
        TextView numberTxt = findViewById(R.id.supportNumberTxt);
        TextView whatsAppTxt = findViewById(R.id.supportWtTxt);

        emailTxt.setText(Api.email);
        numberTxt.setText(Api.number);
        whatsAppTxt.setText(Api.number);

        setUserInformation();
    }

    private void setUserInformation() {
        TextView nameTxt = findViewById(R.id.textView1415);
        TextView timeTxt = findViewById(R.id.textView1511);
        CircularImageView imageView = findViewById(R.id.circularImageView2);

        User user = new User().getUser(this);
        if (user == null) {
            nameTxt.setVisibility(View.INVISIBLE);
            timeTxt.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.INVISIBLE);
            return;
        }
        
        timeTxt.setText(user.getData().getState() + " " + new Utils().getTime());
        String sourceString = "Welcome " + "<b>" + user.getData().getFirstName() + " " + user.getData().getLastName() + "</b> ";
        nameTxt.setText(Html.fromHtml(sourceString));

        if (user.getData().getProfileImage() != null && !user.getData().getProfileImage().isEmpty()) {
            String url = Api.profileImageUrl + user.getData().getProfileImage();
            Picasso.get().load(url).placeholder(R.drawable.profile_pic).into(imageView);
        }
    }

    public void callBtnTapped(View view) {
        Intent i = new Intent(Intent.ACTION_DIAL,Uri.fromParts("tel",Api.number,null));
        startActivity(i);
    }

    public void emailBtnTapped(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO,Uri.parse("mailto:"+Api.email));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        startActivity(Intent.createChooser(emailIntent,"choose Title"));
    }

    public void whatsAppBtnTapped(View view) {
        String url = "https://api.whatsapp.com//send?phone="+ Api.number;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    public void backBtnTapped(View view) {
        finish();
    }

    public void liveChatBtnTapped(View view) {
        if (new User().getUser(this) == null) { Toast.makeText(this, "Please login to chat", Toast.LENGTH_SHORT).show(); }
        else { startActivity(new Intent(this, MessageActivity.class)); }
    }
}
