package com.attendo.retrofit;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProvider {

    private static Retrofit.Builder builder;
    private static Retrofit instance = null;
    private static Gson gson;

    public static Retrofit getInstance(Context context) {

        if (instance == null) {
            synchronized (RetrofitProvider.class) {
                if (instance == null) {
                    gson = new GsonBuilder().serializeNulls().create();
                    OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(chain -> {

                        Request original = chain.request();

                        Request request = original.newBuilder().method(original.method(), original.body()).build();

                        return chain.proceed(request);
                    }).readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS).build();

                    builder = new Retrofit.Builder()
                            .baseUrl("")  //endPoint url
                            .client(okHttpClient)
                            .addConverterFactory(GsonConverterFactory.create(gson));

                    instance = builder.build();
                }
            }
        }
        return instance;

    }
}


