package com.endikaaguilera.mvpitunesapi.global.adapters;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.endikaaguilera.mvpitunesapi.global.callbacks.PlaceholderListener;
import com.endikaaguilera.mvpitunesapi.global.custom.PlaceholderViewHolder;
import com.thisobeystudio.mvpitunesapi.R;

public class PlaceholderRecyclerViewAdapter extends RecyclerView.Adapter<PlaceholderViewHolder> {

    private final PlaceholderListener callbacks;
    private final String headerText;
    @DrawableRes
    private final int drawable;

    public PlaceholderRecyclerViewAdapter(PlaceholderListener callbacks,
                                          String headerText,
                                          int drawable) {

        this.callbacks = callbacks;
        this.headerText = headerText;
        this.drawable = drawable;

    }

    @Override
    public int getItemCount() {

        return 1;

    }

    @NonNull
    @Override
    public PlaceholderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View rootView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_placeholder, parent, false);

        return new PlaceholderViewHolder(rootView);

    }

    @Override
    public void onBindViewHolder(@NonNull final PlaceholderViewHolder viewHolder, int i) {

        viewHolder.init(this.callbacks, this.headerText, this.drawable);

    }

}
