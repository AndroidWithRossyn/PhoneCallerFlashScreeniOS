package com.iosflashscreen.phonecallerid.screencaller.ui;

import static com.iosflashscreen.phonecallerid.screencaller.utils.Utility.nextActivity;
import static com.iosflashscreen.phonecallerid.screencaller.utils.Utility.nextFinishActivity;
import static com.iosflashscreen.phonecallerid.screencaller.utils.Utility.setGradientShaderToTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.iosflashscreen.phonecallerid.screencaller.R;
import com.iosflashscreen.phonecallerid.screencaller.databinding.ActivityTermsOfUseBinding;
import com.iosflashscreen.phonecallerid.screencaller.utils.OtherUntil;
import com.iosflashscreen.phonecallerid.screencaller.utils.Utility;

public class TermsOfUseActivity extends AppCompatActivity {
    ActivityTermsOfUseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_terms_of_use);
        setGradientShaderToTextView(binding.terms, getColor(R.color.primary), getColor(R.color.secondary));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();

       /* binding.btnPrivacy.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(PrivacyLink));
            startActivity(intent);
        });*/
        binding.btnTmc.setOnClickListener(view -> {
            startActivity(new Intent(TermsOfUseActivity.this,TMCActivity.class));
        });

        binding.btnAgree.setOnClickListener(view -> {
         /*   if (OtherUntil.checkPer(TermsOfUseActivity.this)) {
                finish();
            }else{*/
                startActivity(new Intent(TermsOfUseActivity.this,DashboardActivity.class));
//            }

        });

    }

}