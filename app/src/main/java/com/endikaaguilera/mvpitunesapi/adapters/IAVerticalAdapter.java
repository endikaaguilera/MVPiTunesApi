package com.endikaaguilera.mvpitunesapi.adapters;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.endikaaguilera.mvpitunesapi.custom.IAHorizontalRecyclerView;
import com.endikaaguilera.mvpitunesapi.listeners.IAOnTrackItemClickListener;
import com.endikaaguilera.mvpitunesapi.models.IAData;
import com.endikaaguilera.mvpitunesapi.models.IATrackData;
import com.thisobeystudio.mvpitunesapi.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

public class IAVerticalAdapter extends
        RecyclerView.Adapter<IAVerticalAdapter.CategoriesViewHolder> {

    // results data
    private final String[] categories = new String[]{
            "Balkan Beat Box",
            "Crazy Town",
            "Cypress Hill",
            "Guns N' Roses",
            "Led Zeppelin",
            "Metallica",
            "Nirvana",
            "Pink Floyd",
            "Red Hot Chili Peppers",
            "The Beatles",
            "The Rolling Stones",
            "The Who"
    };

    private final IAData data;
    private final IAOnTrackItemClickListener onTrackItemClickListener;

    public IAVerticalAdapter(IAData data, IAOnTrackItemClickListener onTrackItemClickListener) {
        this.data = data;
        this.onTrackItemClickListener = onTrackItemClickListener;

    }

    class CategoriesViewHolder extends RecyclerView.ViewHolder {

        @SuppressWarnings("WeakerAccess")
        @BindView(R.id.categoryTexView)
        TextView categoryTexView;

        @SuppressWarnings("WeakerAccess")
        @BindView(R.id.counterTexView)
        TextView counterTexView;

        @SuppressWarnings("WeakerAccess")
        @BindView(R.id.categoriesRecyclerView)
        IAHorizontalRecyclerView categoriesRecyclerView;

        CategoriesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    @Override
    public int getItemCount() {

        return this.categories.length;

    }

    @Override
    public long getItemId(int position) {

        return position;

    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ia_item_vertical_container, parent, false);

        CategoriesViewHolder viewHolder = new CategoriesViewHolder(rowView);

        viewHolder.categoriesRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                final int index = viewHolder.getAdapterPosition();

                if (index != NO_POSITION) {

                    nestedLayoutStates[index] = viewHolder.categoriesRecyclerView.getLayoutState();
                    super.onScrollStateChanged(recyclerView, newState);

                }

            }

        });

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final CategoriesViewHolder viewHolder, int i) {

        String title = this.categories[i];

        // set category
        viewHolder.categoryTexView.setText(title);

        ArrayList<IATrackData> rowData = this.data == null ? null : this.data.getData(i);

        if (rowData == null) return;

        // set results count
        int count = rowData.size();
        viewHolder.counterTexView.setText(String.valueOf(count));

        viewHolder.categoriesRecyclerView.setData(rowData,
                this.nestedLayoutStates[i],
                this.onTrackItemClickListener);

    }

    private Parcelable[] nestedLayoutStates = new Parcelable[this.categories.length];

    public void setNestedLayoutStates(Parcelable[] nestedLayoutStates) {
        this.nestedLayoutStates = nestedLayoutStates;
    }

    public Parcelable[] getNestedLayoutStates() {
        return nestedLayoutStates;
    }

}