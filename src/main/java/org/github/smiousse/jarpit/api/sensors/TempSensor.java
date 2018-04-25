package org.github.smiousse.jarpit.api.sensors;

import java.math.BigDecimal;

public interface TempSensor {

    public enum TempSensorModel {
        DHT11, DS18B20, DS18B20_WP
    }

    /**
     * @return
     */
    public BigDecimal getTemperature();

    /**
     * @return
     */
    public String getInfo();

    /**
     * @return
     */
    public TempSensorModel getModel();

    /**
     * @return
     */
    public boolean updateTemperature();
}
