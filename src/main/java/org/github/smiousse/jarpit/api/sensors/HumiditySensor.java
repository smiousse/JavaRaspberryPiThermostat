package org.github.smiousse.jarpit.api.sensors;

import java.math.BigDecimal;

import org.github.smiousse.jarpit.model.SensorSetting.HumiditySensorModel;

public interface HumiditySensor {

    /**
     * @return
     */
    public BigDecimal getHumidity();

    /**
     * @return
     */
    public String getInfo();

    /**
     * @return
     */
    public HumiditySensorModel getHumiditySensorModel();

    /**
     * @return
     */
    public boolean updateReadings();
}
