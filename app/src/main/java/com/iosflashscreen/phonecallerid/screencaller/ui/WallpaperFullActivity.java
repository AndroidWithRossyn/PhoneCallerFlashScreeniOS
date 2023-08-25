package com.iosflashscreen.phonecallerid.screencaller.ui;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.iosflashscreen.phonecallerid.screencaller.R;
import com.iosflashscreen.phonecallerid.screencaller.model.Images;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class WallpaperFullActivity extends AppCompatActivity {
    ImageView imageView, back,favourites,downloads;
    Bitmap photoBitmap;
    TextView setWallpaper;

    public static final String FAVORITES_PREF_WALLPAPER_NAME = "my_favorites_wallpaper";
    public static final String DOWNLOAD_PREF_WALLPAPER_NAME = "my_download_wallpaper";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_full);
        imageView = findViewById(R.id.imageView);
        setWallpaper = findViewById(R.id.set_wallpaper);
        back = findViewById(R.id.back);
        downloads = findViewById(R.id.downloads);
        favourites = findViewById(R.id.favourites);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WallpaperFullActivity.super.onBackPressed();

            }
        });
        String imageUrl = getIntent().getStringExtra("imageUrl");
        Picasso.get()
                .load(imageUrl)
                .into(imageView);
        SharedPreferences sharedPreferences = getSharedPreferences(FAVORITES_PREF_WALLPAPER_NAME, Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences1 = getSharedPreferences(DOWNLOAD_PREF_WALLPAPER_NAME, Context.MODE_PRIVATE);
        Set<String> favoriteUrls = sharedPreferences.getStringSet("favorite_wallpaper_urls", new HashSet<>());
        Set<String> downloadUrls = sharedPreferences1.getStringSet("my_download_wallpaper", new HashSet<>());

        if (imageUrl != null && favoriteUrls.contains(imageUrl)) {
            favourites.setColorFilter(ContextCompat.getColor(WallpaperFullActivity.this, R.color.red));
        }
        if (imageUrl != null && downloadUrls.contains(imageUrl)) {
            downloads.setColorFilter(ContextCompat.getColor(WallpaperFullActivity.this, R.color.blue));
        }
        favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToFavoritesIfImageSet(imageUrl);
            }
        });
        downloads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUrl != null && downloadUrls.contains(imageUrl)) {
                    downloadUrls.remove(imageUrl);
                    downloads.setColorFilter(ContextCompat.getColor(WallpaperFullActivity.this, R.color.black));
                } else {
                    downloadUrls.add(imageUrl);
                    downloads.setColorFilter(ContextCompat.getColor(WallpaperFullActivity.this, R.color.blue));
                    downloadImageToGallery(imageUrl);
                }

                SharedPreferences.Editor editor = sharedPreferences1.edit();
                editor.putStringSet("my_download_wallpaper", downloadUrls);
                editor.apply();
            }
        });
        setWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoBitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    String[] options = {"Home screen", "Lock screen", "Both"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(WallpaperFullActivity.this);
                    builder.setTitle("SET WALLPAPER AS");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new SetWallpaperTask().execute(which + 1);
                            finish();
                        }
                    });
                    builder.show();
                } else {
                    new SetWallpaperTask().execute(0);
                    finish();
                }
            }
        });
    }
    private void downloadImageToGallery(String imageUrl) {
        Picasso.get()
                .load(imageUrl)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        String fileName = "Image_" + System.currentTimeMillis() + ".jpg";
                        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                        File imageFile = new File(directory, fileName);

                        try {
                            FileOutputStream outputStream = new FileOutputStream(imageFile);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                            outputStream.flush();
                            outputStream.close();
                            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            Uri contentUri = Uri.fromFile(imageFile);
                            mediaScanIntent.setData(contentUri);
                            WallpaperFullActivity.this.sendBroadcast(mediaScanIntent);

                            Toast.makeText(WallpaperFullActivity.this, "Image saved successfully", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            Toast.makeText(WallpaperFullActivity.this, "Failed to save image", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        Toast.makeText(WallpaperFullActivity.this, "Failed to download image", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
    }
    private void addToFavoritesIfImageSet(String imageUrl) {
        SharedPreferences sharedPreferences = getSharedPreferences(FAVORITES_PREF_WALLPAPER_NAME, Context.MODE_PRIVATE);
        Set<String> favoriteUrls = sharedPreferences.getStringSet("favorite_wallpaper_urls", new HashSet<>());

        if (imageUrl != null) {
            if (favoriteUrls.contains(imageUrl)) {
                favoriteUrls.remove(imageUrl);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet("favorite_wallpaper_urls", favoriteUrls);
                editor.apply();
                favourites.setColorFilter(null);
                Toast.makeText(WallpaperFullActivity.this, "Removed from Favorites", Toast.LENGTH_SHORT).show();
            } else {
                favoriteUrls.add(imageUrl);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet("favorite_wallpaper_urls", favoriteUrls);
                editor.apply();
                favourites.setColorFilter(ContextCompat.getColor(WallpaperFullActivity.this, R.color.red));
                Toast.makeText(WallpaperFullActivity.this, "Added to Favorites", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @SuppressLint("StaticFieldLeak")
    public class SetWallpaperTask extends AsyncTask<Integer, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(Integer... integers) {
            WallpaperManager manager = WallpaperManager.getInstance(getApplicationContext());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (integers[0] == 1) {
                    try {
                        manager.setBitmap(photoBitmap, null, true, WallpaperManager.FLAG_SYSTEM);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (integers[0] == 2) {
                    try {
                        manager.setBitmap(photoBitmap, null, true, WallpaperManager.FLAG_LOCK);//For Lock screen
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        manager.setBitmap(photoBitmap, null, true, WallpaperManager.FLAG_SYSTEM | WallpaperManager.FLAG_LOCK);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                if (integers[0] == 0) {
                    try {
                        manager.setBitmap(photoBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return true;
        }
    }
}