package com.myapplication;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
  //  private Thread thread;
    private static final int SEND_FLAG=1000;
    private Handler mHandler;
    private Boolean isRunning=true;
    private Button btnStart;
    private HandlerThread thread;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //开始初始化控件
        /* thread=new Thread(new Runnable() {
              @Override
              public void run() {
                  try {
                      Thread.sleep(3000);

                      handler.sendEmptyMessage(SEND_FLAG);

                  } catch (InterruptedException e) {
                      e.printStackTrace();
                  }
              }
          });
        thread.start();*/
        /**
         * 线程的启动与销毁
         * */
        //创建线程
       // thread=new HandlerThread("getTestInfo");
       /* thread.start(); //启动线程
        //创建handler
        mHandler=new Handler(thread.getLooper());//使用HandlerThread的looper对象创建Handler，如果使用默认的构造方法，很有可能阻塞UI线程
        mHandler.post(mBackgroundRunable);//将线程post到handler中*/
        btnStart= (Button)findViewById(R.id.tvStart);
        btnStart.setOnClickListener(this);



    }
    Handler uiHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SEND_FLAG:
                    Log.i("info","do one time");
                    Toast.makeText(MainActivity.this,"this is running on main Thread ",Toast.LENGTH_LONG).show();
                    break;
            }

        }
    };
   @Override
   public void onClick(View view) {
       switch (view.getId()){
           case R.id.tvStart:
               thread=new HandlerThread("getTestInfo");
               thread.start(); //启动线程
               //创建handler
               mHandler=new Handler(thread.getLooper());//使用HandlerThread的looper对象创建Handler，如果使用默认的构造方法，很有可能阻塞UI线程
               mHandler.post(mBackgroundRunable);//将线程post到handler中
               break;
       }

   }
    Runnable mBackgroundRunable=new Runnable() {
       @Override
       public void run() {
           if (isRunning){
               try {
                   Log.i("info","testing");
                   Thread.sleep(2000);
                   uiHandler.sendEmptyMessage(SEND_FLAG);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
       }
   };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mBackgroundRunable);
        Log.i("info","thread has destroied");
        Toast.makeText(this,"close_thread",Toast.LENGTH_LONG).show();
    }


}
