package com.yrickwang.library.job.v14;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import com.yrickwang.library.Job;
import com.yrickwang.library.TimingTaskManager;
import com.yrickwang.library.job.Common;

/**
 * Created by wangyi on 2017/3/31.
 */

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    public static final String EXTRA_JOB_ID = "com.yrickwang.extra.ob.id";

    static Intent createIntent(Context context, int jobId) {
        return new Intent(context, AlarmBroadcastReceiver.class).putExtra(EXTRA_JOB_ID, jobId);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.hasExtra(EXTRA_JOB_ID)) {
            if (Build.VERSION.SDK_INT < 19) {
                Intent startService = ConcurrentIntentService.createIntent(context, intent.getIntExtra(EXTRA_JOB_ID, -1));
                context.startService(startService);
            } else {
                int jobId = intent.getIntExtra(EXTRA_JOB_ID, -1);
                Job job = TimingTaskManager.get().getJobDataManager().getJob(jobId);
                Log.d("wangyi", "jobid=" + jobId);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, AlarmBroadcastReceiver.createIntent(context, job.getJobId()), PendingIntent.FLAG_CANCEL_CURRENT);
                Common.alarmJob(context, SystemClock.elapsedRealtime(), job.getIntervalMillis(), pendingIntent);
                Intent startService = ConcurrentIntentService.createIntent(context, intent.getIntExtra(EXTRA_JOB_ID, -1));
                //do your thing!
                context.startService(startService);
            }
        }

    }
}
