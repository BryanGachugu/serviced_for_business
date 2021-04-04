package com.gachugusville.servicedforbusiness.Messaging.Notification;

import retrofit2.Retrofit;

public class Client {

    private static Retrofit retrofit = null;
    public static Retrofit getRetrofit(String url){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }
}
