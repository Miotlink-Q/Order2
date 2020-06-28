package com.order.android.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.order.android.R;
import com.order.android.base.BaseActivity;
import com.order.android.utils.IntentUtils;
import com.order.android.utils.StatusBarUtils;
import com.order.android.view.DrawImageView;
import com.zqzn.android.facesearch.YskCallback;
import com.zqzn.android.facesearch.YskFaceEngine;
import com.zqzn.android.facesearch.YskPerson;
import com.zqzn.android.facesearch.bean.FaceTrackInfo;
import com.zqzn.android.facesearch.camera.CameraTextureView;
import com.zqzn.android.facesearch.exception.ParameterException;

import java.util.List;

public class FaceScanActivity extends BaseActivity implements YskCallback.FaceDetect {

    private static String TAG=FaceScanActivity.class.getName();
    private FrameLayout mFrCamera;
    private ImageView imageView=null;
    private int mCameraWidth;
    private int mCameraHeight;
    /**
     * 人脸框缩放因子:实际View宽度(高度)和摄像头预览宽度(高度)的比例系数，以便在预览View上正确绘制人脸框区域
     */
    private float mCoordinateScaleFactor = -1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face);
        StatusBarUtils.setTransparent(this);
        mFrCamera = findViewById(R.id.fr_camera);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                IntentUtils.startIntent(mContext, OrderActivity.class);
            }
        }, 10000);

    }

    @Override
    protected void onResume() {
        super.onResume();
        startRecognize();
        setCameraPreviewWindow();

    }

    @Override
    protected void onPause() {
        super.onPause();
        stopRecognize();
    }


    private void stopRecognize(){
        try {
            YskFaceEngine.instance().stopRecognize();
        } catch (Exception e) {
            Log.e(TAG, "recognize_stop===" + e.getMessage());
        }
    }

    /**
     * 结束暂停状态，恢复SDK回调信息
     */
    private void resumeRecognize(){
        try {
            YskFaceEngine.instance().resumeRecognize();
        } catch (ParameterException e) {
            Log.e(TAG, "recognize_resume===" + e.getMessage());
        }
    }

    /**
     *  暂停检测识别处理
     *
     *  此时不会再收到SDK回调信息，但是摄像头预览可正常使用
     */
    private void pauseRecognize(){
        try {
            YskFaceEngine.instance().pauseRecognize();
        } catch (ParameterException e) {
            Log.e(TAG, "recognize_pause===" + e.getMessage());
        }
    }

    /**
     * 开始进行人脸识别
     *
     * 将摄像头开启并进行检测，根据检测到的数据进行各种数据和状态回调
     */
    private void startRecognize(){
        try {
            YskFaceEngine.instance().startRecognize(this, 1,this);
        } catch (ParameterException e) {
            Log.e(TAG, "recognize_start===" + e.getMessage());
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }



    private void setCameraPreviewWindow() {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mFrCamera.getLayoutParams();
        //预览区域的实际宽高,(根据项目实际UI设计设置：指定宽或高中的一个，另一个根据示例代码计算)
        int viewWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        //这里指定了宽的长度，高的长度根据代码计算
        int viewHeight = 0;
        //获取摄像头可视化对象
        CameraTextureView cameraTextureView= YskFaceEngine.instance().getCameraTextureView();
        //获取摄像头的宽高互换结果，true为没有互换，false为已互换。互换后，getWidth获取的就是原摄像头的高度信息，getHeight获取的是原摄像头的宽度信息
        if (cameraTextureView.getFaceCamera().getCameraParams().isPreviewWHChange()) {
            mCameraHeight = cameraTextureView.getFaceCamera().getCameraParams().getPreviewSize().getWidth();
            mCameraWidth = cameraTextureView.getFaceCamera().getCameraParams().getPreviewSize().getHeight();
        } else {
            mCameraHeight = cameraTextureView.getFaceCamera().getCameraParams().getPreviewSize().getHeight();
            mCameraWidth = cameraTextureView.getFaceCamera().getCameraParams().getPreviewSize().getWidth();
        }
        //计算实际View的高度
        viewHeight = viewWidth * mCameraHeight / mCameraWidth;
        //缩放因子：实际View宽度(高度)和摄像头预览宽度(高度)的比例系数，以便在预览View上正确绘制人脸框区域
        mCoordinateScaleFactor = 1.0f * viewWidth / mCameraWidth;
        //动态设置预览View的宽高
        layoutParams.width = viewWidth;
        layoutParams.height = viewHeight;
        DrawImageView imageView=new DrawImageView(this);
        mFrCamera.setLayoutParams(layoutParams);
        mFrCamera.addView(cameraTextureView);
        mFrCamera.addView(imageView);
    }

    @Override
    public void onFaceDetected(List<FaceTrackInfo> list) {

    }

    @Override
    public void onNoFaceDetected() {

    }

    @Override
    public void onFaceFind(String s, YskPerson yskPerson, byte[] bytes, String s1, List<YskPerson> list) {

        IntentUtils.startIntent(mContext,OrderActivity.class);
    }

    @Override
    public void onFaceNotFind(String s) {

    }

    @Override
    public void onAttack(String s, YskPerson yskPerson, byte[] bytes, String s1) {

    }

    @Override
    public void faceToSmall(String s) {

    }

    @Override
    public void onLowQuality(int i) {

    }
    YskCallback.CloudBind cloudBind= new YskCallback.CloudBind() {
        @Override
        public void onUnbind() {

        }

        @Override
        public void onSnDisabled() {

        }
    };


}
