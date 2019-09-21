package com.example.baiyu.myapp

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.baiyu.myapp.activity.BaseActivity
import com.example.baiyu.myapp.service.BaseService
import java.util.*
import kotlin.system.exitProcess

/**
 * @author BaiYu
 * @description:
 * @date :2019/9/21 17:20
 */
class MyApplication : Application() {

    init {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
    }

    companion object {
        lateinit var activitys: MutableList<BaseActivity>
        var myApplication: MyApplication? = null

        /**
         * 当前网络为wifi
         */
        var isWifi: Boolean = false
        /**
         * 当前网络为移动数据
         */
        var isMobile: Boolean = false
        /**
         * 当前网络是否连接
         */
        var isConnected: Boolean = false

        var currentActivity : BaseActivity? = null

        fun getContext(): MyApplication? {
            return myApplication
        }

        fun getInstance(): MyApplication? {
            return myApplication
        }

        fun addActivity(activity: BaseActivity) {
            activitys.add(activity)
        }

        fun removeActivity(activity: BaseActivity) {
            activitys.remove(activity)
        }

        fun finishAllActivity() {
            for (activity in activitys) {
                activity.finish()
            }
        }
    }

    private var mainTid = android.os.Process.myTid()

    override fun onCreate() {
        super.onCreate()
        if (myApplication == null) {
            myApplication = this
        }
        activitys = LinkedList<BaseActivity>()

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityResumed(activity: Activity?) {
                if (activity != null) {
                    currentActivity = activity as BaseActivity?
                }
            }

            override fun onActivityDestroyed(activity: Activity?) {
                if (currentActivity?.javaClass?.name.equals(activity?.javaClass?.name)) {
                    currentActivity = null
                }
            }

            override fun onActivityPaused(p0: Activity?) {
            }

            override fun onActivityStarted(p0: Activity?) {
            }

            override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
            }

            override fun onActivityStopped(p0: Activity?) {
            }

            override fun onActivityCreated(p0: Activity?, p1: Bundle?) {
            }

        })
    }

    @SuppressLint("ServiceCast")
    fun finishApp() {
        var actManager: ActivityManager =
            getSystemService(Context.ACCESSIBILITY_SERVICE) as ActivityManager
        val runningServices = actManager.getRunningServices(30)
        for (runningService in runningServices) {
            if (runningService.service.shortClassName.toString().equals("com.example.baiyu.myapp.service.BaseService")) {
                stopService(Intent(this, BaseService::class.java))
            }
        }
        finishApp()
        exitProcess(0)
    }

}