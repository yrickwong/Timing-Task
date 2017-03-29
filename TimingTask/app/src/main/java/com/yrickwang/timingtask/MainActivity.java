package com.yrickwang.timingtask;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import com.yrickwang.library.Job;
import com.yrickwang.library.TimingTaskManager;
import com.yrickwang.library.task.DownloadTask;
import com.yrickwang.library.utils.PersistableBundleCompat;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Job.Builder builder = new Job.Builder();
        builder.setPeriodic(1000 * 10);//设置间隔
        PersistableBundleCompat persistableBundleCompat = new PersistableBundleCompat();
        builder.setExtras(persistableBundleCompat);
        Job job = builder.build();
        job.setTask(new DownloadTask());
        TimingTaskManager.get().schedule(job);
    }
}
