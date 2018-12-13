package com.endikaaguilera.mvpitunesapi.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.endikaaguilera.mvpitunesapi.App;
import com.endikaaguilera.mvpitunesapi.adapters.IAVerticalAdapter;
import com.endikaaguilera.mvpitunesapi.custom.IAVerticalItemDeco;
import com.endikaaguilera.mvpitunesapi.global.NetUtils;
import com.endikaaguilera.mvpitunesapi.global.custom.PlaceholderRecyclerView;
import com.endikaaguilera.mvpitunesapi.global.custom.SuperTextView;
import com.endikaaguilera.mvpitunesapi.global.types.RecyclerViewHolderType;
import com.endikaaguilera.mvpitunesapi.listeners.IAOnTrackItemClickListener;
import com.endikaaguilera.mvpitunesapi.models.IAData;
import com.endikaaguilera.mvpitunesapi.player.IATrackPlayer;
import com.squareup.leakcanary.RefWatcher;
import com.thisobeystudio.mvpitunesapi.R;

import butterknife.BindView;
import butterknife.ButterKnife;

// TODO - Add Logs on errors
// TODO - Remove Test Logs
// TODO - Add this to manifest -> android:configChanges="orientation|screenSize|keyboard"
public class IAMainActivity extends AppCompatActivity
        implements IAMainView, IAOnTrackItemClickListener, IATrackPlayer.IAPlayerListener {

    private static final String ARGS_RESULTS = "args_results";
    private static final String ARGS_LAYOUT_STATE = "args_layout_state";
    private static final String ARGS_NESTED_LAYOUT_STATES = "args_nested_layout_states";

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.iaRecyclerView)
    PlaceholderRecyclerView recyclerView;

    @SuppressWarnings("WeakerAccess")
    @BindView(R.id.playingTextView)
    SuperTextView playingTextView;

    private ImageView lastPlayButtonClicked;

    private IAVerticalAdapter adapter;
    private LinearLayoutManager layoutManager;
    private Parcelable layoutState;
    private Parcelable[] nestedLayoutStates;

    private IAData data;

    private IAMainPresenter presenter;

    private IATrackPlayer trackPlayer;

    // region Lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ia_activity_main);

        ButterKnife.bind(this);

        setTitle(getString(R.string.itunes_api));

        if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.presenter = new IAMainPresenter(IAMainActivity.this, new IAMainInteractor());

        if (savedInstanceState != null) return;

        this.presenter.getData();

    }

    @Override
    protected void onPause() {

        if (this.trackPlayer != null) this.trackPlayer.onPause();

        super.onPause();

    }

    @Override
    protected void onDestroy() {

        if (this.presenter != null) this.presenter.onDestroy();

        if (this.trackPlayer != null) this.trackPlayer.onDestroy();
        this.trackPlayer = null;

        super.onDestroy();

        RefWatcher refWatcher = App.getRefWatcher(this);
        refWatcher.watch(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putParcelable(ARGS_RESULTS, this.data);

        Parcelable p = this.layoutManager == null ? null : this.layoutManager.onSaveInstanceState();
        outState.putParcelable(ARGS_LAYOUT_STATE, p);

        Parcelable[] ps = this.adapter == null ? null : this.adapter.getNestedLayoutStates();
        outState.putParcelableArray(ARGS_NESTED_LAYOUT_STATES, ps);

        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        if (savedInstanceState.containsKey(ARGS_RESULTS) &&
                savedInstanceState.containsKey(ARGS_LAYOUT_STATE) &&
                savedInstanceState.containsKey(ARGS_NESTED_LAYOUT_STATES)) {

            this.data = savedInstanceState.getParcelable(ARGS_RESULTS);

            this.layoutState = savedInstanceState.getParcelable(ARGS_LAYOUT_STATE);
            this.nestedLayoutStates =
                    savedInstanceState.getParcelableArray(ARGS_NESTED_LAYOUT_STATES);

            if (this.data != null) {

                this.presenter.showResultsRecyclerView(this.data);

            } else {

                this.presenter.getData();

            }

        } else {

            this.presenter.onError();

        }

        super.onRestoreInstanceState(savedInstanceState);

    }

    // endregion Lifecycle

    // region IAMainView

    @Override
    public void onGetData() {

        if (NetUtils.isNetworkAvailable(this)) {

            if (this.presenter != null) this.presenter.getData(this::onShowResults);

        } else {

            if (this.presenter != null) this.presenter.onNetworkError();

        }

    }

    @Override
    public void onNoNetworkOnItemClick() {

        Snackbar.make(this.recyclerView,
                getString(R.string.no_internet),
                Snackbar.LENGTH_SHORT)
                .show();

    }

    @Override
    public void onShowResults(IAData data) {

        this.data = data;

        if (data == null ||
                data.getBalkanBeatBox() == null ||
                data.getBalkanBeatBox().size() == 0) {

            this.presenter.onDataError();

            return;
        }

        this.adapter = new IAVerticalAdapter(this.data, this);

        if (this.nestedLayoutStates != null) {

            this.adapter.setNestedLayoutStates(this.nestedLayoutStates);

        }

        this.adapter.setHasStableIds(true);

        this.layoutManager = new LinearLayoutManager(IAMainActivity.this,
                LinearLayoutManager.VERTICAL,
                false);

        this.recyclerView.setNestedScrollingEnabled(false);

        // restore layout state
        if (this.layoutState != null) {

            this.layoutManager.onRestoreInstanceState(this.layoutState);
            this.layoutState = null;

        }

        float itemSpacing = getResources().getDimension(R.dimen.ia_recycler_view_item_spacing);
        IAVerticalItemDeco itemDeco = new IAVerticalItemDeco((int) itemSpacing);
        this.recyclerView.addItemDecoration(itemDeco);

        this.recyclerView.setLayoutManager(this.layoutManager);
        this.recyclerView.setAdapter(this.adapter);

        this.recyclerView.setVisibility(View.VISIBLE);
        this.recyclerView.setHasFixedSize(true);

        int cacheSize = this.adapter.getItemCount();

        this.recyclerView.setItemViewCacheSize(cacheSize); // default is 2;

    }

    @Override
    public void onShowPlaceholder(@RecyclerViewHolderType.RVHolderType int type) {

        this.recyclerView.setupRecyclerView(this::onRetryButtonClick, type);

    }

    @Override
    public void onShowBottomView() {

        this.playingTextView.setVisibility(View.VISIBLE);
        this.playingTextView.setText(getString(R.string.loading), TextView.BufferType.NORMAL);

    }

    @Override
    public void onHideBottomView() {

        this.playingTextView.setVisibility(View.GONE);

    }

    // endregion IAMainView

    // region IAOnTrackItemClickListener

    @Override
    public void onShareTrackClick(String trackUrl) {

        if (NetUtils.isNetworkAvailable(this)) {

            Intent share = new Intent();
            share.setAction(Intent.ACTION_SEND);
            share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            share.putExtra(Intent.EXTRA_TEXT, trackUrl);
            share.setType("text/*");
            String title = getString(R.string.share_intent_header);
            startActivity(Intent.createChooser(share, title));

        } else {

            this.presenter.showNoNetworkMessage();

        }

    }

    @Override
    public void onPlayTrackClick(ImageView source, String trackName, String previewUrl) {

        if (NetUtils.isNetworkAvailable(this)) {

            if (TextUtils.isEmpty(trackName)) trackName = "Undefined";

            if (this.presenter != null) this.presenter.showBottomView();

            if (this.trackPlayer == null) this.trackPlayer = new IATrackPlayer();

            if (this.trackPlayer.isPlaying()) this.trackPlayer.releaseMediaPlayer();

            this.trackPlayer.initMediaPlayer(trackName, previewUrl, this);

            this.trackPlayer.setSource(source);

        } else {

            if (this.presenter != null) this.presenter.showNoNetworkMessage();

        }

    }

    @Override
    public void onViewTrackClick(String trackUrl) {

        if (NetUtils.isNetworkAvailable(this)) {

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(trackUrl));
            startActivity(i);

        } else {

            this.presenter.showNoNetworkMessage();

        }

    }

    // endregion IAOnTrackItemClickListener

    // region IAPlayerListener

    @Override
    public void onSetTrackName(String trackName) {

        this.playingTextView.setText(trackName, TextView.BufferType.NORMAL);

    }

    @Override
    public void onSetResource(int resourceId) {

        if (this.lastPlayButtonClicked != null)
            this.lastPlayButtonClicked.setImageResource(resourceId);

    }

    @Override
    public void onSetImageViewSource(ImageView source) {

        if (this.lastPlayButtonClicked != null)
            this.lastPlayButtonClicked.setImageResource(R.drawable.ic_play_24dp);

        this.lastPlayButtonClicked = source;

        this.lastPlayButtonClicked.setImageResource(R.drawable.ic_play_filled_24dp);

    }

    @Override
    public void onRelease() {

        if (this.trackPlayer != null) this.trackPlayer.releaseMediaPlayer();

        if (this.lastPlayButtonClicked != null)
            this.lastPlayButtonClicked.setImageResource(R.drawable.ic_play_24dp);

        if (this.presenter != null) this.presenter.hideBottomView();

    }

    @Override
    public void onError() {

        Snackbar.make(this.recyclerView,
                R.string.loading_track_error,
                Snackbar.LENGTH_SHORT)
                .show();

    }

    // endregion IAPlayerListener

    // region PlaceholderListener

    private void onRetryButtonClick() {

        if (this.presenter != null) this.presenter.getData();

    }

    // endregion PlaceholderListener

}
