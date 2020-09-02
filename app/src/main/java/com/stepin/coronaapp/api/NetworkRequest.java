package com.stepin.coronaapp.api;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stepin.coronaapp.model.ErrorMessage;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class NetworkRequest<T> {

    // GET
    public void fetch(Context context, Type type, String url, NetworkResponse<T> res) {
        networkRequest(Request.Method.GET, context, type, url, res);
    }

    public void post(Context context, Type type, String url, NetworkResponse<T> res) {
        networkRequest(Request.Method.POST, context, type, url, res);
    }

    private void networkRequest(int method, Context context, Type type, String url, NetworkResponse<T> res) {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest request = new StringRequest(method, url, response -> {

            Log.d("json -> ", response);
            GsonBuilder builder = new GsonBuilder();
            builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

            T obj  = builder.create().fromJson(response, type);

            res.onResponse(obj, null);

        }, error -> {
            error.printStackTrace();
            String message;
            if (error.networkResponse == null || error.networkResponse.data == null) {
                message = error.getLocalizedMessage();
            } else {
                try {
                    String json = new String(error.networkResponse.data);
                    Log.d("json -> ", json);
                    ErrorMessage errorMessage = new Gson().fromJson(json, ErrorMessage.class);
                    message = errorMessage.getMessage();
                }catch (Exception e) {
                    message = "Something went wrong";
                }
            }

            if (message == null) { message = "Something went wrong"; }
            else if (message.isEmpty()) { message = "Something went wrong"; }

            res.onResponse(null, new ErrorMessage(message));
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        });

        request.setRetryPolicy(new DefaultRetryPolicy(
                1000000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);
    }

    // POST
    public void post(Context context, Type type, Object object, String url, NetworkResponse<T> res) {

        try {
            String json = toJson(object);
            JSONObject jsonObj = new JSONObject(json);

            Log.d("json -> ", json);

            RequestQueue queue = Volley.newRequestQueue(context);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObj, response -> {

                String resJson = response.toString();
                GsonBuilder builder = new GsonBuilder();
                builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
                T obj  = builder.create().fromJson(resJson, type);

                res.onResponse(obj, null);
            }, error -> {
                error.printStackTrace();
                String message;
                if (error.networkResponse == null || error.networkResponse.data == null) {
                    message = error.getLocalizedMessage();
                } else {
                    String resJson = new String(error.networkResponse.data);
                    ErrorMessage errorMessage = new Gson().fromJson(resJson, ErrorMessage.class);
                    message = errorMessage.getMessage();
                }

                res.onResponse(null, new ErrorMessage(message));
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            });

            queue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private String toJson(Object obj) {
        GsonBuilder builder = new GsonBuilder();
        builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return builder.create().toJson(obj);
    }


    public void uploadImage(Context context, Type type, Bitmap bitmap, String url, String key, NetworkResponse<T> res) {

        if (bitmap == null) {
            post(context, type, url, res);
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(context);

        VolleyMultipartRequest request = new VolleyMultipartRequest(Request.Method.POST, url, response -> {

            GsonBuilder builder = new GsonBuilder();
            builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

            Log.d("json", new String(response.data));
            T obj  = builder.create().fromJson(new String(response.data), type);

            res.onResponse(obj, null);

         }, error -> {

            error.printStackTrace();
            String message;
            if (error.networkResponse == null || error.networkResponse.data == null) {
                message = error.getLocalizedMessage();
            } else {
                try {
                    String json = new String(error.networkResponse.data);
                    Log.d("json -> ", json);
                    ErrorMessage errorMessage = new Gson().fromJson(json, ErrorMessage.class);
                    message = errorMessage.getMessage();
                }catch (Exception e) {
                    message = "Something went wrong";
                }
            }

            if (message == null) { message = "Something went wrong"; }
            else if (message.isEmpty()) { message = "Something went wrong"; }

            res.onResponse(null, new ErrorMessage(message));
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

         }) {
             @Override
             protected Map<String, DataPart> getByteData() {
                 String filename = ""+System.currentTimeMillis()+".jpeg";
                 Log.d("filename -> ", filename);
                 Map<String, DataPart> params = new HashMap<>();
                 params.put(key, new DataPart(filename, bitmapToByteArray(bitmap), "image/jpeg"));

                 return params;
             }
         };

        request.setRetryPolicy(new DefaultRetryPolicy(
                1000000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);
    }

    private static byte[] getFileDataFromDrawable(Context context, int id) {
        Drawable drawable = ContextCompat.getDrawable(context, id);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private static byte[] getFileDataFromDrawable(Context context, Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}
