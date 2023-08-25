package com.iosflashscreen.phonecallerid.screencaller.ui;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.iosflashscreen.phonecallerid.screencaller.R;
import com.iosflashscreen.phonecallerid.screencaller.adapter.ViewPagerAdapter;
import com.iosflashscreen.phonecallerid.screencaller.model.Images;

import java.util.ArrayList;

public class CategoryShowActivity extends AppCompatActivity {
    private static final String TAG = "CategoryShowActivity";
    ImageView back;
    private ArrayList<Images> lstImages = new ArrayList<>();
    private int clickedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_show);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ViewPager viewPager = findViewById(R.id.view_pager);
        back = findViewById(R.id.back);
        back.setVisibility(View.VISIBLE);
        ArrayList<Parcelable> parcelableArrayList = getIntent().getParcelableArrayListExtra("imageUrl");
        if (parcelableArrayList != null) {
            for (Parcelable parcelable : parcelableArrayList) {
                if (parcelable instanceof Images) {
                    Images image = (Images) parcelable;
                    lstImages.add(image);
                }
            }
        }
        clickedPosition = getIntent().getIntExtra("position", 0);
        Log.println(Log.ASSERT, TAG, "lstImages: " + lstImages.size());


        ViewPagerAdapter adapter = new ViewPagerAdapter(this, lstImages);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(clickedPosition);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    CategoryShowActivity.super.onBackPressed();
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position != 0) {
                    if (position % 5 == 0) {
//                        Constants.hitCounter = 5;
                        back.setVisibility(View.GONE);
                    /*    AdUtils.showNativeAdFull(CategoryShowActivity.this, findViewById(R.id.native_ad_container), true);
                        AdUtils.showInterstitialAd(CategoryShowActivity.this, state_load -> {

                        });*/
                        // Show ads
                    } else {
                        back.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        CategoryShowActivity.super.onBackPressed();

    }
}