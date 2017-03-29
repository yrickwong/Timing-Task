package com.yrickwang.library;

import com.yrickwang.library.task.Task;
import com.yrickwang.library.utils.PersistableBundleCompat;

/**
 * Created by wangyi on 2017/3/28.
 */

public class Job {

    private Job() {
    }

    private int id;

    private long intervalMillis;

    private PersistableBundleCompat mBundle;

    private Task mTask;

    public long getIntervalMillis() {
        return intervalMillis;
    }

    public PersistableBundleCompat getBundle() {
        return mBundle;
    }

    public int getJobId() {
        return id;
    }

    public void setTask(Task task) {
        mTask = task;
    }

    public Task getTask() {
        return mTask;
    }

    public static final class Builder {
        private long mIntervalMillis;
        private PersistableBundleCompat mBundle;
        private int mId;

        public Builder() {
            //任务id自动生成
            mId = TimingTaskManager.get().getJobDataManager().nextJobId();
        }

        public Builder setPeriodic(long intervalMillis) {
            mIntervalMillis = intervalMillis;
            return this;
        }

        public Builder setExtras(PersistableBundleCompat bundle) {
            mBundle = bundle;
            return this;
        }

        public Job build() {
            Job job = new Job();
            job.intervalMillis = mIntervalMillis;
            job.mBundle = mBundle;
            job.id = mId;
            return job;
        }
    }
}
