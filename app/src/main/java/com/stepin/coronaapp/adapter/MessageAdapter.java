package com.stepin.coronaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.stepin.coronaapp.R;
import com.stepin.coronaapp.model.ConversationMessage;
import com.stepin.coronaapp.model.Utils;

import java.util.ArrayList;

public class MessageAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ConversationMessage.Data> items;
    private int userId;
    private String name;

    public MessageAdapter(Context context, ArrayList<ConversationMessage.Data> items, int userId, String name) {
        this.context = context;
        this.items = items;
        this.userId = userId;
        this.name = name;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ConversationMessage.Data getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void update(ArrayList<ConversationMessage.Data> data) {
        this.items = data;
        this.notifyDataSetChanged();
    }

    public void insert(ConversationMessage.Data data) {
        items.add(data);
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.message_list_item, parent, false);
        }

        ConversationMessage.Data data = items.get(position);

        TextView userTxt = convertView.findViewById(R.id.messageNameTxt);
        TextView timeTxt = convertView.findViewById(R.id.messageTimeTxt);
        TextView msgTxt = convertView.findViewById(R.id.messageTxt);

        userTxt.setText(userId == data.getSenderId() ? name : "Admin");
        msgTxt.setText(data.getMsg());
        timeTxt.setText(new Utils().getDateDiff(data.getCreatedAt()));

        return convertView;
    }
}
