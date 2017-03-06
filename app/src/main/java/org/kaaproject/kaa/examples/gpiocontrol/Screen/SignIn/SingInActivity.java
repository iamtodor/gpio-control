package org.kaaproject.kaa.examples.gpiocontrol.Screen.SignIn;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.kaaproject.kaa.examples.gpiocontrol.BaseActivity;
import org.kaaproject.kaa.examples.gpiocontrol.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SingInActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final int SIGN_IN = 777;
    private GoogleApiClient mGoogleApiClient;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sing_in_activity);
        ButterKnife.bind(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @OnClick(R.id.sign_in_button)
    public void singInClick() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, SIGN_IN);
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                // Get account information
                String mFullName = acct.getDisplayName();
                String mEmail = acct.getEmail();
                Log.d(TAG, "onActivityResult: " + mFullName + " " + mEmail);
            }
        }
    }

    @Override public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
