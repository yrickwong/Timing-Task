package com.yrickwang.library;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.LruCache;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wangyi on 2017/3/29.
 */

public class JobDataManager {
    private static final String PREF_FILE_NAME = "timingtask_jobs";
    private static final String DATABASE_NAME = PREF_FILE_NAME + ".db";
    private static final String JOB_TABLE_NAME = "jobs";
    private static final int DATABASE_VERSION = 1;
    private static final String JOB_ID_COUNTER = "JOB_ID_COUNTER";

    private static final int CACHE_SIZE = 30;
    private final SharedPreferences mPreferences;
    private final AtomicInteger mJobCounter;
    private final LruCache<Integer, Job> mCache;
    private final JobOpenHelper mDbHelper;
    private SQLiteDatabase mDatabase;

    static final String COLUMN_ID = "_id";
    static final String COLUMN_TAG = "tag";
    static final String COLUMN_INTERVAL_MS = "intervalMs";
    static final String COLUMN_EXTRAS = "extras";

    public JobDataManager(Context context) {
        mPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        mCache = new JobCacheId();
        int lastJobId = mPreferences.getInt(JOB_ID_COUNTER, 0);
        mJobCounter = new AtomicInteger(lastJobId);
        //job还是需要做本地缓存，因为进程重启的话，又不会去schedule之前的任务，就会导致job获取为空的情况
        mDbHelper = new JobOpenHelper(context);
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

    public synchronized void put(Job job) {
        updateJobInCache(job);
        store(job);
    }

    /**
     * @param tag 可以为空，为空代表获取所有
     */
    public synchronized void getAllJob(@Nullable String tag) {
        //直接从数据库中获取，如果从cache中获取有可能会出现为空的情况

    }

    private void store(Job job) {
        try {
            ContentValues contentValues = job.toContentValues();
            getDatabase().insert(JOB_TABLE_NAME, null, contentValues);
            Log.d("wangyi", "store=" + job);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateJobInCache(Job job) {
        mCache.put(job.getJobId(), job);
    }

    public synchronized Job getJob(int jobId) {
        return mCache.get(jobId);
    }


    /**
     *
     * @param tag  为null的话就说明获取所有
     * @return
     */
    public synchronized Set<Job> getAllJobByTag(@Nullable String tag) {
        return null;
    }

    private class JobCacheId extends LruCache<Integer, Job> {

        public JobCacheId() {
            super(CACHE_SIZE);
        }

        @Override
        protected Job create(Integer id) {
            return load(id);
        }
    }

    private Job load(int id) {
        Cursor cursor = null;
        try {
            String where = COLUMN_ID + "=?";
            cursor = getDatabase().query(JOB_TABLE_NAME, null, where, new String[]{String.valueOf(id)}, null, null, null);
            if (cursor.moveToFirst()) {
                Log.d("wangyi", "store=" + id);
                return Job.fromCursor(cursor);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return null;
    }


    private SQLiteDatabase getDatabase() {
        if (mDatabase == null) {
            synchronized (this) {
                if (mDatabase == null) {
                    mDatabase = mDbHelper.getWritableDatabase();
                }
            }
        }
        return mDatabase;
    }

    private class JobOpenHelper extends SQLiteOpenHelper {

        public JobOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            createJobTable(db);
        }

        private void createJobTable(SQLiteDatabase db) {
            db.execSQL("create table " + JOB_TABLE_NAME + " ("
                    + COLUMN_ID + " integer primary key, "
                    + COLUMN_TAG + " text not null, "
                    + COLUMN_INTERVAL_MS + " integer, "
                    + COLUMN_EXTRAS + " text);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
