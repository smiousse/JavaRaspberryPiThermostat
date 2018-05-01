package org.github.smiousse.jarpit.raspberrypi.sensors;

import org.github.smiousse.jarpit.api.sensors.Sensor;
import org.github.smiousse.jarpit.model.SensorSetting;

/**
 * @author smiousse
 *
 */
public abstract class AbstractSensor implements Sensor {

    private SensorSetting sensorSetting;

    /**
     * @param sensorSetting
     */
    public AbstractSensor(SensorSetting sensorSetting) {
        super();
        this.sensorSetting = sensorSetting;
    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.github.smiousse.jarpit.api.sensors.Sensor#getSensorSetting()
     */
    @Override
    public SensorSetting getSensorSetting() {
        // TSLT Auto-generated method stub
        return sensorSetting;
    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.github.smiousse.jarpit.api.sensors.Sensor#updateReadings()
     */
    @Override
    public boolean updateReadings() {
        return true;
    }

}
