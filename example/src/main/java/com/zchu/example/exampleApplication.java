package com.zchu.example;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.zchu.log.LogLevel;
import com.zchu.log.Logger;
import com.zchu.log.Settings;

/**
 * Created by Chu on 2016/1/23.
 */
public class exampleApplication extends Application {
    private static Settings logSet = null;

    @Override
    public void onCreate() {
        super.onCreate();
        logSet = Logger.init("zchu").fileIsShowLog(true).fileIsShowTime(true).fileWithContext(this).setLogLevel(LogLevel.FULL);
        Logger.i(" Logger 初始化完毕");
        startThreadExceptionHandler();
    }

    public Settings logSet() {
        return logSet;
    }

    /**
     * 捕获未捕获的异常
     */
    private void startThreadExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                //  (信息,文件的前缀名) ,完整名称为:prefixName_time_versionName.txt
                Logger.file(ex, "crash");
                //设置延时重启
                restartApplication();
                //自杀
                android.os.Process.killProcess(android.os.Process.myPid());
           //     System.exit(0);
            }
        });
    }
  private void  restartApplication(){

      SharedPreferences preferences=getSharedPreferences("zchu", Context.MODE_PRIVATE);
      long curTime = System.currentTimeMillis();
      //获取最后重启时间
      long saveTime = preferences.getLong("LastRestartTime", 0);
      if (curTime - saveTime > 30 * 1000) {
          PendingIntent restartIntent;
          Intent intent = new Intent();
          intent.setClassName("com.zchu.example",
                  "com.zchu.example.MainActivity");
          restartIntent = PendingIntent.getActivity(
                  exampleApplication.this, 0, intent,
                  PendingIntent.FLAG_CANCEL_CURRENT);
          AlarmManager mgr = (AlarmManager)
                  getSystemService(Context.ALARM_SERVICE);
          mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 300,
                  restartIntent); // 1秒钟后重启应用
          preferences.edit().putLong("LastRestartTime",curTime).commit();
      }
  }

}
