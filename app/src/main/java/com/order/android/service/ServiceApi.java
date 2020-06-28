package com.order.android.service;


import com.order.android.bean.HeartBeat;
import com.order.android.http.Res;
import com.order.android.request.HeartBeatRequest;


import io.reactivex.Observable;
import retrofit2.http.Body;

import retrofit2.http.POST;


public interface ServiceApi {

    @POST("/takeMeal/device/heart_beat")
    Observable<Res<HeartBeat>> getHeartBeat(@Body HeartBeatRequest request);
}
