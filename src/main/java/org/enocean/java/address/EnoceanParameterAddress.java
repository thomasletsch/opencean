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

    public EnoceanId getEnoceanDeviceId() {
        return deviceId;
    }

    @Override
    public String toString() {
        return "EnoceanParameter: " + getAsString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((deviceId == null) ? 0 : deviceId.hashCode());
        result = prime * result + ((parameterId == null) ? 0 : parameterId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        EnoceanParameterAddress other = (EnoceanParameterAddress) obj;
        if (deviceId == null) {
            if (other.deviceId != null) {
                return false;
            }
        } else if (!deviceId.equals(other.deviceId)) {
            return false;
        }
        if (parameterId == null) {
            if (other.parameterId != null) {
                return false;
            }
        } else if (!parameterId.equals(other.parameterId)) {
            return false;
        }
        return true;
    }

}
