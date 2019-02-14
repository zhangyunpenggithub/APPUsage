package com.example.appusage

import android.app.usage.UsageStatsManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.test.*
import android.content.Intent
import android.widget.Toast
import android.R
import android.app.usage.UsageStats
import android.provider.Settings
import android.util.Log
import java.util.*
import kotlin.math.log


class MainActivity : AppCompatActivity() {

    private lateinit var mUsageStatsManager: UsageStatsManager

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_list_item)
        //第一次会跳转，请授予权限
        startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))

    }

    override fun onResume() {
        super.onResume()
        mUsageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
        val usageStatistics = getUsageStatistics(UsageStatsManager.INTERVAL_DAILY)
        usageStatistics.forEach {
            if (it.packageName == "com.example.appusage"){
                Log.e("zhangyun", it.totalTimeInForeground.toString())
            }
        }
    }


    /**
     * 第一次会跳转，请授予权限
     */
    private fun getUsageStatistics(intervalType: Int): List<UsageStats> {        // Get the app statistics since one year ago from the current time.
        val cal = Calendar.getInstance()
        cal.add(Calendar.YEAR, -1)

        val queryUsageStats = mUsageStatsManager
                .queryUsageStats(intervalType, cal.timeInMillis,
                        System.currentTimeMillis())
        if (queryUsageStats.size == 0) {
            Toast.makeText(this,
                    "explanation_access_to_appusage_is_not_enabled",
                    Toast.LENGTH_LONG).show()
                startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }
        return queryUsageStats
    }



}
