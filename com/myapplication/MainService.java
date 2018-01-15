package com.example.com.myapplication;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;
import org.opencv.android.BaseLoaderCallback;

public class MainService extends IntentService {

    public static UpdateUI updateUI;

    public static void setUpdateUI(UpdateUI updateUIInterface){
        updateUI=updateUIInterface;
    }
    public MainService() {
        super("MainService");
    }
    public void createNotification(){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        //设置状态栏的通知图标
        builder.setSmallIcon(R.drawable.content);
        //设置通知栏横条的图标
        // builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.screenflash_logo));
        //禁止用户点击删除按钮删除
        builder.setAutoCancel(false);
        //禁止滑动删除
        builder.setOngoing(true);
        //右上角的时间显示
        builder.setShowWhen(true);
        //设置通知栏的标题内容
        builder.setContentTitle("Service");
        //创建通知
        Notification notification = builder.build();
        //设置为前台服务
        startForeground(1,notification);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "服务已经启动", Toast.LENGTH_LONG).show();
        super.onStartCommand(intent, flags,startId);
        createNotification();
        Log.v("d","MainServices.onStartCommand");
        return START_STICKY;
    }
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.v("d","MainServices.onHandleIntent");
       // while(true) {
            try {
                Thread.sleep(10000);
                SystemManager.RootCommand("system/bin/screencap -p /storage/emulated/0/t1.png");
                int spacetime=800;
                SystemManager.RootCommand("input swipe 600 250 600 400 "+spacetime);
                //  Message msg1 = new Message();
              //  msg1.obj =;
                //通知主线程去更新UI
                if(updateUI!=null){
                  //  updateUI.updateUI(msg1);
                    Log.v("d","MainServices.onHandleIntent.updateUI");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

      //  }
    }
    public interface UpdateUI{
        void updateUI(Message message);
    }
    /** 当服务被创建时调用. */
    @Override
    public void onCreate() {
        super.onCreate();
    }
    /** 服务不再有用且将要被销毁时调用 */
    @Override
    public void onDestroy() {
        stopForeground(true);
        super.onDestroy();
        Toast.makeText(this, "服务已经停止", Toast.LENGTH_LONG).show();
    }
    //OpenCV库加载并初始化成功后的回调函数
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {

        @Override
        public void onManagerConnected(int status) {
            // TODO Auto-generated method stub
            switch (status){
                case BaseLoaderCallback.SUCCESS:
                    Log.v("d", "opencv 成功加载");
                    break;
                default:
                    super.onManagerConnected(status);
                    Log.v("d", "opencv 加载失败");
                    break;
            }

        }
    };
}
