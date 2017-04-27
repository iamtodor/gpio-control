package org.kaaproject.kaa.examples.gpiocontrol.network;


import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

class GPIOSlaveSettings {

    @JsonProperty("LockSettings")
    private List<LockEntry> LockSettings = new ArrayList<>();

    public GPIOSlaveSettings() {
    }

    public GPIOSlaveSettings(List<LockEntry> lockSettings) {
        LockSettings = lockSettings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GPIOSlaveSettings that = (GPIOSlaveSettings) o;

        return LockSettings != null ? LockSettings.equals(that.LockSettings) : that.LockSettings == null;
    }

    @Override
    public int hashCode() {
        return LockSettings != null ? LockSettings.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "GPIOSlaveSettings{" +
                "LockSettings=" + LockSettings +
                '}';
    }

    public List<LockEntry> getLockSettings() {
        return LockSettings;
    }

    public void setLockSettings(List<LockEntry> lockSettings) {
        LockSettings = lockSettings;
    }
}
