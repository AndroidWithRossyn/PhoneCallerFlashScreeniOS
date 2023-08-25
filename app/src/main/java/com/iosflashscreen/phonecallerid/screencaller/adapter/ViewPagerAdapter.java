package com.iosflashscreen.phonecallerid.screencaller.adapter;


import static com.iosflashscreen.phonecallerid.screencaller.utils.Utility.setGradientShaderToTextView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.iosflashscreen.phonecallerid.screencaller.R;
import com.iosflashscreen.phonecallerid.screencaller.model.Images;
import com.iosflashscreen.phonecallerid.screencaller.ui.Theme_Activity_Calling_Theme_Preview;
import com.iosflashscreen.phonecallerid.screencaller.utils.SharedPreferenceManager;
import com.iosflashscreen.phonecallerid.screencaller.viewPager.GradientProgressBar;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import jp.wasabeef.blurry.Blurry;

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private List<Images> lstImages;
    private LayoutInflater layoutInflater;
    GradientProgressBar progressBar;
    Dialog dialog;
    private String downloadedImageUrl;
    private SharedPreferenceManager preferenceManager;
    LinearLayout leftArrowContainer, rightArrowContainer, ads;

    public ViewPagerAdapter(Context context, List<Images> lstImages) {
        this.context = context;
        this.lstImages = lstImages;
        this.layoutInflater = LayoutInflater.from(context);
        preferenceManager = new SharedPreferenceManager(context);
    }

    @Override
    public int getCount() {
        return lstImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.item_page, container, false);
        ImageView imageView = view.findViewById(R.id.image_view);
        TextView tv_apply = view.findViewById(R.id.tv_apply);
        TextView phone_no = view.findViewById(R.id.phone_no);
        TextView Username = view.findViewById(R.id.Username);
        ImageView receive = view.findViewById(R.id.receive);
        RelativeLayout full=view.findViewById(R.id.full);
        TextView reply = view.findViewById(R.id.reply);
        ImageView end = view.findViewById(R.id.end);

        LinearLayout native_ad_container=view.findViewById(R.id.native_ad_container);

        imageView.setVisibility(View.VISIBLE);
        Username.setVisibility(View.VISIBLE);
        phone_no.setVisibility(View.VISIBLE);
        reply.setVisibility(View.VISIBLE);
        full.setVisibility(View.VISIBLE);
        tv_apply.setVisibility(View.VISIBLE);

        leftArrowContainer = view.findViewById(R.id.left_arrow_container);
        rightArrowContainer = view.findViewById(R.id.right_arrow_container);

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


        receive.setOnTouchListener(new SwipeTouchListener(leftArrowContainer, rightArrowContainer, receive));
        end.setOnTouchListener(new SwipeTouchListener(leftArrowContainer, rightArrowContainer, end));


        String imageUrl = lstImages.get(position).getUrl();

        Picasso.get().load(imageUrl)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        saveImageToSharedPreferences(bitmap);
                        imageView.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        // Handle bitmap load failure
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        // Handle bitmap loading preparation
                    }
                });

        tv_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isDownloaded = preferenceManager.getImageUrl().equals(imageUrl);
                Log.d("Download", "isDownloaded: " + isDownloaded);
                Log.d("Download", "preferenceManager.getImageUrl(): " + preferenceManager.getImageUrl());
                Log.d("Download", "imageUrl: " + imageUrl);

                if (isDownloaded) {
                    Intent intent = new Intent(context, Theme_Activity_Calling_Theme_Preview.class);
                    intent.putExtra("image_url", imageUrl);
                    context.startActivity(intent);
                } else {
                    // Image is not downloaded, show the download dialog
                    downloadDialog(imageUrl);
                }
            }
        });
        if (position != 0){
            if (position%5 == 0){
                imageView.setVisibility(View.GONE);
                Username.setVisibility(View.GONE);
                phone_no.setVisibility(View.GONE);
                reply.setVisibility(View.GONE);
                full.setVisibility(View.GONE);
                tv_apply.setVisibility(View.GONE);
                native_ad_container.setVisibility(View.VISIBLE);

//                AdUtils.showNativeAdFull((Activity) context, native_ad_container, true);
                // Show ads
            } else {
//                native_ad_container.setVisibility(View.GONE);
                // hide ads
            }
        }
        container.addView(view);
        return view;
    }


    private void downloadDialog(final String imageUrl) {
        boolean isDownloaded = preferenceManager.getImageUrl().equals(imageUrl);
        dialog = new Dialog(context);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.custom_downloading_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView txt1=dialog.findViewById(R.id.txt1);
        setGradientShaderToTextView(txt1, context.getColor(R.color.primary1), context.getColor(R.color.secondary1));
        TextView downloading = dialog.findViewById(R.id.txt3);
        downloading.setText("Downloading...");
        progressBar = dialog.findViewById(R.id.my_progressBar);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final AtomicInteger progress = new AtomicInteger(0);
                while (progress.get() <= 100) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    progressBar.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(progress.get());
                            progressBar.invalidate();
                        }
                    });

                    progress.addAndGet(10);
                }
                downloadedImageUrl = imageUrl;
                preferenceManager.setImageUrl(imageUrl);

                progressBar.post(new Runnable() {
                    @Override
                    public void run() {


                        progressBar.setVisibility(View.GONE); // Hide the progress bar

                        dialog.dismiss();
                        Toast.makeText(context, "Downloaded Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, Theme_Activity_Calling_Theme_Preview.class);
                        intent.putExtra("image_url", imageUrl);

                        context.startActivity(intent);
                    }
                });
            }
        }).start();
        dialog.show();
    }

    private void saveImageToSharedPreferences(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] byteArray = baos.toByteArray();
        String encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        preferenceManager.setImageUrl(encodedImage);

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


    class SwipeTouchListener implements View.OnTouchListener {
        private LinearLayout leftArrowContainer;
        private LinearLayout rightArrowContainer;
        private ImageView leftArrow1;
        private ImageView leftArrow2;

        private ImageView rightArrow1;
        private ImageView rightArrow2;
        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

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
                    showArrows();
                    initialX = event.getX();
                    return true;
                case MotionEvent.ACTION_MOVE:
                    if (!isSwiping && Math.abs(event.getX() - initialX) > SWIPE_THRESHOLD) {
                        isSwiping = true;
                    }
                    if (isSwiping) {
                        buttonView.setTranslationX(event.getX() - initialX);
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                    hideArrows();
                    if (isSwiping) {
                        float deltaX = event.getX() - initialX;
                        float absDeltaX = Math.abs(deltaX);
                        float velocityX = Math.abs(event.getX() - initialX) / (float) event.getEventTime();
                        if (absDeltaX > SWIPE_THRESHOLD && velocityX > SWIPE_VELOCITY_THRESHOLD) {
                            if (deltaX > 0) {

                            } else {

                            }
                        }
                        buttonView.setTranslationX(0f);
                    }
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
}