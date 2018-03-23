package edu.pitt.sfc17.androidtrivia;


import android.app.Service;
import android.content.Intent;

import android.media.MediaPlayer;
import android.os.IBinder;


public class BackgroundMusicService extends Service {

    private MediaPlayer mediaPlayer;

    public IBinder onBind(Intent arg0) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.bgmusic);
        mediaPlayer.setLooping(true);
        mediaPlayer.setVolume(100,100);

    }


    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();
        return Service.START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}