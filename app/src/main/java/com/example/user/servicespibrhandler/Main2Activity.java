package com.example.user.servicespibrhandler;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

public class Main2Activity extends AppCompatActivity {
    //Handler Use Example Service. Обратная связь с помощью Handler

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private Button startBtn;
    private Intent intent;
    private Handler handler;

    private final int KEY_TASK1_SERVICE = 5000;
    private final int KEY_TASK2_SERVICE = 6000;
    private final int KEY_TASK3_SERVICE = 7000;
    public static final String KEY_TASK_REQUEST = "taskRequest";
    public static final String KEY_SEND_HANDLER = "hendler";
    public static final String KEY_SEND_TIME = "time";
    public static final int KEY_HANDLER_RESULT_START = 100;
    public static final int KEY_HANDLER_RESULT_FINISH = 200;

    Handler.Callback handlerCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == KEY_HANDLER_RESULT_START) {
                switch (msg.arg1) {
                    case KEY_TASK1_SERVICE:
                        textView1.setText("Task 1 start...");
                        break;
                    case KEY_TASK2_SERVICE:
                        textView2.setText("Task 2 start...");
                        break;
                    case KEY_TASK3_SERVICE:
                        textView3.setText("Task 3 start...");
                        break;
                }
            }
            else if(msg.what==KEY_HANDLER_RESULT_FINISH){
                switch (msg.arg1) {
                    case KEY_TASK1_SERVICE:
                        textView1.setText("Task 1 finished:");
                        break;
                    case KEY_TASK2_SERVICE:
                        textView2.setText("Task 2 finished:");
                        break;
                    case KEY_TASK3_SERVICE:
                        textView3.setText("Task 3 finished:");
                        break;
                }
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        startBtn = findViewById(R.id.start_service_handler_btn);
        textView1 = findViewById(R.id.show_service_handler_result_tv1);
        textView2 = findViewById(R.id.show_service_handler_result_tv2);
        textView3 = findViewById(R.id.show_service_handler_result_tv3);

        handler = new Handler(handlerCallback);
    }

    public void startButtonClickWork(View view) {
        startTask1Service();
        startTask2Service();
        startTask3Service();
    }

    public void startTask1Service() {
        intent = new Intent(this, MyService2.class);
        intent.putExtra(KEY_TASK_REQUEST, KEY_TASK1_SERVICE);
        intent.putExtra(KEY_SEND_TIME, 2000);
        intent.putExtra(KEY_SEND_HANDLER, (Serializable) handler);
        startService(intent);
    }

    public void startTask2Service() {
        intent = new Intent(this, MyService2.class);
        intent.putExtra(KEY_TASK_REQUEST, KEY_TASK2_SERVICE);
        intent.putExtra(KEY_SEND_TIME, 4000);
        intent.putExtra(KEY_SEND_HANDLER, (Serializable)handler);
        startService(intent);
    }

    public void startTask3Service() {
        intent = new Intent(this, MyService2.class);
        intent.putExtra(KEY_TASK_REQUEST, KEY_TASK3_SERVICE);
        intent.putExtra(KEY_SEND_TIME, 6000);
        intent.putExtra(KEY_SEND_HANDLER, (Serializable) handler);
        startService(intent);
    }
}
