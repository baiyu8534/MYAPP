package com.example.baiyu.myapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi

/**
 * @author BaiYu
 * @description:
 * @date :2019/9/21 22:00
 */
class NetworkConnUtil {
    companion object {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        @SuppressLint("ServiceCast")
        fun isNetWorkAvailable(context: Context): Boolean {
            val systemService =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = systemService.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        @SuppressLint("ServiceCast")
        fun isWifiConnected(context: Context): Boolean {
            val systemService =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = systemService.getActiveNetworkInfo()
            return activeNetworkInfo != null && activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI

        }

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        @SuppressLint("ServiceCast")
        fun is5GConnected(context: Context): Boolean {
            val systemService =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = systemService.getActiveNetworkInfo()
            return activeNetworkInfo != null && activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE

        }
    }

}