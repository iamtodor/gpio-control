package org.kaaproject.kaa.examples.gpiocontrol;

import android.util.Log;

import org.codehaus.jackson.map.ObjectMapper;
import org.kaaproject.kaa.examples.gpiocontrol.Network.GPIOSlaveSettings;
import org.kaaproject.kaa.examples.gpiocontrol.Network.LockEntry;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES;

public class NetworkManager {

    private static final String HTTP = "http://";
    private static final String HOST = "10.2.2.85";
    private static final String PORT = "8080";
    private static final String KAA_ADMIN_REST_API = "/kaaAdmin/rest/api/";
    private static final String UPDATE_SP_URL = HTTP + HOST + ":" + PORT + KAA_ADMIN_REST_API + "updateServerProfile";
    private static final String GET_SP_URL = HTTP + HOST + ":" + PORT + KAA_ADMIN_REST_API + "endpointProfileBody";
    private static final String EP_KEY_HASH = "p3J6sYU0EAl9G9rdNpTD6MjBesY=";
    private static final ObjectMapper MAPPER = new ObjectMapper().configure(FAIL_ON_UNKNOWN_PROPERTIES, false);

    private static final String TAG = NetworkManager.class.getSimpleName();
    private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final String ENDPOINT_PROFILE_KEY = "endpointProfileKey";
    private static final String VERSION = "version";
    private static final String SERVER_PROFILE_BODY = "serverProfileBody";
    private static final String AUTHORIZATION = "Authorization";
    private static final String TOKEN = "Basic ZGV2dXNlcjpkZXZ1c2VyMTIz";

    public static void toggleLock(final long id, final String password) {
        String uri = GET_SP_URL + "/" + EP_KEY_HASH;
        HttpUrl.Builder urlBuilder = HttpUrl.parse(uri).newBuilder();

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .addHeader(AUTHORIZATION, TOKEN)
                .url(url)
                .build();

        OK_HTTP_CLIENT.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "Response Code : " + response.code());

                String jsonResp = response.body().string();
                String jsonRead1 = jsonResp.replaceAll("\\\\", "");
                String jsonRead2 = jsonRead1.replaceAll("\"\\{", "{");
                String jsonRead3 = jsonRead2.replaceAll("\\}\"", "}");

                GPIOSlaveSettings settings = MAPPER.readValue(jsonRead3, EndpointProfileBody.class).getServerSideProfile();

                LockEntry existingLock = findExistingLock(id, settings);

                if (null != existingLock) {
                    if (!existingLock.getLockPassword().equals(password)) {
                        throw new IllegalArgumentException(String.format("The entered password for the lock with id=[%s] is wrong.", id));
                    }
                    unlockDevice(existingLock, settings);
                } else {
                    lockDevice(new LockEntry(id, password), settings);
                }
            }
        });
    }

    private static LockEntry findExistingLock(final long id, GPIOSlaveSettings currentProfile) {
        for (LockEntry lockEntry : currentProfile.getLockSettings()) {
            if (lockEntry.getId() == id) {
                return lockEntry;
            }
        }
        return null;
    }

    private static void unlockDevice(LockEntry lock, GPIOSlaveSettings currentProfile) throws IOException {
        currentProfile.getLockSettings().remove(lock);
        updateSettings(currentProfile);
        Log.d(TAG, "The lock for id= " + lock.getId() + " has been removed.");
    }

    private static void lockDevice(LockEntry lock, GPIOSlaveSettings currentProfile) throws IOException {
        currentProfile.getLockSettings().add(lock);
        updateSettings(currentProfile);
        Log.d(TAG, "The lock for id= " + lock.getId() + " has been set.");
    }

    private static void updateSettings(GPIOSlaveSettings settings) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(UPDATE_SP_URL).newBuilder();

        urlBuilder.addQueryParameter(ENDPOINT_PROFILE_KEY, EP_KEY_HASH);
        urlBuilder.addQueryParameter(VERSION, "1");
        urlBuilder.addQueryParameter(SERVER_PROFILE_BODY, MAPPER.writeValueAsString(settings));

        String url = urlBuilder.build().toString();
        RequestBody body = RequestBody.create(JSON, MAPPER.writeValueAsString(settings));

        Request request = new Request.Builder()
                .addHeader(AUTHORIZATION, TOKEN)
                .url(url)
                .post(body)
                .build();

        OK_HTTP_CLIENT.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "\nSending 'POST' request to URL : " + UPDATE_SP_URL);
                Log.d(TAG, "Post parameters : " + response.body());
                Log.d(TAG, "Response Code : " + response.code());
            }
        });
    }

}
