package com.fullstackoasis.webviewscreensaverontouch;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class VideoViewActivity extends AppCompatActivity implements View.OnTouchListener {
    private static String TAG = VideoViewActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        VideoView view = (VideoView)findViewById(R.id.videoView);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.slomoflame_512kb;
        view.setVideoURI(Uri.parse(path));
        view.start();
        view.setOnTouchListener(this);
    }

    /**
     * Go back to the MainActivity.
     * @param v unused
     * @param event unused
     * @return unused
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        return false;
    }
}
