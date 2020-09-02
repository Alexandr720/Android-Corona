package com.stepin.coronaapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.stepin.coronaapp.App;
import com.stepin.coronaapp.R;
import com.stepin.coronaapp.adapter.MessageAdapter;
import com.stepin.coronaapp.api.Api;
import com.stepin.coronaapp.api.NetworkRequest;
import com.stepin.coronaapp.model.ConversationMessage;
import com.stepin.coronaapp.model.SendMessageResponse;
import com.stepin.coronaapp.model.User;
import com.stepin.coronaapp.model.Utils;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MessageActivity extends AppCompatActivity {

    private ListView listView;
    private MessageAdapter adapter;
    private ArrayList<ConversationMessage.Data> messages;
    private User user;
    private ProgressBar progressBar, sendProgressBar;
    private EditText messageEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        messageEdt = findViewById(R.id.sendMessageEdt);
        progressBar = findViewById(R.id.messageProgressBar);
        sendProgressBar = findViewById(R.id.sendProgressBar);
        setListView();
        if(!user.getConversationId().isEmpty()) { getAllMessages(); }
        else { progressBar.setVisibility(View.GONE); }

        progressBar.bringToFront();
    }

    private void setListView() {
        user = new User().getUser(this);
        messages = new ArrayList<>();
        listView = findViewById(R.id.messageListView);
        adapter = new MessageAdapter(this, messages, user.getData().getId(), user.getData().getFirstName() + " " + user.getData().getLastName());
        listView.setAdapter(adapter);
    }

    public void getAllMessages() {
        if(App.isNetworkAvailable(MessageActivity.this)){
            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            map.put("conversation_id", user.getConversationId());

            String url = new Api().generateUrl(Api.msg, map);
            Type type = new TypeToken<ConversationMessage>(){}.getType();

            new NetworkRequest<ConversationMessage>().post(this, type, url, (obj, error) -> {
                progressBar.setVisibility(View.GONE);
                if (error != null && obj == null) {
                    showError(error.getMessage());
                } else if (obj.getStatus().equals("failed")) {
                    showError(obj.getMsg());
                } else {
                    messages = (ArrayList<ConversationMessage.Data>) obj.getAllMsg();
                    adapter.update(messages);
                }
            });
        }else{
            Toast.makeText(MessageActivity.this,"You are working offline",Toast.LENGTH_LONG).show();
        }

    }

    public void backBtnTapped(View view) {
        finish();
    }


    private void showError(String message) {
        new AlertDialog.Builder(this).setMessage(message)
                .setPositiveButton("Ok", (dialog, which) -> { })
                .show();
    }

    public void sendMessageTapped(View view) {
        if(App.isNetworkAvailable(MessageActivity.this)){
            String message = messageEdt.getText().toString().trim();
            if (message.isEmpty()) { return; }

            LinkedHashMap<String, String> map = new LinkedHashMap<>();
            map.put("sender_id", String.valueOf(user.getData().getId()));
            map.put("msg", message);

            String url = new Api().generateUrl(Api.sendMessage, map);
            Type type = new TypeToken<SendMessageResponse>(){}.getType();

            messageEdt.setText("");

            ConversationMessage.Data data = new ConversationMessage.Data(user.getData().getId(), user.getConversationId(), "1 sec", message);
            adapter.insert(data);
            sendProgressBar.setVisibility(View.VISIBLE);
            new NetworkRequest<SendMessageResponse>().post(this, type, url, (obj, error) -> {
                sendProgressBar.setVisibility(View.INVISIBLE);
                if (error != null && obj == null) {
                    showError(error.getMessage());
                } else if (obj.getStatus().equals("failed")) {
                    showError(obj.getMsg());
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences(Utils.sharedPerfName, MODE_PRIVATE).edit();
                    editor.putString("conversation_id", obj.getConverstionId());
                    editor.apply();
                }
            });
        }else{
            Toast.makeText(MessageActivity.this,"You are working offline",Toast.LENGTH_LONG).show();
        }

    }
}
