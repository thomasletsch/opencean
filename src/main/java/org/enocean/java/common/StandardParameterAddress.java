package org.enocean.java.common;


public class StandardParameterAddress implements ParameterAddress {

    private String deviceId;
    private String parameterId;

    public StandardParameterAddress(String deviceId, String parameterId) {
        this.deviceId = deviceId;
        this.parameterId = parameterId;
    }

    @Override
    public String getAsString() {
        return deviceId + "#" + parameterId;
    }

    @Override
    public String getParameterId() {
        return parameterId;
    }

    public String getDeviceId() {
        return deviceId;
    }

}
