package com.stepin.coronaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.stepin.coronaapp.R;
import com.stepin.coronaapp.model.TracingListResponse;
import com.stepin.coronaapp.model.TracingPassengerListResponse;

import java.util.ArrayList;

    public class TracingPassengerAdapter extends BaseAdapter {

        private Context context;
        private ArrayList<TracingPassengerListResponse.Data> items;

        public TracingPassengerAdapter(Context context, ArrayList<TracingPassengerListResponse.Data> items) {
            this.context = context;
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public TracingPassengerListResponse.Data getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void updateItems(ArrayList<TracingPassengerListResponse.Data> items) {
            this.items = items;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_tracing_list, parent, false);
            }

            TracingPassengerListResponse.Data center = getItem(position);

            TextView titleTextView = convertView.findViewById(R.id.infTitleTxt);
            TextView description = convertView.findViewById(R.id.des);
            TextView time = convertView.findViewById(R.id.time);

            titleTextView.setText(center.getPassenger_name());
            description.setText(center.getTemp());
            time.setText(center.getPublish_date());
            return convertView;
        }
    }

