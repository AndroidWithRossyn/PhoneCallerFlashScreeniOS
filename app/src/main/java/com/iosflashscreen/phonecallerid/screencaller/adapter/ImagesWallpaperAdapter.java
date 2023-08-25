package com.iosflashscreen.phonecallerid.screencaller.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iosflashscreen.phonecallerid.screencaller.R;
import com.iosflashscreen.phonecallerid.screencaller.model.Images;
import com.iosflashscreen.phonecallerid.screencaller.ui.CategoryShowActivity;
import com.iosflashscreen.phonecallerid.screencaller.ui.WallpaperFullActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImagesWallpaperAdapter extends RecyclerView.Adapter<ImagesWallpaperAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Images> arrayList;


    public ImagesWallpaperAdapter(Context context, ArrayList<Images> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ImagesWallpaperAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parse_item_large_wallpaper, parent, false);
        return new ImagesWallpaperAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ImagesWallpaperAdapter.ViewHolder holder, int position) {
        Images wallpaper = arrayList.get(position);
        holder.progressBar.setVisibility(View.VISIBLE);

        Picasso.get()
                .load(arrayList.get(position).getUrl())
                .into(holder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        holder.progressBar.setVisibility(View.GONE);
                    }
                });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WallpaperFullActivity.class);
                intent.putExtra("imageUrl", wallpaper.getUrl());
                intent.putExtra("position", position);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }
        });


    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ProgressBar progressBar;
        public ViewHolder(View itemView/*, RecyclerInterface recyclerInterface*/) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            progressBar = itemView.findViewById(R.id.progressBar);

        }

    }

}
