package com.sgr.sample.exoplayerfullscreen;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

/**
 * Created by shekh on 26/9/17.
 *
 * @author shagar shekh
 */


public class FullScreenActivity extends AppCompatActivity {
    public static final String INTENT_EXTRA_VIDEO_URL = "VIDEO_URL";

    private SimpleExoPlayerView mSimpleExoPlayerView;
    private ImageButton mIbFullScreen;

    private String mVideoUrl;
    private boolean shouldDestroyVideo = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memo_video_player);
        mVideoUrl = getIntent().getStringExtra(INTENT_EXTRA_VIDEO_URL);

    }

    private void initializePlayer() {
        mSimpleExoPlayerView = (SimpleExoPlayerView) findViewById(R.id.player_view);
        mIbFullScreen = (ImageButton) findViewById(R.id.exo_fullscreen_btn);

        ExoPlayerVideoHandler.getInstance().prepareExoPlayerForUri(getApplicationContext(), Uri.parse(mVideoUrl), mSimpleExoPlayerView, findViewById(R.id.pBarBuffer), findViewById(R.id.container_play_pause));
        ExoPlayerVideoHandler.getInstance().goToForeground();

        mIbFullScreen.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shouldDestroyVideo = false;
                        finish();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializePlayer();
    }

    @Override
    public void onBackPressed() {
        shouldDestroyVideo = false;
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ExoPlayerVideoHandler.getInstance().goToBackground();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (shouldDestroyVideo) {
            ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
        }
    }
}
