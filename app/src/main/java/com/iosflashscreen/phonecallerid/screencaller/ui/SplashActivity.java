package com.iosflashscreen.phonecallerid.screencaller.ui;

import static com.iosflashscreen.phonecallerid.screencaller.singletonClass.AppOpenAds.currentActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.iosflashscreen.phonecallerid.screencaller.R;
import com.iosflashscreen.phonecallerid.screencaller.singletonClass.MyApplication;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            int visibilityFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            window.getDecorView().setSystemUiVisibility(visibilityFlags);
        }
        window.setStatusBarColor(Color.TRANSPARENT);
        beginActivity();
    }
    private void beginActivity() {
        startActivity(new Intent(SplashActivity.this, OnboardingActivity.class));
    }
}