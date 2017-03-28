package com.yrickwang.library.task.v14;

import android.content.Context;

import com.yrickwang.library.Job;
import com.yrickwang.library.task.JobExecutor;

/**
 * Created by wangyi on 2017/3/28.
 */

public class JobExecutor14 implements JobExecutor {

    protected final Context mApplicationContext;

    public JobExecutor14(Context applicationContext) {
        this.mApplicationContext=applicationContext;
    }

    @Override
    public void execute(Job request) {

    }
}
