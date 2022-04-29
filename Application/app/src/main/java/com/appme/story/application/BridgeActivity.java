package com.appme.story.application;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.appme.story.engine.app.analytics.permission.overlay.setting.LSettingPage;
import com.appme.story.engine.app.analytics.permission.overlay.setting.MSettingPage;
import com.appme.story.engine.app.analytics.permission.source.ActivitySource;
import com.appme.story.engine.app.analytics.permission.source.Source;
import com.appme.story.engine.app.analytics.permission.bridge.BridgeRequest;
import com.appme.story.engine.app.analytics.permission.bridge.Messenger;

public final class BridgeActivity extends AppCompatActivity {

    private static final String KEY_TYPE = "KEY_TYPE";
    private static final String KEY_PERMISSIONS = "KEY_PERMISSIONS";
    private static final String KEY_ACTION_SUFFIX = "KEY_ACTION_SUFFIX";

    /**
     * Request for permissions.
     */
    public static void requestAppDetails(Source source, String suffix) {
        Intent intent = new Intent(source.getContext(), BridgeActivity.class);
        intent.putExtra(KEY_TYPE, BridgeRequest.TYPE_APP_DETAILS);
        intent.putExtra(KEY_ACTION_SUFFIX, suffix);
        source.startActivity(intent);
    }

    /**
     * Request for permissions.
     */
    public static void requestPermission(Source source, String suffix, String[] permissions) {
        Intent intent = new Intent(source.getContext(), BridgeActivity.class);
        intent.putExtra(KEY_TYPE, BridgeRequest.TYPE_PERMISSION);
        intent.putExtra(KEY_PERMISSIONS, permissions);
        intent.putExtra(KEY_ACTION_SUFFIX, suffix);
        source.startActivity(intent);
    }

    /**
     * Request for package install.
     */
    public static void requestInstall(Source source, String suffix) {
        Intent intent = new Intent(source.getContext(), BridgeActivity.class);
        intent.putExtra(KEY_TYPE, BridgeRequest.TYPE_INSTALL);
        intent.putExtra(KEY_ACTION_SUFFIX, suffix);
        source.startActivity(intent);
    }

    /**
     * Request for overlay.
     */
    public static void requestOverlay(Source source, String suffix) {
        Intent intent = new Intent(source.getContext(), BridgeActivity.class);
        intent.putExtra(KEY_TYPE, BridgeRequest.TYPE_OVERLAY);
        intent.putExtra(KEY_ACTION_SUFFIX, suffix);
        source.startActivity(intent);
    }

    /**
     * Request for alert window.
     */
    public static void requestAlertWindow(Source source, String suffix) {
        Intent intent = new Intent(source.getContext(), BridgeActivity.class);
        intent.putExtra(KEY_TYPE, BridgeRequest.TYPE_ALERT_WINDOW);
        intent.putExtra(KEY_ACTION_SUFFIX, suffix);
        source.startActivity(intent);
    }

    /**
     * Request for notify.
     */
    public static void requestNotify(Source source, String suffix) {
        Intent intent = new Intent(source.getContext(), BridgeActivity.class);
        intent.putExtra(KEY_TYPE, BridgeRequest.TYPE_NOTIFY);
        intent.putExtra(KEY_ACTION_SUFFIX, suffix);
        source.startActivity(intent);
    }

    /**
     * Request for notification listener.
     */
    public static void requestNotificationListener(Source source, String suffix) {
        Intent intent = new Intent(source.getContext(), BridgeActivity.class);
        intent.putExtra(KEY_TYPE, BridgeRequest.TYPE_NOTIFY_LISTENER);
        intent.putExtra(KEY_ACTION_SUFFIX, suffix);
        source.startActivity(intent);
    }

    /**
     * Request for write system setting.
     */
    public static void requestWriteSetting(Source source, String suffix) {
        Intent intent = new Intent(source.getContext(), BridgeActivity.class);
        intent.putExtra(KEY_TYPE, BridgeRequest.TYPE_WRITE_SETTING);
        intent.putExtra(KEY_ACTION_SUFFIX, suffix);
        source.startActivity(intent);
    }

    private String mActionSuffix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) return;

        Intent intent = getIntent();
        int operation = intent.getIntExtra(KEY_TYPE, -1);
        mActionSuffix = intent.getStringExtra(KEY_ACTION_SUFFIX);
        switch (operation) {
            case BridgeRequest.TYPE_APP_DETAILS: {
                    Intent appDetailsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    appDetailsIntent.setData(Uri.fromParts("package", getPackageName(), null));
                    startActivityForResult(appDetailsIntent, BridgeRequest.TYPE_APP_DETAILS);
                    break;
                }
            case BridgeRequest.TYPE_PERMISSION: {
                    String[] permissions = intent.getStringArrayExtra(KEY_PERMISSIONS);
                    requestPermissions(permissions, BridgeRequest.TYPE_PERMISSION);
                    break;
                }
            case BridgeRequest.TYPE_INSTALL: {
                    Intent manageIntent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                    manageIntent.setData(Uri.fromParts("package", getPackageName(), null));
                    startActivityForResult(manageIntent, BridgeRequest.TYPE_INSTALL);
                    break;
                }
            case BridgeRequest.TYPE_OVERLAY: {
                    MSettingPage settingPage = new MSettingPage(new ActivitySource(this));
                    settingPage.start(BridgeRequest.TYPE_OVERLAY);
                    break;
                }
            case BridgeRequest.TYPE_ALERT_WINDOW: {
                    LSettingPage settingPage = new LSettingPage(new ActivitySource(this));
                    settingPage.start(BridgeRequest.TYPE_ALERT_WINDOW);
                    break;
                }
            case BridgeRequest.TYPE_NOTIFY: {
                    Intent settingIntent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                    settingIntent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                    settingIntent.setData(Uri.fromParts("package", getPackageName(), null));
                    startActivityForResult(settingIntent, BridgeRequest.TYPE_NOTIFY);
                    break;
                }
            case BridgeRequest.TYPE_NOTIFY_LISTENER: {
                    Intent settingIntent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
                    settingIntent.setData(Uri.fromParts("package", getPackageName(), null));
                    startActivityForResult(settingIntent, BridgeRequest.TYPE_NOTIFY_LISTENER);
                    break;
                }
            case BridgeRequest.TYPE_WRITE_SETTING: {
                    Intent settingIntent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    settingIntent.setData(Uri.fromParts("package", getPackageName(), null));
                    startActivityForResult(settingIntent, BridgeRequest.TYPE_WRITE_SETTING);
                    break;
                }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Messenger.send(this, mActionSuffix);
        finish();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Messenger.send(this, mActionSuffix);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
