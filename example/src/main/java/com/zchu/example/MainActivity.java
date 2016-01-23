package com.zchu.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zchu.log.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    HttpURLConnection urlConnection = (HttpURLConnection) new URL("http://v6.api.eoemarket.com/index.php?r=list/index&listype=1").openConnection();
                    urlConnection.setConnectTimeout(5000);
                    urlConnection.setRequestMethod("GET");
                    if( urlConnection.getResponseCode()==200){
                        InputStream inputStream = urlConnection.getInputStream();
                        int len=-1;
                        ByteArrayOutputStream baos=new ByteArrayOutputStream();
                        byte[] buf=new byte[1024];
                        while ((len=inputStream.read(buf))!=-1){
                            baos.write(buf,0,len);
                        }
                        Logger.dJson(new String(baos.toByteArray()));

                    }
                    urlConnection.connect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();


    }
}
