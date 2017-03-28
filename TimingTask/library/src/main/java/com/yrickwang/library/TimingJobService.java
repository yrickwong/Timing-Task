package com.yrickwang.library;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class TimingJobService extends JobService {
    public TimingJobService() {
    }

    /**
     * @param params 返回带的参数
     * @return 当返回值为false时：表示这个Job已经执行完成。
     * 当返回值为true是：表示这个Job还没有执行完成，一般是因为要进行耗时操作，
     * 单独起了一个或多个线程来执行。但是要注意的是，如果返回值是true，一定要记住在任务都做完之后调用：
     */
    @Override
    public boolean onStartJob(JobParameters params) {
        PersistableBundle extras = params.getExtras();
        String result = (String) extras.get("key_job");
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        jobFinished(params, false);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

}
