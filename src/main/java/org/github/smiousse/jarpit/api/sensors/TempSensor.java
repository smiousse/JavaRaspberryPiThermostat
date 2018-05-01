package org.github.smiousse.jarpit.api.sensors;

import java.math.BigDecimal;

import org.github.smiousse.jarpit.model.SensorSetting.TempSensorModel;

/**
 * @author smiousse
 *
 */
public interface TempSensor extends Sensor {

    /**
     * @return
     */
    public BigDecimal getTemperature();

    /**
     * @return
     */
    public TempSensorModel getTempSensorModel();

}
