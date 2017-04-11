package org.kaaproject.kaa.examples.gpiocontrol.screen.alarmList;


import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.Alarm;
import org.kaaproject.kaa.examples.gpiocontrol.storage.Repository;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolderPinGroupItem> {

    private List<Alarm> alarmList = new ArrayList<>();
    private TurnOnAlarmListener turnOnAlarmListener;
    private Repository repository;

    public AlarmAdapter(Repository repository) {
        this.repository = repository;
    }

    void updateAdapter(List<Alarm> alarmList) {
        this.alarmList.clear();
        this.alarmList.addAll(alarmList);
        notifyDataSetChanged();
    }

    void setTurnOnAlarmListener(TurnOnAlarmListener turnOnAlarmListener) {
        this.turnOnAlarmListener = turnOnAlarmListener;
    }

    @Override public AlarmAdapter.ViewHolderPinGroupItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_item, parent, false);
        return new ViewHolderPinGroupItem(view);
    }

    @Override public void onBindViewHolder(final ViewHolderPinGroupItem holder, int position) {
        final Alarm alarm = alarmList.get(position);

        holder.time.setText(alarm.getTime());
        holder.action.setText(alarm.getAction());
        holder.name.setText(alarm.getName());
        holder.iteration.setText(alarm.getIteration());
        holder.switchCompat.setChecked(alarm.isActive());

        holder.switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                turnOnAlarmListener.setAlarmTurnOn(isChecked);
                repository.turnOnAlarm(alarm.getId(), isChecked);
            }
        });
    }

    @Override public int getItemCount() {
        return alarmList.size();
    }

    static class ViewHolderPinGroupItem extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.time) TextView time;
        @BindView(R.id.action) TextView action;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.iteration) TextView iteration;
        @BindView(R.id.switch_active) SwitchCompat switchCompat;

        ViewHolderPinGroupItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override public void onClick(View v) {
            switchCompat.setChecked(!switchCompat.isChecked());
        }
    }

}
