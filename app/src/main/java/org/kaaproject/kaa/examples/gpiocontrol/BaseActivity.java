package org.kaaproject.kaa.examples.gpiocontrol;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import gpiocontol.examples.kaa.kaaproject.org.gpiocontrol.R;

public class BaseActivity extends AppCompatActivity {

    protected void showFragment(Fragment fragment) {
        showFragment(fragment, null);
    }

    protected void showFragment(Fragment fragment, String backStackTag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);

        if (backStackTag != null) {
            transaction.addToBackStack(backStackTag);
        }

        transaction.commit();
    }

}
