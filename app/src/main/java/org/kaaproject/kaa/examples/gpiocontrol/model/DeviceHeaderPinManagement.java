package org.kaaproject.kaa.examples.gpiocontrol.model;


import java.util.List;

public class DeviceHeaderPinManagement<Controller> extends Header {

    private String name;
    private List<Controller> controllerList;
    private int id;

    public DeviceHeaderPinManagement(String name, int id, List<Controller> portList) {
        this.name = name;
        this.id = id;
        this.controllerList = portList;
    }

    @Override public String getName() {
        return name;
    }

    @Override public int childrenCount() {
        return controllerList.size();
    }

    @Override public Controller childAt(int childPosition) {
        return controllerList.get(childPosition);
    }

    @Override public List<Controller> getChildList() {
        return controllerList;
    }

    @Override public int getId() {
        return id;
    }

    @Override public int getChildSize() {
        return controllerList.size();
    }

    @Override public void cancelSelection() {
        for (Controller controllerToCastObject : controllerList) {
            org.kaaproject.kaa.examples.gpiocontrol.model.Controller controller =
                    (org.kaaproject.kaa.examples.gpiocontrol.model.Controller) controllerToCastObject;
            controller.setSelected(false);
        }
    }

    @Override public String toString() {
        return "GroupPort{" +
                "name='" + name + '\'' +
                ", controllerList=" + controllerList +
                '}';
    }
}
