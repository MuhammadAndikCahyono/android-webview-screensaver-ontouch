package com.fullstackoasis.webviewscreensaverontouch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    private String TAG = MainActivity.class.getCanonicalName();
    private static String URL = "https://www.fullstackoasis.com/webview-screensaver-ontouch/";
    private Handler waitHandler;
    private StartScreenSaver startScreenSaver = new StartScreenSaver();
    private static int TIME_TO_SCREENSAVER_SECONDS = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context applicationContext = this.getApplicationContext();
        final WebView myWebView = new WebView(applicationContext);

        // JavaScript runs in this "browser" (WebView)
        myWebView.getSettings().setJavaScriptEnabled(true);

        // Allow DOM Storage. Probably do not need this, but some webpages might.
        myWebView.getSettings().setDomStorageEnabled(true);

        // If you do not clear the cache, the webpage will never refresh.
        // You won't see any new changes that you've made to the webpage.
        myWebView.clearCache(true);
        setContentView(myWebView);
        myWebView.setOnTouchListener(this);

        // Always reset the user's cache when this activity is created.
        clearCookies();

        // Show the web page in a WebView
        myWebView.loadUrl(URL);

        // A screensaver video will open after a brief timeout, provided the user does not
        // interact with the webview. User interaction is detected via onTouch.
        startTimerToOpenScreenSaverAfterDelay(TIME_TO_SCREENSAVER_SECONDS);
    }


    /**
     * When a user touches the view to interact with the website being shown in the WebView,
     * the timer for showing a screensaver is reset.
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        startTimerToOpenScreenSaverAfterDelay(TIME_TO_SCREENSAVER_SECONDS);
        return false;
    }

    private void startTimerToOpenScreenSaverAfterDelay(int delayInSeconds) {
        if (waitHandler != null) {
            // See https://stackoverflow.com/questions/22718951/stop-handler-postdelayed/22719065
            waitHandler.removeCallbacksAndMessages(null);
        } else {
            waitHandler = new Handler();
        }
        waitHandler.postDelayed(startScreenSaver, delayInSeconds*1000);
    }

    /**
     * This method opens the ViewViewActivity, which shows a screensaver.
     */
    protected void goToVideoViewActivity() {
        Intent i = new Intent(this, VideoViewActivity.class);
        startActivity(i);
    }

    /**
     * Remove any users cookies for privacy.
     */
    protected void clearCookies() {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookies(null);
    }

    /**
     * If the activity paused, that probably means the user is no longer interacting with the
     * WebView, so remove cookies.
     */
    @Override
    protected void onPause() {
        super.onPause();
        clearCookies();
    }

    /**
     * If the activity stopped, that probably means the user is no longer interacting with the
     * WebView, so remove cookies.
     */
    @Override
    protected void onStop() {
        super.onStop();
        clearCookies();
    }

    /**
     * A Runnable that can be run to open the VideoViewActivity that shows a screensaver.
     */
    class StartScreenSaver implements Runnable {
        @Override
        public void run() {
            goToVideoViewActivity();
        }
    }
}
