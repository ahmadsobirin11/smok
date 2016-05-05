package com.smok.ahmad.smok.utility;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Ahmad on 05/05/2016.
 */

public class OkHttpRequest {
    public static Call getDataFromServer(String url)throws IOException{
        Call call = null;

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();

        call = client.newCall(request);

        return call;

    }

    public static Call postDataToServer(String url, RequestBody params) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(params)
                .build();

        return client.newCall(request);
    }

}
