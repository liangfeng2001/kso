package com.ekek.tftcooker.utils;

import android.content.Context;
import android.media.MediaPlayer;


import com.ekek.tftcooker.R;

public class SoundManager {
    public static final int CURRENT_TASK_PLAY_NONE = 0;
    public static final int CURRENT_TASK_PLAY_ALARM = 1;
    private static volatile SoundManager instance;
    private MediaPlayer mediaPlayer;
    private Context context;
    private int currentTask = CURRENT_TASK_PLAY_NONE;

    private SoundManager(Context context) {
        this.context = context;
    }

    public static SoundManager getInstance(Context context) {
        if (instance == null) {
            synchronized (SoundManager.class) {
                if (instance == null) {
                    instance = new SoundManager(context);
                }
            }
        }
        return instance;
    }

    public void playAlarm() {
        playAlarm(R.raw.sonar, true);
    }

    public void playAlarm(int audioResource, final boolean circulate) {
        playAlarm(audioResource, 1.0f, 1.0f, circulate);
    }

    public void playAlarm(
            final int audioResource,
            final float leftVolume,
            final float rightVolume,
            final boolean circulate) {

        switch (currentTask) {
            case CURRENT_TASK_PLAY_ALARM:
                if (mediaPlayer.isPlaying()) {
                    if (mediaPlayer.isLooping()) {
                        mediaPlayer.setLooping(false);
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        doPlayAlarm(audioResource, leftVolume, rightVolume, circulate);
                    } else {
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                doPlayAlarm(audioResource, leftVolume, rightVolume, circulate);
                            }
                        });
                    }
                } else {
                    doPlayAlarm(audioResource, leftVolume, rightVolume, circulate);
                }
                break;
            case CURRENT_TASK_PLAY_NONE:
                doPlayAlarm(audioResource, leftVolume, rightVolume, circulate);
                break;
        }
    }

    public void stopAlarm() {
        if(currentTask != CURRENT_TASK_PLAY_NONE) {
            if (mediaPlayer.isLooping() && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                currentTask = CURRENT_TASK_PLAY_NONE;
            }
        }
    }

    private void doPlayAlarm(
            final int audioResource,
            final float leftVolume,
            final float rightVolume,
            final boolean circulate) {
        currentTask = CURRENT_TASK_PLAY_ALARM;
        mediaPlayer = MediaPlayer.create(context, audioResource);
        mediaPlayer.setVolume(leftVolume,rightVolume);
        mediaPlayer.setLooping(circulate);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                currentTask = CURRENT_TASK_PLAY_NONE;
            }
        });
    }
}
