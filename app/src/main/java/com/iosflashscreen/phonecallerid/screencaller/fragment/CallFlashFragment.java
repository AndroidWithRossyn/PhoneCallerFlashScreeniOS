package com.iosflashscreen.phonecallerid.screencaller.fragment;

import static com.iosflashscreen.phonecallerid.screencaller.utils.Utility.setGradientShaderToTextView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.slider.Slider;
import com.iosflashscreen.phonecallerid.screencaller.R;
import com.iosflashscreen.phonecallerid.screencaller.databinding.FlashBackgroundBinding;
import com.iosflashscreen.phonecallerid.screencaller.databinding.FragmentCallFlashBinding;
import com.iosflashscreen.phonecallerid.screencaller.service.FlashlightService;
import com.iosflashscreen.phonecallerid.screencaller.utils.CameraHelper;

import java.util.Timer;
import java.util.TimerTask;

import jp.wasabeef.blurry.Blurry;

public class CallFlashFragment extends Fragment {
    FragmentCallFlashBinding binding;
    private CameraHelper helper;
    private SharedPreferences sharedPreferences,defaultPreferences;

    private boolean isStroboscopeActive = false;
    private Timer stroboscopeTimer = new Timer();
    private int stroboscopeIntervalMillis = 500;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCallFlashBinding.inflate(inflater, container, false);
        defaultPreferences = PreferenceManager.getDefaultSharedPreferences(requireActivity());
        setGradientShaderToTextView(binding.callFlash, requireActivity().getColor(R.color.primary2), requireActivity().getColor(R.color.secondary2));
        setGradientShaderToTextView(binding.sos, requireActivity().getColor(R.color.primary2), requireActivity().getColor(R.color.secondary2));
        sharedPreferences = requireContext().getSharedPreferences("flash1", Context.MODE_PRIVATE);
        binding.checkboxStatus.setVisibility(View.VISIBLE);
        binding.powerIcon.setVisibility(View.VISIBLE);

        binding.infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCloseDialog();
            }
        });
        binding.checkboxStatus.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked && binding.checkboxStatus.isChecked()) {
                binding.powerIcon.setVisibility(View.GONE);
                binding.checkboxStatus.setBackgroundResource(R.drawable.switch_on);
                Intent serviceIntent = new Intent(requireActivity(), FlashlightService.class);
                requireActivity().startService(serviceIntent);
                helper.toggleStroboscope(requireActivity());

            } else {
                binding.powerIcon.setVisibility(View.VISIBLE);
                binding.checkboxStatus.setBackgroundResource(R.drawable.ic_power_center);
                Intent serviceIntent = new Intent(requireActivity(), FlashlightService.class);
                requireActivity().stopService(serviceIntent);
            }
        });
        binding.stroboscopeIntervalSlider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(Slider slider, float value, boolean fromUser) {
                stroboscopeIntervalMillis = (int) (value * 1000);
                toggleStroboscope(isStroboscopeActive); // Update the stroboscope interval
            }
        });



        sharedPreferences = requireContext().getSharedPreferences("flash1", Context.MODE_PRIVATE);
        helper = CameraHelper.getInstance(requireContext());


        return binding.getRoot();
    }



    @Override
    public void onResume() {
        stroboscopeIntervalMillis = (int) (binding.stroboscopeIntervalSlider.getValue() * 1000);

        toggleStroboscope(isStroboscopeActive);
        boolean isCheckboxStatusChecked = sharedPreferences.getBoolean("checkboxStatusChecked", false);

        binding.checkboxStatus.setChecked(isCheckboxStatusChecked);
        binding.checkboxStatus.setVisibility(View.VISIBLE);
        super.onResume();
    }
    private void toggleStroboscope(boolean isActive) {
        if (isActive) {
            if (stroboscopeTimer != null) {
                stroboscopeTimer.cancel();
            }

            stroboscopeTimer = new Timer();

            stroboscopeTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    helper.toggleStroboscope(requireActivity());
                }
            }, 0, stroboscopeIntervalMillis);
        } else {
            stroboscopeTimer.cancel();
            helper.turnOffFlashlight();
        }
    }

    private void openCloseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        FlashBackgroundBinding bind = FlashBackgroundBinding.inflate(LayoutInflater.from(requireActivity()));
        builder.setView(bind.getRoot());
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setGradientShaderToTextView(bind.addPhoto, requireActivity().getColor(R.color.primary2), requireActivity().getColor(R.color.secondary2));
        View rootView = requireActivity().getWindow().getDecorView().getRootView();
        Blurry.with(requireActivity()).radius(10).sampling(8).animate(500).onto((ViewGroup) rootView);

        dialog.setOnDismissListener(dialogInterface -> {
            Blurry.delete((ViewGroup) rootView);
        });
        dialog.show();
        bind.statusIncomingSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                Intent serviceIntent = new Intent(requireActivity(), FlashlightService.class);
                requireActivity().startService(serviceIntent);
            } else {
                Intent serviceIntent = new Intent(requireActivity(), FlashlightService.class);
                requireActivity().stopService(serviceIntent);
            }

        });

    }
    @Override
    public void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("checkboxStatusChecked", binding.checkboxStatus.isChecked());
        editor.apply();

        SharedPreferences.Editor editor1 = defaultPreferences.edit();
        editor1.putFloat("stroboscope_interval", binding.stroboscopeIntervalSlider.getValue());
        editor1.apply();
    }
}
