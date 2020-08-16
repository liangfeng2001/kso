package com.ekek.tftcooker.common;

import android.content.Context;
import android.media.AudioManager;


/**
 * Created by Samhung on 2018/2/2.
 */

public class SoundUtil {

    public static int getSystemMaxVolume(Context context) {
        int maxVolume = 0;
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        }
        return maxVolume;
    }

    public static int getSystemCurrentVolume(Context context) {
        int volume = 0;
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        }
        return volume;
    }

    public static void setSystemVolume(Context context , int volume) {
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
        }

    }

    public static void setSystemMute(Context context) {
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
        }
    }

}
