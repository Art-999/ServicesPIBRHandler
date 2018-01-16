package com.example.user.servicespibrhandler;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {
    // Service. Обратная связь с помощью BroadcastReceiver

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private Button startBtn;
    private BroadcastReceiver broadcastReceiver;
    private Intent intent;
    private ProgressBar progressBar1, progressBar2, progressBar3;

    public final static String BROADCAST_ACTION = "servicebackbroadcast";
    public final static String KEY_SEND_TIME = "timeKey";
    public final static String KEY_TASK_REQUEST_ID = "taskRequest";
    public final static String KEY_RESULT_STATUS = "resultStatus";
    public final static String KEY_RESULT_WORKINGPROGRESS = "resultProgress";
    public final static int STATUS_START = 4000;
    public final static int STATUS_WORKING = 5000;
    public final static int STATUS_FINISH = 6000;
    public final static int STATUS_FINISHED = 7000;

    private final int TASK1_ID = 100;
    private final int TASK2_ID = 200;
    private final int TASK3_ID = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        textView1 = findViewById(R.id.show_service_broadcast_result_tv1);
        textView2 = findViewById(R.id.show_service_broadcast_result_tv2);
        textView3 = findViewById(R.id.show_service_broadcast_result_tv3);
        startBtn = findViewById(R.id.start_service_broadcast_btn);
        progressBar1 = findViewById(R.id.prog_bar1);
        progressBar2 = findViewById(R.id.prog_bar2);
        progressBar3 = findViewById(R.id.prog_bar3);

        if(isMyServiceRunning(MyService3.class)){
            startBtn.setEnabled(false);
        }


        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int taskResultStatus = intent.getIntExtra(KEY_RESULT_STATUS, 0);
                int taskID = intent.getIntExtra(KEY_TASK_REQUEST_ID, 0);
                if (taskResultStatus == STATUS_START) {
                    switch (taskID) {
                        case TASK1_ID:
                            textView1.setText("Task 1 start...");
                            startBtn.setEnabled(false);
                            break;
                        case TASK2_ID:
                            textView2.setText("Task 2 start...");
                            break;
                        case TASK3_ID:
                            textView3.setText("Task 3 start...");
                            break;
                    }
                } else if (taskResultStatus == STATUS_WORKING) {
                    int workingProgress = intent.getIntExtra(KEY_RESULT_WORKINGPROGRESS, 0);
                    switch (taskID) {
                        case TASK1_ID:
                            progressBar1.setVisibility(View.VISIBLE);
                            progressBar1.setProgress(workingProgress);
                            textView1.setText("  " + workingProgress*10+"%");
                            break;
                        case TASK2_ID:
                            progressBar2.setVisibility(View.VISIBLE);
                            progressBar2.setProgress(workingProgress);
                            textView2.setText("  " + workingProgress*10+"%");
                            break;
                        case TASK3_ID:
                            progressBar3.setVisibility(View.VISIBLE);
                            progressBar3.setProgress(workingProgress);
                            textView3.setText("  " + workingProgress*10+"%");
                            break;
                    }
                } else if (taskResultStatus == STATUS_FINISH) {
                    switch (taskID) {
                        case TASK1_ID:
                            progressBar1.setVisibility(View.GONE);
                            textView1.setText("Task 1 finish:");
                            break;
                        case TASK2_ID:
                            progressBar2.setVisibility(View.GONE);
                            textView2.setText("Task 2 finish:");
                            break;
                        case TASK3_ID:
                            progressBar3.setVisibility(View.GONE);
                            textView3.setText("Task 3 finish:");
                            break;
                    }
                } else if (taskResultStatus == STATUS_FINISHED) {
                    switch (taskID) {
                        case TASK1_ID:
                            textView1.setText("");
                            break;
                        case TASK2_ID:
                            textView2.setText("");
                            break;
                        case TASK3_ID:
                            textView3.setText("");
                            startBtn.setEnabled(true);
                            break;
                    }
                }

            }
        };
        IntentFilter intentFilter = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    //uxaki stugum e: petqakan method e irakanum
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (manager != null) {
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                if (serviceClass.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void startBtnClick(View view) {
        startTask1Service();
        startTask2Service();
        startTask3Service();
    }

    public void startTask1Service() {
        intent = new Intent(this, MyService3.class);
        intent.putExtra(KEY_SEND_TIME, 2000);
        intent.putExtra(KEY_TASK_REQUEST_ID, TASK1_ID);
        startService(intent);
    }

    public void startTask2Service() {
        intent = new Intent(this, MyService3.class);
        intent.putExtra(KEY_SEND_TIME, 4000);
        intent.putExtra(KEY_TASK_REQUEST_ID, TASK2_ID);
        startService(intent);
    }

    public void startTask3Service() {
        intent = new Intent(this, MyService3.class);
        intent.putExtra(KEY_SEND_TIME, 6000);
        intent.putExtra(KEY_TASK_REQUEST_ID, TASK3_ID);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
