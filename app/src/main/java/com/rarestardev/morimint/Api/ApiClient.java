package com.rarestardev.morimint.Api;

import com.rarestardev.morimint.Constants.ApiToken;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;

    public static Retrofit getRetrofit(){
        if (retrofit == null){

            OkHttpClient client = UnsafeOkHttpClient.getUnsafeOkHttpClient();


            retrofit = new Retrofit.Builder()
                    .baseUrl(ApiToken.SERVER_ADDRESS)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
