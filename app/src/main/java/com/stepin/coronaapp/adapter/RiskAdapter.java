package com.stepin.coronaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.stepin.coronaapp.R;
import com.stepin.coronaapp.model.InformationCenterRiskData;

import java.util.ArrayList;

public class RiskAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<InformationCenterRiskData.Data> items;

    public RiskAdapter(Context context, ArrayList<InformationCenterRiskData.Data> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public InformationCenterRiskData.Data getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateData(ArrayList<InformationCenterRiskData.Data> data) {
        this.items = data;
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.risk_layout_item, parent, false);
        }

        InformationCenterRiskData.Data data = items.get(position);
        TextView descInf = convertView.findViewById(R.id.riskTxt);
        CardView cardView = convertView.findViewById(R.id.riskCardView);

        int white = ContextCompat.getColor(context, R.color.white);
        int red = ContextCompat.getColor(context, R.color.red);
        int black = ContextCompat.getColor(context, R.color.black);

        descInf.setText(data.getDescription());
        cardView.setCardBackgroundColor(data.getIsRisk().equals("Yes") ? red : white);
        descInf.setTextColor(data.getIsRisk().equals("Yes") ? white : black);

        return convertView;
    }
}
