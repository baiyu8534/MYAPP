package com.example.baiyu.myapp.activity

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.baiyu.myapp.MyApplication


/**
 * @author BaiYu
 * @description:基础activity
 * @date :2019/9/21 14:23
 */
open class BaseActivity : AppCompatActivity() {

    protected val TAG: String = javaClass.simpleName
    lateinit var myBaseActivity: BaseActivity
    protected lateinit var mConstant: Context

    //TODO 这又网络监听，好像暂时用不到这个hander
//    public lateinit var mbaseActivityHandler: BaseActivityHandler

//    class BaseActivityHandler(var activity: BaseActivity) : Handler() {
//        var activityWeakReference: WeakReference<BaseActivity>
//
//        init {
//            activityWeakReference = WeakReference(activity)
//        }
//
//        override fun handleMessage(msg: Message?) {
//            super.handleMessage(msg)
//            var activity = activityWeakReference.get()
//            if (activity != null && msg != null) {
//                when (msg.what) {
//                    AppConstant.HANDLER_WHAT_NETWORK_CONN_FAIL -> {
//                        activity.noNetworkConnFail()
//                    }
//                    AppConstant.HANDLER_WHAT_NETWORK_CONN_SUCCESS -> {
//                        activity.noNetworkConnSuccess()
//                    }
//                }
//            }
//        }
//
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.addActivity(this)
        myBaseActivity = this
        mConstant = this
//        mbaseActivityHandler = BaseActivityHandler(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.navigationBarColor = Color.TRANSPARENT
        }
    }

    override fun onResume() {
        super.onResume()
        myBaseActivity = this

    }

    override fun onDestroy() {
        super.onDestroy()
        MyApplication.removeActivity(this)
    }

    /**
     * 需要时的页面提示，一般不提示突然断网（没有网络请求的时候），就是在页面中加载是判断下有网没，没救提示让后不加载就行
     * 一般用不到
     * 需要监听网络断开的activity复写这个方法即可
     */
    open fun onNetworkConnFail() {
        //放不到这里，因为用snackBar，需要view。如果用其他提示方法就可以统一放这里，比如Dialog
        //用snackBar就导致了我得在所有activity中都加
        //虽然在这可以获取contentView，但是snackBar不是随便给个view就可以的。。。很烦。。
        //UIUtil.snackNewWorkErrorMessage(mRvShow, getString(R.string.alert_message_no_network_conn));

    }

    /**
     * 需要监听网络回复的activity复写这个方法即可
     * 一般比如视频播放时突然WiFi掉了然后视频提示是否用流量继续，不用则卡着，这是有wifi连接后回调视频自动播放
     * 网络为wifi
     */
    open fun onNetworkConnSuccessAsWifi() {

    }

    /**
     * 连接到移动流量时，要看这个页面用不用特意监听网络变化，如费流量多的情况下，突然变5g需要提示用户是否继续
     * 一般页面用不到
     * 需要监听网络回复的activity复写这个方法即可
     * 网络为w流量
     */
    open fun onNetworkConnSuccessAs5G() {

    }

}