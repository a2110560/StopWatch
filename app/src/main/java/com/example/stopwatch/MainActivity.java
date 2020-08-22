package com.example.stopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button btnstart,btnreset;
    private TextView textView;
    private boolean isrunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnstart=findViewById(R.id.rightbtn);
        btnreset=findViewById(R.id.leftbtn);
        textView=findViewById(R.id.time);

        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickright();
            }
        });
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickleft();
            }
        });
    }

    private void clickleft() {
        if(isrunning){
            dolap();
        }else {
            doreset();
        }
    }

    private void dolap() {
    }

    private void doreset() {
        counter=0;
        uiHandler.sendEmptyMessage(0);
    }

    private void clickright() {
        isrunning=!isrunning;
        btnreset.setText(isrunning?"Lap":"Reset");
        btnstart.setText(isrunning?"Stop":"Start");
        if(isrunning){
            startclock();
        }else {
            stopclock();
        }
    }
    private Timer timer = new Timer();
    private int counter;
    private uihandler uiHandler = new uihandler();
    private class MyTask extends TimerTask {
        @Override
        public void run() {
            counter++;
            uiHandler.sendEmptyMessage(0);//使用handler不會發生背景操縱前景的狀況
//            textView.setText("run: "+counter);
//            Log.d(TAG, "run: "+counter);
        }
    }
    private MyTask myTask;


    private void startclock() {
        myTask = new MyTask();
        timer.schedule(myTask, 100, 100);
    }
    private void stopclock() {
        myTask.cancel();
    }

    @Override
    public void finish() {
        if(timer!=null){
            timer.cancel();
            timer.purge();
            timer=null;
        }
        super.finish();
    }
    private class uihandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            textView.setText("counter = " + counter);
        }
    }
}