package com.yrickwang.library.job.v14;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;

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
        PendingIntent pendingIntent = getPendingIntent(mApplicationContext, job);
        long triggerAtMillis = SystemClock.elapsedRealtime();
        alarmJob(mApplicationContext, triggerAtMillis, 0, pendingIntent);
    }

    static PendingIntent getPendingIntent(Context context, Job job) {
        Intent intent = new Intent(context, ConcurrentIntentService.class);
        intent.setAction(ALARM_ACTION);//自定义的执行定义任务的Action
        intent.putExtra(EXTRA_JOB_ID, job.getJobId());
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        return pendingIntent;
    }

    static void alarmJob(Context context, long triggerAtMillis, long intervalMillis, PendingIntent pendingIntent) {
        if (context == null || pendingIntent == null) {
            throw new IllegalArgumentException("pendingIntent not be null!");
        }
        //setRepeating在api19开始就失效了，所以需要适配19
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis + intervalMillis, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis + intervalMillis, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis + intervalMillis, pendingIntent);
        }
    }
}
