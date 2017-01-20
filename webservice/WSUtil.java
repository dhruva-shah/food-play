package com.restaurantsapp.webservice;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by indianic on 04/02/16.
 */
public class WSUtil {


    public String wsGet(final String url) {
        String stringResponse = "";
        try {


            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(WSConstant.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(WSConstant.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();
            final Response response = okHttpClient.newCall(request).execute();
            stringResponse = response.body().string();
            System.out.println(stringResponse);
        } catch (Exception e) {
            e.printStackTrace();
            stringResponse = getNetWorkError();
        }
        return stringResponse;
    }

    public String wsPost(final String url, final RequestBody formBody) {
        String stringResponse = "";
        try {

            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(WSConstant.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(WSConstant.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
            final Response response = okHttpClient.newCall(request).execute();
            stringResponse = response.body().string();
            System.out.println(stringResponse);
        } catch (Exception e) {
            e.printStackTrace();
            stringResponse = getNetWorkError();
        }
        return stringResponse;
    }


    private String getNetWorkError() {
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("error", false);
            jsonObject.put("error_msg", "Network error, Please try again after some time.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }


}


