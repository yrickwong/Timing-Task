package com.yrickwang.timingtask.job;

import com.yrickwang.library.task.Task;
import com.yrickwang.library.task.TaskFactory;

/**
 * Created by wangyi on 2017/3/30.
 */

public class BackgroundTaskFactory implements TaskFactory {
    @Override
    public Task createTask() {
        return new BackgroundTask();
    }
}
