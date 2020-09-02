package com.stepin.coronaapp.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import com.stepin.coronaapp.R;
import com.stepin.coronaapp.api.Api;
import com.stepin.coronaapp.model.NewsData;
import com.stepin.coronaapp.model.Utils;

public class EnforceDetailActivity  extends AppCompatActivity implements Player.EventListener{

        ImageView newsImageView;
        TextView titleTxt,timeTxt,descTxt;
        String url="";
        PlayerView videoView;
        SimpleExoPlayer player;
        ProgressBar spinnerVideoDetails;

        private String id,title,description,file_type,image,type;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enforce_detail);

        spinnerVideoDetails=findViewById(R.id.newsWebProgress);
        videoView=findViewById(R.id.videoview);
        newsImageView=findViewById(R.id.imageView15);
        titleTxt=findViewById(R.id.newNewsTitleTxt);
        timeTxt=findViewById(R.id.textView1010);
        descTxt=findViewById(R.id.newDescTxt);


        try{
            id = getIntent().getStringExtra("id");
        }catch (Exception e){
            id = "";
        }
        try{
            id = getIntent().getStringExtra("id");
        }catch (Exception e){
            id = "";
        }
        try{
            type = getIntent().getStringExtra("type");
        }catch (Exception e){
            type = "";
        }
        try{
            title = getIntent().getStringExtra("title");
        }catch (Exception e){
            title = "";
        }
        try{
            description = getIntent().getStringExtra("description");
        }catch (Exception e){
            description = "";
        }
        try{
            image = getIntent().getStringExtra("image");
        }catch (Exception e){
            image = "";
        }
        try{
            file_type = getIntent().getStringExtra("file_type");
        }catch (Exception e){
            file_type = "";
        }

        setData(id,title,description,type,file_type,image);
    }

    public void backBtnTapped(View view){
        finish();
    }

    private void setData(String id, String title, String description, String type, String file_type, String image){
        String type_str = "";
        if(type.equalsIgnoreCase("1")){
            type_str = "People gathering in large numbers";
        }else if(type.equalsIgnoreCase("2")){
            type_str = "People not following orders";
        }else if(type.equalsIgnoreCase("3")){
            type_str = "People not wearing face mask";
        }else if(type.equalsIgnoreCase("4")){
            type_str = "People not observing social distance";
        }
        titleTxt.setText(title);
        timeTxt.setText(type_str);
        descTxt.setText(description);


        if(file_type.equals("video")){
            newsImageView.setVisibility(View.INVISIBLE);
            initializePlayer(image);
        }else{
            Picasso.get().load(Api.enforceImageUrl+image).placeholder(R.drawable.placeholder).into(newsImageView);
        }
    }

private void initializePlayer(String name){
        if(player==null){
        player= ExoPlayerFactory.newSimpleInstance(this);
        videoView.setPlayer(player);
        buildMediaSource(Uri.parse(Api.imageBaseUrl+name));
        spinnerVideoDetails.setVisibility(View.VISIBLE);
        }
        }

private void buildMediaSource(Uri mUri){
        DefaultBandwidthMeter bandwidthMeter=new DefaultBandwidthMeter();
        DataSource.Factory dataSourceFactory=new DefaultDataSourceFactory(this,
        Util.getUserAgent(this,getString(R.string.app_name)),bandwidthMeter);
        MediaSource videoSource=new ExtractorMediaSource.Factory(dataSourceFactory)
        .createMediaSource(mUri);
        player.prepare(videoSource);
        player.setPlayWhenReady(true);
        player.addListener(this);
        }

public void websiteTapped(View view){
        try{
        if(!url.contains("http://")){url="http://"+url;}
        Intent i=new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
        }catch(ActivityNotFoundException e){
        Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
        }

@Override
public void onPlayerStateChanged(boolean playWhenReady,int playbackState){
        switch(playbackState){
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
