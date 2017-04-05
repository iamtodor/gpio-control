package org.kaaproject.kaa.examples.gpiocontrol;


import android.content.Context;
import android.util.Log;

import org.kaaproject.kaa.client.AndroidKaaPlatformContext;
import org.kaaproject.kaa.client.Kaa;
import org.kaaproject.kaa.client.KaaClient;
import org.kaaproject.kaa.client.event.registration.UserAttachCallback;
import org.kaaproject.kaa.common.endpoint.gen.SyncResponseResultType;
import org.kaaproject.kaa.common.endpoint.gen.UserAttachResponse;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class KaaManager {

    private static final String TAG = KaaManager.class.getSimpleName();
    private KaaClient kaaClient;
    private RemoteControlECF remoteControlECF;

    private KaaManager() {
    }

    public static KaaManager newInstance(Context context) throws IOException {
        return new KaaManager()
                .initKaaClient(context)
                .initRemoteControl()
                .initListeners();
    }

    private KaaManager initKaaClient(Context context) throws IOException {
        kaaClient = Kaa.newClient(new AndroidKaaPlatformContext(context));

        kaaClient.start();
        return this;
    }

    private KaaManager initRemoteControl() {
        remoteControlECF = kaaClient.getEventFamilyFactory().getRemoteControlECF();
        return this;
    }

    private KaaManager initListeners() {
        remoteControlECF.addListener(new RemoteControlECF.Listener() {
            @Override public void onEvent(DeviceInfoResponse deviceInfoResponse, String s) {

            }
        });
        return this;
    }

    public KaaManager attachToUser(final String userId, String userAccessToken) {
        final CountDownLatch attachLatch = new CountDownLatch(1);
        kaaClient.attachUser(userId, userAccessToken, new UserAttachCallback() {
            @Override public void onAttachResult(UserAttachResponse userAttachResponse) {
                //            log.info("Attach to user result: {}", response.getResult());
                if (userAttachResponse.getResult() == SyncResponseResultType.SUCCESS) {
                    Log.d(TAG, "Current endpoint have been successfully attached to user [ID=" + userId + "]!");
                } else {
                    Log.e(TAG, "Attaching current endpoint to user [ID=" + userId + "] FAILED.");
                    Log.e(TAG, "Attach response: " + userAttachResponse);
                    Log.e(TAG, "GenericEventType exchange will be NOT POSSIBLE.");
                }
                attachLatch.countDown();
            }
        });
        try {
            attachLatch.await();
        } catch (InterruptedException e) {
            Log.w(TAG, "Thread interrupted when wait for attach current endpoint to user", e);
        }
        return this;
    }

    public void sendRemoteControlEvent(DeviceInfoRequest event) {
        remoteControlECF.sendEventToAll(event);
    }

    public void sendRemoteControlEvent(GpioToggleRequest event) {
        remoteControlECF.sendEventToAll(event);
    }

}
