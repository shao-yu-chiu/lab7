package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int progressRabbit =0;
    private int progressTurtle =0;

    private Button btn_start;
    private SeekBar seekBar,seekBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start=findViewById(R.id.btn_start);
        seekBar=findViewById(R.id.seekBar);//rabbit
        seekBar2=findViewById(R.id.seekBar2);//turtle

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_start.setEnabled(false);
                progressRabbit=0;
                progressTurtle=0;
                seekBar.setProgress(0);
                seekBar2.setProgress(0);
                runRabbit();
                runTurtle();
            }
        });
    }
    private final Handler handler = new Handler(Looper.myLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            if(message.what ==1)
                seekBar.setProgress(progressRabbit);
            else if(message.what ==2)
                seekBar2.setProgress(progressTurtle);

            if(progressRabbit>=100 && progressTurtle<100){
                Toast.makeText(MainActivity.this,"兔子勝利",Toast.LENGTH_SHORT).show();
                btn_start.setEnabled(true);
            }
            else if(progressRabbit<100 && progressTurtle>=100){
                Toast.makeText(MainActivity.this,"烏龜勝利",Toast.LENGTH_SHORT).show();
                btn_start.setEnabled(true);
            }
            return false;
        }
    });

    private void runRabbit(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean[] sleepProbabilty={true,true,false};

                while (progressRabbit<=100 &&progressTurtle<100){
                    try {
                        Thread.sleep(100);
                        if(sleepProbabilty[(int)(Math.random()*3)])
                            Thread.sleep(300);

                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    progressRabbit+=3;
                    Message message = new Message();
                    message.what=1;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }
    private void runTurtle(){
        new Thread(() ->{
            while (progressRabbit<100 &&progressTurtle<=100){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                progressTurtle+=1;
                Message message = new Message();
                message.what=2;
                handler.sendMessage(message);
            }
        }).start();
    }
}