package com.sheng.windowloading.base;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author wangsheng
 */
public abstract class BaseFragment extends Fragment
{
    protected View mRootView;
    /**
     * 是否第一次对用户可见
     */
    private boolean mIsFirstVisibleToUser = true;
    /**
     * 是否对用户可见
     */
    private boolean mIsVisibleToUser;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        if (mRootView == null)
        {
            mRootView = inflater.inflate(getLayoutResId(), container, false);
            initView(mRootView);
        }
        return mRootView;
    }


    @LayoutRes
    protected abstract int getLayoutResId();

    protected void initView(View view)
    {
    }


    public boolean isVisibleToUser()
    {
        return mIsVisibleToUser;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisibleToUser = isVisibleToUser && !isHidden() && isResumed();
        if (mIsVisibleToUser)
        {
            onVisibleToUser();
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onHiddenChanged(boolean hidden)
    {
        super.onHiddenChanged(hidden);
        mIsVisibleToUser = !hidden && getUserVisibleHint() && isResumed();
        if (mIsVisibleToUser)
        {
            onVisibleToUser();
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onResume()
    {
        super.onResume();
        mIsVisibleToUser = !isHidden() && getUserVisibleHint() && isResumed();
        if (mIsVisibleToUser)
        {
            onVisibleToUser();
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onPause()
    {
        super.onPause();
        mIsVisibleToUser = !isHidden() && getUserVisibleHint() && isResumed();
        if (!mIsFirstVisibleToUser)
        {
            onInvisibleToUser();
        }
    }

    /**
     * 对用户可见
     */
    protected void onVisibleToUser()
    {
        if (mIsFirstVisibleToUser)
        {
            mIsFirstVisibleToUser = false;
        }
    }

    protected void onInvisibleToUser()
    {

    }

    public BaseActivity getBaseActivity()
    {
        return (BaseActivity) getActivity();
    }

    /**
     * 显示加载中弹框（如果当前计数为0，显示弹框，如果大于0，表示弹框已经显示）
     * {@link BaseActivity#mShowLoadingDialogCount}
     */
    public void showLoadingFragment()
    {
        getBaseActivity().showLoadingFragment();
    }

    /**
     * 隐藏加载中弹框，当计数为0的时候，才真正隐藏弹框
     * {@link BaseActivity#mShowLoadingDialogCount}
     */
    public void hideLoadingFragment()
    {
        getBaseActivity().hideLoadingFragment();
    }

}
