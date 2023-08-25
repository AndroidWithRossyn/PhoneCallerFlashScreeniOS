package com.iosflashscreen.phonecallerid.screencaller.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iosflashscreen.phonecallerid.screencaller.R;
import com.iosflashscreen.phonecallerid.screencaller.adapter.ImagesAdapter;
import com.iosflashscreen.phonecallerid.screencaller.adapter.ImagesWallpaperAdapter;
import com.iosflashscreen.phonecallerid.screencaller.model.Images;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TrendingWallpaperFragment extends Fragment {

    private RecyclerView wallpaperRecyclerView;
    private ImagesWallpaperAdapter wallpaperAdapter;
    private List<Images> wallpaperList;

    public TrendingWallpaperFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trending_wallpaper, container, false);
        wallpaperRecyclerView = view.findViewById(R.id.wallpaper_recycler);
        wallpaperList = new ArrayList<>();
        wallpaperAdapter = new ImagesWallpaperAdapter(requireActivity(), (ArrayList<Images>) wallpaperList);

        GridLayoutManager layoutManager = new GridLayoutManager(requireActivity(), 2);
        wallpaperRecyclerView.setLayoutManager(layoutManager);
        wallpaperRecyclerView.setHasFixedSize(true);
        wallpaperRecyclerView.setNestedScrollingEnabled(false);
        wallpaperRecyclerView.setAdapter(wallpaperAdapter);

        fetchWallpapers();

        return view;
    }

    private void fetchWallpapers() {
        try {
            InputStream inputStream = requireActivity().getAssets().open("category.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String jsonConfig = new String(buffer, "UTF-8");

            JSONObject jsonObject = new JSONObject(jsonConfig);
            JSONObject categoriesObject = jsonObject.getJSONObject("Categories");
            processCategory(categoriesObject, "TrendingWallpapers");

            wallpaperAdapter.notifyDataSetChanged();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void processCategory(JSONObject categoriesObject, String categoryName) throws JSONException {
        if (categoriesObject.has(categoryName)) {
            JSONArray urlsArray = categoriesObject.getJSONObject(categoryName).getJSONArray("urls");
            for (int i = 0; i < urlsArray.length(); i++) {
                String url = urlsArray.getString(i);
                Images wallpaper = new Images();
                wallpaper.setUrl(url);
                wallpaperList.add(wallpaper);
            }
        }
    }
}