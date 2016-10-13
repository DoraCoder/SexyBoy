package com.example.administrator.sexyboy;

import android.app.Application;

/**
 * Created by Administrator on 2016/10/13.
 */

public class SexyBoyApplication extends Application {
    public static final String TAG = "SexyBoyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        NetworkManager.init(getApplicationContext());
    }
}
