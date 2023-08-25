package com.iosflashscreen.phonecallerid.screencaller.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.iosflashscreen.phonecallerid.screencaller.R;
import com.iosflashscreen.phonecallerid.screencaller.adapter.IconCustomAdapter;
import com.iosflashscreen.phonecallerid.screencaller.databinding.ActivityPreviewAllBinding;
import com.iosflashscreen.phonecallerid.screencaller.model.CallReceiveRejectCall;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PreviewAllActivity extends AppCompatActivity implements IconCustomAdapter.RefreshCallback {
    private ActivityPreviewAllBinding binding;
    IconCustomAdapter adapterCallingiconpreview;
    ArrayList<CallReceiveRejectCall> callReceiveRejectCalls = new ArrayList<>();
    int receiveIcon, rejectIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPreviewAllBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        this.callReceiveRejectCalls.add(new CallReceiveRejectCall(R.drawable.accept_button_one, R.drawable.decline_button_one));
        this.callReceiveRejectCalls.add(new CallReceiveRejectCall(R.drawable.accept_button_two, R.drawable.decline_button_two));
        this.callReceiveRejectCalls.add(new CallReceiveRejectCall(R.drawable.accept_button_three, R.drawable.decline_button_three));
        this.callReceiveRejectCalls.add(new CallReceiveRejectCall(R.drawable.accept_button_four, R.drawable.decline_button_four));
        this.callReceiveRejectCalls.add(new CallReceiveRejectCall(R.drawable.accept_button_five, R.drawable.decline_button_five));
        this.callReceiveRejectCalls.add(new CallReceiveRejectCall(R.drawable.accept_button_six, R.drawable.decline_button_six));
        this.callReceiveRejectCalls.add(new CallReceiveRejectCall(R.drawable.accept_button_seven, R.drawable.decline_button_seven));
        this.callReceiveRejectCalls.add(new CallReceiveRejectCall(R.drawable.accept_button_eight, R.drawable.decline_button_eight));
        this.callReceiveRejectCalls.add(new CallReceiveRejectCall(R.drawable.accept_button_nine, R.drawable.decline_button_nine));
        this.callReceiveRejectCalls.add(new CallReceiveRejectCall(R.drawable.accept_button_ten, R.drawable.decline_button_ten));
        this.callReceiveRejectCalls.add(new CallReceiveRejectCall(R.drawable.accept_button_eleven, R.drawable.decline_button_eleven));
        this.callReceiveRejectCalls.add(new CallReceiveRejectCall(R.drawable.accept_button_twelve, R.drawable.decline_button_twelve));
        this.callReceiveRejectCalls.add(new CallReceiveRejectCall(R.drawable.accept_button_thirteen, R.drawable.decline_button_thirteen));
        this.callReceiveRejectCalls.add(new CallReceiveRejectCall(R.drawable.accept_button_fourteen, R.drawable.decline_button_fourteen));
        this.callReceiveRejectCalls.add(new CallReceiveRejectCall(R.drawable.accept_button_fiftyteen, R.drawable.decline_button_fiftyteen));
        binding.itemOfIconsRl.setLayoutManager(new LinearLayoutManager(PreviewAllActivity.this, LinearLayoutManager.HORIZONTAL, false));
        binding.itemOfIconsRl.setHasFixedSize(true);
        IconCustomAdapter adapter_calling_icon_preview = new IconCustomAdapter(this.callReceiveRejectCalls, PreviewAllActivity.this);
        this.adapterCallingiconpreview = adapter_calling_icon_preview;
        adapter_calling_icon_preview.setRefreshCallback(this);
        binding.itemOfIconsRl.setAdapter(adapter_calling_icon_preview);


          /* SharedPreferences anotherSharedPreferences = getSharedPreferences("AnotherPrefs", Context.MODE_PRIVATE);
        int receiveIcon = anotherSharedPreferences.getInt("receiveIcon", 0);
        int rejectIcon = anotherSharedPreferences.getInt("rejectIcon", 0);

        // Set the receive and reject icons in the ImageView elements
        binding.imgRecive.setImageResource(receiveIcon);
        binding.imgReject.setImageResource(rejectIcon);*/

        SharedPreferences imageSharedPreferences = getSharedPreferences("image_theme", Context.MODE_PRIVATE);
        SharedPreferences gifSharedPreferences = getSharedPreferences("gif_theme", Context.MODE_PRIVATE);
        String imageUrl = imageSharedPreferences.getString("image_url1", null);
        String gifUrl = gifSharedPreferences.getString("image_url1", null);
        int cornerRadius = 20; // Set the corner radius in pixels or dp
        RequestOptions requestOptions = new RequestOptions()
                .transform(new RoundedCorners(cornerRadius));
        if (imageUrl != null && gifUrl != null) {
            long imageTimestamp = imageSharedPreferences.getLong("timestamp", 0);
            long gifTimestamp = gifSharedPreferences.getLong("timestamp", 0);

            if (imageTimestamp > gifTimestamp) {
                binding.imageViewPreview.setVisibility(View.VISIBLE);
                binding.gifImageViewPreview.setVisibility(View.GONE);
                Glide.with(this).load(imageUrl).apply(requestOptions).into(binding.imageViewPreview);
            } else {
                binding.imageViewPreview.setVisibility(View.GONE);
                binding.gifImageViewPreview.setVisibility(View.VISIBLE);
                Glide.with(PreviewAllActivity.this).load(gifUrl).apply(requestOptions).into(binding.gifImageViewPreview);
            }
        } else if (imageUrl != null) {
            binding.imageViewPreview.setVisibility(View.VISIBLE);
            binding.gifImageViewPreview.setVisibility(View.GONE);
            Picasso.get().load(imageUrl).into(binding.imageViewPreview);
        } else if (gifUrl != null) {
            binding.imageViewPreview.setVisibility(View.GONE);
            binding.gifImageViewPreview.setVisibility(View.VISIBLE);
            Glide.with(PreviewAllActivity.this).load(gifUrl).into(binding.gifImageViewPreview);
        } else {
            binding.imageViewPreview.setVisibility(View.GONE);
            binding.gifImageViewPreview.setVisibility(View.GONE);
        }

        binding.applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences anotherActivitySharedPreferences = getSharedPreferences("AnotherActivityPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor anotherActivityEditor = anotherActivitySharedPreferences.edit();
                anotherActivityEditor.putInt("receiveIcon", receiveIcon);
                anotherActivityEditor.putInt("rejectIcon", rejectIcon);
                anotherActivityEditor.apply();
                Toast.makeText(PreviewAllActivity.this, "Set Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PreviewAllActivity.this, CallerScreenActivity.class));
            }
        });
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PreviewAllActivity.super.onBackPressed();
            }
        });

    }


    @Override
    public void onRefresh() {
        SharedPreferences anotherSharedPreferences = getSharedPreferences("AnotherPrefs", Context.MODE_PRIVATE);
        receiveIcon = anotherSharedPreferences.getInt("receiveIcon", 0);
        rejectIcon = anotherSharedPreferences.getInt("rejectIcon", 0);
        // Update the image views with the retrieved icons
        binding.imgRecive.setImageResource(receiveIcon);
        binding.imgReject.setImageResource(rejectIcon);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}