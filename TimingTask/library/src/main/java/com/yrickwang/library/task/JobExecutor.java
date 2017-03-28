package com.yrickwang.library.task;

import com.yrickwang.library.Job;

/**
 * Created by wangyi on 2017/3/28.
 */

public interface JobExecutor {
    void execute(Job job);
}
