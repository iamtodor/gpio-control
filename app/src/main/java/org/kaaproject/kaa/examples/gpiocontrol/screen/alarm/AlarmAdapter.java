package org.kaaproject.kaa.examples.gpiocontrol.screen.alarm;


import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.Alarm;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolderPinGroupItem> {

    private LayoutInflater inflater;
    private List<Alarm> controllerList = new ArrayList<>();

    void updateAdapter(List<Alarm> colorItems) {
        controllerList.clear();
        controllerList.addAll(colorItems);
        notifyDataSetChanged();
    }

    @Override public AlarmAdapter.ViewHolderPinGroupItem onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        return ViewHolderPinGroupItem.create(inflater, parent);
    }

    @Override public void onBindViewHolder(final ViewHolderPinGroupItem holder, int position) {
        Alarm alarm = controllerList.get(position);

        holder.time.setText(alarm.getTime());
        holder.action.setText(alarm.getAction());
        holder.name.setText(alarm.getName());
        holder.iteration.setText(alarm.getIteration());
        holder.switchCompat.setChecked(alarm.isActive());
    }

    @Override public int getItemCount() {
        return controllerList.size();
    }

    static class ViewHolderPinGroupItem extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.time) TextView time;
        @BindView(R.id.action) TextView action;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.iteration) TextView iteration;
        @BindView(R.id.switch_active) SwitchCompat switchCompat;

        static ViewHolderPinGroupItem create(LayoutInflater inflater, ViewGroup parent) {
            return new ViewHolderPinGroupItem(inflater.inflate(R.layout.alarm_item, parent, false));
        }

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
