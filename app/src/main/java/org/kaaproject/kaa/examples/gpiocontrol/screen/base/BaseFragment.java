package org.kaaproject.kaa.examples.gpiocontrol.screen.base;


import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import com.squareup.leakcanary.RefWatcher;

import org.kaaproject.kaa.examples.gpiocontrol.App;

public class BaseFragment extends Fragment {

    protected final String TAG = this.getClass().getSimpleName();

    protected BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    protected ActionBar getSupportActionBar() {
        return getBaseActivity().getSupportActionBar();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        RefWatcher refWatcher = App.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

}
