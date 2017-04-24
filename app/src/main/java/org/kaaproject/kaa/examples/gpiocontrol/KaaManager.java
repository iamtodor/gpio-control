package org.kaaproject.kaa.examples.gpiocontrol;


import android.content.Context;
import android.util.Log;

import org.kaaproject.kaa.client.AndroidKaaPlatformContext;
import org.kaaproject.kaa.client.Kaa;
import org.kaaproject.kaa.client.KaaClient;
import org.kaaproject.kaa.client.SimpleKaaClientStateListener;
import org.kaaproject.kaa.client.event.EndpointAccessToken;
import org.kaaproject.kaa.client.event.EndpointKeyHash;
import org.kaaproject.kaa.client.event.FindEventListenersCallback;
import org.kaaproject.kaa.client.event.registration.OnAttachEndpointOperationCallback;
import org.kaaproject.kaa.client.event.registration.UserAttachCallback;
import org.kaaproject.kaa.common.endpoint.gen.SyncResponseResultType;
import org.kaaproject.kaa.common.endpoint.gen.UserAttachResponse;
import org.kaaproject.kaa.examples.gpiocontrol.model.Device;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KaaManager {

    private static final String TAG = KaaManager.class.getSimpleName();
    private KaaClient kaaClient;
    private RemoteControlECF remoteControlECF;
    private final List<RemoteControlECF.Listener> listenerList = new ArrayList<>();

    private KaaManager() {
    }

    public static KaaManager newInstance(Context context) throws IOException {
        return new KaaManager()
                .initKaaClient(context)
                .initRemoteControl()
                .initListeners();
    }

    private KaaManager initKaaClient(Context context) throws IOException {
        kaaClient = Kaa.newClient(new AndroidKaaPlatformContext(context), new SimpleKaaClientStateListener() {
            @Override public void onStarted() {
                super.onStarted();
                Log.d(TAG, "Kaa started");

                Log.d(TAG, "Attaching user...");

                attachUser("userId");
            }
        }, true);
        kaaClient.start();
        return this;
    }

    private void attachUser(String userId) {
        kaaClient.attachUser(userId,
                kaaClient.getEndpointAccessToken(), new UserAttachCallback() {
                    @Override
                    public void onAttachResult(UserAttachResponse response) {
                        Log.d(TAG, "User attach result: " + response.toString());

                        if (response.getResult() == SyncResponseResultType.SUCCESS) {
                            kaaClient.findEventListeners(
                                    Collections.singletonList("org.kaaproject.kaa.examples.gpiocontrol.DeviceInfoResponse"),
                                    new FindEventListenersCallback() {
                                        @Override
                                        public void onEventListenersReceived(List<String> eventListeners) {
                                            Log.d(TAG, "onEventListenersReceived: " + eventListeners);
                                        }

                                        @Override
                                        public void onRequestFailed() {
                                            Log.d(TAG, "onEventListenersReceived: failed");
                                        }
                                    });
                        }
                    }
                });
    }

    private KaaManager initRemoteControl() {
        remoteControlECF = kaaClient.getEventFamilyFactory().getRemoteControlECF();
        return this;
    }

    private KaaManager initListeners() {
        remoteControlECF.addListener(new RemoteControlECF.Listener() {
            @Override public void onEvent(DeviceInfoResponse event, String source) {
                Log.d(TAG, "!!! onEvent: " + event + ", " + source);

                for (RemoteControlECF.Listener listener : listenerList) {
                    listener.onEvent(event, source);
                }
            }
        });
        return this;
    }

    public void sendRemoteControlEvent(DeviceInfoRequest event) {
        remoteControlECF.sendEventToAll(event);
    }

    public void sendRemoteControlEvent(GpioToggleRequest event) {
        remoteControlECF.sendEventToAll(event);
    }

    public void sendGpioToggleRequest(GpioToggleRequest gpioToggleRequest, String kaaEndpointId) {
        remoteControlECF.sendEvent(gpioToggleRequest, kaaEndpointId);
    }

    public void attachEndpoint(String endpoint, final OnAttachEndpointOperationCallback onAttach) {
        kaaClient.attachEndpoint(new EndpointAccessToken(endpoint), new OnAttachEndpointOperationCallback() {
            @Override
            public void onAttach(SyncResponseResultType result, EndpointKeyHash resultContext) {
                Log.d(TAG, "attachEndpoint result: " + result.toString() + ", endpoint hash:" + resultContext.toString());

                onAttach.onAttach(result, resultContext);
            }
        });
    }

    public void sendDeviceInfoRequestToAll() {
        remoteControlECF.sendEventToAll(new DeviceInfoRequest());
    }

    public void addEventListener(RemoteControlECF.Listener callback) {
        listenerList.add(callback);
    }

    public void removeEventListener(RemoteControlECF.Listener callback) {
        listenerList.remove(callback);
    }

    public void turnOnDevice(Device device, boolean turnOn) {
        GpioStatus gpioStatus = device.getGpioStatus();
        gpioStatus.setStatus(turnOn);
        sendGpioToggleRequest(new GpioToggleRequest(gpioStatus),
                device.getEndpointId());
    }
}
