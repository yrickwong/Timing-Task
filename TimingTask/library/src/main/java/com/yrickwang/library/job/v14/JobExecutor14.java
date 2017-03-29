package com.yrickwang.library.job.v14;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.yrickwang.library.AlarmJobService;
import com.yrickwang.library.Job;
import com.yrickwang.library.job.JobExecutor;

import static android.content.Context.ALARM_SERVICE;
import static com.yrickwang.library.AlarmJobService.ALARM_ACTION;

/**
 * Created by wangyi on 2017/3/28.
 */

public class JobExecutor14 implements JobExecutor {

    protected final Context mApplicationContext;

    public JobExecutor14(Context applicationContext) {
        this.mApplicationContext = applicationContext;
    }

    @Override
    public void execute(Job job) {
        AlarmManager alarmManager = (AlarmManager) mApplicationContext.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(mApplicationContext, AlarmJobService.class);
        intent.setAction(ALARM_ACTION);//自定义的执行定义任务的Action
        PendingIntent pendingIntent = PendingIntent.getService(mApplicationContext, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        //在时间点triggerAtMills执行，如果该时间已过则立即执行，然后以intervalAtMills间隔重复执行该任务
        alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis() + 20 * 1000, job.getIntervalMillis(), pendingIntent);
    }
}
