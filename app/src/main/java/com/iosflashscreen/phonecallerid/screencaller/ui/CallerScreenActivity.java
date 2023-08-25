package com.iosflashscreen.phonecallerid.screencaller.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.iosflashscreen.phonecallerid.screencaller.R;
import com.iosflashscreen.phonecallerid.screencaller.SettingsFragment;
import com.iosflashscreen.phonecallerid.screencaller.databinding.ActivityCallerScreenBinding;
import com.iosflashscreen.phonecallerid.screencaller.fragment.CallFlashFragment;
import com.iosflashscreen.phonecallerid.screencaller.fragment.HomeFragment;
import com.iosflashscreen.phonecallerid.screencaller.fragment.IphoneDialerFragment;

public class CallerScreenActivity extends AppCompatActivity {
     ActivityCallerScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCallerScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(
                R.id.fragment_container, new HomeFragment()
        ).commit();
        binding.callLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new HomeFragment());
                    fragmentTransaction.commit();
            }
        });
        binding.iphoneDialerLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new IphoneDialerFragment());
                    fragmentTransaction.commit();
            }
        });
        binding.callFlashLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new CallFlashFragment());
                fragmentTransaction.commit();
            }
        });
        binding.settingsLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new SettingsFragment());
                    fragmentTransaction.commit();
            }
        });
    }
}