package com.yrickwang.library;

import android.content.Context;
import android.os.Build;

import com.yrickwang.library.task.JobExecutor;
import com.yrickwang.library.task.v14.JobExecutor14;
import com.yrickwang.library.task.v21.JobExecutor21;

/**
 * Created by wangyi on 2017/3/28.
 */

public class TimingTaskManager {

    private static JobExecutor sExecutor;

    public static void init(Context context) {
        if (Build.VERSION.SDK_INT >= 21) {
            sExecutor = new JobExecutor21(context);
        } else if (Build.VERSION.SDK_INT >= 14) {
            sExecutor = new JobExecutor14(context);
        }
    }

    public static void startTimingTask(Job job) {
        if (sExecutor == null) {
            throw new IllegalArgumentException("sExecutor is crash can't be null!");
        }
        sExecutor.execute(job);
    }

}
