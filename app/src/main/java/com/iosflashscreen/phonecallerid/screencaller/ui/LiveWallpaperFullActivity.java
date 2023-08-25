package com.iosflashscreen.phonecallerid.screencaller.ui;

import android.app.DownloadManager;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.iosflashscreen.phonecallerid.screencaller.R;
import com.iosflashscreen.phonecallerid.screencaller.service.GIFWallpaperService;

import java.util.HashSet;
import java.util.Set;

import pl.droidsonroids.gif.GifImageView;

public class LiveWallpaperFullActivity extends AppCompatActivity {
    String imageUrl = "";
    private ImageView back,favourites,downloads;
    private GifImageView gifImageView;
    private TextView setWallpaper;
    public static final String FAVORITES_PREF_LIVE_WALLPAPER_NAME = "my_favorites_live_wallpaper";
    public static final String DOWNLOAD_PREF_LIVE_WALLPAPER_NAME = "my_download_live_wallpaper";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_wallpaper_full);
        gifImageView = findViewById(R.id.gifimageView);
        setWallpaper = findViewById(R.id.set_wallpaper);
        favourites = findViewById(R.id.favourites);
        downloads = findViewById(R.id.downloads);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LiveWallpaperFullActivity.super.onBackPressed();

            }
        });

        imageUrl = getIntent().getStringExtra("imageUrl1");
        Glide.with(LiveWallpaperFullActivity.this).asGif().load(imageUrl).into(gifImageView);
        SharedPreferences sharedPreferences = getSharedPreferences(FAVORITES_PREF_LIVE_WALLPAPER_NAME, Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences1 = getSharedPreferences(DOWNLOAD_PREF_LIVE_WALLPAPER_NAME, Context.MODE_PRIVATE);
        Set<String> favoriteUrls = sharedPreferences.getStringSet("my_favorites_live_wallpaper", new HashSet<>());
        Set<String> downloadUrls = sharedPreferences1.getStringSet("my_download_live_wallpaper", new HashSet<>());

        if (imageUrl != null && favoriteUrls.contains(imageUrl)) {
            favourites.setColorFilter(ContextCompat.getColor(LiveWallpaperFullActivity.this, R.color.red));
        }
        if (imageUrl != null && downloadUrls.contains(imageUrl)) {
            downloads.setColorFilter(ContextCompat.getColor(LiveWallpaperFullActivity.this, R.color.blue));
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
                    downloads.setColorFilter(ContextCompat.getColor(LiveWallpaperFullActivity.this, R.color.black));
                } else {
                    downloadUrls.add(imageUrl);
                    downloads.setColorFilter(ContextCompat.getColor(LiveWallpaperFullActivity.this, R.color.blue));
                    downloadImageToGallery(imageUrl);
                }

                SharedPreferences.Editor editor = sharedPreferences1.edit();
                editor.putStringSet("my_download_live_wallpaper", downloadUrls);
                editor.apply();
            }
        });
        setWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = getSharedPreferences("LiveWallpaper", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("live_wallpaper1", imageUrl);
                editor.apply();
                Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, new ComponentName(LiveWallpaperFullActivity.this, GIFWallpaperService.class));
                startActivity(intent);

            }
        });
    }
    private void addToFavoritesIfImageSet(String imageUrl) {
        SharedPreferences sharedPreferences = getSharedPreferences(FAVORITES_PREF_LIVE_WALLPAPER_NAME, Context.MODE_PRIVATE);
        Set<String> favoriteUrls = sharedPreferences.getStringSet("my_favorites_live_wallpaper", new HashSet<>());

        if (imageUrl != null) {
            if (favoriteUrls.contains(imageUrl)) {
                favoriteUrls.remove(imageUrl);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet("my_favorites_live_wallpaper", favoriteUrls);
                editor.apply();
                favourites.setColorFilter(null);
                Toast.makeText(LiveWallpaperFullActivity.this, "Removed from Favorites", Toast.LENGTH_SHORT).show();
            } else {
                favoriteUrls.add(imageUrl);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet("favorite_wallpaper_urls", favoriteUrls);
                editor.apply();
                favourites.setColorFilter(ContextCompat.getColor(LiveWallpaperFullActivity.this, R.color.red));
                Toast.makeText(LiveWallpaperFullActivity.this, "Added to Favorites", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void downloadImageToGallery(String imageUrl) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(imageUrl));

        String fileName = "Image_" + System.currentTimeMillis() + ".gif";
        request.setTitle(fileName);
        request.setDescription("Downloading GIF");

        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_PICTURES, fileName);
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        long downloadId = downloadManager.enqueue(request);


        BroadcastReceiver onComplete = new BroadcastReceiver() {
            public void onReceive(Context ctxt, Intent intent) {
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (downloadId == id) {
                    Uri downloadUri = downloadManager.getUriForDownloadedFile(downloadId);
                    if (downloadUri != null) {
                        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        mediaScanIntent.setData(downloadUri);
                        sendBroadcast(mediaScanIntent);
                        Toast.makeText(LiveWallpaperFullActivity.this, "GIF saved successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LiveWallpaperFullActivity.this, "Failed to save GIF", Toast.LENGTH_SHORT).show();
                    }
                }

                unregisterReceiver(this);
            }
        };
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public void onBackPressed() {
        LiveWallpaperFullActivity.super.onBackPressed();

    }
}
