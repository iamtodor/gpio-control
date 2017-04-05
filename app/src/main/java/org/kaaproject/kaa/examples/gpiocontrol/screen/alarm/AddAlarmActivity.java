package org.kaaproject.kaa.examples.gpiocontrol.screen.alarm;


import android.app.TimePickerDialog;
import android.content.DialogInterface;
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

import org.kaaproject.kaa.examples.gpiocontrol.App;
import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.Alarm;
import org.kaaproject.kaa.examples.gpiocontrol.screen.base.BaseActivity;
import org.kaaproject.kaa.examples.gpiocontrol.storage.Repository;
import org.kaaproject.kaa.examples.gpiocontrol.utils.DialogFactory;
import org.kaaproject.kaa.examples.gpiocontrol.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static org.kaaproject.kaa.examples.gpiocontrol.screen.alarm.AlarmListActivity.GROUP_ID;

public class AddAlarmActivity extends BaseActivity {

    private static final String TIME = "Time";
    private static final String LIST_ID = "idList";

    @BindView(R.id.repeat_options) protected LinearLayout repeatOptions;
    @BindView(R.id.toolbar) protected Toolbar toolbar;
    @BindView(R.id.time_picker) protected TextView timePicker;
    @BindView(R.id.action) protected RadioGroup actionRadioGroup;
    @BindView(R.id.repeat) protected CheckBox repeat;
    @BindView(R.id.alarm_name) protected EditText alarmName;

    @BindViews({R.id.sunday, R.id.monday, R.id.tuesday, R.id.wednesday,
            R.id.thursday, R.id.friday, R.id.saturday}) CheckBox[] weekDays;

    private List<String> alarmDays = new ArrayList<>();
    private ArrayList<Long> listID;
    private long groupId;

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

//        listID = (ArrayList<Long>) getIntent().getExtras().get(LIST_ID);
        groupId = (long) getIntent().getExtras().get(GROUP_ID);

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
        if (!isInfoValid()) {
            return;
        }

        if (repeat.isChecked()) {
            for (CheckBox day : weekDays) {
                if (day.isChecked()) {
                    alarmDays.add(day.getTag().toString());
                }
            }
        }

        Repository repository = ((App) (getApplication())).getRealmRepository();
        Alarm alarm = new Alarm();
        alarm.setName(alarmName.getText().toString());

        String action = getAlarmAction();
        alarm.setAction(action);

        alarm.setTime(timePicker.getText().toString());

        String iteration = Utils.getIterationStringFromList(alarmDays);
        alarm.setIteration(iteration);

        alarm.setActive(true);
        alarm.setId(repository.getIdForModel(Alarm.class));

        repository.addAlarmToGroup(groupId, alarm);

        DialogFactory.getConfirmationDialog(this, getString(R.string.alarm_was_added),
                getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        setResult(RESULT_OK);
                        finish();
                    }
                }).show();
    }

    @OnClick(R.id.cancel)
    public void cancelOnClick() {
        onBackPressed();
    }

    private boolean isInfoValid() {
        if (TextUtils.isEmpty(alarmName.getText().toString())) {
            alarmName.setError(getString(R.string.edit_text_cant_be_empty_error));
            return false;
        } else if (timePicker.getText().toString().equals(TIME)) {
            timePicker.setError(getString(R.string.edit_text_cant_be_empty_error));
            return false;
        }
        return true;
    }

    private String getAlarmAction() {
        RadioButton selectedActionRadioButton = (RadioButton) actionRadioGroup.findViewById(actionRadioGroup.getCheckedRadioButtonId());
        return selectedActionRadioButton.getTag().toString();
    }

}
