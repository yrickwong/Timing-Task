package com.yrickwang.library.task;

import android.support.v4.util.ArrayMap;

/**
 * Created by wangyi on 2017/3/30.
 */

public class TaskFactoryHolder {

    private final ArrayMap<String, TaskFactory> mFactoryMap;
    private static final Object sLocker = new Object();

    public TaskFactoryHolder() {
        mFactoryMap = new ArrayMap<>();
    }

    public void putFactory(String tag, TaskFactory taskFactory) {
        synchronized (sLocker) {
            if (!mFactoryMap.containsKey(tag)) {
                mFactoryMap.put(tag, taskFactory);
            }
        }
    }

    public Task createTask(String tag) {
        TaskFactory taskFactory;
        synchronized (sLocker) {
            taskFactory = mFactoryMap.get(tag);
        }
        if (taskFactory != null) {
            return taskFactory.createTask();
        }
        return null;
    }
}
