package com.yrickwang.timingtask;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yrickwang.library.Job;
import com.yrickwang.library.TimingTaskManager;
import com.yrickwang.library.utils.PersistableBundleCompat;
import com.yrickwang.timingtask.job.BackgroundTask;

public class MainActivity extends AppCompatActivity {


    private int mLastId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //创建了一个background的task后台定时任务请求
        Job.Builder builder = new Job.Builder(BackgroundTask.TAG);
        builder.setPeriodic(1000 * 5);//设置间隔
        PersistableBundleCompat persistableBundleCompat = new PersistableBundleCompat();
        persistableBundleCompat.putString("key", "hello world");
        builder.setExtras(persistableBundleCompat);
        Job job = builder.build();
        mLastId = job.schedule();

    }

    public void stopTask(View view) {
        TimingTaskManager.get().cancel(mLastId);
    }
}
