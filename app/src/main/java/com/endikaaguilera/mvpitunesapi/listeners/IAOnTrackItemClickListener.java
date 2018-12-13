package com.endikaaguilera.mvpitunesapi.listeners;

import android.widget.ImageView;

/**
 * A simple interface to handle onClick callbacks
 */
public interface IAOnTrackItemClickListener {

    void onShareTrackClick(String trackUrl);

    void onPlayTrackClick(ImageView source, String trackName, String previewUrl);

    void onViewTrackClick(String trackUrl);

}