package com.yrickwang.library.job.v21;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.yrickwang.library.Job;
import com.yrickwang.library.job.v14.JobExecutor14;

import static android.app.job.JobInfo.NETWORK_TYPE_NONE;


/**
 * Created by wangyi on 2017/3/28.
 */

public class JobExecutor21 extends JobExecutor14 {

    public JobExecutor21(Context applicationContext) {
        super(applicationContext);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void execute(Job job) {
        //到这里来 每次都要确保new一个新的job!
        JobScheduler scheduler = (JobScheduler) mApplicationContext.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        ComponentName service = new ComponentName(mApplicationContext, TimingJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(job.getJobId(), service);
        builder.setPeriodic(job.getIntervalMillis());
//        PersistableBundleCompat bundleCompat = job.getBundle();
//        if (bundleCompat != null) {
//            PersistableBundle bundle = new PersistableBundle();
//            builder.setExtras(bundle);
//        }

        builder.setRequiredNetworkType(NETWORK_TYPE_NONE);
        scheduler.schedule(builder.build());
    }
}
