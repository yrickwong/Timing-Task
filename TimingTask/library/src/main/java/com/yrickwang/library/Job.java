package com.yrickwang.library;

import com.yrickwang.library.utils.PersistableBundleCompat;

/**
 * Created by wangyi on 2017/3/28.
 */

public class Job {

    private Job() {
    }

    /**
     * 这个tag代表task的类别
     */
    private String tag;

    private int id;

    private long intervalMillis;

    private PersistableBundleCompat mBundle;

    public long getIntervalMillis() {
        return intervalMillis;
    }

    public PersistableBundleCompat getExtras() {
        return mBundle;
    }

    public int getJobId() {
        return id;
    }

    public static final class Builder {
        private final String mTag;
        private long mIntervalMillis;
        private PersistableBundleCompat mBundle;
        private int mId;


        public Builder(String tag) {
            mTag = tag;
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
            job.tag = mTag;
            return job;
        }
    }
}
