package com.leelit.stuer.utils;

import android.app.Activity;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Leelit on 2015/12/25.
 */
public class OkHttpUtils {

    public static final OkHttpClient client = new OkHttpClient();


    public static Response get(String url) throws IOException {
        Request request = getBuilderWidthHeaders(url, null).build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            response.body().close();
            throw new IOException("Unexpected code " + response);
        }
        return response;
    }

    public static Response get(String url, Map<String, String> headers) throws IOException {
        Request request = getBuilderWidthHeaders(url, headers).build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            response.body().close();
            throw new IOException("Unexpected code " + response);
        }
        return response;
    }

    public static Call get(String url, Callback callback) {
        return get(url, null, callback);
    }

    public static Call get(String url, Map<String, String> headers, Callback callback) {
        Request request = getBuilderWidthHeaders(url, headers).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    private static Request.Builder getBuilderWidthHeaders(String url, Map<String, String> headers) {
        Request.Builder builder = new Request.Builder()
                .url(url);
        if (headers != null && headers.size() != 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        return builder;
    }


    public static Response post(String url, String data) throws IOException {
        return post(url, null, data);
    }

    public static Response post(String url, Map<String, String> headers, String data) throws IOException {
        Request request = getBuilderWidthHeaders(url, headers)
                .post(RequestBody.create(MediaType.parse("text/plain; charset=utf-8"), data))
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            response.body().close();
            throw new IOException("Unexpected code " + response);
        }
        return response;
    }

    public static void post(String url, Map<String, String> headers, String data, Callback callback) {
        Request request = getBuilderWidthHeaders(url, headers)
                .post(RequestBody.create(MediaType.parse("text/plain; charset=utf-8"), data))
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void post(String url, String data, Callback callback) {
        post(url, null, data, callback);
    }

    public static Call getOnUiThread(String url, final Callback callback, final Activity activity) {
        Request request = getBuilderWidthHeaders(url, null).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailure(request, e);
                        }
                    });
                }
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                callback.onResponse(response);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
        return call;
    }

    public static Call postOnUiThread(String url, String data, final Callback callback, final Activity activity) {
        Request request = getBuilderWidthHeaders(url, null)
                .post(RequestBody.create(MediaType.parse("text/plain; charset=utf-8"), data))
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailure(request, e);
                        }
                    });
                }
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                callback.onResponse(response);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
        return call;
    }
}
