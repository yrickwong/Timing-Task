package com.yrickwang.library.task;

import android.support.annotation.WorkerThread;

import com.yrickwang.library.Job;
import com.yrickwang.library.utils.PersistableBundleCompat;

/**
 * Created by wangyi on 2017/3/29.
 * <p>
 */

public interface Task {

    @WorkerThread
    void doActionInBackground(Params params);

    void setJob(Job job);

    Params getParams();

    final class Params {
        private final Job mJob;
        private PersistableBundleCompat mExtras;

        Params(Job job) {
            mJob = job;
        }

        public PersistableBundleCompat getExtras() {
            if (mExtras == null) {
                mExtras = mJob.getExtras();
                if (mExtras == null) {
                    mExtras = new PersistableBundleCompat();
                }
            }
            return mExtras;
        }

        public long IntervalMillis() {
            return mJob.getIntervalMillis();
        }
    }
}
