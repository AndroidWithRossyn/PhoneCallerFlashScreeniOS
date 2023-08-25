package com.iosflashscreen.phonecallerid.screencaller.singletonClass;

/*import static com.adsmodule.api.AdsModule.Retrofit.APICallHandler.callAppCountApi;
import static com.adsmodule.api.AdsModule.Utils.Constants.MAIN_BASE_URL;*/

import android.app.Application;

import com.iosflashscreen.phonecallerid.screencaller.utils.SharePreferences;
/*
import com.adsmodule.api.AdsModule.PreferencesManager.AppPreferences;
import com.adsmodule.api.AdsModule.Retrofit.AdsDataRequestModel;
import com.adsmodule.api.AdsModule.Utils.ConnectionDetector;
import com.adsmodule.api.AdsModule.Utils.Global;*/



public class MyApplication extends Application {

    static SharePreferences preferences;
//    static ConnectionDetector connectionDetector;
    private static MyApplication app;

    public static SharePreferences getPreferences() {
        if (preferences == null) preferences = new SharePreferences(app);
        return preferences;
    }


    public static synchronized MyApplication getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
      /*  AppPreferences preferences = new AppPreferences(app);
        if (preferences.isFirstRun()) {
            callAppCountApi(MAIN_BASE_URL, new AdsDataRequestModel(app.getPackageName(), Global.getDeviceId(app)), () -> {
                preferences.setFirstRun(false);
            });
        }

        new AppOpenAds(app);*/

    }


}
