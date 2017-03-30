package com.yrickwang.library;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.LruCache;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wangyi on 2017/3/29.
 */

public class JobDataManager {
    private static final String PREF_FILE_NAME = "timingtask_jobs";
    private static final String JOB_ID_COUNTER = "JOB_ID_COUNTER";

    private static final int CACHE_SIZE = 30;
    private final SharedPreferences mPreferences;
    private final AtomicInteger mJobCounter;
    private final LruCache<Integer, Job> mCache;

    public JobDataManager(Context context) {
        mPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        mCache = new LruCache<>(CACHE_SIZE);
        int lastJobId = mPreferences.getInt(JOB_ID_COUNTER, 0);
        mJobCounter = new AtomicInteger(lastJobId);
    }

    public synchronized int nextJobId() {
        int id = mJobCounter.incrementAndGet();
        if (id < 0) {
            id = 1;
            mJobCounter.set(id);
        }
        mPreferences.edit().putInt(JOB_ID_COUNTER, id).apply();
        return id;
    }

    public synchronized void put(final Job job) {
        updateJobInCache(job);
    }

    private void updateJobInCache(Job job) {
        mCache.put(job.getJobId(), job);
    }

    public synchronized Job getJob(int jobId) {
        return mCache.get(jobId);
    }
}
