package com.yrickwang.library;

/**
 * Created by wangyi on 2017/3/28.
 */

public class Job {
    private static int id;

    private Job() {

    }

    /**
     * jobID 任务id  递增
     *
     * @return
     */
    public static int getJobId() {
        return id++;
    }

    private long intervalMillis;

    private Object mBundle;

    public long getIntervalMillis() {
        return intervalMillis;
    }

    public Object getBundle() {
        return mBundle;
    }

    public static final class Builder {
        private long mIntervalMillis;
        private Object mBundle;

        public Builder setPeriodic(long intervalMillis) {
            mIntervalMillis = intervalMillis;
            return this;
        }

        public Builder setBundle(Object bundle) {
            mBundle = bundle;
            return this;
        }

        public Job build() {
            Job job = new Job();
            job.intervalMillis = mIntervalMillis;
            job.mBundle = mBundle;
            return job;
        }
    }
}
