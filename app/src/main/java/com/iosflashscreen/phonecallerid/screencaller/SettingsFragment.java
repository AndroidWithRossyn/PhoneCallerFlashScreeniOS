package com.iosflashscreen.phonecallerid.screencaller;

import static com.iosflashscreen.phonecallerid.screencaller.utils.Utility.setGradientShaderToTextView;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;
import com.iosflashscreen.phonecallerid.screencaller.adapter.BlockedPersonsAdapter;
import com.iosflashscreen.phonecallerid.screencaller.databinding.FragmentSettingsBinding;
import com.iosflashscreen.phonecallerid.screencaller.service.FlashlightService;

import java.util.ArrayList;
import java.util.List;
import android.Manifest;
public class SettingsFragment extends Fragment {
    FragmentSettingsBinding binding;
    private static final int READ_CONTACTS_PERMISSION_REQUEST = 1;
    private static final int CONTACT_PICKER_REQUEST = 2;

    private List<String> blockedPersonsList;
    private BlockedPersonsAdapter blockedPersonsAdapter;

    private AudioManager audioManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        setGradientShaderToTextView(binding.settings, requireActivity().getColor(R.color.primary4), requireActivity().getColor(R.color.secondary4));
        binding.callBlockAll.setVisibility(View.VISIBLE);
        binding.callManagementAll.setVisibility(View.VISIBLE);
        binding.audioManagementAll.setVisibility(View.VISIBLE);
        binding.callScreenAll.setVisibility(View.VISIBLE);
        binding.callFlashAll.setVisibility(View.VISIBLE);
        binding.iphoneDialerAll.setVisibility(View.VISIBLE);
        binding.rightArrow.setVisibility(View.VISIBLE);

        blockedPersonsList = new ArrayList<>();
        blockedPersonsAdapter = new BlockedPersonsAdapter(blockedPersonsList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        binding.personBlocked.setLayoutManager(layoutManager);
        binding.personBlocked.setAdapter(blockedPersonsAdapter);
        if (ContextCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.READ_CONTACTS},
                    READ_CONTACTS_PERMISSION_REQUEST);
        }

        binding.callBlockAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.callBlockAll.setVisibility(View.VISIBLE);
                binding.callManagementAll.setVisibility(View.GONE);
                binding.audioManagementAll.setVisibility(View.GONE);
                binding.callScreenAll.setVisibility(View.GONE);
                binding.callFlashAll.setVisibility(View.GONE);
                binding.iphoneDialerAll.setVisibility(View.GONE);
                binding.rightArrow.setVisibility(View.GONE);
                binding.blockperson.setVisibility(View.VISIBLE);
                binding.addBlock.setVisibility(View.VISIBLE);
                binding.clickAdd.setVisibility(View.VISIBLE);
                binding.blockperson.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ContextCompat.checkSelfPermission(requireActivity(),
                                Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                            openContactPicker();
                        } else {
                            ActivityCompat.requestPermissions(requireActivity(),
                                    new String[]{Manifest.permission.READ_CONTACTS},
                                    READ_CONTACTS_PERMISSION_REQUEST);
                        }
                    }
                });

            }
        });
        binding.callManagementAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.callBlockAll.setVisibility(View.GONE);
                binding.callManagementAll.setVisibility(View.VISIBLE);
                binding.audioManagementAll.setVisibility(View.GONE);
                binding.callScreenAll.setVisibility(View.GONE);
                binding.callFlashAll.setVisibility(View.GONE);
                binding.iphoneDialerAll.setVisibility(View.GONE);
                binding.callManagement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        binding.dialManagement.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        binding.audioManagementAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.callBlockAll.setVisibility(View.GONE);
                binding.callManagementAll.setVisibility(View.GONE);
                binding.audioManagementAll.setVisibility(View.VISIBLE);
                binding.callScreenAll.setVisibility(View.GONE);
                binding.callFlashAll.setVisibility(View.GONE);
                binding.iphoneDialerAll.setVisibility(View.GONE);
                binding.audioArrow.setVisibility(View.GONE);
                binding.volumeManagement.setVisibility(View.VISIBLE);
                audioManager = (AudioManager) requireContext().getSystemService(Context.AUDIO_SERVICE);
                binding.volumeSeekbarOnTime.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_RING));
                int currentDeviceVolume = audioManager.getStreamVolume(AudioManager.STREAM_RING);
                binding.volumeSeekbarOnTime.setProgress(currentDeviceVolume);

                binding.volumeSeekbarOnTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            audioManager.setStreamVolume(AudioManager.STREAM_RING, progress, 0);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });

                binding.volumeSeekbarOffTime.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
                int currentMusicVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                binding.volumeSeekbarOffTime.setProgress(currentMusicVolume);

                binding.volumeSeekbarOffTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }
                });


            }
        });
        binding.callScreenAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.callBlockAll.setVisibility(View.GONE);
                binding.callManagementAll.setVisibility(View.GONE);
                binding.audioManagementAll.setVisibility(View.GONE);
                binding.callScreenAll.setVisibility(View.VISIBLE);
                binding.callFlashAll.setVisibility(View.GONE);
                binding.iphoneDialerAll.setVisibility(View.GONE);
            }
        });
        binding.callFlashAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.callBlockAll.setVisibility(View.GONE);
                binding.callManagementAll.setVisibility(View.GONE);
                binding.audioManagementAll.setVisibility(View.GONE);
                binding.callScreenAll.setVisibility(View.GONE);
                binding.callFlashAll.setVisibility(View.VISIBLE);
                binding.iphoneDialerAll.setVisibility(View.GONE);
                binding.callFlash.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        binding.flashOnCallManagement.setVisibility(View.VISIBLE);
                        binding.statusFlash.setOnCheckedChangeListener((buttonView, isChecked) -> {

                            if (isChecked) {
                                Intent serviceIntent = new Intent(requireActivity(), FlashlightService.class);
                                requireActivity().startService(serviceIntent);
                            } else {
                                Intent serviceIntent = new Intent(requireActivity(), FlashlightService.class);
                                requireActivity().stopService(serviceIntent);
                            }

                        });
                    }
                });
            }
        });
         binding.iphoneDialerAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.callBlockAll.setVisibility(View.GONE);
                binding.callManagementAll.setVisibility(View.GONE);
                binding.audioManagementAll.setVisibility(View.GONE);
                binding.callScreenAll.setVisibility(View.GONE);
                binding.callFlashAll.setVisibility(View.GONE);
                binding.iphoneDialerAll.setVisibility(View.VISIBLE);
                binding.iphoneDialer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        binding.iphoneDialerManagement.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_CONTACTS_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                blockContact();
            } else {
                Toast.makeText(requireActivity(), "Permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void openContactPicker() {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CONTACT_PICKER_REQUEST && resultCode == requireActivity().RESULT_OK) {
            if (data != null) {
                Uri contactUri = data.getData();
                // Process the selected contact's information and add it to blockedPersonsList
                String blockedPersonName = retrieveContactName(contactUri);
                if (blockedPersonName != null) {
                    blockedPersonsList.add(blockedPersonName);
                    blockedPersonsAdapter.notifyDataSetChanged();
                }
            }
        }
    }
    private String retrieveContactName(Uri contactUri) {
        String contactName = null;
        Cursor cursor = requireActivity().getContentResolver().query(contactUri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            cursor.close();
        }
        return contactName;
    }


    private void blockContact(Uri contactUri) {
        String blockedPersonName = retrieveContactName(contactUri);

        if (blockedPersonName != null) {

            if (performBlockingAction(blockedPersonName)) {
                blockedPersonsList.add(blockedPersonName);
                blockedPersonsAdapter.notifyDataSetChanged();
                Toast.makeText(requireActivity(), "Contact blocked: " + blockedPersonName, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireActivity(), "Failed to block contact.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private boolean performBlockingAction(String contactName) {
        List<String> blockedContacts = new ArrayList<>();
        if (!blockedContacts.contains(contactName)) {
            blockedContacts.add(contactName);
            return true;
        } else {
            return false;
        }
    }


}