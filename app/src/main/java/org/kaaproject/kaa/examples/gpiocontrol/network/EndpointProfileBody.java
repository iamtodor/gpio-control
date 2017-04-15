package org.kaaproject.kaa.examples.gpiocontrol.network;

class EndpointProfileBody {

    private GPIOSlaveSettings serverSideProfile;

    @Override
    public String toString() {
        return "EndpointProfileBody{" +
                "serverSideProfile=" + serverSideProfile +
                '}';
    }

    GPIOSlaveSettings getServerSideProfile() {
        return serverSideProfile;
    }

    public void setServerSideProfile(GPIOSlaveSettings serverSideProfile) {
        this.serverSideProfile = serverSideProfile;
    }

}
