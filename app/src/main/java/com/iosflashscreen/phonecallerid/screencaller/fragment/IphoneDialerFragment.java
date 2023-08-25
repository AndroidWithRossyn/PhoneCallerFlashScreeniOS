package com.iosflashscreen.phonecallerid.screencaller.fragment;

import static com.iosflashscreen.phonecallerid.screencaller.utils.Utility.setGradientShaderToTextView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.telecom.TelecomManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iosflashscreen.phonecallerid.screencaller.R;
import com.iosflashscreen.phonecallerid.screencaller.databinding.DefaultDialerBinding;
import com.iosflashscreen.phonecallerid.screencaller.databinding.FragmentHomeBinding;
import com.iosflashscreen.phonecallerid.screencaller.databinding.FragmentIphoneDialerBinding;
import com.iosflashscreen.phonecallerid.screencaller.databinding.TakePhotoBinding;

import jp.wasabeef.blurry.Blurry;

public class IphoneDialerFragment extends Fragment {
    FragmentIphoneDialerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentIphoneDialerBinding.inflate(inflater, container, false);
        setGradientShaderToTextView(binding.inputNumberET, requireActivity().getColor(R.color.primary3), requireActivity().getColor(R.color.secondary3));
        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentText = binding.inputNumberET.getText().toString();
                if (!currentText.isEmpty()) {
                    String newText = currentText.substring(0, currentText.length() - 1);
                    binding.inputNumberET.setText(newText);
                }
            }
        });
        View.OnClickListener digitClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentText = binding.inputNumberET.getText().toString();
                String digit = view.getTag().toString();
                String newText = currentText + digit;
                binding.inputNumberET.setText(newText);
            }
        };
        binding.callPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputNumber = binding.inputNumberET.getText().toString();

                if (!inputNumber.isEmpty()) {
                    initiateCall(inputNumber);
                } else {
                    Toast.makeText(getContext(), "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                }
            }
        });


        binding.btn01.setOnClickListener(digitClickListener);
        binding.btn02.setOnClickListener(digitClickListener);
        binding.btn03.setOnClickListener(digitClickListener);
        binding.btn04.setOnClickListener(digitClickListener);
        binding.btn05.setOnClickListener(digitClickListener);
        binding.btn06.setOnClickListener(digitClickListener);
        binding.btn07.setOnClickListener(digitClickListener);
        binding.btn08.setOnClickListener(digitClickListener);
        binding.btn09.setOnClickListener(digitClickListener);
        binding.btn0.setOnClickListener(digitClickListener);
        binding.btnStar.setOnClickListener(digitClickListener);
        binding.btnHash.setOnClickListener(digitClickListener);
        return binding.getRoot();
    }
    private void initiateCall(String phoneNumber) {
        Context context = getContext();
        if (context != null) {
            TelecomManager telecomManager = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
            Uri uri = Uri.fromParts("tel", phoneNumber, null);
            Bundle extras = new Bundle();
            extras.putBoolean(TelecomManager.EXTRA_START_CALL_WITH_SPEAKERPHONE, false);

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                if (telecomManager != null && telecomManager.getDefaultDialerPackage().equals(context.getPackageName())) {
                    telecomManager.placeCall(uri, extras);
                } else {
                    Uri phoneNumberUri = Uri.parse("tel:" + phoneNumber);
                    Intent callIntent = new Intent(Intent.ACTION_CALL, phoneNumberUri);
                    callIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(callIntent);
                }
            } else {
                Toast.makeText(context, "Please allow permission", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void openCloseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        DefaultDialerBinding bind = DefaultDialerBinding.inflate(LayoutInflater.from(requireActivity()));
        builder.setView(bind.getRoot());
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setGradientShaderToTextView(bind.setIphone, requireActivity().getColor(R.color.primary3), requireActivity().getColor(R.color.secondary3));
        View rootView = requireActivity().getWindow().getDecorView().getRootView();
        Blurry.with(requireActivity()).radius(10).sampling(8).animate(500).onto((ViewGroup) rootView);

        dialog.setOnDismissListener(dialogInterface -> {
            Blurry.delete((ViewGroup) rootView);
        });
        dialog.show();
        bind.btnBack.setOnClickListener(v -> {
            dialog.dismiss();

        });
        bind.btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }
}