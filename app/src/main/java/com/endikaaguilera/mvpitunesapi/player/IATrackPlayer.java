package com.endikaaguilera.mvpitunesapi.player;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.thisobeystudio.mvpitunesapi.R;

import java.io.IOException;
import java.util.Random;

public class IATrackPlayer {

    public interface IAPlayerListener {

        void onSetTrackName(String trackName);

        void onSetResource(int resourceID);

        void onSetImageViewSource(ImageView source);

        void onRelease();

        void onError();

    }

    private IAPlayerListener listener;
    private Handler handler = null;
    private Runnable runnable = null;
    private MediaPlayer mediaPlayer;
    private int playingImageIndex = 0;

    private int getDrawableResource() {

        this.playingImageIndex++;

        if (this.playingImageIndex > 3) this.playingImageIndex = 1;

        switch (this.playingImageIndex) {

            case 1:
                return R.drawable.ic_playing_03_24dp;

            case 2:
                return R.drawable.ic_playing_02_24dp;

            case 3:
                return R.drawable.ic_playing_01_24dp;

            default:
                return R.drawable.ic_play_24dp;

        }

    }

    private void playTrack() {

        if (this.listener == null || this.mediaPlayer == null) return;

        this.mediaPlayer.start();

        this.playingImageIndex = 0;

        if (this.handler == null) this.handler = new Handler();

        if (this.runnable == null) {

            this.runnable = new Runnable() {

                @Override
                public void run() {

                    if (listener == null) return;

                    listener.onSetResource(getDrawableResource());

                    Random r = new Random();
                    int delay = r.nextInt(500 - 200) + 200;
                    handler.postDelayed(this, delay); // repeat every N(delayed) milliseconds

                }

            };

        }

        this.handler.post(this.runnable);

    }

    public void releaseMediaPlayer() {

        if (this.handler != null && this.runnable != null) {

            this.handler.removeCallbacks(this.runnable);

        }

        if (this.mediaPlayer != null) this.mediaPlayer.release();

        this.listener = null;
        this.handler = null;
        this.runnable = null;
        this.mediaPlayer = null;

    }

    public void initMediaPlayer(String trackName, String previewUrl, IAPlayerListener listener) {

        this.listener = listener;

        this.mediaPlayer = new MediaPlayer();

        this.mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build());

        this.mediaPlayer.setOnPreparedListener(mp -> {

            if (this.listener != null) this.listener.onSetTrackName(trackName);
            Log.e("IAMainInteractor", "mediaPlayer playTrack");
            playTrack();

        });

        this.mediaPlayer.setOnCompletionListener(mp -> {

            if (this.listener != null) this.listener.onRelease();
            else releaseMediaPlayer();

        });

        this.mediaPlayer.setOnErrorListener((mp, what, extra) -> {

            if (this.listener != null) {
                this.listener.onError();
                this.listener.onRelease();
            } else releaseMediaPlayer();

            Log.e("IAMainInteractor", "mediaPlayer error: " + what);

            return false;

        });

        try {

            this.mediaPlayer.setDataSource(previewUrl);
            new Thread(() -> this.mediaPlayer.prepareAsync()).start();

        } catch (IOException e) {

            if (this.listener != null) {
                this.listener.onError();
                this.listener.onRelease();
            } else releaseMediaPlayer();

            Log.e("IAMainInteractor", "mediaPlayer setDataSource e: " + e);

        }

    }

    public void setSource(ImageView source) {

        if (this.listener != null) this.listener.onSetImageViewSource(source);

    }

    public void onPause() {

        if (this.listener != null) this.listener.onRelease();

    }

    public void onDestroy() {

        if (this.listener != null) this.listener.onRelease();

    }

    public boolean isPlaying() {

        return this.listener != null;

    }

}
