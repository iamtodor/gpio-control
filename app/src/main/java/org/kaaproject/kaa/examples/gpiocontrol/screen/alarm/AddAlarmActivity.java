package org.kaaproject.kaa.examples.gpiocontrol.screen.alarm;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.screen.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddAlarmActivity extends BaseActivity {

    @BindView(R.id.toolbar) protected Toolbar toolbar;
    @BindView(R.id.time_picker) protected TextView timePicker;
    @BindView(R.id.action) protected RadioGroup actionRadioGroup;
    @BindView(R.id.repeat) protected CheckBox repeat;
    @BindView(R.id.repeat_options) protected LinearLayout repeatOptions;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm_activity);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("New alarm");
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        repeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    repeatOptions.setVisibility(View.VISIBLE);
                } else {
                    repeatOptions.setVisibility(View.GONE);
                }
            }
        });
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

    @OnClick(R.id.time_picker)
    public void timeOnClick() {
        final TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timePicker.setText(hourOfDay + "h " + minute + "m");
            }
        }, 0, 0, true);

        timePickerDialog.show();

    }

    @OnClick(R.id.add)
    public void addAlarmOnClick() {
        String action = null;
        int actionId = actionRadioGroup.getCheckedRadioButtonId();
        switch (actionId) {
            case R.id.power_on:
                action = "Power on";
                break;
            case R.id.power_off:
                action = "Power off";
                break;
            case R.id.toggle:
                action = "Toggle";
                break;
        }

//        Alarm alarm = new Alarm()
    }
}
