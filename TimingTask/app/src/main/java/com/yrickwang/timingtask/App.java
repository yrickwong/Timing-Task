package com.yrickwang.timingtask;

import android.app.Application;

import com.yrickwang.library.TimingTaskManager;
import com.yrickwang.timingtask.job.BackgroundTask;
import com.yrickwang.timingtask.job.BackgroundTaskFactory;

/**
 * Created by wangyi on 2017/3/28.
 */

public class App extends Application {
    private static App sInstance;

    public App() {
        sInstance = this;
    }

    public static App getApp() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TimingTaskManager.get().init(this);
        //新增的task类别需要先注册，
        TimingTaskManager.get().putTaskFactory(BackgroundTask.TAG, new BackgroundTaskFactory());
    }
}
