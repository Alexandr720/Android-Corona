package com.stepin.coronaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stepin.coronaapp.R;
import com.stepin.coronaapp.api.Api;
import com.stepin.coronaapp.model.EnforceListResponse;
import com.stepin.coronaapp.model.TracingListResponse;

import java.util.ArrayList;

public class TracingAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<TracingListResponse.Data> items;

    public TracingAdapter(Context context, ArrayList<TracingListResponse.Data> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public TracingListResponse.Data getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateItems(ArrayList<TracingListResponse.Data> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_tracing_list, parent, false);
        }

        TracingListResponse.Data center = getItem(position);

        TextView titleTextView = convertView.findViewById(R.id.infTitleTxt);
        TextView description = convertView.findViewById(R.id.des);
        TextView time = convertView.findViewById(R.id.time);

        titleTextView.setText(center.getTracing_title());
        description.setText(center.getPassenger_name());
        time.setText(center.getPublish_date());
        return convertView;
    }
}