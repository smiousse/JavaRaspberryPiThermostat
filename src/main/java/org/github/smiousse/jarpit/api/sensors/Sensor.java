package org.github.smiousse.jarpit.api.sensors;

import org.github.smiousse.jarpit.model.SensorSetting;

public interface Sensor {

    /**
     * @return
     */
    public SensorSetting getSensorSetting();

    /**
     * @return
     */
    public boolean updateReadings();

}
