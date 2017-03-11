package org.kaaproject.kaa.examples.gpiocontrol.screen.alarm;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.Alarm;
import org.kaaproject.kaa.examples.gpiocontrol.screen.base.BaseActivity;
import org.kaaproject.kaa.examples.gpiocontrol.utils.DialogFactory;
import org.kaaproject.kaa.examples.gpiocontrol.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlarmListActivity extends BaseActivity {

    @BindView(R.id.toolbar) protected Toolbar toolbar;
    @BindView(R.id.recycler_view) protected RecyclerView recyclerView;
    @BindView(R.id.no_device_message) protected TextView noDeviceMessage;
    @BindView(R.id.fab) protected FloatingActionButton fab;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_list_activity);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Alarm settings");
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(itemAnimator);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 && fab.isShown()) {
                    fab.hide();
                } else if (dy < 0 && !fab.isShown()) {
                    fab.show();
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        List<Alarm> groupPinList = Utils.getMockedAlarmList();

        AlarmAdapter pinManagementAdapter = new AlarmAdapter(groupPinList);
        recyclerView.setAdapter(pinManagementAdapter);

        if (groupPinList.isEmpty()) {
            showNoDevices();
        } else {
            showDevices();
        }
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        DialogFactory.showAddDeviceDialog(this);
    }

    private void showNoDevices() {
        recyclerView.setVisibility(View.GONE);
        noDeviceMessage.setVisibility(View.VISIBLE);
    }

    private void showDevices() {
        recyclerView.setVisibility(View.VISIBLE);
        noDeviceMessage.setVisibility(View.GONE);
    }

}
