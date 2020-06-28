package com.order.android.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.order.android.R;
import com.order.android.adapter.DishesAdapter;
import com.order.android.base.BaseActivity;
import com.order.android.bean.DishesDetails;
import com.order.android.utils.Const;
import com.order.android.view.CircleImageView;
import com.order.android.view.CustomTimeView;

import java.util.Calendar;
import java.util.Date;

import es.dmoral.toasty.Toasty;

public class OrderActivity extends BaseActivity implements View.OnClickListener {

    public static void startActivity(Context mContext){
        Intent intent=new Intent(mContext, OrderActivity.class);
        mContext.startActivity(intent);
    }

    /**
     * 菜单列表
     */
    private RecyclerView recyclerView=null;
    /**
     * 菜单适配器
     */
    private DishesAdapter dishesAdapter=null;
    /**
     * 餐类型 中餐 午餐 晚餐
     */
    private TextView mealTypeTv=null;
    /**
     * 星期
     */
    private TextView mealWeekTv=null;
    /**
     * 时间
     */
    private CustomTimeView mealTimeTv=null;
    /**
     * 用户名
     */
    private TextView orderUsernameTv=null;
    /**
     * 用户地址
     */
    private TextView orderUserAddressTv=null;
    /**
     * 价格
     */
    private TextView orderPriceTv=null;

    /**
     * 上一位按钮
     */
    private Button orderPreviousBtn=null;
    /**
     * 确定
     */
    private Button orderSubmitBtn=null;
    /**
     * 取消
     */
    private Button orderCancelBtn=null;

    /**
     * 头像图片
     */
    private CircleImageView mealUserCircleImg=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();
    }

    private void initView(){
        Date date=new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);

        mealTypeTv=findViewById(R.id.meal_type_tv);
        mealWeekTv=findViewById(R.id.meal_week_tv);
        mealTimeTv=findViewById(R.id.meal_time_tv);
        orderUsernameTv=findViewById(R.id.order_username_tv);
        orderUserAddressTv=findViewById(R.id.order_user_address_tv);
        orderPriceTv=findViewById(R.id.order_price_tv);
        orderPreviousBtn=findViewById(R.id.order_previous_btn);
        orderSubmitBtn=findViewById(R.id.order_submit_btn);
        orderCancelBtn=findViewById(R.id.order_cancel_btn);
        orderPreviousBtn.setOnClickListener(this);
        orderSubmitBtn.setOnClickListener(this);
        orderCancelBtn.setOnClickListener(this);
        mealWeekTv.setText(Const.getWeekDay(calendar.getFirstDayOfWeek()));
        mealUserCircleImg=findViewById(R.id.meal_user_circle_img);
        recyclerView=(RecyclerView)findViewById(R.id.dishes_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dishesAdapter = new DishesAdapter();

        recyclerView.setAdapter(dishesAdapter);
        View headView = getLayoutInflater().inflate(R.layout.item_header, null);
        dishesAdapter.addHeaderView(headView);
        dishesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });

        /**
         * 请求数据更新
         */
        dishesAdapter.setNewData(DishesDetails.getDishesDetailsList());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.order_previous_btn:
                Toasty.success(mContext, orderPreviousBtn.getText().toString()).show();
                break;
            case R.id.order_cancel_btn:
                Toasty.success(mContext, orderSubmitBtn.getText().toString()).show();
                break;
            case R.id.order_submit_btn:
                Toasty.success(mContext, orderCancelBtn.getText().toString()).show();
                break;
        }
    }
}