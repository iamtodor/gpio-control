package org.kaaproject.kaa.examples.gpiocontrol.screen.base;


import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {

    protected final String TAG = this.getClass().getSimpleName();

    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

}
