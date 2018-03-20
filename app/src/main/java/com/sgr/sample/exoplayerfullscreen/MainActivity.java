package com.sgr.sample.exoplayerfullscreen;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Created by shekh on 26/9/17.
 * @author shagar shekh
 */


public class MainActivity extends AppCompatActivity {
    private String mVideoUrl = "http://www.sample-videos.com/video/mp4/720/big_buck_bunny_720p_30mb.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fragment fragment = new VideoPlayerFragment();

        Bundle bundle = new Bundle();
        bundle.putString(VideoPlayerFragment.BUNDLE_KEY_VIDEO_URL, mVideoUrl);
        fragment.setArguments(bundle);

        getFragmentManager().beginTransaction().replace(android.R.id.content, fragment).commit();
    }
}
