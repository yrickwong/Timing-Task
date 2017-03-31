package com.yrickwang.library;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

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

    private PersistableBundleCompat mExtras;

    public long getIntervalMillis() {
        return intervalMillis;
    }

    public PersistableBundleCompat getExtras() {
        return mExtras;
    }

    public int getJobId() {
        return id;
    }

    public String getTag() {
        return tag;
    }

    public static Job fromCursor(Cursor cursor) {
        Job job = new Builder(cursor).build();
        return job;
    }

    @Override
    public String toString() {
        return "Job{" +
                "tag='" + tag + '\'' +
                ", id=" + id +
                ", intervalMillis=" + intervalMillis +
                ", mExtras=" + mExtras +
                '}';
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(JobDataManager.COLUMN_ID, id);
        contentValues.put(JobDataManager.COLUMN_TAG, tag);
        contentValues.put(JobDataManager.COLUMN_INTERVAL_MS, intervalMillis);
        if (mExtras != null) {
            contentValues.put(JobDataManager.COLUMN_EXTRAS, mExtras.saveToXml());
        }
        return contentValues;
    }

    public static final class Builder {
        private String mTag;
        private long mIntervalMillis;
        private PersistableBundleCompat mBundle;
        private int mId;


        public Builder(String tag) {
            mTag = tag;
            //任务id自动生成
            mId = TimingTaskManager.get().getJobDataManager().nextJobId();
        }

        public Builder(Cursor cursor) {
            mId = cursor.getInt(cursor.getColumnIndex(JobDataManager.COLUMN_ID));
            mTag = cursor.getString(cursor.getColumnIndex(JobDataManager.COLUMN_TAG));
            mIntervalMillis = cursor.getLong(cursor.getColumnIndex(JobDataManager.COLUMN_INTERVAL_MS));
            String extrasXml = cursor.getString(cursor.getColumnIndex(JobDataManager.COLUMN_EXTRAS));
            if (!TextUtils.isEmpty(extrasXml)) {
                mBundle = PersistableBundleCompat.fromXml(extrasXml);
            }
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
            job.mExtras = mBundle;
            job.id = mId;
            job.tag = mTag;
            return job;
        }
    }
}
