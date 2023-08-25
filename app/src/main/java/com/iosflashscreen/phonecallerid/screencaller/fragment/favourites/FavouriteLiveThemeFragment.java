package com.iosflashscreen.phonecallerid.screencaller.fragment.favourites;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.iosflashscreen.phonecallerid.screencaller.R;
import com.iosflashscreen.phonecallerid.screencaller.adapter.FavouritesLiveAdapter;
import com.iosflashscreen.phonecallerid.screencaller.model.Images;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class FavouriteLiveThemeFragment extends Fragment {

    private static final String FAVORITES_PREF_NAME = "my_favorites_live_theme";
    private RecyclerView favorite_live_recyclerview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_favourite_live_theme, container, false);
        favorite_live_recyclerview=view.findViewById(R.id.favorite_live_recyclerview);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(FAVORITES_PREF_NAME, Context.MODE_PRIVATE);
        Set<String> favoriteUrls = sharedPreferences.getStringSet("favorite_urls", new HashSet<>());

        ArrayList<Images> favoriteDataList = new ArrayList<>();
        for (String imageUrl : favoriteUrls) {
            Images data = new Images();
            data.setUrl(imageUrl);
            favoriteDataList.add(data);
        }

        FavouritesLiveAdapter favoriteAdapter = new FavouritesLiveAdapter(requireActivity(), favoriteDataList);
        favorite_live_recyclerview.setLayoutManager(new GridLayoutManager(requireActivity(), 2));
        favorite_live_recyclerview.setAdapter(favoriteAdapter);

        return view;
    }
}