package com.yrickwang.library.task;

import com.yrickwang.library.Job;

/**
 * Created by wangyi on 2017/3/30.
 */

public abstract class BaseTask implements Task {


    private Params mParams;

    @Override
    public void setJob(Job job) {
        mParams = new Params(job);
    }

    public Params getParams() {
        return mParams;
    }


}
