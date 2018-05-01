package org.github.smiousse.jarpit.api.sensors;

import java.math.BigDecimal;

import org.github.smiousse.jarpit.model.SensorSetting.HumiditySensorModel;

/**
 * @author smiousse
 *
 */
public interface HumiditySensor extends Sensor {

    /**
     * @return
     */
    public BigDecimal getHumidity();

    /**
     * @return
     */
    public HumiditySensorModel getHumiditySensorModel();

}
