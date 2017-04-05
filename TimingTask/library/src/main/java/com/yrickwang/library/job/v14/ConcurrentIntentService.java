package com.yrickwang.library.job.v14;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.yrickwang.library.Job;
import com.yrickwang.library.TimingTaskManager;
import com.yrickwang.library.task.Task;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 并发处理多个request的IntentService
 * <p>
 * 如果是以单线程的方式处理多个启动请求，就会采用IntentService了
 * IntentService还有一个好处就是 处理完请求后就会自行销毁，不用手动stop
 */
public class ConcurrentIntentService extends Service {

    public static final String EXTRA_JOB_ID = "com.yrickwang.extra.ob.id";

    private ExecutorService mExecutorService;
    private Set<Integer> mStartIds;
    private int mLastStartId;
    private final Object mMonitor = new Object();

    public ConcurrentIntentService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mExecutorService = Executors.newCachedThreadPool(Executors.defaultThreadFactory());
        mStartIds = new HashSet<>();
    }

    @Override
    public int onStartCommand(final Intent intent, int flags, final int startId) {
        if (intent != null && intent.hasExtra(EXTRA_JOB_ID)) {
            synchronized (mMonitor) {
                mStartIds.add(startId);
                mLastStartId = startId;
            }
            int jobId = intent.getIntExtra(EXTRA_JOB_ID, -1);
            final Job job = TimingTaskManager.get().getJobDataManager().getJob(jobId);
            //task保证每次都是new的
            final Task task = TimingTaskManager.get().getTaskFactoryHolder().createTask(job.getTag());
            task.setJob(job);
            mExecutorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        task.doActionInBackground(task.getParams());
                    } finally {
                        stopSelfIfNecessary(startId);
                    }
                }
            });
        }
        return START_NOT_STICKY;
    }


    private void stopSelfIfNecessary(int startId) {
        synchronized (mMonitor) {
            Set<Integer> startIds = mStartIds;
            if (startIds != null) {
                // service not destroyed
                startIds.remove(startId);
                if (startIds.isEmpty()) {
                    stopSelf(mLastStartId);//set集合中的所有startID处理完才会stopservice
                }
            }
        }
    }

    public static Intent createIntent(Context context, int jobId) {
        Intent intent = new Intent(context, ConcurrentIntentService.class);
        intent.putExtra(AlarmBroadcastReceiver.EXTRA_JOB_ID, jobId);
        return intent;
    }
}
