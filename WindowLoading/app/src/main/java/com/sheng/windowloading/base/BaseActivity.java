package com.sheng.windowloading.base;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.sheng.windowloading.R;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangsheng
 */
public abstract class BaseActivity extends AppCompatActivity
{
    /**
     * 记录显示加载中弹框的次数，每调一个显示加载中弹框，计数+1，调隐藏加载中弹框，
     * 计数-1，不一定隐藏弹框，当计数为0的时候，才会真正隐藏加载中弹框。
     */
    private AtomicInteger mShowLoadingDialogCount = new AtomicInteger(0);

    private View loadingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // 禁止activity横竖屏切换
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onDestroy()
    {
        forceHideLoadingFragment();
        super.onDestroy();
    }

    /**
     * 显示加载中弹框（如果当前计数为0，显示弹框，如果大于0，表示弹框已经显示）
     * {@link BaseActivity#mShowLoadingDialogCount}
     */
    public void showLoadingFragment()
    {

        int count = mShowLoadingDialogCount.getAndIncrement();
        if (count == 0)
        {

            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            addContentView(getLoadingView(), layoutParams);
        }
    }


    private View getLoadingView()
    {
        if (loadingView == null)
        {
            loadingView = getLayoutInflater().inflate(R.layout.dialog_loading, null);
        }
        return loadingView;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt("mShowLoadingCount", mShowLoadingDialogCount.get());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        mShowLoadingDialogCount = new AtomicInteger(savedInstanceState.getInt("mShowLoadingCount"));
    }

    /**
     * 隐藏加载中弹框，当计数为0的时候，才真正隐藏弹框
     * {@link BaseActivity#mShowLoadingDialogCount}
     */
    @SuppressLint("NewApi")
    public void hideLoadingFragment()
    {
        if (!isDestroyed()||isFinishing())
        {
            int count = mShowLoadingDialogCount.decrementAndGet();
            if (count < 0)
            {
                mShowLoadingDialogCount.set(0);
                count = 0;
            }
            if (count == 0 && loadingView != null && loadingView.getParent() != null)
            {
                ((ViewGroup) loadingView.getParent()).removeView(loadingView);
            }
        }
    }

    /**
     * 强制关闭LoadingFragment
     * {@link BaseActivity#mShowLoadingDialogCount}
     */
    public void forceHideLoadingFragment()
    {
        if (mShowLoadingDialogCount != null)
        {
            mShowLoadingDialogCount.set(0);
        }
        hideLoadingFragment();
    }

}
