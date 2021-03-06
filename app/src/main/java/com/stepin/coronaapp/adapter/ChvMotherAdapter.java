package com.stepin.coronaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.stepin.coronaapp.R;
import com.stepin.coronaapp.model.ChvListResponse;
import com.stepin.coronaapp.model.ChvMotherListResponse;

import java.util.ArrayList;

public class ChvMotherAdapter  extends BaseAdapter {

    private Context context;
    private ArrayList<ChvMotherListResponse.Data> items;

    public ChvMotherAdapter(Context context, ArrayList<ChvMotherListResponse.Data> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ChvMotherListResponse.Data getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateItems(ArrayList<ChvMotherListResponse.Data> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_tracing_list, parent, false);
        }

        ChvMotherListResponse.Data center = getItem(position);

        TextView titleTextView = convertView.findViewById(R.id.infTitleTxt);
        TextView description = convertView.findViewById(R.id.des);
        TextView time = convertView.findViewById(R.id.time);
        titleTextView.setText(center.getTitle());
        description.setText(center.getName());
        time.setText(center.getMother_id());
        return convertView;
    }
}