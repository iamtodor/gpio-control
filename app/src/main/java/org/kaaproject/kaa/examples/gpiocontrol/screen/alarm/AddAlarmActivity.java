package org.kaaproject.kaa.examples.gpiocontrol.screen.alarm;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.Alarm;
import org.kaaproject.kaa.examples.gpiocontrol.screen.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddAlarmActivity extends BaseActivity {

    @BindView(R.id.toolbar) protected Toolbar toolbar;
    @BindView(R.id.time_picker) protected TextView timePicker;
    @BindView(R.id.action) protected RadioGroup actionRadioGroup;
    @BindView(R.id.repeat) protected CheckBox repeat;
    @BindView(R.id.alarm_name) protected EditText alarmName;

    @BindViews({R.id.sunday, R.id.monday, R.id.tuesday, R.id.wednesday,
            R.id.thursday, R.id.friday, R.id.saturday}) CheckBox[] weekDays;

    @BindView(R.id.repeat_options) protected LinearLayout repeatOptions;
    private List<String> alarmDays = new ArrayList<>();

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
                    alarmDays.clear();
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
        RadioButton selectedActionRadioButton = (RadioButton) actionRadioGroup.findViewById(actionRadioGroup.getCheckedRadioButtonId());
        String action = selectedActionRadioButton.getTag().toString();

        if (repeat.isChecked()) {
            for (CheckBox day : weekDays) {
                if (day.isChecked()) {
                    alarmDays.add(day.getTag().toString());
                }
            }
        }

        if (TextUtils.isEmpty(alarmName.getText().toString())) {
            alarmName.setError(getString(R.string.edit_text_cant_be_empty_error));
        }

        Alarm alarm = new Alarm(timePicker.getText().toString(), action, alarmName.getText().toString(),
                alarmDays.toString(), true);
    }

    @OnClick(R.id.cancel)
    public void cancelOnClick() {
        onBackPressed();
    }
}
