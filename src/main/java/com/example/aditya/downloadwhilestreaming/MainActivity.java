package com.example.aditya.downloadwhilestreaming;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback,MediaPlayer.OnPreparedListener {


    VideoView video;
    String video_url = "https://socialcops.com/images/old/spec/home/header-img-background_video-1920-480.mp4";
    ProgressDialog pd;
    String outFilePath = "";//
    File outFile;
    static String  name;
    Button button;
    static Context context;
    SurfaceView first;
    SurfaceHolder surfaceHolder;


    private VideoDownloadAndPlayService videoDownloadAndPlayService;
    MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;


        name = getFilename(video_url);

        outFilePath = getExternalFilesDir("/") + "/" + name;


        button  = (Button)findViewById(R.id.button);
        first = (SurfaceView)findViewById(R.id.surface);
        surfaceHolder = first.getHolder();
        surfaceHolder.addCallback(MainActivity.this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File f = new File(outFilePath);

                Log.d("Main","F: "+ f.exists());
                if(f.exists())
                {
                    playpath();

                }
                else {
                    startServer();
                }
            }
        });

    }


    private void startServer()
    {


        videoDownloadAndPlayService = VideoDownloadAndPlayService.startServer(this, video_url,outFilePath, "127.0.0.1", new VideoDownloadAndPlayService.VideoStreamInterface()
        {
            @Override
            public void onServerStart(String videoStreamUrl)
            {
                // use videoStreamUrl to play video through media player



                Log.d("Activity","URL: "+ videoStreamUrl);


                video = (VideoView)findViewById(R.id.video);

                Uri uri = Uri.parse(videoStreamUrl);
                video.setVideoURI(uri);
                video.start();



            }


        });
    }



    @Override
    public void onStop()
    {
        super.onStop();
        if(videoDownloadAndPlayService != null)
            videoDownloadAndPlayService.stop();
    }


    public String getFilename(String video_url)
    {
        int l = video_url.length();
        String s = "";

        for(int i=l-1;i>0;i--)
        {
            if(video_url.charAt(i)=='/')
                break;
            s = s + video_url.charAt(i);
        }

        StringBuilder input1 = new StringBuilder();
        input1.append(s);
        input1 = input1.reverse();

        s = input1.toString();



        return s;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMp();

    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMp();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

        mp.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        mp = new MediaPlayer();
        mp.setDisplay(surfaceHolder);



    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private void releaseMp(){
        if(mp!=null)
        {
            mp.release();
            mp = null;
        }
    }

    public void playpath()
    {
        try{
            mp.setDataSource(outFilePath);
            mp.prepare();
            mp.setOnPreparedListener(this);
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
