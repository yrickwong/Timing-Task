package com.yrickwang.timingtask;

import android.app.Application;

import com.yrickwang.library.TimingTaskManager;

/**
 * Created by wangyi on 2017/3/28.
 */

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        TimingTaskManager.init(this);
    }
}
