package com.yrickwang.library;

import android.content.Context;
import android.os.Build;

import com.yrickwang.library.job.JobExecutor;
import com.yrickwang.library.job.v14.JobExecutor14;
import com.yrickwang.library.job.v21.JobExecutor21;
import com.yrickwang.library.task.TaskFactory;
import com.yrickwang.library.task.TaskFactoryHolder;

import java.util.Set;

/**
 * Created by wangyi on 2017/3/28.
 */

public class TimingTaskManager {


    private static class Holder {

        private static TimingTaskManager INSTANCE = new TimingTaskManager();
    }

    private TimingTaskManager() {

    }

    private JobDataManager mJobDataManager;

    private JobExecutor mExecutor;

    private TaskFactoryHolder mTaskFactoryHolder;

    public void init(Context context) {
        if (Build.VERSION.SDK_INT >= 21) {
            mExecutor = new JobExecutor21(context);
        } else if (Build.VERSION.SDK_INT >= 14) {
            mExecutor = new JobExecutor14(context);
        }

        mJobDataManager = new JobDataManager(context);
        mTaskFactoryHolder = new TaskFactoryHolder();
    }


    public static TimingTaskManager get() {
        return TimingTaskManager.Holder.INSTANCE;
    }

    public JobDataManager getJobDataManager() {
        return mJobDataManager;
    }

    public TaskFactoryHolder getTaskFactoryHolder() {
        return mTaskFactoryHolder;
    }

    public JobExecutor getExecutor() {
        return mExecutor;
    }

    //启动任务
    public void schedule(Job job) {
        if (mExecutor == null) {
            throw new IllegalArgumentException("sExecutor is crash can't be null!");
        }
        mJobDataManager.put(job);
        mExecutor.execute(job);
    }

    /**
     * 取消任务
     *
     * @param jobid
     */
    public void cancel(int jobid) {
        mExecutor.cancel(jobid);
    }

    //增加tag只是为了分类而已，其实不加tag也行，在job中定义setTask接口就行了,客户端自行去new一个自定义的即可
    public void cancelAllByTag(String tag) {
        Set<Job> jobSet = mJobDataManager.getAllJobByTag(tag);
        for (Job job : jobSet) {
            cancel(job.getJobId());
        }
    }

    public void cancelAll() {
        Set<Job> jobSet = mJobDataManager.getAllJobByTag(null);
        for (Job job : jobSet) {
            cancel(job.getJobId());
        }
    }

    public void putTaskFactory(String tag, TaskFactory taskFactory) {
        mTaskFactoryHolder.putFactory(tag, taskFactory);
    }
}
