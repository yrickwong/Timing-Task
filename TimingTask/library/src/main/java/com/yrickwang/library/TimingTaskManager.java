package com.yrickwang.library;

import android.content.Context;
import android.os.Build;

import com.yrickwang.library.job.JobExecutor;
import com.yrickwang.library.job.v14.JobExecutor14;
import com.yrickwang.library.job.v21.JobExecutor21;

/**
 * Created by wangyi on 2017/3/28.
 */

public class TimingTaskManager {

    private static class Holder {

        private static TimingTaskManager INSTANCE = new TimingTaskManager();
    }

    private TimingTaskManager() {

    }

    private JobDataManager mJobDataManager;

    private JobExecutor mExecutor;

    public void init(Context context) {
        if (Build.VERSION.SDK_INT >= 21) {
            mExecutor = new JobExecutor21(context);
        } else if (Build.VERSION.SDK_INT >= 14) {
            mExecutor = new JobExecutor14(context);
        }

        mJobDataManager = new JobDataManager(context);
    }


    public static TimingTaskManager get() {
        return TimingTaskManager.Holder.INSTANCE;
    }

    public JobDataManager getJobDataManager() {
        return mJobDataManager;
    }

    public JobExecutor getExecutor() {
        return mExecutor;
    }

    public void schedule(Job job) {
        if (mExecutor == null) {
            throw new IllegalArgumentException("sExecutor is crash can't be null!");
        }
        mJobDataManager.put(job);
        mExecutor.execute(job);
    }

}
