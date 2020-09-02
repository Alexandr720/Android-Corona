package com.stepin.coronaapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.stepin.coronaapp.App;
import com.stepin.coronaapp.R;
import com.stepin.coronaapp.adapter.NewsAdapter;
import com.stepin.coronaapp.api.Api;
import com.stepin.coronaapp.api.NetworkRequest;
import com.stepin.coronaapp.model.NewsData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class LatestNewsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView newsListView;
    NewsAdapter adapter;
    ArrayList<NewsData.Data> news;
    ProgressBar bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_news);

        newsListView = findViewById(R.id.newsListView);
        bar = findViewById(R.id.newsProgressBar);

        news = new ArrayList<>();
        adapter = new NewsAdapter(this, news);
        newsListView.setAdapter(adapter);
        getNews();

        newsListView.setOnItemClickListener(this);
    }

    private void showError(String message) {
        new AlertDialog.Builder(this).setMessage(message)
                .setPositiveButton("Ok", (dialog, which) -> { })
                .show();
    }

    private void getNews() {
        if(App.isNetworkAvailable(LatestNewsActivity.this)){
            String url = new Api().generateUrl(Api.news, new LinkedHashMap<>());
            Type type = new TypeToken<NewsData>(){}.getType();

            new NetworkRequest<NewsData>().fetch(this, type, url, (obj, error) -> {
                bar.setVisibility(View.GONE);
                if (error != null && obj == null) {
                    showError(error.getMessage());
                } else if (!obj.getStatus().equals("success")){
                    showError(obj.getMsg());
                } else {
                    news = (ArrayList<NewsData.Data>) obj.getData();
                    adapter.updateNews(news);
                }
            });
        }else{
            Toast.makeText(LatestNewsActivity.this,"You are working offline",Toast.LENGTH_LONG).show();
        }

    }

    public void backBtnTapped(View view) {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(this, NewsWebViewActivity.class);
        intent.putExtra("news", new Gson().toJson(news.get(position)));
        startActivity(intent);

//        try {
//            String url = news.get(position).getUrl();
//            if (!url.contains("http://")) { url = "http://" + url; }
//            Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setData(Uri.parse(url));
//            startActivity(i);
//        } catch (ActivityNotFoundException e) {
//            showError(e.getMessage());
//        }
    }
}
