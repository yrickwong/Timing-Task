package com.yrickwang.timingtask.job;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.yrickwang.library.task.BaseTask;
import com.yrickwang.library.utils.PersistableBundleCompat;
import com.yrickwang.timingtask.App;

/**
 * Created by wangyi on 2017/3/30.
 */

public class BackgroundTask extends BaseTask {

    public static final String TAG = "backgroundTask_tag";

    @Override
    public void doActionInBackground(Params params) {
//        params maybe null
        if (params != null) {
            PersistableBundleCompat extras = params.getExtras();
            final String value = extras.getString("key", null);
            if (value != null) {
                Log.d("wangyi", "value=" + value);
                new Handler(App.getApp().getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Toast.makeText(App.getApp(), value, Toast.LENGTH_SHORT).show();
                    }
                }.sendEmptyMessage(1);
            }
        }
    }
}
