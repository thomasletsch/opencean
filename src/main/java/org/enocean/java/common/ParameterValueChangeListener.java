package org.enocean.java.common;

import org.enocean.java.common.values.Value;

public interface ParameterValueChangeListener {

    void valueChanged(ParameterAddress parameterId, Value value);

}
