package com.example.user.servicespibrhandler;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //PendingIntent Example Service. Обратная связь с помощью PendingIntent
    // nuyni aveli mec orinake Service2-um ka

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private Button startBtn;
    private PendingIntent pendingIntent;
    private Intent intent;

    private final int TASK1_PENDINGINTENT_REQUESTCODE = 1;
    private final int TASK2_PENDINGINTENT_REQUESTCODE = 2;
    private final int TASK3_PENDINGINTENT_REQUESTCODE = 3;
    public static final String KEY_SEND_PENDINGINTENT = "pendingIntent";
    public static final String KEY_SEND_TIME = "time";
    public static final int KEY_STATUS_RESULTCODE_START = 100;
    public static final int KEY_STATUS_RESULTCODE_FINISH = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startBtn = findViewById(R.id.start_service_pending_btn);
        textView1 = findViewById(R.id.show_service_pending_result_tv1);
        textView2 = findViewById(R.id.show_service_pending_result_tv2);
        textView3 = findViewById(R.id.show_service_pending_result_tv3);

        if (isMyServiceRunning(MyService.class)) {
            startBtn.setEnabled(false);
        }
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

    public void startButtonClick(View view) {
        startTask1Service();
        startTask2Service();
        startTask3Service();
    }

    public void startTask1Service() {
        pendingIntent = createPendingResult(TASK1_PENDINGINTENT_REQUESTCODE, new Intent(), 0);
        intent = new Intent(this, MyService.class);
        intent.putExtra(KEY_SEND_PENDINGINTENT, pendingIntent);
        intent.putExtra(KEY_SEND_TIME, 2000);
        startService(intent);
    }

    public void startTask2Service() {
        pendingIntent = createPendingResult(TASK2_PENDINGINTENT_REQUESTCODE, new Intent(), 0);
        intent = new Intent(this, MyService.class);
        intent.putExtra(KEY_SEND_PENDINGINTENT, pendingIntent);
        intent.putExtra(KEY_SEND_TIME, 4000);
        startService(intent);
    }

    public void startTask3Service() {
        pendingIntent = createPendingResult(TASK3_PENDINGINTENT_REQUESTCODE, new Intent(), 0);
        intent = new Intent(this, MyService.class);
        intent.putExtra(KEY_SEND_PENDINGINTENT, pendingIntent);
        intent.putExtra(KEY_SEND_TIME, 6000);
        startService(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == KEY_STATUS_RESULTCODE_START) {
            switch (requestCode) {
                case TASK1_PENDINGINTENT_REQUESTCODE:
                    textView1.setText("Task 1 start...");
                    startBtn.setEnabled(false);
                    break;
                case TASK2_PENDINGINTENT_REQUESTCODE:
                    textView2.setText("Task 2 start...");
                    break;
                case TASK3_PENDINGINTENT_REQUESTCODE:
                    textView3.setText("Task 3 start...");
                    break;
            }
        } else if (resultCode == KEY_STATUS_RESULTCODE_FINISH) {
            switch (requestCode) {
                case TASK1_PENDINGINTENT_REQUESTCODE:
                    textView1.setText("Task 1 finished:");
                    break;
                case TASK2_PENDINGINTENT_REQUESTCODE:
                    textView2.setText("Task 2 finished:");
                    break;
                case TASK3_PENDINGINTENT_REQUESTCODE:
                    textView3.setText("Task 3 finished:");
                    startBtn.setEnabled(true);
                    break;
            }
        }
    }
}
