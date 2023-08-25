package com.iosflashscreen.phonecallerid.screencaller.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.iosflashscreen.phonecallerid.screencaller.R;
import com.iosflashscreen.phonecallerid.screencaller.model.CallReceiveRejectCall;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class IconCustomAdapter extends RecyclerView.Adapter<IconCustomAdapter.ViewHolder> {
    ArrayList<CallReceiveRejectCall> callReceiveRejectCalls;
    Context context;
    private RefreshCallback refreshCallback;
    private View selectedItem;

    public IconCustomAdapter(ArrayList<CallReceiveRejectCall> arrayList, Context context2) {
        this.callReceiveRejectCalls = arrayList;
        this.context = context2;

    }

    public interface RefreshCallback {
        void onRefresh();
    }

    public void setRefreshCallback(RefreshCallback callback) {
        this.refreshCallback = callback;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.customize_icon, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        final CallReceiveRejectCall item = callReceiveRejectCalls.get(position);


        SharedPreferences imageSharedPreferences = context.getSharedPreferences("image_theme", Context.MODE_PRIVATE);
        SharedPreferences gifSharedPreferences = context.getSharedPreferences("gif_theme", Context.MODE_PRIVATE);

        String imageUrl = imageSharedPreferences.getString("image_url1", null);
        String gifUrl = gifSharedPreferences.getString("image_url1", null);
        int cornerRadius = 20; // Set the corner radius in pixels or dp

        RequestOptions requestOptions = new RequestOptions()
                .transform(new RoundedCorners(cornerRadius));
        if (imageUrl != null && gifUrl != null) {
            long imageTimestamp = imageSharedPreferences.getLong("timestamp", 0);
            long gifTimestamp = gifSharedPreferences.getLong("timestamp", 0);

            if (imageTimestamp > gifTimestamp) {
                holder.imageView.setVisibility(View.VISIBLE);
                holder.gifimageView.setVisibility(View.GONE);
                Glide.with(context).load(imageUrl).apply(requestOptions).into(holder.imageView);
            } else {
                holder.imageView.setVisibility(View.GONE);
                holder.gifimageView.setVisibility(View.VISIBLE);
                Glide.with(context).load(gifUrl).apply(requestOptions).into(holder.gifimageView);
            }
        } else if (imageUrl != null) {
            holder.imageView.setVisibility(View.VISIBLE);
            holder.gifimageView.setVisibility(View.GONE);
            Picasso.get().load(imageUrl).into(holder.imageView);
        } else if (gifUrl != null) {
            holder.imageView.setVisibility(View.GONE);
            holder.gifimageView.setVisibility(View.VISIBLE);
            Glide.with(context).load(gifUrl).into(holder.gifimageView);
        } else {
            holder.imageView.setVisibility(View.GONE);
            holder.gifimageView.setVisibility(View.GONE);
        }

        // Set the appropriate images for the ImageView elements
        holder.reccive_call.setImageResource(item.getReceive());
        holder.reject_call.setImageResource(item.getReject());

        // Set click listeners for the ImageView elements
        holder.reccive_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("receiveIcon", item.getReceive());
                editor.apply();
                SharedPreferences anotherSharedPreferences = context.getSharedPreferences("AnotherPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor anotherEditor = anotherSharedPreferences.edit();

                // Store the receive icon in another SharedPreferences
                anotherEditor.putInt("receiveIcon", item.getReceive());
                anotherEditor.apply();
            }
        });

        holder.reject_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("rejectIcon", item.getReject());
                editor.apply();
                SharedPreferences anotherSharedPreferences = context.getSharedPreferences("AnotherPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor anotherEditor = anotherSharedPreferences.edit();

                // Store the reject icon in another SharedPreferences
                anotherEditor.putInt("rejectIcon", item.getReject());
                anotherEditor.apply();
            }
        });

        holder.rl_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedItem != v) {
                    if (selectedItem != null) {
                        selectedItem.setSelected(false);
                    }

                    selectedItem = v;
                    selectedItem.setSelected(true);

                }
                v.setSelected(!v.isSelected());
                SharedPreferences anotherSharedPreferences = context.getSharedPreferences("AnotherPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor anotherEditor = anotherSharedPreferences.edit();
                anotherEditor.putInt("rejectIcon", item.getReject());
                anotherEditor.putInt("receiveIcon", item.getReceive());
                anotherEditor.apply();

                if (refreshCallback != null) {
                    refreshCallback.onRefresh();
                }


            }
        });
    }


    public int getItemCount() {
        return callReceiveRejectCalls.size() > 15 ? 15 : callReceiveRejectCalls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout rl_icon;
        ImageView reccive_call;
        ImageView reject_call, imageView, gifimageView;

        public ViewHolder(View view) {
            super(view);
            this.reccive_call = (ImageView) view.findViewById(R.id.reccive_call);
            this.reject_call = (ImageView) view.findViewById(R.id.reject_call);
            this.imageView = (ImageView) view.findViewById(R.id.imageView);
            this.gifimageView = (ImageView) view.findViewById(R.id.gifimageView);
            this.rl_icon = (ConstraintLayout) view.findViewById(R.id.liner_main);
        }
    }
}
