package com.sheng.windowloading;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.sheng.windowloading.base.BaseActivity;

/**
 * Created by wangsheng on 17/7/19.
 */

public class SecondActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showLoadingFragment();
            }
        },500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoadingFragment();
            }
        },10000);
    }

}
