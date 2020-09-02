package com.stepin.coronaapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.stepin.coronaapp.App;
import com.stepin.coronaapp.R;
import com.stepin.coronaapp.adapter.InformationAdapter;
import com.stepin.coronaapp.api.Api;
import com.stepin.coronaapp.api.NetworkRequest;
import com.stepin.coronaapp.model.InformationCenterResponse;
import com.stepin.coronaapp.model.User;
import com.stepin.coronaapp.model.Utils;
import com.google.gson.reflect.TypeToken;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class InformationCenterActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView informationListView;
    InformationAdapter adapter;
    ArrayList<InformationCenterResponse.Data> items;
    TextView nameTxt, timeTxt;
    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_center);

//        setItems();

        items = new ArrayList<>();
        informationListView = findViewById(R.id.informationListView);
        adapter = new InformationAdapter(this, items);
        informationListView.setAdapter(adapter);
        informationListView.setOnItemClickListener(this);
        bar = findViewById(R.id.infProgressBar);

        setUserInformation();
        getInformationDetails();
    }

    private void getInformationDetails() {
        if(App.isNetworkAvailable(InformationCenterActivity.this)){
            String url = new Api().generateUrl(Api.informationCenter, new LinkedHashMap<>());
            Type type = new TypeToken<InformationCenterResponse>(){}.getType();

            new NetworkRequest<InformationCenterResponse>().fetch(this, type, url, (obj, error) -> {
                bar.setVisibility(View.GONE);
                if (error == null && obj != null) {
                    items = (ArrayList<InformationCenterResponse.Data>) obj.getInformationCenter();
                    adapter.updateItems(items);
                }
            });
        }else{
            Toast.makeText(InformationCenterActivity.this,"You are working offline",Toast.LENGTH_LONG).show();
        }

    }

    private void setUserInformation() {
        nameTxt = findViewById(R.id.textView14);
        timeTxt = findViewById(R.id.textView15);

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

//    private void setItems() {
//        items = new ArrayList<>();
//        items.add(new InformationCenter("Am I at risk?", R.drawable.viruslogo));
//        items.add(new InformationCenter("How can I Help?", R.drawable.stethoscope));
//        items.add(new InformationCenter("Latest News", R.drawable.mask));
//        items.add(new InformationCenter("Latest Updates", R.drawable.medical));
//        items.add(new InformationCenter("Trends", R.drawable.trend));
//        items.add(new InformationCenter("Get Help Here", R.drawable.help));
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent;
        String content = items.get(position).getContent();
        switch (items.get(position).getTitle().toLowerCase()) {
            case "news":
                startActivity(new Intent(this, LatestNewsActivity.class));
                break;
            case "am i at risk?":
                startActivity(new Intent(this, IamAtRiskActivity.class));
                break;
            case "how can i help?":
                intent = new Intent(this, TermsOfUseActivity.class);
                intent.putExtra("type","How can I Help?");
                intent.putExtra("content", content == null ? "" : content);
                startActivity(intent);
                break;
            case "latest updates":
                intent = new Intent(this, TermsOfUseActivity.class);
                intent.putExtra("type","Latest Updates");
                intent.putExtra("content", content == null ? "" : content);
                startActivity(intent);
                break;
            case "trends":
                intent = new Intent(this, TermsOfUseActivity.class);
                intent.putExtra("type","Trends");
                startActivity(intent);
                intent.putExtra("content", content == null ? "" : content);
                break;
            case "get help here":
                intent = new Intent(this, TermsOfUseActivity.class);
                intent.putExtra("type","Get Help Here");
                intent.putExtra("content", content == null ? "" : content);
                startActivity(intent);
                break;
            default:
                Log.d("InformationCenter", "No Item found");
        }
    }

    public void backBtnTapped(View view) {
        finish();
    }

    public void informationCenterWhatsAppTapped(View view) {
        String url = "https://api.whatsapp.com//send?phone="+ Api.number;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
