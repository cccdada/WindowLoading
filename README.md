# WindowLoading

请求对话框的非dialog实现，使用的是addContentView添加布局实现

比起dialog方式:

- 1.绑定activity，在需要的时候add，在结束或者activity的onDestory()方法中强制remove掉；
- 2.不影响activity的关闭，比如用户点击返回键关闭activity的情况
