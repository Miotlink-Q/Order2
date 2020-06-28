package com.order.android.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Calendar;

public class CustomTimeView extends android.support.v7.widget.AppCompatTextView {

    private final CustomTimeView textView;

    private String time;

    private TimeHandler mTimehandler=new TimeHandler();

    public CustomTimeView(Context context) {
        this(context,null);
    }

    public CustomTimeView(Context context, AttributeSet attrs) {
        super(context,attrs);
        this.textView=this;
        init(context);
    }

    private void init(Context context) {
        try{
            updateClock();
            new Thread(new Runnable() {
                @Override public void run() {
                    mTimehandler.startScheduleUpdate();
                }

            }).start();

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public String getTime(){
        mTimehandler.stopScheduleUpdate();
        return time;

    }

//返回当前的时间，并结束handler的信息发送

    private void updateClock() {
        Calendar calendar= Calendar.getInstance();
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        int second=calendar.get(Calendar.SECOND);
        String s="";
        String m="";
        String h="";
        if(hour<10){
            h="0"+hour;
        }else{
            h=hour+"";
        }
        if(minute<10){
            m="0"+minute;
        }else{
            m=minute+"";
        }
        if(second<10){
            s="0"+second;
        }else{
            s=second+"";

        }
        time=h+":"+m+":"+s;
        textView.setText(time);

    }

    private class TimeHandler extends Handler {

        private boolean mStopped;
        private void post(){
            sendMessageDelayed(obtainMessage(0),1000);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(!mStopped){
                updateClock();
                post();
            }
        }

//开始更新
        public void startScheduleUpdate(){

            mStopped=false;

            post();

        }
//停止更新
       public void stopScheduleUpdate(){
            mStopped=true;
            removeMessages(0);
        }
    }
}
