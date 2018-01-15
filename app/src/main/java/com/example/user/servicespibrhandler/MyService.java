package com.example.user.servicespibrhandler;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

/**
 * Created by USER on 16.01.2018.
 */

public class MyService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int time = intent.getIntExtra(MainActivity.KEY_SEND_TIME, 0);
        PendingIntent pendingIntent = intent.getParcelableExtra(MainActivity.KEY_SEND_PENDINGINTENT);

        new MyThread(startId, time, pendingIntent).start();
        return START_REDELIVER_INTENT;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class MyThread extends Thread {
        private int startId;
        private int time;
        private PendingIntent pendingIntent;

        public MyThread(int startId, int time, PendingIntent pendingIntent) {
            this.startId = startId;
            this.time = time;
            this.pendingIntent = pendingIntent;
        }

        @Override
        public void run() {
            try {
                pendingIntent.send(MainActivity.KEY_STATUS_RESULTCODE_START);
                SystemClock.sleep(time);
                pendingIntent.send(MainActivity.KEY_STATUS_RESULTCODE_FINISH);
                stopSelf(startId);
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
        }
    }
}
