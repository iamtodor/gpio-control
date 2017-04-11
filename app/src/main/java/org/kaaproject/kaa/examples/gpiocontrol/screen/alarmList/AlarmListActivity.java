package org.kaaproject.kaa.examples.gpiocontrol.screen.alarmList;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.kaaproject.kaa.examples.gpiocontrol.App;
import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.Alarm;
import org.kaaproject.kaa.examples.gpiocontrol.model.Device;
import org.kaaproject.kaa.examples.gpiocontrol.model.Group;
import org.kaaproject.kaa.examples.gpiocontrol.screen.addAlarm.AddAlarmActivity;
import org.kaaproject.kaa.examples.gpiocontrol.screen.base.BaseActivity;
import org.kaaproject.kaa.examples.gpiocontrol.storage.Repository;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlarmListActivity extends BaseActivity implements TurnOnAlarmListener {

    private static final int UPDATE_ADAPTER = 1111;
    public static final String GROUP_ID = "groupId";

    @BindView(R.id.toolbar) protected Toolbar toolbar;
    @BindView(R.id.recycler_view) protected RecyclerView recyclerView;
    @BindView(R.id.no_device_message) protected TextView noDeviceMessage;
    @BindView(R.id.fab) protected FloatingActionButton fab;

    private Repository repository;
    private AlarmAdapter alarmAdapter;
    private long groupId;
    private Group group;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_list_activity);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        repository = ((App) (getApplication())).getRealmRepository();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Alarm settings");
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        groupId = (long) getIntent().getExtras().get(GROUP_ID);
        group = repository.getGroupById(groupId);

        setupRecyclerView();
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == UPDATE_ADAPTER) {
                List<Alarm> alarmList = repository.getAlarmListFromGroup(groupId);
                hideShowRecyclerView(alarmList);
                alarmAdapter.updateAdapter(alarmList);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        Intent intent = new Intent(AlarmListActivity.this, AddAlarmActivity.class);
        intent.putExtra(GROUP_ID, groupId);
        startActivityForResult(intent, UPDATE_ADAPTER);
    }

    private void setupRecyclerView() {
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(itemAnimator);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        alarmAdapter = new AlarmAdapter();
        alarmAdapter.setTurnOnAlarmListener(this);
        recyclerView.setAdapter(alarmAdapter);

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

        List<Alarm> alarmList = repository.getAlarmListFromGroup(groupId);
        alarmAdapter.updateAdapter(alarmList);

        hideShowRecyclerView(alarmList);
    }

    private void showNoDevices() {
        recyclerView.setVisibility(View.GONE);
        noDeviceMessage.setVisibility(View.VISIBLE);
    }

    private void showDevices() {
        recyclerView.setVisibility(View.VISIBLE);
        noDeviceMessage.setVisibility(View.GONE);
    }

    private void hideShowRecyclerView(List<Alarm> alarmList) {
        if (alarmList.isEmpty()) {
            showNoDevices();
        } else {
            showDevices();
        }
    }

    @Override public void isTurnOn(boolean isTurnOn) {
        for (Device device : group.getDeviceList()) {
            repository.setDeviceHasAlarm(device.getId(), isTurnOn);
        }
    }
}
