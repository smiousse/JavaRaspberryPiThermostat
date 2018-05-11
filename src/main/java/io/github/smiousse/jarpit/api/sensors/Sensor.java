package io.github.smiousse.jarpit.api.sensors;

import io.github.smiousse.jarpit.model.SensorSetting;

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
