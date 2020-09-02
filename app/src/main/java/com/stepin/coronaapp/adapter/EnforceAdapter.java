package com.stepin.coronaapp.adapter;

import android.content.Context;
import android.util.Log;
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
import com.stepin.coronaapp.model.InformationCenterResponse;

import java.util.ArrayList;

public class EnforceAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<EnforceListResponse.Data> items;

    public EnforceAdapter(Context context, ArrayList<EnforceListResponse.Data> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public EnforceListResponse.Data getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateItems(ArrayList<EnforceListResponse.Data> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.information_center_list, parent, false);
        }

        EnforceListResponse.Data center = getItem(position);

        TextView titleTextView = convertView.findViewById(R.id.infTitleTxt);
        TextView description = convertView.findViewById(R.id.des);
        ImageView imageView = convertView.findViewById(R.id.infImage);

        titleTextView.setText(center.getTitle());
        description.setText(center.getDescription());
        Picasso.get().load(Api.enforceImageUrl + center.getImage()).into(imageView);

        return convertView;
    }
}