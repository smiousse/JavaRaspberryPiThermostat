package org.github.smiousse.jarpit.model;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

public class Settings {

    private ClimateSetting climateSetting;
    private HvacControllerSetting hvacControllerSetting;

    private String masterOutsideTempSensorIdentifier;
    private String masterMainFloorTempSensorIdentifier;
    private String masterBasementTempSensorIdentifier;

    private String masterOutsideHumiditySensorIdentifier;
    private String masterInsideHumiditySensorIdentifier;

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
     * @return the masterMainFloorTempSensorIdentifier
     */
    public String getMasterMainFloorTempSensorIdentifier() {
        return masterMainFloorTempSensorIdentifier;
    }

    /**
     * @param masterMainFloorTempSensorIdentifier
     * the masterMainFloorTempSensorIdentifier to set
     */
    public void setMasterMainFloorTempSensorIdentifier(String masterMainFloorTempSensorIdentifier) {
        this.masterMainFloorTempSensorIdentifier = masterMainFloorTempSensorIdentifier;
    }

    /**
     * @return the masterBasementTempSensorIdentifier
     */
    public String getMasterBasementTempSensorIdentifier() {
        return masterBasementTempSensorIdentifier;
    }

    /**
     * @param masterBasementTempSensorIdentifier
     * the masterBasementTempSensorIdentifier to set
     */
    public void setMasterBasementTempSensorIdentifier(String masterBasementTempSensorIdentifier) {
        this.masterBasementTempSensorIdentifier = masterBasementTempSensorIdentifier;
    }

    /**
     * @return the masterOutsideHumiditySensorIdentifier
     */
    public String getMasterOutsideHumiditySensorIdentifier() {
        return masterOutsideHumiditySensorIdentifier;
    }

    /**
     * @param masterOutsideHumiditySensorIdentifier
     * the masterOutsideHumiditySensorIdentifier to set
     */
    public void setMasterOutsideHumiditySensorIdentifier(String masterOutsideHumiditySensorIdentifier) {
        this.masterOutsideHumiditySensorIdentifier = masterOutsideHumiditySensorIdentifier;
    }

    /**
     * @return the masterInsideHumiditySensorIdentifier
     */
    public String getMasterInsideHumiditySensorIdentifier() {
        return masterInsideHumiditySensorIdentifier;
    }

    /**
     * @param masterInsideHumiditySensorIdentifier
     * the masterInsideHumiditySensorIdentifier to set
     */
    public void setMasterInsideHumiditySensorIdentifier(String masterInsideHumiditySensorIdentifier) {
        this.masterInsideHumiditySensorIdentifier = masterInsideHumiditySensorIdentifier;
    }

}
