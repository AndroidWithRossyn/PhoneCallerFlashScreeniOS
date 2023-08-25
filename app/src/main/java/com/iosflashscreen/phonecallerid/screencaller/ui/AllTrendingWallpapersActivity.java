package com.iosflashscreen.phonecallerid.screencaller.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.iosflashscreen.phonecallerid.screencaller.R;
import com.iosflashscreen.phonecallerid.screencaller.databinding.ActivityAllTrendingWallpapersBinding;
import com.iosflashscreen.phonecallerid.screencaller.databinding.ActivityCallerScreenBinding;
import com.iosflashscreen.phonecallerid.screencaller.databinding.FragmentHomeBinding;
import com.iosflashscreen.phonecallerid.screencaller.fragment.CallScreen.LiveThemeFragment;
import com.iosflashscreen.phonecallerid.screencaller.fragment.CallScreen.ThemeFragment;
import com.iosflashscreen.phonecallerid.screencaller.fragment.CallScreen.WallpaperFragment;
import com.iosflashscreen.phonecallerid.screencaller.fragment.HomeFragment;
import com.iosflashscreen.phonecallerid.screencaller.fragment.TrendingLiveWallpaperFragment;
import com.iosflashscreen.phonecallerid.screencaller.fragment.TrendingWallpaperFragment;

import java.util.ArrayList;

public class AllTrendingWallpapersActivity extends AppCompatActivity {
    ActivityAllTrendingWallpapersBinding binding;
    private ArrayList<ThemeOptionsModel> tabListThemesType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllTrendingWallpapersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllTrendingWallpapersActivity.super.onBackPressed();
            }
        });
        tabListThemesType = new ArrayList<>();
        tabListThemesType.add(new ThemeOptionsModel("Wallpaper"));
        tabListThemesType.add(new ThemeOptionsModel("Live Wallpaper"));
        TabLayout tabLayoutThemesOption = binding.tabLayoutThemesOption;

        for (ThemeOptionsModel themeOptionsModel : tabListThemesType) {
            TabLayout.Tab tab = tabLayoutThemesOption.newTab();
            tab.setText(themeOptionsModel.getName());
            tabLayoutThemesOption.addTab(tab);
        }

        ViewPagerAdapter adapter = new ViewPagerAdapter(tabListThemesType.size());
        binding.viewPagerThemesOption.setAdapter(adapter);
        binding.viewPagerThemesOption.setOffscreenPageLimit(4);

        TabLayoutMediator.TabConfigurationStrategy tabConfigurationStrategy = (tab, position) -> {
            tab.setText(tabListThemesType.get(position).getName());
        };

        new TabLayoutMediator(tabLayoutThemesOption, binding.viewPagerThemesOption, tabConfigurationStrategy).attach();
    }
    private static class ThemeOptionsModel {
        private final String name;

        public ThemeOptionsModel(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private class ViewPagerAdapter extends FragmentStateAdapter {
        private final int totalTabs;

        public ViewPagerAdapter(int totalTabs) {
            super(AllTrendingWallpapersActivity.this);
            this.totalTabs = totalTabs;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new TrendingWallpaperFragment();
                default:
                    return new TrendingLiveWallpaperFragment();
            }
        }

        @Override
        public int getItemCount() {
            return totalTabs;
        }
    }
}