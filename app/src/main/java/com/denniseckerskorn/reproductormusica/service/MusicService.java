package com.denniseckerskorn.reproductormusica.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class MusicService extends Service {
    private final IBinder binder = new MusicBinder();
    private MediaPlayer player;
    private float playbackSpeed = 1.0f;

    public class MusicBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    public void setOnCompletionListener(MediaPlayer.OnCompletionListener listener) {
        player.setOnCompletionListener(listener);
    }

    public void setPlaybackSpeed(float speed) {
        this.playbackSpeed = speed;
        player.setPlaybackParams(player.getPlaybackParams().setSpeed(speed));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public boolean isPlaying() {
        return player.isPlaying();
    }

    public void play(int resId) {
        if (player.isPlaying()) {
            player.stop();
        }
        player.reset();
        player = MediaPlayer.create(this, resId);
        player.setPlaybackParams(player.getPlaybackParams().setSpeed(playbackSpeed));
        player.start();
    }

    public void pause() {
        if (player.isPlaying()) {
            player.pause();
        }
    }

    public void stop() {
        if (player.isPlaying()) {
            player.stop();
        }
    }

    public void resume() {
        if (!player.isPlaying()) {
            player.start();
        }
    }

    public int getDuration() {
        return player.getDuration();
    }

    public int getCurrentPosition() {
        return player.getCurrentPosition();
    }

    public void seekTo(int position) {
        player.seekTo(position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}