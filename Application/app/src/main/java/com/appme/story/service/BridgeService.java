package com.appme.story.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import android.support.annotation.Nullable;

import com.appme.story.application.BridgeActivity;
import com.appme.story.engine.app.analytics.permission.bridge.IBridge;
import com.appme.story.engine.app.analytics.permission.source.Source;
import com.appme.story.engine.app.analytics.permission.source.ContextSource;

public class BridgeService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mStub.asBinder();
    }

    private IBridge.Stub mStub = new IBridge.Stub() {

        private Source mSource = new ContextSource(BridgeService.this);

        @Override
        public void requestAppDetails(String suffix) throws RemoteException {
            BridgeActivity.requestAppDetails(mSource, suffix);
        }

        @Override
        public void requestPermission(String suffix, String[] permissions) throws RemoteException {
            BridgeActivity.requestPermission(mSource, suffix, permissions);
        }

        @Override
        public void requestInstall(String suffix) throws RemoteException {
            BridgeActivity.requestInstall(mSource, suffix);
        }

        @Override
        public void requestOverlay(String suffix) throws RemoteException {
            BridgeActivity.requestOverlay(mSource, suffix);
        }

        @Override
        public void requestAlertWindow(String suffix) throws RemoteException {
            BridgeActivity.requestAlertWindow(mSource, suffix);
        }

        @Override
        public void requestNotify(String suffix) throws RemoteException {
            BridgeActivity.requestNotify(mSource, suffix);
        }

        @Override
        public void requestNotificationListener(String suffix) throws RemoteException {
            BridgeActivity.requestNotificationListener(mSource, suffix);
        }

        @Override
        public void requestWriteSetting(String suffix) throws RemoteException {
            BridgeActivity.requestWriteSetting(mSource, suffix);
        }
    };
}
