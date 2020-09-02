package com.stepin.coronaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.stepin.coronaapp.R;
import com.stepin.coronaapp.api.Api;
import com.stepin.coronaapp.model.NewsData;
import com.stepin.coronaapp.model.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<NewsData.Data> items;

    public NewsAdapter(Context context, ArrayList<NewsData.Data> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public NewsData.Data getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateNews(ArrayList<NewsData.Data> data) {
        this.items = data;
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.news_new_layout_item, parent, false);
        }

        NewsData.Data data = getItem(position);

        ImageView imageView = convertView.findViewById(R.id.newsImageView);
        TextView titleTxt = convertView.findViewById(R.id.newsTitleTxt);
        TextView timeWebsiteTxt = convertView.findViewById(R.id.newTimeWebsiteTxt);

//        TextView descText = convertView.findViewById(R.id.newsDescTxt);
//        TextView websiteTxt = convertView.findViewById(R.id.websiteTxt);
//        TextView timeTxt = convertView.findViewById(R.id.newsTimeTxt);

        titleTxt.setText(data.getTitle());
        String str = data.getUrl().replace("www.", "") + "  -  " + new Utils().getDateDiff(data.getDateCreated());
        timeWebsiteTxt.setText(str);

//        descText.setText(data.getDescription());
//        websiteTxt.setText(data.getUrl().replace("www.", ""));
//        timeTxt.setText(data.getDateCreated());

        if (data.getFileType() == null || !data.getFileType().equals("video")) {
            Picasso.get().load(Api.imageBaseUrl + data.getImage()).placeholder(R.drawable.placeholder).into(imageView);
        } else {
            imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.thumnail));
        }

        return convertView;
    }
}
