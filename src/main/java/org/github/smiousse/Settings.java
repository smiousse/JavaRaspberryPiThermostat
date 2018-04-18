package org.github.smiousse;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

import org.github.smiousse.raspberrypi.hvac.ClimateSetting;
import org.github.smiousse.raspberrypi.hvac.HvacControllerSetting;
import org.github.smiousse.raspberrypi.sensors.SensorSetting;

public class Settings {

    private ClimateSetting climateSetting;
    private HvacControllerSetting hvacControllerSetting;

    private List<SensorSetting> sensorSettings;

    /**
     * @return the climateSetting
     */
    public ClimateSetting getClimateSetting() {
        return climateSetting;
    }

    /**
     * @param climateSetting
     * the climateSetting to set
     */
    public void setClimateSetting(ClimateSetting climateSetting) {
        this.climateSetting = climateSetting;
    }

    /**
     * @return the hvacControllerSetting
     */
    public HvacControllerSetting getHvacControllerSetting() {
        return hvacControllerSetting;
    }

    /**
     * @param hvacControllerSetting
     * the hvacControllerSetting to set
     */
    public void setHvacControllerSetting(HvacControllerSetting hvacControllerSetting) {
        this.hvacControllerSetting = hvacControllerSetting;
    }

    /**
     * @return the sensorSettings
     */
    public List<SensorSetting> getSensorSettings() {
        return sensorSettings;
    }

    /**
     * @param sensorSettings
     * the sensorSettings to set
     */
    public void setSensorSettings(List<SensorSetting> sensorSettings) {
        this.sensorSettings = sensorSettings;
    }

    /**
     * @param name
     * @param pinNumber
     */
    @Transient
    public void addSensor(String name, int pinNumber) {
        if (this.sensorSettings == null) {
            this.sensorSettings = new ArrayList<SensorSetting>();
        }

        this.sensorSettings.add(new SensorSetting(name, pinNumber));

    }

}
