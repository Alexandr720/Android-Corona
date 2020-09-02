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
import com.stepin.coronaapp.model.InformationCenterResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class InformationAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<InformationCenterResponse.Data> items;

    public InformationAdapter(Context context, ArrayList<InformationCenterResponse.Data> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public InformationCenterResponse.Data getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateItems(ArrayList<InformationCenterResponse.Data> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.information_center_list, parent, false);
        }

        InformationCenterResponse.Data center = getItem(position);

        TextView titleTextView = convertView.findViewById(R.id.infTitleTxt);
        TextView description = convertView.findViewById(R.id.des);
        ImageView imageView = convertView.findViewById(R.id.infImage);

        titleTextView.setText(center.getTitle());
        description.setText(center.getDescription());
        Picasso.get().load(Api.informationImageUrl + center.getIcon()).into(imageView);

        return convertView;
    }
}
