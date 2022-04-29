package com.appme.story;

import android.app.Application;
import android.view.View;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.singhajit.sherlock.core.Sherlock;
import com.singhajit.sherlock.core.investigation.AppInfo;
import com.singhajit.sherlock.core.investigation.AppInfoProvider;
import com.singhajit.sherlock.core.SherlockNotInitializedException;
import com.singhajit.sherlock.util.AppInfoUtil;


import com.appme.story.engine.widget.AlertWindow;
import com.appme.story.engine.widget.LauncherView;

public class AppController extends Application
{
    private static AppController _instance;

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
        try {

            Sherlock.init(this); //Initializing Sherlock
            Sherlock.setAppInfoProvider(new AppInfoProvider() {
                    @Override
                    public AppInfo getAppInfo() {
                        return new AppInfo.Builder()
                            .with("Version", AppInfoUtil.getAppVersion(getApplicationContext())) //You can get the actual version using "AppInfoUtil.getAppVersion(context)"
                            .with("BuildNumber", "1")
                            .build();
                    }
                });

        } catch (SherlockNotInitializedException e) {
            e.printStackTrace();
        } 
    }

    public static synchronized AppController get() {
        return _instance;
    }

    public void showLauncherView() {
        final AlertWindow alertWindow = new AlertWindow(this);
        LauncherView view = new LauncherView(this);
        view.setCancelClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertWindow.dismiss();
                }
            });
        alertWindow.setContentView(view);
        alertWindow.show();
    }
    
}
