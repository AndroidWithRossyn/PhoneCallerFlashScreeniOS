package com.iosflashscreen.phonecallerid.screencaller.ui;

import static com.iosflashscreen.phonecallerid.screencaller.utils.Utility.setGradientShaderToTextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.app.role.RoleManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telecom.TelecomManager;
import android.view.View;
import android.widget.CompoundButton;

import com.iosflashscreen.phonecallerid.screencaller.R;
import com.iosflashscreen.phonecallerid.screencaller.databinding.ActivityPermissionBinding;
import com.iosflashscreen.phonecallerid.screencaller.setting.DialogSetting;
import com.iosflashscreen.phonecallerid.screencaller.utils.OtherUntil;

public class PermissionActivity extends AppCompatActivity {
    ActivityPermissionBinding binding;
    private boolean isPermissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_permission);
        setGradientShaderToTextView(binding.txt, getColor(R.color.primary), getColor(R.color.secondary));
        binding.callSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    action(0);
                } else {

                }
            }
        });
        binding.contactSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    action(2);
                } else {

                }
            }
        });

    }
    private void action(int permissionType) {
        if (permissionType == 0) {
            TelecomManager telecomManager = (TelecomManager) getSystemService(Context.TELECOM_SERVICE);
            if (telecomManager == null || getPackageName().equals(telecomManager.getDefaultDialerPackage())) {
                return;
            }
            if (Build.VERSION.SDK_INT >= 29) {
                RoleManager roleManager = (RoleManager) getSystemService(RoleManager.class);
                if (roleManager.isRoleAvailable("android.app.role.DIALER") && !roleManager.isRoleHeld("android.app.role.DIALER")) {
                    startActivityForResult(roleManager.createRequestRoleIntent("android.app.role.DIALER"), 1);
                    return;
                }
            }
            requestDefault();
        } else if (permissionType == 2) {
            if (!OtherUntil.checkPer(this, "android.permission.READ_EXTERNAL_STORAGE") || !OtherUntil.checkPer(this, "android.permission.WRITE_EXTERNAL_STORAGE")||!OtherUntil.checkPer(this, "android.permission.READ_CONTACTS")||!OtherUntil.checkPer(this, "android.permission.CALL_PHONE")) {
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE","android.permission.READ_CONTACTS","android.permission.CALL_PHONE"},1);
            } else {
                binding.btnAgree.setVisibility(View.VISIBLE);
            }
        }
        // Add similar handling for other permission types
    }
    @Override
    protected void onResume() {
        int i;
        super.onResume();
        TelecomManager telecomManager = (TelecomManager) getSystemService(Context.TELECOM_SERVICE);
        if (telecomManager != null && !getPackageName().equals(telecomManager.getDefaultDialerPackage())) {
            i = 0;
        } else if (!OtherUntil.checkPer(this, "android.permission.CALL_PHONE")) {
            i = 1;
        } else if (!OtherUntil.checkPer(this, "android.permission.READ_CONTACTS")) {
            i = 2;
        } else {
            i = (OtherUntil.checkPer(this, "android.permission.READ_EXTERNAL_STORAGE") || OtherUntil.checkPer(this, "android.permission.WRITE_EXTERNAL_STORAGE")) ? -1 : 3;
        }
        if (i == -1) {
            binding.btnAgree.setVisibility(View.VISIBLE);
            return;
        }
        binding.btnAgree.setVisibility(View.GONE);
    }


    @Override
    protected void onActivityResult(int i, int i2, Intent intent) {
        Intent intent2;
        super.onActivityResult(i, i2, intent);
        TelecomManager telecomManager = (TelecomManager) getSystemService(Context.TELECOM_SERVICE);
        if ((telecomManager != null && getPackageName().equals(telecomManager.getDefaultDialerPackage())) || i2 != 0) {
            return;
        }
        if (i == 123) {
            try {
                if (Build.VERSION.SDK_INT >= 24) {
                    intent2 = new Intent("android.settings.MANAGE_DEFAULT_APPS_SETTINGS");
                } else {
                    intent2 = new Intent("android.settings.APPLICATION_SETTINGS");
                }
                startActivityForResult(intent2, 12);
            } catch (ActivityNotFoundException unused) {
                new DialogSetting(this).show();
            }
        } else if (i == 12) {
            new DialogSetting(this).show();
        }
    }
    private void requestDefault() {

        try {
            Intent intent = new Intent("android.telecom.action.CHANGE_DEFAULT_DIALER");
            intent.putExtra("android.telecom.extra.CHANGE_DEFAULT_DIALER_PACKAGE_NAME", getPackageName());
            startActivityForResult(intent, 123);
        } catch (ActivityNotFoundException unused) {
            new DialogSetting(this).show();
        }
    }


}
