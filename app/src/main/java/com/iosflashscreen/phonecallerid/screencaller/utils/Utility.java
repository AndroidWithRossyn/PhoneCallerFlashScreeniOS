package com.iosflashscreen.phonecallerid.screencaller.utils;


import static com.iosflashscreen.phonecallerid.screencaller.singletonClass.AppOpenAds.currentActivity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/*import com.adsmodule.api.AdsModule.AdUtils;
import com.adsmodule.api.AdsModule.Utils.Constants;*/
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;
import com.iosflashscreen.phonecallerid.screencaller.BuildConfig;
import com.iosflashscreen.phonecallerid.screencaller.R;
import com.iosflashscreen.phonecallerid.screencaller.singletonClass.MyApplication;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Utility {

    public static final String MALE = "Male";
    public static final String FEMALE = "Female";
    public static final String PrivacyLink = "https://analogdreamsmedias.blogspot.com/p/privacy-policy.html";


    public static void setFullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsetsController insetsController = activity.getWindow().getInsetsController();
            if (insetsController != null) {
                insetsController.hide(WindowInsets.Type.statusBars());
            }
        } else {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

   /* public static void readJsonFromRaw(Context context) {

        try {
            Resources resources = context.getResources();
            InputStream inputStream = resources.openRawResource(R.raw.mobilecodes);
            Scanner scanner = new Scanner(inputStream);

            StringBuilder stringBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
            }

            JSONArray jsonArray = new JSONArray(stringBuilder.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);
                CodesModel model = new CodesModel(object.getInt("ID"),
                        object.getString("code"),
                        object.getString("codeDesc"),
                        object.getString("brand"));
                AsyncTask.execute(() -> {
                        getCodesDao().insertData(model);
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    public static List<String> getGenders() {
        List<String> list = new ArrayList<>();

        list.add(MALE);
        list.add(FEMALE);

        return list;
    }

    public static void setGradientShaderToTextView(TextView textView, int firstColor, int secondColor) {
        Paint paint = textView.getPaint();
        int[] colors = {firstColor, secondColor};
        float width = paint.measureText(textView.getText().toString());
        Shader textShader = new LinearGradient(0f, 1f, width, textView.getTextSize(), colors, null, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(textShader);
    }

    public static void setGradientMainShaderToTextView(TextView textView) {
        Paint paint = textView.getPaint();
        int[] colors = new int[0];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            colors = new int[]{textView.getContext().getColor(R.color.secondary), textView.getContext().getColor(R.color.primary)};
        }
        float width = paint.measureText(textView.getText().toString());
        Shader textShader = new LinearGradient(0f, 1f, width, textView.getTextSize(), colors, null, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(textShader);
    }

    public static long convertToMilliseconds(String time) {
        int milliSeconds = 1000;
        if (time.contains("Sec")) return (long) Integer.parseInt(time.split(" ")[0]) * milliSeconds;
        return (long) Integer.parseInt(time.split(" ")[0]) * 60 * milliSeconds;
    }

    public static void nextActivity(Activity activity, Class<?> className) {
       /* AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
            activity.startActivity(new Intent(currentActivity, className));
        });*/
    }
    public static void showRateApp() {
        ReviewManager reviewManager = ReviewManagerFactory.create(currentActivity);
        Task<ReviewInfo> request = reviewManager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ReviewInfo reviewInfo = task.getResult();

                Task <Void> flow = reviewManager.launchReviewFlow(currentActivity, reviewInfo);
                flow.addOnCompleteListener(task1 -> {
                });
            }
        });
    }


    public static void nextActivity(Activity activity, Class<?> className, String time) {
      /*  MyApplication.getPreferences().setMilliSeconds(convertToMilliseconds(time));
        AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
            activity.startActivity(new Intent(activity, className).putExtra("time", time));
        });*/
    }


    public static void nextFinishActivity(Activity activity, Class<?> className) {
     /*   AdUtils.showInterstitialAd(Constants.adsResponseModel.getInterstitial_ads().getAdx(), activity, isLoaded -> {
            activity.startActivity(new Intent(activity, className).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            activity.finish();
        });*/
    }

    public static void finishActivity(Activity activity) {
      /*  AdUtils.showBackPressAds(activity, Constants.adsResponseModel.getApp_open_ads().getAdx(), isLoaded -> {
            activity.finish();
        });*/
    }

    public static String createTime(int duration) {
        String time = "", minute = "", secs = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;

        if (min < 10) minute = "0" + min;
        else minute = "" + min;
        if (sec < 10) secs = "0" + sec;
        else secs = "" + sec;
        time = minute + ":" + secs;
        return time;
    }

    public static void copy(Context context, String str) {
        ((ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("label", str));
        Toast.makeText(context, "Text copied to clipboard.", Toast.LENGTH_SHORT).show();
    }


    public static void openMapsApp(Context context, String str) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + str));
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e("openMapsApp: ", e.getLocalizedMessage());
            Toast.makeText(context, "Please install Google Map.", Toast.LENGTH_SHORT).show();
//            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.google.android.apps.maps")));
        }
    }

    public static boolean isCreditCardValid(String creditCardNumber) {
        String cleanedNumber = creditCardNumber.replaceAll("[ -]", "");
        StringBuilder reversedNumber = new StringBuilder(cleanedNumber).reverse();
        int sum = 0;
        boolean alternate = false;

        for (int i = 0; i < reversedNumber.length(); i++) {
            int digit = Character.getNumericValue(reversedNumber.charAt(i));
            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit = digit % 10 + 1;
                }
            }
            sum += digit;
            alternate = !alternate;
        }

        return sum % 10 == 0;
    }

    public static String checkCardType(String creditCardNumber) {
        String amexPattern = "^3[47][0-9]{13}$";
        String visaPattern = "^4[0-9]{12}(?:[0-9]{3})?$";
        String mastercardPattern = "^5[1-5][0-9]{14}$";
        String discoverPattern = "^6(?:011|5[0-9]{2})[0-9]{12}$";

        String cardType;
        if (creditCardNumber.matches(amexPattern)) {
            cardType = "American Express";
        } else if (creditCardNumber.matches(visaPattern)) {
            cardType = "Visa";
        } else if (creditCardNumber.matches(mastercardPattern)) {
            cardType = "Mastercard";
        } else if (creditCardNumber.matches(discoverPattern)) {
            cardType = "Discover";
        } else {
            cardType = "Unknown";
        }

        return cardType;
    }

    public static LiveData<List<String>> getAllVideosFromUri(Context context) {
        MutableLiveData<List<String>> data = new MutableLiveData<>();
        List<String> videoPaths = new ArrayList<>();

        String[] projection = {MediaStore.Video.Media.BUCKET_ID, MediaStore.Video.Media.BUCKET_DISPLAY_NAME, MediaStore.Video.Media.DATE_TAKEN, MediaStore.Video.Media.DATA};
        String sortOrder = MediaStore.Video.Media.DATE_MODIFIED + " DESC";

        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, sortOrder);

        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            while (cursor.moveToNext()) {
                String videoPath = cursor.getString(columnIndex);
                videoPaths.add(videoPath);
            }
            cursor.close();
            Log.e("getAllVideosFromUri: ", videoPaths.size() + " ");
            AsyncTask.execute(() -> data.postValue(videoPaths));
        }

        return data;
    }

    public static LiveData<List<String>> getAllVideosFromFolder(Context context) {
        MutableLiveData<List<String>> data = new MutableLiveData<>();
        List<String> videoPaths = new ArrayList<>();

        // Define the columns you want to retrieve
        String[] projection = {MediaStore.Video.Media.DATA};

        // Define the selection query
        String folderPath = "/storage/emulated/0/Download/All Video Downloader";

        String selection = MediaStore.Video.Media.DATA + " like ?";
        String[] selectionArgs = new String[]{"%" + folderPath + "%"};

        // Query the media store for videos matching the folder path
        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, null);

        if (cursor != null) {

            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);

            while (cursor.moveToNext()) {
                String videoPath = cursor.getString(columnIndex);
                videoPaths.add(videoPath);
            }

            cursor.close();
            AsyncTask.execute(() -> data.postValue(videoPaths));

        }

        return data;
    }

    public static LiveData<List<String>> getAllImagesFromFolder() {
        MutableLiveData<List<String>> data = new MutableLiveData<>();
        List<String> imagePaths = new ArrayList<>();

        String folderPath = "/storage/emulated/0/Download/"/* + directoryInstaShoryDirectorydownload_images*/;
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && isImageFile(file.getName())) {
                        String imagePath = file.getAbsolutePath();
                        imagePaths.add(imagePath);
                    }
                }
            }
        }

        String sfolder = "/storage/emulated/0"/* + SAVE_FOLDER_NAME*/;
        File files = new File(sfolder);
        if (files.exists() && files.isDirectory()) {
            File[] listFiles = files.listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    if (file.isFile() && isImageFile(file.getName())) {
                        String imagePath = file.getAbsolutePath();
                        imagePaths.add(imagePath);
                    }
                }
            }
        }
        AsyncTask.execute(() -> data.postValue(imagePaths));

        return data;
    }

    private static boolean isImageFile(String fileName) {
        String extension = getFileExtension(fileName);
        return extension != null && (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("gif") || extension.equalsIgnoreCase("bmp"));
    }

    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex != -1 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return null;
    }

//    public static ShimmerDrawable getShimmer() {
//        Shimmer shimmer = new Shimmer.AlphaHighlightBuilder().setDuration(1200) // how long the shimmering animation takes to do one full sweep
//                .setBaseAlpha(0.7f) //the alpha of the underlying children
//                .setHighlightAlpha(0.9f) // the shimmer alpha amount
//                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT).setShape(Shimmer.Shape.LINEAR).setAutoStart(true).build();
//        ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
//        shimmerDrawable.setShimmer(shimmer);
//
//        return shimmerDrawable;
//    }


    public static String maskString(String input) {
        if (input == null || input.length() < 4) {
            return input;
        }

        StringBuilder maskedString = new StringBuilder(input.length());

        maskedString.append(input.substring(0, 2));
        for (int i = 2; i < input.length() - 2; i++) {
            maskedString.append("*");
        }


        maskedString.append(input.substring(input.length() - 2));

        return maskedString.toString();
    }

   /* public static void openCloseDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        DialogExitBinding binding = DialogExitBinding.inflate(LayoutInflater.from(currentActivity));
        builder.setView(binding.getRoot());
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        AdUtils.showNativeAd(activity, Constants.adsResponseModel.getNative_ads().getAdx(), binding.adsView, 1, null);

        binding.btnExit.setOnClickListener(v -> {
            dialog.dismiss();
            activity.finishAffinity();
        });
        binding.btnRate.setOnClickListener(v -> {
            dialog.dismiss();
            final String appPackageName = activity.getPackageName();
            try {
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (ActivityNotFoundException e) {
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        });
        binding.btnClose.setOnClickListener(v -> dialog.dismiss());

    }
*/
 /*   public static void shareApp() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_SUBJECT, currentActivity.getString(R.string.app_name) + " App Invitation");
        intent.putExtra(Intent.EXTRA_TEXT, currentActivity.getString(R.string.first_onboard) + "\nDownload now to stay secure!" + "\nhttps://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
        intent.setType("text/plain");
        currentActivity.startActivity(intent);
    }*/


/*
    public static void setAppleCodesData() {

        List<CodesModel> list = new ArrayList<>();

        // iPhone
        list.add(new CodesModel("*67", "Hide Caller ID", "Apple"));
        list.add(new CodesModel("*225#", "Check Postpaid Cellular Balance", "Apple"));
        list.add(new CodesModel("*777#", "Check Prepaid Cellular Balance", "Apple"));
        list.add(new CodesModel("*43#", "Enable Call Waiting Status", "Apple"));
        list.add(new CodesModel("#43#", "Disable Call Waiting Status", "Apple"));
        list.add(new CodesModel("*#06#", "Check Your iPhone's IMEI number", "Apple"));
        list.add(new CodesModel("#61#", "Check Missed Calls", "Apple"));
        list.add(new CodesModel("*#07#", "Check Legal and Regulatory Details", "Apple"));
        list.add(new CodesModel("*#30#", "Check Call Line Presentation", "Apple"));
        list.add(new CodesModel("*3001#12345#*", "Check Your iPhone's Signal", "Apple"));
        list.add(new CodesModel("*5005*25371#", "Check whether the alert system is working or not", "Apple"));
        list.add(new CodesModel("*5005*25370#", "To disable the alert system", "Apple"));




    }
*/
}

/*0*/
/*1*/
/*2*/
/*3*/
/*4*/
/*5*/
/*6*/
/*7*/
/*8*/
/*9*/