package com.example.baiyu.myapp.service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import com.example.baiyu.myapp.MyApplication
import com.example.baiyu.myapp.utils.NetworkConnUtil

/**
 * @author BaiYu
 * @description:基础服务，监控网络变化
 * @date :2019/9/21 18:19
 */
class BaseService : Service() {

    lateinit var mContext: Context

    var mReceiver = object : BroadcastReceiver() {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent != null && context != null) {
                if (intent.action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {

                    if (NetworkConnUtil.isWifiConnected(context)) {
                        MyApplication.isWifi = true
                        MyApplication.isConnected = true
                        MyApplication.currentActivity?.onNetworkConnSuccessAsWifi()
                    }

                    if (NetworkConnUtil.is5GConnected(context)) {
                        MyApplication.isMobile = true
                        MyApplication.isConnected = true
                        MyApplication.currentActivity?.onNetworkConnSuccessAs5G()
                    }

                    if (!NetworkConnUtil.isNetWorkAvailable(context)) {
                        MyApplication.isMobile = false
                        MyApplication.isConnected = false
                        MyApplication.isWifi = false
                        MyApplication.currentActivity?.onNetworkConnFail()
                    }
                }
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate() {
        super.onCreate()
        mContext = this
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(mReceiver, intentFilter)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}