package com.yrickwang.library.job.v14;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.yrickwang.library.Job;
import com.yrickwang.library.job.JobExecutor;

import static android.content.Context.ALARM_SERVICE;
import static com.yrickwang.library.job.v14.ConcurrentIntentService.ALARM_ACTION;
import static com.yrickwang.library.job.v14.ConcurrentIntentService.EXTRA_JOB_ID;

/**
 * Created by wangyi on 2017/3/28.
 * api 14版本的兼容
 */

public class JobExecutor14 implements JobExecutor {

    protected final Context mApplicationContext;

    public JobExecutor14(Context applicationContext) {
        this.mApplicationContext = applicationContext;
    }

    @Override
    public void execute(Job job) {
        AlarmManager alarmManager = (AlarmManager) mApplicationContext.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(mApplicationContext, ConcurrentIntentService.class);
        intent.setAction(ALARM_ACTION);//自定义的执行定义任务的Action
        intent.putExtra(EXTRA_JOB_ID, job.getJobId());
        PendingIntent pendingIntent = PendingIntent.getService(mApplicationContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent);
        long triggerAtMillis = System.currentTimeMillis() + 2 * 1000;
        //在时间点triggerAtMills执行，如果该时间已过则立即执行，然后以intervalAtMills间隔重复执行该任务

        //setRepeating在api19开始就失效了，所以需要适配19
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
        } else {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, job.getIntervalMillis(), pendingIntent);
        }
    }
}
