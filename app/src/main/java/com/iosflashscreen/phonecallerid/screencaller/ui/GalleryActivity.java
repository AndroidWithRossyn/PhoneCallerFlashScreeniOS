package com.iosflashscreen.phonecallerid.screencaller.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;

import com.iosflashscreen.phonecallerid.screencaller.R;
import com.iosflashscreen.phonecallerid.screencaller.adapter.GalleryAdapter;
import com.iosflashscreen.phonecallerid.screencaller.databinding.ActivityGalleryBinding;
import com.iosflashscreen.phonecallerid.screencaller.model.Images;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GalleryActivity extends AppCompatActivity {
    ActivityGalleryBinding binding;
    GalleryAdapter galleryAdapter = null;
    private ArrayList<Images> imagesItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGalleryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        GridLayoutManager layoutManager2 = new GridLayoutManager(this, 2);
        binding.showRecyclerImageGallery.setLayoutManager(layoutManager2);
        galleryAdapter = new GalleryAdapter(this, imagesItems);
        binding.showRecyclerImageGallery.setAdapter(galleryAdapter);

        Intent intent = getIntent();
        if (intent != null) {
            ArrayList<String> selectedImagePaths = intent.getStringArrayListExtra("selected_image_paths");
            if (selectedImagePaths != null) {
                for (String imagePath : selectedImagePaths) {
                    Images selectedImageItem = new Images(imagePath);
                    imagesItems.add(selectedImageItem);
                }
            }

            galleryAdapter.notifyDataSetChanged();
        }
    }
}
