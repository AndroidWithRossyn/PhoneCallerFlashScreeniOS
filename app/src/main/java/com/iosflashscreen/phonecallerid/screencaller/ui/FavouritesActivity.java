package com.iosflashscreen.phonecallerid.screencaller.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.iosflashscreen.phonecallerid.screencaller.R;
import com.iosflashscreen.phonecallerid.screencaller.databinding.ActivityFavouritesBinding;
import com.iosflashscreen.phonecallerid.screencaller.fragment.HomeFragment;
import com.iosflashscreen.phonecallerid.screencaller.fragment.favourites.FavouriteLiveThemeFragment;
import com.iosflashscreen.phonecallerid.screencaller.fragment.favourites.FavouriteThemeFragment;
import com.iosflashscreen.phonecallerid.screencaller.fragment.favourites.FavouriteWallpaperFragment;

import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {
    ActivityFavouritesBinding binding;
    private ArrayList<ThemeOptionsModel> tabListThemesType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favourites);
        binding.viewPagerThemesOption.setUserInputEnabled(false);
        tabListThemesType = new ArrayList<>();
        tabListThemesType.add(new ThemeOptionsModel("Theme"));
        tabListThemesType.add(new ThemeOptionsModel("Live Theme"));
        tabListThemesType.add(new ThemeOptionsModel("Wallpaper"));


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
            super(FavouritesActivity.this);
            this.totalTabs = totalTabs;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new FavouriteThemeFragment();
                case 1:
                    return new FavouriteLiveThemeFragment();
                default:
                    return new FavouriteWallpaperFragment();
            }
        }

        @Override
        public int getItemCount() {
            return totalTabs;
        }
    }
}