package com.iosflashscreen.phonecallerid.screencaller.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.iosflashscreen.phonecallerid.screencaller.R;
import com.iosflashscreen.phonecallerid.screencaller.model.DataClass;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import pl.droidsonroids.gif.GifImageView;

public class Theme_GifActivity_Calling_Theme_Preview extends AppCompatActivity {
    ImageView favourites,customize;
    GifImageView gifimageView;
    TextView btn_set_theam;
    private ArrayList<DataClass> dataList;
    LinearLayout leftArrowContainer, rightArrowContainer;
    ImageView img_recive, img_reject,back1;
    public static final String FAVORITES_PREF_NAME = "my_favorites_live_theme";
    private boolean isThemeApplied = false;
    private String selectedImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_gif_calling_theme_preview);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gifimageView = findViewById(R.id.gif_image_view_preview);
        btn_set_theam = findViewById(R.id.btn_set_theam);
        back1 = findViewById(R.id.back1);
        customize = findViewById(R.id.customize);

        img_recive = findViewById(R.id.img_recive);
        img_reject = findViewById(R.id.img_reject);
        favourites = findViewById(R.id.favourites);
        dataList = new ArrayList<>();
        leftArrowContainer = findViewById(R.id.left_arrow_container);
        rightArrowContainer = findViewById(R.id.right_arrow_container);
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Theme_GifActivity_Calling_Theme_Preview.super.onBackPressed();
            }
        });
        ObjectAnimator leftArrow1Animator = ObjectAnimator.ofFloat(leftArrowContainer.getChildAt(0), "translationX", -40f);
        ObjectAnimator leftArrow2Animator = ObjectAnimator.ofFloat(leftArrowContainer.getChildAt(1), "translationX", -40f);

        ObjectAnimator rightArrow1Animator = ObjectAnimator.ofFloat(rightArrowContainer.getChildAt(0), "translationX", 40f);
        ObjectAnimator rightArrow2Animator = ObjectAnimator.ofFloat(rightArrowContainer.getChildAt(1), "translationX", 40f);



        AnimatorSet leftArrowAnimatorSet = new AnimatorSet();
        leftArrowAnimatorSet.playSequentially(
                leftArrow1Animator,
                leftArrow2Animator

        );
        leftArrowAnimatorSet.setDuration(500);
        leftArrowAnimatorSet.setStartDelay(200);


        AnimatorSet rightArrowAnimatorSet = new AnimatorSet();
        rightArrowAnimatorSet.playSequentially(
                rightArrow1Animator,
                rightArrow2Animator

        );
        rightArrowAnimatorSet.setDuration(500);
        rightArrowAnimatorSet.setStartDelay(200);


        img_recive.setOnTouchListener(new SwipeTouchListener(leftArrowContainer, rightArrowContainer, img_recive));
        img_reject.setOnTouchListener(new SwipeTouchListener(leftArrowContainer, rightArrowContainer, img_reject));

        customize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isThemeApplied) {
                    startActivity(new Intent(Theme_GifActivity_Calling_Theme_Preview.this, PreviewAllActivity.class));
                } else {
                    Toast.makeText(Theme_GifActivity_Calling_Theme_Preview.this, "Please apply the theme first", Toast.LENGTH_SHORT).show();
                }
            }
        });
        favourites.setColorFilter(ContextCompat.getColor(this, R.color.white));
        String imageUrl = getIntent().getStringExtra("image_url");
        Glide.with(Theme_GifActivity_Calling_Theme_Preview.this).load(imageUrl).into(gifimageView);





        btn_set_theam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("gif_theme", Context.MODE_PRIVATE);

                if (imageUrl != null) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("image_url1", imageUrl);
                    editor.putLong("timestamp", System.currentTimeMillis());
                    editor.apply();
                    selectedImageUrl = imageUrl;
                    Toast.makeText(Theme_GifActivity_Calling_Theme_Preview.this, "Applied Successfully", Toast.LENGTH_SHORT).show();
                    isThemeApplied = true;
                    customize.setEnabled(true);
                } else {
                    Toast.makeText(Theme_GifActivity_Calling_Theme_Preview.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });
        favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToFavoritesIfImageSet(imageUrl);
            }
        });



    }
    private void addToFavoritesIfImageSet(String imageUrl) {
        SharedPreferences sharedPreferences = getSharedPreferences(FAVORITES_PREF_NAME, Context.MODE_PRIVATE);
        Set<String> favoriteUrls = sharedPreferences.getStringSet("favorite_urls", new HashSet<>());

        if (imageUrl != null) {
            if (favoriteUrls.contains(imageUrl)) {
                Toast.makeText(Theme_GifActivity_Calling_Theme_Preview.this, "Already in Favorites", Toast.LENGTH_SHORT).show();
            } else {
                favoriteUrls.add(imageUrl);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet("favorite_urls", favoriteUrls);
                editor.apply();
                favourites.setColorFilter(ContextCompat.getColor(Theme_GifActivity_Calling_Theme_Preview.this, R.color.red));

                Toast.makeText(Theme_GifActivity_Calling_Theme_Preview.this, "Added to Favorites", Toast.LENGTH_SHORT).show();
            }
        }
    }
    static class SwipeTouchListener implements View.OnTouchListener {
        private LinearLayout leftArrowContainer;
        private LinearLayout rightArrowContainer;
        private ImageView leftArrow1;
        private ImageView leftArrow2;

        private ImageView rightArrow1;
        private ImageView rightArrow2;

        private boolean isSwiping = false;
        private float initialX;
        private ImageView buttonView;
        private ObjectAnimator fadeOutAnimator;
        private ObjectAnimator fadeInAnimator;

        SwipeTouchListener(LinearLayout leftArrowContainer, LinearLayout rightArrowContainer, ImageView buttonView) {
            this.leftArrowContainer = leftArrowContainer;
            this.rightArrowContainer = rightArrowContainer;
            this.buttonView = buttonView;

            leftArrow1 = leftArrowContainer.findViewById(R.id.left_arrow_1);
            leftArrow2 = leftArrowContainer.findViewById(R.id.left_arrow_2);
            rightArrow1 = rightArrowContainer.findViewById(R.id.right_arrow_1);
            rightArrow2 = rightArrowContainer.findViewById(R.id.right_arrow_2);

            fadeOutAnimator = ObjectAnimator.ofFloat(buttonView, "alpha", 1f, 0.5f);
            fadeOutAnimator.setDuration(200);
            fadeOutAnimator.setInterpolator(new LinearInterpolator());

            fadeInAnimator = ObjectAnimator.ofFloat(buttonView, "alpha", 0.5f, 1f);
            fadeInAnimator.setDuration(200);
            fadeInAnimator.setInterpolator(new LinearInterpolator());
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isSwiping = false;
                    initialX = event.getX();
                    showArrows();
                    fadeOutAnimator.cancel();
                    fadeInAnimator.cancel();
                    buttonView.setAlpha(1f);
                    return true;
                case MotionEvent.ACTION_MOVE:
                    if (!isSwiping && Math.abs(event.getX() - initialX) > 100) {
                        isSwiping = true;
                    }
                    if (isSwiping) {
                        buttonView.setTranslationX(event.getX() - initialX);
                        fadeOutAnimator.start();
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                    hideArrows();
                    if (isSwiping) {
                        float deltaX = event.getX() - initialX;
                        if (deltaX > 0) {

                        } else {
                        }
                    }
                    buttonView.setTranslationX(0f);
                    fadeInAnimator.start();
                    return true;
            }
            return false;
        }

        private void showArrows() {
            leftArrow1.setVisibility(View.VISIBLE);
            leftArrow1.setTranslationX(-50);
            leftArrow1.setAlpha(0f);
            leftArrow1.animate().translationXBy(50).alpha(1f).setDuration(200).start();

            leftArrow2.setVisibility(View.VISIBLE);
            leftArrow2.setTranslationX(-50);
            leftArrow2.setAlpha(0f);
            leftArrow2.animate().translationXBy(50).alpha(1f).setStartDelay(100).setDuration(200).start();



            rightArrow1.setVisibility(View.VISIBLE);
            rightArrow1.setTranslationX(50);
            rightArrow1.setAlpha(0f);
            rightArrow1.animate().translationXBy(-50).alpha(1f).setDuration(200).start();

            rightArrow2.setVisibility(View.VISIBLE);
            rightArrow2.setTranslationX(50);
            rightArrow2.setAlpha(0f);
            rightArrow2.animate().translationXBy(-50).alpha(1f).setStartDelay(100).setDuration(200).start();


        }


        private void hideArrows() {
            leftArrow1.animate().translationXBy(50).alpha(0f).setDuration(200).start();
            leftArrow2.animate().translationXBy(50).alpha(0f).setDuration(200).start();
            rightArrow1.animate().translationXBy(-50).alpha(0f).setDuration(200).start();
            rightArrow2.animate().translationXBy(-50).alpha(0f).setDuration(200).start();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

