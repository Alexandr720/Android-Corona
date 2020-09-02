package com.stepin.coronaapp.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.reflect.TypeToken;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.stepin.coronaapp.App;
import com.stepin.coronaapp.R;
import com.stepin.coronaapp.adapter.EnforceAdapter;
import com.stepin.coronaapp.adapter.InformationAdapter;
import com.stepin.coronaapp.api.Api;
import com.stepin.coronaapp.api.NetworkRequest;
import com.stepin.coronaapp.model.APIResponse;
import com.stepin.coronaapp.model.EnforceListResponse;
import com.stepin.coronaapp.model.EnforceLocal;
import com.stepin.coronaapp.model.GbvLocal;
import com.stepin.coronaapp.model.InformationCenterResponse;
import com.stepin.coronaapp.model.User;
import com.stepin.coronaapp.model.Utils;
import com.stepin.coronaapp.sqlite.DatabaseQueryClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class EnforceActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private int RESULT_LOAD_IMG = 123, CAMERA = 124;


    private LinearLayout add_btn ;
    private ProgressDialog progressDialog;

    ListView informationListView;
    EnforceAdapter adapter;

    ArrayList<EnforceListResponse.Data> items;
    String type = "1";
    User user;
    private DatabaseQueryClass databaseQueryClass;
    private Bitmap selectedImage = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enforce);
        databaseQueryClass = new DatabaseQueryClass(EnforceActivity.this);
        user = new User().getUser(this);
        add_btn = findViewById(R.id.add_btn);
        items = new ArrayList<>();
        informationListView = findViewById(R.id.informationListView);
        adapter = new EnforceAdapter(this, items);
        informationListView.setAdapter(adapter);
        informationListView.setOnItemClickListener(this);

        setProgressDialog();
        getInformationDetails();
        requestCameraPermission();
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAddDlg();
            }
        });
    }
    private void setProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Submitting Report ...");
    }
    private void getInformationDetails() {
        if(App.isNetworkAvailable(EnforceActivity.this)){
            String url = new Api().generateUrl(Api.enforce_list, new LinkedHashMap<>());
            Type type = new TypeToken<EnforceListResponse>(){}.getType();

            new NetworkRequest<EnforceListResponse>().fetch(this, type, url, (obj, error) -> {
                if (error == null && obj != null) {
                    items = (ArrayList<EnforceListResponse.Data>) obj.getEnforceList();
                    adapter.updateItems(items);
                }
            });
        }else{
            Toast.makeText(EnforceActivity.this,"You are working offline",Toast.LENGTH_LONG).show();
        }

    }



//    private void setItems() {
//        items = new ArrayList<>();
//        items.add(new InformationCenter("Am I at risk?", R.drawable.viruslogo));
//        items.add(new InformationCenter("How can I Help?", R.drawable.stethoscope));
//        items.add(new InformationCenter("Latest News", R.drawable.mask));
//        items.add(new InformationCenter("Latest Updates", R.drawable.medical));
//        items.add(new InformationCenter("Trends", R.drawable.trend));
//        items.add(new InformationCenter("Get Help Here", R.drawable.help));
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String type = String.valueOf(items.get(position).getType());
        String id_str = String.valueOf(items.get(position).getId());
        String image = items.get(position).getImage();
        String title = items.get(position).getTitle();
        String description = items.get(position).getDescription();
        String file_type = items.get(position).getFile_type();
        Intent intent = new Intent(EnforceActivity.this,EnforceDetailActivity.class);
        intent.putExtra("id",id_str);
        intent.putExtra("title",title);
        intent.putExtra("description",description);
        intent.putExtra("file_type",file_type);
        intent.putExtra("type",type);
        intent.putExtra("image",image);
        startActivity(intent);

    }

    private void createAddDlg(){
        View dialogView = getLayoutInflater().inflate(R.layout.dlg_enforce, null);


        AlertDialog emailDlg = new android.app.AlertDialog.Builder(EnforceActivity.this)
                .setView(dialogView)
                .setCancelable(true)
                .create();

        final EditText title = dialogView.findViewById(R.id.title);
        final EditText description = dialogView.findViewById(R.id.description);
        final RadioGroup radio_group = dialogView.findViewById(R.id.radio_group);
        final Button report = dialogView.findViewById(R.id.report);
        final Button photo = dialogView.findViewById(R.id.photo);
        final Button gallery = dialogView.findViewById(R.id.gallery);
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioButton1:
                        type = "1";
                        break;
                    case R.id.radioButton2:
                        type = "2";
                        break;
                    case R.id.radioButton3:
                        type = "3";
                        break;
                    case R.id.radioButton4:
                        type = "4";
                        break;
                    default:
                        break;

                }
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(title.getText().toString())) {
                    title.setError("Please enter title");
                } else if (TextUtils.isEmpty(description.getText().toString())){
                    description.setError("Please enter description");
                } else{
                    if(App.isNetworkAvailable(EnforceActivity.this)){
                        LinkedHashMap<String, String> map = new LinkedHashMap<>();
                        map.put("user_id", String.valueOf(user.getData().getId()));
                        map.put("enforce_type", type);
                        map.put("title", title.getText().toString());
                        map.put("description", description.getText().toString());
                        map.put("file_type","image");

                        String url = new Api().generateUrl(Api.enforce, map);
                        Type type = new TypeToken<APIResponse>(){}.getType();


                        progressDialog.show();

                        new NetworkRequest<APIResponse>().uploadImage(EnforceActivity.this, type, selectedImage, url, Api.reportImage, (status, error) -> {
                            progressDialog.dismiss();
                            if (error != null && status == null) {
                                showError(error.getMessage());
                            } else if (status.getStatus().equals("failed")) {
                                showError(status.getMsg());
                            } else {
                                Toast.makeText(EnforceActivity.this, status.getMsg(), Toast.LENGTH_LONG).show();
                                emailDlg.dismiss();
                                items.clear();
                                getInformationDetails();
                                selectedImage = null;
                            }
                        });
                    }else{
                        Toast.makeText(EnforceActivity.this,"You are working offline",Toast.LENGTH_LONG).show();
                        String created_time =  new SimpleDateFormat("MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                        String luser_id = String.valueOf(user.getData().getId());
                        String enforce_type = type;
                        String ltitle = title.getText().toString();
                        String ldescription = description.getText().toString();

                        EnforceLocal tempdata = new EnforceLocal(-1, luser_id, enforce_type, ltitle,  ldescription,created_time);

                        long id = databaseQueryClass.insertEnforceData(tempdata);
                        if(id!=-1){
                            title.setText("");
                            description.setText("");
                            Toast.makeText(EnforceActivity.this,"Your data is saved in your phone",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(EnforceActivity.this,"Local Database error",Toast.LENGTH_LONG).show();
                        }
                    }


                }
            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoBtnTapped();
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseBtnTapped();
            }
        });
        emailDlg.setCanceledOnTouchOutside(true);
        emailDlg.setCancelable(true);
        emailDlg.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        emailDlg.show();


    }
    private void showError(String message) {
        new AlertDialog.Builder(this).setMessage(message)
                .setPositiveButton("Ok", (dialog, which) -> { })
                .show();
    }
    public void backBtnTapped(View view) {
        finish();
    }


    private void requestCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
            }
        }
    }
    private void browseBtnTapped() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[] {"image/*", "video/*"});
        startActivityForResult(intent, RESULT_LOAD_IMG);
    }
    private void photoBtnTapped() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA);
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
            }
        } else {
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, CAMERA);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0) {
                    for(int i = 0; i < grantResults.length; i++) {
                        String permission = permissions[i];
                        if(Manifest.permission.CAMERA.equals(permission)) {
                            if(grantResults[i] == PackageManager.PERMISSION_GRANTED) {
//                                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                                startActivityForResult(intent, CAMERA);
                            }
                        }
                    }
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
            }
        }

        // other 'case' lines to check for other
        // permissions this app might request
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == RESULT_LOAD_IMG) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                selectedImage = BitmapFactory.decodeStream(imageStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong while picking the image", Toast.LENGTH_LONG).show();
            }
        } else if (resultCode == RESULT_OK && requestCode == CAMERA) {
            selectedImage = (Bitmap) data.getExtras().get("data");
        }
    }
}
