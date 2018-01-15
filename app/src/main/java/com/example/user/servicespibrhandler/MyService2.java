package com.example.user.servicespibrhandler;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

/**
 * Created by USER on 16.01.2018.
 */

public class MyService2 extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int time = intent.getIntExtra(Main2Activity.KEY_SEND_TIME, 0);
        Handler handler = intent.getParcelableExtra(Main2Activity.KEY_SEND_HANDLER);
        int taskId = intent.getIntExtra(Main2Activity.KEY_TASK_REQUEST, 0);

        new MyThreadUseHandler(startId, time, handler, taskId).start();
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

    class MyThreadUseHandler extends Thread {
        private int startId;
        private int time;
        private Handler handler;
        private int taskId;

        public MyThreadUseHandler(int startId, int time, Handler handler, int taskId) {
            this.startId = startId;
            this.time = time;
            this.handler = handler;
            this.taskId = taskId;
        }

        @Override
        public void run() {
            //handler.sendEmptyMessage(Main2Activity.KEY_HANDLER_RESULT_START);
            handler.sendMessage(handler.obtainMessage(Main2Activity.KEY_HANDLER_RESULT_START, taskId, 0));
            SystemClock.sleep(time);
            handler.sendMessage(handler.obtainMessage(Main2Activity.KEY_HANDLER_RESULT_FINISH, taskId, 0));
            stopSelf(startId);
        }
    }
}
