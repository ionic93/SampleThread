package com.example.samplethread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//쓰레드는 핸들러 객체 안에 있는 Message객체를 이용해 메세지를 전달한다.
public class MainActivity extends AppCompatActivity {
    Button button;
    int value = 0;
    TextView textView;
    MainHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        button.setOnClickListener(v -> {
            BackgroundThread t = new BackgroundThread();
            t.start();
        });
        handler = new MainHandler();
    }

    class BackgroundThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(500);
//                    textView.setText(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                value++;
                Log.d("Thread", "value: " + value);

                Message message = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putInt("value",value);
                message.setData(bundle);

                handler.sendMessage(message); //핸들러로 메세지 객체 보내기
            }
        }
    }
//핸들러 안에서 전달받은 메세지 객체 처리하기
    private class MainHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            Bundle bundle = msg.getData();
            int value = bundle.getInt("value");
            textView.setText("value 값: "+value);
        }
    }
}