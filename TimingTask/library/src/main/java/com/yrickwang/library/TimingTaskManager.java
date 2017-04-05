package com.yrickwang.library;

import android.content.Context;
import android.os.Build;

import com.yrickwang.library.job.JobExecutor;
import com.yrickwang.library.job.v14.JobExecutor14;
import com.yrickwang.library.task.TaskFactory;
import com.yrickwang.library.task.TaskFactoryHolder;

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

    private TaskFactoryHolder mTaskFactoryHolder;

    public void init(Context context) {
        if (Build.VERSION.SDK_INT >= 21) {
            mExecutor = new JobExecutor14(context);
        } else if (Build.VERSION.SDK_INT >= 14) {
            mExecutor = new JobExecutor14(context);
        }

        mJobDataManager = new JobDataManager(context);
        mTaskFactoryHolder = new TaskFactoryHolder();
    }


    public static TimingTaskManager get() {
        return TimingTaskManager.Holder.INSTANCE;
    }

    public JobDataManager getJobDataManager() {
        return mJobDataManager;
    }

    public TaskFactoryHolder getTaskFactoryHolder() {
        return mTaskFactoryHolder;
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

    public void putTaskFactory(String tag, TaskFactory taskFactory) {
        mTaskFactoryHolder.putFactory(tag, taskFactory);
    }
}
