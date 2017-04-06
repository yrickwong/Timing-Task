package com.yrickwang.library.job.v21;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.yrickwang.library.Job;
import com.yrickwang.library.job.JobExecutor;

import static android.app.job.JobInfo.NETWORK_TYPE_NONE;


/**
 * Created by wangyi on 2017/3/28.
 */

public class JobExecutor21 extends JobExecutor {

    private final Context mApplicationContext;

    public JobExecutor21(Context applicationContext) {
        mApplicationContext = applicationContext;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void execute(Job job) {
        JobScheduler scheduler = (JobScheduler) mApplicationContext.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        ComponentName service = new ComponentName(mApplicationContext, TimingJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(job.getJobId(), service);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //As a workaround, I'm using following code to schedule jobs at periodic intervals if job interval is less than 15minutes.
            builder.setMinimumLatency(job.getIntervalMillis());
        } else {
            builder.setPeriodic(job.getIntervalMillis());
        }
        builder.setRequiredNetworkType(NETWORK_TYPE_NONE);
        scheduler.schedule(builder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void cancel(int jodId) {
        JobScheduler scheduler = (JobScheduler) mApplicationContext.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        scheduler.cancel(jodId);
    }
}
