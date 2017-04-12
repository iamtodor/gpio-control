package org.kaaproject.kaa.examples.gpiocontrol;

import org.kaaproject.kaa.examples.gpiocontrol.Network.GPIOSlaveSettings;

public class EndpointProfileBody {

    private GPIOSlaveSettings serverSideProfile;

    @Override
    public String toString() {
        return "EndpointProfileBody{" +
                "serverSideProfile=" + serverSideProfile +
                '}';
    }

    public GPIOSlaveSettings getServerSideProfile() {
        return serverSideProfile;
    }

    public void setServerSideProfile(GPIOSlaveSettings serverSideProfile) {
        this.serverSideProfile = serverSideProfile;
    }

}
