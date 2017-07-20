# WindowLoading

请求对话框的非dialog实现，使用的是addContentView添加布局实现

比起dialog方式:

- 1.绑定activity，在需要的时候add，在结束或者activity的onDestory()方法中强制remove掉；
- 2.不影响activity的关闭，比如用户点击返回键关闭activity的情况

# 核心代码
```
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
```
