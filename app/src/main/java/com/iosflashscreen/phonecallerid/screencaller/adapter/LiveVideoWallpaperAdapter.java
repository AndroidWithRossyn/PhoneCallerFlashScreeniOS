package com.iosflashscreen.phonecallerid.screencaller.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.iosflashscreen.phonecallerid.screencaller.R;
import com.iosflashscreen.phonecallerid.screencaller.model.Images;
import com.iosflashscreen.phonecallerid.screencaller.ui.CategoryShowVideoActivity;
import com.iosflashscreen.phonecallerid.screencaller.ui.LiveWallpaperFullActivity;
import com.iosflashscreen.phonecallerid.screencaller.ui.WallpaperFullActivity;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class LiveVideoWallpaperAdapter extends RecyclerView.Adapter<LiveVideoWallpaperAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Images> arrayList;


    public LiveVideoWallpaperAdapter(Context context, ArrayList<Images> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public LiveVideoWallpaperAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parse_item_live_wallpaper, parent, false);
        return new LiveVideoWallpaperAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LiveVideoWallpaperAdapter.ViewHolder holder, int position) {
        Images wallpaper = arrayList.get(position);
        holder.progressBar.setVisibility(View.VISIBLE);
        Glide.with(context)
                .asGif()
                .load(arrayList.get(position).getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<GifDrawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);



                        return false;
                    }
                })
                .into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LiveWallpaperFullActivity.class);
                intent.putExtra("imageUrl1", wallpaper.getUrl());
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
    public class ViewHolder extends RecyclerView.ViewHolder {
        GifImageView imageView;
        TextView tvApply;
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            tvApply = itemView.findViewById(R.id.tv_apply);
            progressBar = itemView.findViewById(R.id.progressBar);
        }


    }
}