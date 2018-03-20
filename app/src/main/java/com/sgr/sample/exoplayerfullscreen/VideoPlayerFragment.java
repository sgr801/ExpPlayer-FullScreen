package com.sgr.sample.exoplayerfullscreen;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by shekh on 26/9/17.
 *
 * @author shagar shekh
 */

public class VideoPlayerFragment extends Fragment {
    public static final String BUNDLE_KEY_VIDEO_URL = "VIDEO_URL";

    private String mVideoUrl;
    private SimpleExoPlayerView mSimpleExoPlayerView;
    private ImageButton mIbFullScreen;
    private View mRootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mRootView = inflater.inflate(R.layout.memo_video_player, container, false);
        if (getArguments() != null) {
            mVideoUrl = getArguments().getString(BUNDLE_KEY_VIDEO_URL);
        }
        mSimpleExoPlayerView = mRootView.findViewById(R.id.player_view);
        mIbFullScreen = mRootView.findViewById(R.id.exo_fullscreen_btn);
        return mRootView;
    }

    /**
     * Initializes the Player when isFragmentVisible is true
     */
    private void initializePlayer() {
        if (mVideoUrl != null && mSimpleExoPlayerView != null) {
            ExoPlayerVideoHandler.getInstance().prepareExoPlayerForUri(mRootView.getContext(), Uri.parse(mVideoUrl), mSimpleExoPlayerView, mRootView.findViewById(R.id.pBarBuffer), mRootView.findViewById(R.id.container_play_pause));
            ExoPlayerVideoHandler.getInstance().goToForeground();
            mIbFullScreen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), FullScreenActivity.class);
                    intent.putExtra(FullScreenActivity.INTENT_EXTRA_VIDEO_URL, mVideoUrl);
                    getActivity().startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        ExoPlayerVideoHandler.getInstance().goToBackground();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
