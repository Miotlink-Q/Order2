package com.order.android.http;

import com.order.android.bean.HeartBeat;
import com.order.android.request.HeartBeatRequest;
import com.order.android.rx.BaseObserver;
import com.order.android.rx.RxSchedulers;
import com.order.android.service.ServiceApi;

import io.reactivex.Observable;


public class HttpEngine {

    private static ServiceApi serviceApi = RetrofitServiceManager.getInstance().create(ServiceApi.class);

    /*
     * 获取豆瓣电影榜单
     * **/
    public static void getHeartBeat(HeartBeatRequest request, BaseObserver<Res<HeartBeat>> observer) {
        setSubscribe(serviceApi.getHeartBeat(request), observer);
    }

    private static <T> void setSubscribe(Observable<T> observable, BaseObserver<T> observer) {
        observable.compose(RxSchedulers.<T>compose())
                .subscribe(observer);
    }
}
