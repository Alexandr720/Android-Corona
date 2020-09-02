package com.stepin.coronaapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.stepin.coronaapp.R;
import com.stepin.coronaapp.api.Api;
import com.stepin.coronaapp.model.NewsData;
import com.stepin.coronaapp.model.Utils;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


public class NewsWebViewActivity extends AppCompatActivity implements Player.EventListener {

    ImageView newsImageView;
    TextView titleTxt, timeTxt, descTxt;
    TextView websiteTxt, time2Txt;
    String url = "";
    PlayerView videoView;
    SimpleExoPlayer player;
    ProgressBar spinnerVideoDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_web_view);

        spinnerVideoDetails = findViewById(R.id.newsWebProgress);
        videoView = findViewById(R.id.videoview);
        websiteTxt = findViewById(R.id.textView18);
        time2Txt = findViewById(R.id.textView13);
        newsImageView = findViewById(R.id.imageView15);
        titleTxt = findViewById(R.id.newNewsTitleTxt);
        timeTxt = findViewById(R.id.textView1010);
        descTxt = findViewById(R.id.newDescTxt);

        String news = getIntent().getStringExtra("news");
        if (news != null) {
            setData(new Gson().fromJson(news, NewsData.Data.class));
        }
    }

    public void backBtnTapped(View view) {
        finish();
    }

    private void setData(NewsData.Data data) {
        titleTxt.setText(data.getTitle());
        timeTxt.setText(new Utils().getDateDiff(data.getDateCreated()));
        descTxt.setText(data.getDescription());
        time2Txt.setText("  " + data.getPeriod());

        if (data.getUrl().contains("http://")) {
            websiteTxt.setText("  " + data.getUrl().replace("http://www.", ""));
        } else if (data.getUrl().contains("https://")) {
            websiteTxt.setText("  " + data.getUrl().replace("https://www.", ""));
        } else if (data.getUrl().contains("www.")) {
            websiteTxt.setText("  " + data.getUrl().replace("www.", ""));
        }

        if (data.getFileType() != null && data.getFileType().equals("video")) {
            newsImageView.setVisibility(View.INVISIBLE);
            initializePlayer(data.getImage());
        } else {
            Picasso.get().load(Api.imageBaseUrl + data.getImage()).placeholder(R.drawable.placeholder).into(newsImageView);
        }
    }

    private void initializePlayer(String name) {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(this);
            videoView.setPlayer(player);
            buildMediaSource(Uri.parse(Api.imageBaseUrl + name));
            spinnerVideoDetails.setVisibility(View.VISIBLE);
        }
    }

    private void buildMediaSource(Uri mUri) {
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, getString(R.string.app_name)), bandwidthMeter);
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mUri);
        player.prepare(videoSource);
        player.setPlayWhenReady(true);
        player.addListener(this);
    }

        public void websiteTapped(View view) {
        try {
            if (!url.contains("http://")) { url = "http://" + url; }
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch (playbackState) {
            case Player.STATE_BUFFERING:
                spinnerVideoDetails.setVisibility(View.VISIBLE);
                break;
            case Player.STATE_ENDED:
                // Activate the force enable
                break;
            case Player.STATE_IDLE:
                break;
            case Player.STATE_READY:
                spinnerVideoDetails.setVisibility(View.GONE);
                break;
            default:
                // status = PlaybackStatus.IDLE;
                break;
        }
    }
}
