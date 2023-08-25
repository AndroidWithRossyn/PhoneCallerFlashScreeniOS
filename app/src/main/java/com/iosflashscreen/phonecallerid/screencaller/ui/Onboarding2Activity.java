package com.iosflashscreen.phonecallerid.screencaller.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.iosflashscreen.phonecallerid.screencaller.R;
import com.iosflashscreen.phonecallerid.screencaller.databinding.ActivityOnboarding2Binding;
import com.iosflashscreen.phonecallerid.screencaller.databinding.ActivityOnboardingBinding;

public class Onboarding2Activity extends AppCompatActivity {

    ActivityOnboarding2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_onboarding2);
        binding.txtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Onboarding2Activity.this, TermsOfUseActivity.class));
            }
        });

    }
}