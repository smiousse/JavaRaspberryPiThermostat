package org.github.smiousse.jarpit.model;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

public class Settings {

    private ClimateSetting climateSetting;
    private HvacControllerSetting hvacControllerSetting;

    private String masterOutsideTempSensorIdentifier;
    private String masterInsideTempSensorIdentifier;

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
     * @param sensorSetting
     */
    @Transient
    public void addSensor(SensorSetting sensorSetting) {
        if (this.sensorSettings == null) {
            this.sensorSettings = new ArrayList<SensorSetting>();
        }

        this.sensorSettings.add(sensorSetting);

    }

    /**
     * @return the masterOutsideTempSensorIdentifier
     */
    public String getMasterOutsideTempSensorIdentifier() {
        return masterOutsideTempSensorIdentifier;
    }

    /**
     * @param masterOutsideTempSensorIdentifier
     * the masterOutsideTempSensorIdentifier to set
     */
    public void setMasterOutsideTempSensorIdentifier(String masterOutsideTempSensorIdentifier) {
        this.masterOutsideTempSensorIdentifier = masterOutsideTempSensorIdentifier;
    }

    /**
     * @return the masterInsideTempSensorIdentifier
     */
    public String getMasterInsideTempSensorIdentifier() {
        return masterInsideTempSensorIdentifier;
    }

    /**
     * @param masterInsideTempSensorIdentifier
     * the masterInsideTempSensorIdentifier to set
     */
    public void setMasterInsideTempSensorIdentifier(String masterInsideTempSensorIdentifier) {
        this.masterInsideTempSensorIdentifier = masterInsideTempSensorIdentifier;
    }

}
