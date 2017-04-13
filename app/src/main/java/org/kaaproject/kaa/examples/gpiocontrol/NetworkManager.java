package org.kaaproject.kaa.examples.gpiocontrol;

import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.kaaproject.kaa.examples.gpiocontrol.Network.GPIOSlaveSettings;
import org.kaaproject.kaa.examples.gpiocontrol.Network.LockEntry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES;

public class NetworkManager {

    private static final String HOST = "10.2.2.85";
    private static final String PORT = "8080";
    private static final String HTTP = "http://";
    private static final String KAA_ADMIN_REST_API = "/kaaAdmin/rest/api/";
    private static final String UPDATE_SP_URL = HTTP + HOST + ":" + PORT + KAA_ADMIN_REST_API + "updateServerProfile";
    private static final String GET_SP_URL = HTTP + HOST + ":" + PORT + KAA_ADMIN_REST_API + "endpointProfileBody";
    private static final String EP_KEY_HASH = "p3J6sYU0EAl9G9rdNpTD6MjBesY=";

    private final static ObjectMapper MAPPER = new ObjectMapper().configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static final String TAG = NetworkManager.class.getSimpleName();

    public static void toggleLock(final long id, final String password) {
        Thread thread = new Thread(new Runnable() {
            @Override public void run() {
                GPIOSlaveSettings currentProfile;
                try {
                    currentProfile = getServerSideProfile();
                    LockEntry existingLock = findExistingLock(id, currentProfile);

                    if (null != existingLock) {

                        if (!existingLock.getLockPassword().equals(password)) {
                            throw new IllegalArgumentException(String.format("The entered password for the lock with id=[%s] is wrong.", id));
                        }
                        unlockDevice(existingLock, currentProfile);
                    } else {
                        lockDevice(new LockEntry(id, password), currentProfile);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private static LockEntry findExistingLock(final long id, GPIOSlaveSettings currentProfile) {
        for (LockEntry lockEntry : currentProfile.getLockSettings()) {
            if (lockEntry.getId() == id) {
                return lockEntry;
            }
        }
        return null;
    }

    private static void lockDevice(LockEntry lock, GPIOSlaveSettings currentProfile) throws IOException {
        currentProfile.getLockSettings().add(lock);
        updateSettings(currentProfile);
        Log.d(TAG, "The lock for id= "+ lock.getId() + " has been set.");
    }

    private static void unlockDevice(LockEntry lock, GPIOSlaveSettings currentProfile) throws IOException {
        currentProfile.getLockSettings().remove(lock);
        updateSettings(currentProfile);
        System.out.println(String.format("The lock for id=[%s] has been removed.", lock.getId()));
    }

    private static void updateSettings(GPIOSlaveSettings settings) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(UPDATE_SP_URL);
        post.setHeader("Authorization", "Basic ZGV2dXNlcjpkZXZ1c2VyMTIz");

        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("endpointProfileKey", EP_KEY_HASH));
        urlParameters.add(new BasicNameValuePair("version", "1"));
        urlParameters.add(new BasicNameValuePair("serverProfileBody", settings.toString()));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));

        HttpResponse response = client.execute(post);
        Log.d(TAG, "\nSending 'POST' request to URL : " + UPDATE_SP_URL);
        Log.d(TAG, "Post parameters : " + post.getEntity());
        Log.d(TAG, "Response Code : " + response.getStatusLine().getStatusCode());
    }

    private static GPIOSlaveSettings getServerSideProfile() throws IOException {
        HttpClient client = new DefaultHttpClient();
        String uri = GET_SP_URL + "/" + EP_KEY_HASH;
        HttpGet httpGet = new HttpGet(uri);
        httpGet.setHeader("Authorization", "Basic ZGV2dXNlcjpkZXZ1c2VyMTIz");
        HttpResponse response = client.execute(httpGet);

        Log.d(TAG, "Response Code : " + response.getStatusLine().getStatusCode());

        String jsonResp = IOUtils.toString(response.getEntity().getContent());
        String jsonRead1 = jsonResp.replaceAll("\\\\", "");
        String jsonRead2 = jsonRead1.replaceAll("\"\\{", "{");
        String res = jsonRead2.replace("LockSettings", "lockSettings");
        Log.d(TAG, "getServerSideProfile: "+res);

        return MAPPER.readValue(res, EndpointProfileBody.class).getServerSideProfile();
    }

}
