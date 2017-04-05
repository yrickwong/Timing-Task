package com.yrickwang.library.job;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by wangyi on 2017/4/5.
 */

public class Common {
    //alarm太水了把，只限制了setRepeating的API，并没有设置set这类的API，
    public static void alarmJob(Context context, long triggerAtMillis, long intervalMillis, PendingIntent pendingIntent) {
        if (context == null || pendingIntent == null) {
            throw new IllegalArgumentException("context or pendingIntent not be null!");
        }

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //setExact在api24开始就失效了，所以需要适配24
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis + intervalMillis, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //setRepeating在api19开始就失效了，所以需要适配19
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis + intervalMillis, pendingIntent);
        } else {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, intervalMillis, pendingIntent);
        }
    }
}
