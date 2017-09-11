package com.sairam.data.model.retrofit;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.sairam.data.model.ApiConstants;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Sairam Rachapudi on 10/09/17.
 */

public class RetrofitClient {

    private static HackerEarthApiService hapiApiService;

    private RetrofitClient() {
    }

    public static HackerEarthApiService getAPIService() {
        if (hapiApiService == null)
            hapiApiService = getClient().create(HackerEarthApiService.class);
        return hapiApiService;
    }

    private static Retrofit getClient() {
        return new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClientObject())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    private static OkHttpClient getOkHttpClientObject() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession arg1) {
                return hostname.equalsIgnoreCase("http://starlord.hackerearth.com");
            }
        });
        httpClient.addInterceptor(logging);
        return httpClient.build();
    }
}
