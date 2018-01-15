package com.example.com.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements MainService.UpdateUI{

    private static TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String apkRoot="chmod 777 "+getPackageCodePath();
        SystemManager.RootCommand(apkRoot);
        text=(TextView)findViewById(R.id.textView1);
        MainService.setUpdateUI(this);
        Log.v("d","MainActivity.onCreate");
    }
    // Method to start the service
    public void startService(View view) {
        startService(new Intent(getBaseContext(), MainService.class));
    }

    // Method to stop the service
    public void stopService(View view) {
        stopService(new Intent(getBaseContext(), MainService.class));
    }
    private static final Handler mUIHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            text.setText((String)msg.obj);
            Log.v("d","MainActivity.handleMessage");
        }
    };
    @Override
    public void updateUI(Message message) {
        Log.v("d","MainActivity.updateUI");
        mUIHandler.sendMessage(message);

    }
}



