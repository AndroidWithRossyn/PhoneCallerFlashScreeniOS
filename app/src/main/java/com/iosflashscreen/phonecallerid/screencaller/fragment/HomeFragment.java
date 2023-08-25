package com.iosflashscreen.phonecallerid.screencaller.fragment;

import static com.iosflashscreen.phonecallerid.screencaller.utils.Utility.setGradientShaderToTextView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.iosflashscreen.phonecallerid.screencaller.R;
import com.iosflashscreen.phonecallerid.screencaller.adapter.GalleryAdapter;
import com.iosflashscreen.phonecallerid.screencaller.databinding.FragmentHomeBinding;
import com.iosflashscreen.phonecallerid.screencaller.databinding.TakePhotoBinding;
import com.iosflashscreen.phonecallerid.screencaller.fragment.CallScreen.LiveThemeFragment;
import com.iosflashscreen.phonecallerid.screencaller.fragment.CallScreen.ThemeFragment;
import com.iosflashscreen.phonecallerid.screencaller.fragment.CallScreen.WallpaperFragment;
import com.iosflashscreen.phonecallerid.screencaller.model.Images;
import com.iosflashscreen.phonecallerid.screencaller.ui.CameraActivity;
import com.iosflashscreen.phonecallerid.screencaller.ui.DashboardActivity;
import com.iosflashscreen.phonecallerid.screencaller.ui.FavouritesActivity;
import com.iosflashscreen.phonecallerid.screencaller.ui.GalleryActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import jp.wasabeef.blurry.Blurry;


public class HomeFragment extends Fragment {
    private static final int REQUEST_CODE_GALLERY = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 1001;

    private static final int PICK_IMAGE_REQUEST = 1;
    FragmentHomeBinding binding;

    private ArrayList<ThemeOptionsModel> tabListThemesType;
    private ArrayList<String> selectedImagePaths = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        setGradientShaderToTextView(binding.callScreen, requireActivity().getColor(R.color.primary), requireActivity().getColor(R.color.secondary));
        binding.menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireActivity(), DashboardActivity.class));
            }
        });
        binding.first.setVisibility(View.VISIBLE);
        binding.second.setVisibility(View.GONE);
        binding.backBtn.setVisibility(View.GONE);
        binding.favourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireActivity(), FavouritesActivity.class));
            }
        });
        binding.callerScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.first.setVisibility(View.GONE);
                binding.second.setVisibility(View.VISIBLE);
                binding.menuBtn.setVisibility(View.GONE);
                binding.backBtn.setVisibility(View.VISIBLE);
                binding.viewPagerThemesOption.setUserInputEnabled(false);
                tabListThemesType = new ArrayList<>();
                tabListThemesType.add(new ThemeOptionsModel("Theme"));
                tabListThemesType.add(new ThemeOptionsModel("Live Theme"));
                tabListThemesType.add(new ThemeOptionsModel("Wallpaper"));


                TabLayout tabLayoutThemesOption = binding.tabLayoutThemesOption;

                for (ThemeOptionsModel themeOptionsModel : tabListThemesType) {
                    TabLayout.Tab tab = tabLayoutThemesOption.newTab();
                    tab.setText(themeOptionsModel.getName());
                    tabLayoutThemesOption.addTab(tab);
                }

                ViewPagerAdapter adapter = new ViewPagerAdapter(tabListThemesType.size());
                binding.viewPagerThemesOption.setAdapter(adapter);
                binding.viewPagerThemesOption.setOffscreenPageLimit(4);

                TabLayoutMediator.TabConfigurationStrategy tabConfigurationStrategy = (tab, position) -> {
                    tab.setText(tabListThemesType.get(position).getName());
                };

                new TabLayoutMediator(tabLayoutThemesOption, binding.viewPagerThemesOption, tabConfigurationStrategy).attach();
            }
        });


        binding.customCaller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCloseDialog();
                binding.first.setVisibility(View.VISIBLE);
                binding.second.setVisibility(View.GONE);
                binding.menuBtn.setVisibility(View.VISIBLE);
                binding.backBtn.setVisibility(View.GONE);

            }
        });
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.backBtn.setVisibility(View.GONE);
                binding.menuBtn.setVisibility(View.VISIBLE);
                binding.second.setVisibility(View.GONE);
                binding.first.setVisibility(View.VISIBLE);
            }
        });
        return binding.getRoot();
    }

    private void openCloseDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        TakePhotoBinding bind = TakePhotoBinding.inflate(LayoutInflater.from(requireActivity()));
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
        bind.chooseFromGallery.setOnClickListener(v -> {
            dialog.dismiss();
            openGallery();

        });
        bind.takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                openCamera();
            }
        });

    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    private void openCamera() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            launchCameraActivity();
        }
    }

    private void launchCameraActivity() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchCameraActivity();
            } else {
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            String imagePath = imageUri.toString();
            selectedImagePaths.add(imagePath);

            Intent galleryIntent = new Intent(requireContext(), GalleryActivity.class);
            galleryIntent.putStringArrayListExtra("selected_image_paths", selectedImagePaths);
            startActivity(galleryIntent);
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            Uri imageUri = getImageUri(requireContext(), imageBitmap);
            Intent galleryIntent = new Intent(requireContext(), GalleryActivity.class);
            galleryIntent.putExtra("captured_image_uri", imageUri.toString());
            startActivity(galleryIntent);
        }
    }


    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Image", null);
        return Uri.parse(path);

    }


    private static class ThemeOptionsModel {
        private final String name;

        public ThemeOptionsModel(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private class ViewPagerAdapter extends FragmentStateAdapter {
        private final int totalTabs;

        public ViewPagerAdapter(int totalTabs) {
            super(HomeFragment.this);
            this.totalTabs = totalTabs;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new ThemeFragment();
                case 1:
                    return new LiveThemeFragment();
                default:
                    return new WallpaperFragment();
            }
        }

        @Override
        public int getItemCount() {
            return totalTabs;
        }
    }
}