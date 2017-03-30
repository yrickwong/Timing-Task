package com.yrickwang.library;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.LruCache;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wangyi on 2017/3/29.
 */

public class JobDataManager {
    private static final String PREF_FILE_NAME = "timingtask_jobs";
    public static final String DATABASE_NAME = PREF_FILE_NAME + ".db";
    public static final String JOB_TABLE_NAME = "jobs";
    public static final int DATABASE_VERSION = 1;
    private static final String JOB_ID_COUNTER = "JOB_ID_COUNTER";

    private static final int CACHE_SIZE = 30;
    private final SharedPreferences mPreferences;
    private final AtomicInteger mJobCounter;
    private final LruCache<Integer, Job> mCache;
    private final JobOpenHelper mDbHelper;
    private SQLiteDatabase mDatabase;

    public static final String COLUMN_ID = "_id";

    public JobDataManager(Context context) {
        mPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        mCache = new LruCache<>(CACHE_SIZE);
        int lastJobId = mPreferences.getInt(JOB_ID_COUNTER, 0);
        mJobCounter = new AtomicInteger(lastJobId);
        mDbHelper = new JobOpenHelper(context);  //id还是需要做本地缓存，因为进程重启的话，会导致从0开始计算，然后alarmManager还在运行，会导致任务重复运行
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

    private void store(Job job) {
        try {
            ContentValues contentValues = job.toContentValues();
            getDatabase().insert(JOB_TABLE_NAME, null, contentValues);
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
                return Job.fromCursor(cursor);
            }

        } catch (Exception e) {

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
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
