package org.kaaproject.kaa.examples.gpiocontrol.utils;


import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.kaaproject.kaa.examples.gpiocontrol.R;

public class DialogFactory {

    public static AlertDialog.Builder getAddDeviceDialog(Context context, View.OnClickListener groupListener,
                                                         View.OnClickListener controllerListener) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.add_device_dialog, null, false);
        dialogBuilder.setView(view);

        TextView addGroup = (TextView) view.findViewById(R.id.add_group);
        TextView addController = (TextView) view.findViewById(R.id.add_controller);

        addGroup.setOnClickListener(groupListener);
        addController.setOnClickListener(controllerListener);

        return dialogBuilder;
    }


}
