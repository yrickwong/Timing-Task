package com.yrickwang.library.job.v21;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.yrickwang.library.Job;
import com.yrickwang.library.TimingTaskManager;
import com.yrickwang.library.task.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class TimingJobService extends JobService {

    //开一个线程池来处理后台任务
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool(Executors.defaultThreadFactory());


    public TimingJobService() {
    }

    /**
     * @param params 返回带的参数
     * @return 当返回值为false时：表示这个Job已经执行完成。
     * 当返回值为true是：表示这个Job还没有执行完成，一般是因为要进行耗时操作，
     * 单独起了一个或多个线程来执行。但是要注意的是，如果返回值是true，一定要记住在任务都做完之后调用：
     */
    @Override
    public boolean onStartJob(final JobParameters params) {
        final int jobId = params.getJobId();
        final Job job = TimingTaskManager.get().getJobDataManager().getJob(jobId);

        //耗时任务全部丢到这里
        EXECUTOR_SERVICE.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final Task task = job.getTask();
                    if (task != null) {
                        task.doAction();
                    }
                } finally {
                    // do not reschedule

                    jobFinished(params, false);
                }
            }
        });
        //由于大都后台任务都是耗时的，即异步的，所以这里返回true
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

}
