package com.yrickwang.library.job.v14;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.yrickwang.library.Job;
import com.yrickwang.library.job.Common;
import com.yrickwang.library.job.JobExecutor;

/**
 * Created by wangyi on 2017/3/28.
 * api 14版本的兼容
 */

public class JobExecutor14 extends JobExecutor {

    protected final Context mApplicationContext;

    public JobExecutor14(Context applicationContext) {
        this.mApplicationContext = applicationContext;
    }

    @Override
    public void execute(Job job) {
        PendingIntent pendingIntent = getPendingIntent(mApplicationContext, job);
        long triggerAtMillis = SystemClock.elapsedRealtime();
        Common.alarmJob(mApplicationContext, triggerAtMillis, job.getIntervalMillis(), pendingIntent);
    }

    protected PendingIntent getPendingIntent(Context context, Job job) {
        Intent intent = AlarmBroadcastReceiver.createIntent(context, job.getJobId());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        return pendingIntent;
    }


}
