package com.sharma.shivamflix.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.sharma.shivamflix.R;
import com.sharma.shivamflix.util.sharedpref.PrefKeys;
import com.sharma.shivamflix.util.sharedpref.PrefUtils;

public class SplashActivity extends BaseActivity {
    private static final long SPLASH_TIME_MILLIS = 2000;
    private static final int REQUEST_CODE_MULTIPLE = 3000;

    Handler splashHandler = new Handler();
    Runnable splashRunnable = () -> {
        if (PrefUtils.getInstance(this).getBoolanValue(PrefKeys.IS_LOGGED_IN,false)) {
            Intent splashIntent = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(splashIntent);
        }else {
            Intent splashIntent = new Intent(SplashActivity.this,LoginActivity.class);
            startActivity(splashIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStop() {
        super.onStop();
        splashHandler.removeCallbacks(splashRunnable);
    }

    @Override
    protected void onStart() {
        super.onStart();
        splashHandler.postDelayed(splashRunnable,SPLASH_TIME_MILLIS);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }
}