package com.endikaaguilera.mvpitunesapi.global.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.endikaaguilera.mvpitunesapi.global.adapters.PlaceholderRecyclerViewAdapter;
import com.endikaaguilera.mvpitunesapi.global.callbacks.PlaceholderListener;
import com.endikaaguilera.mvpitunesapi.global.types.RecyclerViewHolderType;
import com.thisobeystudio.mvpitunesapi.R;

public class PlaceholderRecyclerView extends RecyclerView {

    public PlaceholderRecyclerView(@NonNull Context context) {
        super(context);
    }

    public PlaceholderRecyclerView(@NonNull Context context,
                                   @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PlaceholderRecyclerView(@NonNull Context context,
                                   @Nullable AttributeSet attrs,
                                   int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setupRecyclerView(PlaceholderListener callbacks,
                                  @RecyclerViewHolderType.RVHolderType int type) {

        Context context = getContext();

        if (context == null) return;

        this.setVisibility(View.VISIBLE);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        this.setHasFixedSize(true);

        this.setNestedScrollingEnabled(true);

        PlaceholderRecyclerViewAdapter adapter = new PlaceholderRecyclerViewAdapter(null,
                context.getString(R.string.error),
                R.drawable.ic_sentiment_dissatisfied_24);

        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);

        switch (type) {

            case RecyclerViewHolderType.LOADING:
                adapter = new PlaceholderRecyclerViewAdapter(null,
                        context.getString(R.string.loading_data),
                        0);
                break;

            case RecyclerViewHolderType.ERROR:
                adapter = new PlaceholderRecyclerViewAdapter(callbacks,
                        context.getString(R.string.error),
                        R.drawable.ic_sentiment_dissatisfied_24);
                break;

            case RecyclerViewHolderType.NO_DATA:
                adapter = new PlaceholderRecyclerViewAdapter(callbacks,
                        context.getString(R.string.no_data),
                        R.drawable.ic_error_outline_24dp);
                break;

            case RecyclerViewHolderType.NO_INTERNET:
                adapter = new PlaceholderRecyclerViewAdapter(callbacks,
                        context.getString(R.string.no_internet),
                        R.drawable.ic_network_check_24dp);
                break;

        }

        this.setLayoutManager(layoutManager);

        // specify an adapter
        this.setAdapter(adapter);

    }

}
