package com.gachugusville.servicedforbusiness.Messaging.Notification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public abstract class ApiService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:keys=AAAAW_uPQBw:APA91bGVuA3zi8kG3P6l_VvuWbD4U25uxob2iXYJRs8Hul7h3Ofp7WkRpwk0mV3rkxCE3q_7KdFXZiT0xlMukoHJQQfhLYDFxIZ56GJ7vo8w3xVZAixdRFqQHmvXerSJTL_HJptXzO4G"
    })

    @POST("fcm/send")
    abstract Call<Response> sendNotification(@Body Sender body);
}
