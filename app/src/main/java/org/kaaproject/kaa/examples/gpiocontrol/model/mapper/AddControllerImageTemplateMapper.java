package org.kaaproject.kaa.examples.gpiocontrol.model.mapper;


import org.kaaproject.kaa.examples.gpiocontrol.R;

public class AddControllerImageTemplateMapper {

    public enum DeviceItem {
        NO_SELECTED(R.id.no_image_selected, R.drawable.no_image_selected),
        FLAT_TV(R.id.flat_tv, R.drawable.flat_tv),
        MICROWAVE(R.id.microwave, R.drawable.microwave),
        KITCHEN(R.id.kitchen, R.drawable.kitchen),
        GAS_STOVE(R.id.gas_stove, R.drawable.gas_stove),
        COND(R.id.cond, R.drawable.cond),
        LAMP(R.id.lamp, R.drawable.lamp),
        TABLE_LAMP(R.id.table_lamp, R.drawable.table_lamp),
        FAN(R.id.fan, R.drawable.fan),
        REFRIGERATOR(R.id.refrigerator, R.drawable.refrigerator),
        WASH(R.id.wash, R.drawable.wash),
        WINDOW(R.id.window, R.drawable.window);

        private int viewId;
        private int drawableId;

        DeviceItem(int viewId, int drawableId) {
            this.viewId = viewId;
            this.drawableId = drawableId;
        }

        public int getViewId() {
            return viewId;
        }

        public int getDrawableId() {
            return drawableId;
        }

        public static DeviceItem getDeviceItemById(int viewId) {
            for (DeviceItem deviceItem : DeviceItem.values()) {
                if (viewId == deviceItem.getViewId()) {
                    return deviceItem;
                }
            }
            throw  new RuntimeException("DeviceItem was not found");
        }
    }
}


