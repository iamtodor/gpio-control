package org.kaaproject.kaa.examples.gpiocontrol;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gpiocontol.examples.kaa.kaaproject.org.gpiocontrol.R;

public class DeviceListFragment extends Fragment {

    @BindView(R.id.recyclerView) protected RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.device_list_fragment, container, false);
        ButterKnife.bind(this, view);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Device switch management");

        return view;
    }

    @OnClick(R.id.fab)
    public void onFabClick() {

    }
}
