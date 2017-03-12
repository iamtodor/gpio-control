package org.kaaproject.kaa.examples.gpiocontrol.screen.alarm;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.screen.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddAlarmActivity extends BaseActivity {

    @BindView(R.id.toolbar) protected Toolbar toolbar;
    @BindView(R.id.time_picker) protected TimePicker timePicker;
    @BindView(R.id.action) protected RadioGroup action;
    @BindView(R.id.iteration) protected RadioGroup iteration;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm_activity);
        ButterKnife.bind(this);

        iteration.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.custom){
                    Log.d(TAG, "onCheckedChanged: " + checkedId);
                }
            }
        });

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("New alarm");
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        timePicker.setIs24HourView(true);
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

}
