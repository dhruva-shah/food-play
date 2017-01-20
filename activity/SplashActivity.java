package com.restaurantsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import com.restaurantsapp.R;
import com.restaurantsapp.utils.PreferenceUtils;

public class SplashActivity extends AppCompatActivity {


    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                final PreferenceUtils preferenceUtils = new PreferenceUtils(SplashActivity.this);
                final String userId = preferenceUtils.getString(PreferenceUtils.USER_ID);
                Intent intent;
                if (TextUtils.isEmpty(userId)) {
                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                }
                startActivity(intent);
                finish();

            }
        };

        handler.postDelayed(runnable, 5000);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }
}
