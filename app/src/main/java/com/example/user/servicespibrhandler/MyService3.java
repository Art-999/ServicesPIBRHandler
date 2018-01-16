package com.example.user.servicespibrhandler;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

/**
 * Created by artur.musayelyan on 16/01/2018.
 */

public class MyService3 extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int time = intent.getIntExtra(Main3Activity.KEY_SEND_TIME, 0);
        int taskID = intent.getIntExtra(Main3Activity.KEY_TASK_REQUEST_ID, 0);

        new MyThread(startId, time, taskID).start();
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
        private int taskID;

        public MyThread(int startId, int time, int taskID) {
            this.startId = startId;
            this.time = time;
            this.taskID = taskID;
        }


        @Override
        public void run() {
            Intent intent = new Intent(Main3Activity.BROADCAST_ACTION);
            intent.putExtra(Main3Activity.KEY_TASK_REQUEST_ID, taskID);
            intent.putExtra(Main3Activity.KEY_RESULT_STATUS, Main3Activity.STATUS_START);
            sendBroadcast(intent);
            SystemClock.sleep(time);

            intent.putExtra(Main3Activity.KEY_RESULT_STATUS, Main3Activity.STATUS_WORKING);
            for (int i = 1; i <= 10; i++) {
                intent.putExtra(Main3Activity.KEY_RESULT_WORKINGPROGRESS, i);
                sendBroadcast(intent);
                SystemClock.sleep(1000);
            }


            intent.putExtra(Main3Activity.KEY_RESULT_STATUS, Main3Activity.STATUS_FINISH);
            sendBroadcast(intent);
            SystemClock.sleep(time + 1000);

            intent.putExtra(Main3Activity.KEY_RESULT_STATUS, Main3Activity.STATUS_FINISHED);
            sendBroadcast(intent);
            stopSelf(startId);
        }
    }
}
