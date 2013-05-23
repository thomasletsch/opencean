package org.enocean.java.address;

import org.enocean.java.common.ParameterAddress;

public class EnoceanParameterAddress implements ParameterAddress {

    private EnoceanId deviceId;
    private String parameterId;

    public EnoceanParameterAddress(EnoceanId enoceanId, String parameterId) {
        this.deviceId = enoceanId;
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

    @Override
    public String getDeviceId() {
        return deviceId.toString();
    }

    @Override
    public String toString() {
        return "EnoceanParameter: " + getAsString();
    }

}
