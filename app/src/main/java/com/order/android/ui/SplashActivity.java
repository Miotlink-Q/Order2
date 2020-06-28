package com.order.android.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.order.android.R;
import com.order.android.base.BaseActivity;
import com.order.android.bean.HeartBeat;
import com.order.android.dialog.TipsDialog;
import com.order.android.http.HttpEngine;
import com.order.android.http.Res;
import com.order.android.request.HeartBeatRequest;
import com.order.android.rx.BaseObserver;
import com.order.android.utils.IntentUtils;
import com.order.android.utils.StatusBarUtils;
import com.order.android.view.RxRoundProgressBar;
import com.order.android.view.SuperTextView;
import com.zqzn.android.facesearch.YskCallback;
import com.zqzn.android.facesearch.YskFaceEngine;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SplashActivity extends BaseActivity implements YskCallback.LoadPerson, YskCallback.SDKInit{
    private static final int REQUEST_PERMISSION_CODE = 1;
    private SuperTextView superTextView=null;
    private RxRoundProgressBar progressBar=null;
    private TextView splashSettingTv=null;
    private int onClickCount=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StatusBarUtils.setTransparent(this);
        initView();
        HeartBeatRequest heartBeatRequest=new HeartBeatRequest();
        heartBeatRequest.device_sn="JVA0-QF97-4F7A-ZKA5";
        HttpEngine.getHeartBeat(heartBeatRequest, new BaseObserver<Res<HeartBeat>>(){});
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "必需权限未授予：" + permissions[i], Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (mContext.checkSelfPermission(Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                    YskFaceEngine.instance().initSDK(mContext, "", this, this);
                }
            }else {
                YskFaceEngine.instance().initSDK(mContext, "", this, this);
            }
        }
    }

    private void initView(){
        superTextView=findViewById(R.id.super_textview);
        progressBar=findViewById(R.id.loading_progress_bar) ;
        splashSettingTv=findViewById(R.id.splash_setting_tv);
        superTextView.setDynamicText(R.string.app_splash_tv);
        superTextView.setOnDynamicListener(new SuperTextView.OnDynamicListener() {
            @Override
            public void onChange(int position) {

            }

            @Override
            public void onCompile() {
                ActivityCompat.requestPermissions(SplashActivity.this,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION_CODE);
            }
        });
        superTextView.setDynamicStyle(SuperTextView.DynamicStyle.CHANGE_COLOR);
        superTextView.setDuration(100);
        superTextView.setSelectedColorResource(R.color.color_ffffff);
        superTextView.start();
        splashSettingTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickCount==5){
                    onClickCount=0;
                }else {
                    onClickCount++;
                    Toasty.normal(mContext, "再点击"+(5-onClickCount)+"次进入可设置服务器").show();
                }
                IntentUtils.startIntent(mContext, SettingActivity.class);
            }
        });
    }


    @Override
    public void onChange(int i, int i1) {
        if (superTextView!=null){
            superTextView.setVisibility(View.GONE);
        }
        if (progressBar!=null){
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setMax(i);
            progressBar.setProgress(i1);
        }
    }

    @Override
    public void onUpgradePersonFaceFeature(int i, int i1) {

    }

    @Override
    public void initSuccess() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toasty.success(mContext,"初始化成功").show();
                TipsDialog tipsDialog=new TipsDialog(mContext);
                tipsDialog.show();
                tipsDialog.setmUserAgreementOnClick(new TipsDialog.UserAgreementOnClick() {
                    @Override
                    public void userAgreementOnClick(boolean isAgreement) {
                        IntentUtils.startIntent(mContext, FaceScanActivity.class);
                        finish();
                    }
                });
            }
        });

    }

    @Override
    public void needBind(Throwable throwable) {
        throwable.printStackTrace();

    }

    @Override
    public void initFailed(Throwable throwable) {
        throwable.printStackTrace();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toasty.error(mContext,"初始化失败").show();
                TipsDialog tipsDialog=new TipsDialog(mContext);
                tipsDialog.show();
                tipsDialog.setmUserAgreementOnClick(new TipsDialog.UserAgreementOnClick() {
                    @Override
                    public void userAgreementOnClick(boolean isAgreement) {
                        IntentUtils.startIntent(mContext, FaceScanActivity.class);
                        finish();
                    }
                });
            }
        });


    }
}
