package com.endikaaguilera.mvpitunesapi.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.endikaaguilera.mvpitunesapi.GlideApp;
import com.endikaaguilera.mvpitunesapi.listeners.IAOnTrackItemClickListener;
import com.endikaaguilera.mvpitunesapi.models.IATrackData;
import com.thisobeystudio.mvpitunesapi.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

public class IAHorizontalAdapter extends
        RecyclerView.Adapter<IAHorizontalAdapter.FestivitiesViewHolder> {

    // results data
    private final ArrayList<IATrackData> trackData;

    // the track click callbacks handler
    private final IAOnTrackItemClickListener onTrackItemClickListener;

    private Drawable placeholderIcon;

    class FestivitiesViewHolder extends RecyclerView.ViewHolder {

        @SuppressWarnings("WeakerAccess")
        @BindView(R.id.cardContentContainer)
        protected ConstraintLayout cardContentContainer;

        @SuppressWarnings("WeakerAccess")
        @BindView(R.id.thumbnailImageView)
        protected ImageView thumbnailImageView;

        @SuppressWarnings("WeakerAccess")
        @BindView(R.id.artistNameTextView)
        protected TextView artistNameTextView;

        @SuppressWarnings("WeakerAccess")
        @BindView(R.id.trackNameTextView)
        protected TextView trackNameTextView;

        @SuppressWarnings("WeakerAccess")
        @BindView(R.id.shareButton)
        protected ImageView shareButton;

        @SuppressWarnings("WeakerAccess")
        @BindView(R.id.playButton)
        protected ImageView playButton;

        @SuppressWarnings("WeakerAccess")
        @BindView(R.id.viewButton)
        protected ImageView viewButton;

        FestivitiesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public IAHorizontalAdapter(ArrayList<IATrackData> trackData,
                               IAOnTrackItemClickListener onTrackItemClickListener) {
        this.trackData = trackData;
        this.onTrackItemClickListener = onTrackItemClickListener;
    }

    @Override
    public int getItemCount() {
        return trackData == null ? 0 : trackData.size();
    }

    @Override
    public long getItemId(int position) {

        return position;

    }

    @NonNull
    @Override
    public FestivitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ia_item_horizontal_container, parent, false);

        if (this.placeholderIcon == null)
            this.placeholderIcon =
                    ContextCompat.getDrawable(parent.getContext(), R.drawable.ic_ia_placeholder);

        final FestivitiesViewHolder viewHolder = new FestivitiesViewHolder(rowView);

        // set on track click listener

        viewHolder.shareButton.setOnClickListener(view -> {

            final int index = viewHolder.getAdapterPosition();

            if (index == NO_POSITION ||
                    this.trackData == null ||
                    this.trackData.size() < index) return;

            String url = this.trackData.get(index).getCollectionViewUrl();

            if (!TextUtils.isEmpty(url)) {

                if (this.onTrackItemClickListener == null) return;

                this.onTrackItemClickListener.onShareTrackClick(url);

            }

        });

        viewHolder.viewButton.setOnClickListener(view -> {

            final int index = viewHolder.getAdapterPosition();

            if (index == NO_POSITION ||
                    this.trackData == null ||
                    this.trackData.size() < index) return;

            String url = this.trackData.get(index).getCollectionViewUrl();

            if (!TextUtils.isEmpty(url)) {

                if (this.onTrackItemClickListener == null) return;

                this.onTrackItemClickListener.onViewTrackClick(url);

            }

        });

        viewHolder.playButton.setOnClickListener(view -> {

            final int index = viewHolder.getAdapterPosition();

            if (index == NO_POSITION ||
                    this.trackData == null ||
                    this.trackData.size() < index) return;

            final String trackName =
                    this.trackData.get(index).getTrackName();
            final String previewUrl =
                    this.trackData.get(index).getPreviewUrl();

            if (!TextUtils.isEmpty(previewUrl)) {

                if (this.onTrackItemClickListener == null) return;

                this.onTrackItemClickListener.onPlayTrackClick(viewHolder.playButton,
                        trackName,
                        previewUrl);

            }

        });


        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final FestivitiesViewHolder viewHolder, int i) {
        if (this.trackData == null
                || this.trackData.size() < i
                || this.trackData.get(i) == null) {
            return;
        }

        Context context = viewHolder.itemView.getContext();
        if (context == null) return;

        // set track thumbnail
        String artworkUrl = this.trackData.get(i).getArtworkUrl100();

        if (!TextUtils.isEmpty(artworkUrl)) {

            GlideApp.with(context)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(this.placeholderIcon)
                    .error(this.placeholderIcon)
                    .load(artworkUrl)
                    .into(viewHolder.thumbnailImageView);

        }

        // set artist name
        String artistName = this.trackData.get(i).getArtistName();
        viewHolder.artistNameTextView.setText(artistName);

        // set track name
        String trackName = this.trackData.get(i).getTrackName();
        viewHolder.trackNameTextView.setText(trackName);

    }

}
