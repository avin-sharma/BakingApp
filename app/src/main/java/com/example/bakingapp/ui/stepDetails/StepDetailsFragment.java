package com.example.bakingapp.ui.stepDetails;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.bumptech.glide.Glide;
import com.example.bakingapp.R;
import com.example.bakingapp.models.Step;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepDetailsFragment extends Fragment implements ExoPlayer.EventListener{

    private static final String PLAYBACK_POSITION_KEY = "playbackPosition";
    private Step  mStep;
    public static final String TAG = StepDetailsFragment.class.getSimpleName();
    public static final String STEP_KEY = "step-key";
    public static final String IS_DUAL_PANE = "dual-pane";
    private TextView mTvDescription;
    private PlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;
    private ImageView mThumbnail;
    private boolean mTwoPane;

    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    private Context mContext;

    private FragmentLoadStatus mLoadStatus;

    public static StepDetailsFragment newInstance(Step step, boolean dualPane){
        Bundle bundle = new Bundle();
        bundle.putSerializable(STEP_KEY, step);
        bundle.putBoolean(IS_DUAL_PANE, dualPane);
        StepDetailsFragment fragment = new StepDetailsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public interface FragmentLoadStatus {
        void isNowIdle();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        mLoadStatus = (FragmentLoadStatus) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStep = (Step) getArguments().getSerializable(STEP_KEY);
        mTwoPane = getArguments().getBoolean(IS_DUAL_PANE);

        Log.d(TAG, mStep.getVideoURL());

        mTvDescription = view.findViewById(R.id.tv_step_description);
        mTvDescription.setText(mStep.getDescription());

        mPlayerView = view.findViewById(R.id.video_view);
        mThumbnail = view.findViewById(R.id.iv_step_thumbnail);

        Drawable defaultTextDrawable = TextDrawable.builder()
                .beginConfig()
                .height(50)
                .endConfig()
                .buildRect(mStep.getShortDescription(), R.color.colorAccent);
        if (mStep.getVideoURL().isEmpty()){
            mPlayerView.setVisibility(View.GONE);
            Glide.with(view)
                    .load(mStep.getThumbnailURL())
                    .placeholder(defaultTextDrawable)
                    .error(defaultTextDrawable)
                    .into(mThumbnail);
        }
        else {
            mPlayerView.setVisibility(View.VISIBLE);
            mThumbnail.setVisibility(View.GONE);
        }

        mLoadStatus.isNowIdle();
        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION_KEY);
        }

        initializeMediaSession();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT < 24 || mExoPlayer == null)) {
            initializePlayer();
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        int orientation = getResources().getConfiguration().orientation;
        // If we are on mobile and landscape only then go fullscreen
        if (!mTwoPane && orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }

    private void initializePlayer() {
        if (mPlayerView.getVisibility() == View.VISIBLE) {
            mExoPlayer = new SimpleExoPlayer.Builder(getContext()).build();
            mPlayerView.setPlayer(mExoPlayer);
            MediaSource mediaSource = buildMediaSource(Uri.parse(mStep.getVideoURL()));
            mExoPlayer.setPlayWhenReady(playWhenReady);
            mExoPlayer.seekTo(currentWindow, playbackPosition);
            mExoPlayer.prepare(mediaSource, false, false);
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        DataSource.Factory dataSourceFactory =
                new DefaultDataSourceFactory(getContext(), "BakingApp");
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            playWhenReady = mExoPlayer.getPlayWhenReady();
            playbackPosition = mExoPlayer.getCurrentPosition();
            currentWindow = mExoPlayer.getCurrentWindowIndex();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(PLAYBACK_POSITION_KEY, playbackPosition);
    }

    /**
     * Initializes the Media Session to be enabled with media buttons, transport controls, callbacks
     * and media controller.
     */
    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(mContext, TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }
    /**
     * Media Session Callbacks, where all external clients control the player.
     */
    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }
}