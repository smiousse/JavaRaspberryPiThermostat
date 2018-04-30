package org.github.smiousse.jarpit.model;

public class SensorSetting {

    public enum SensorType {
        TEMPERATURE, HUMIDITY, PRESSURE
    }

    public enum TempSensorModel {
        DHT11, DS18B20, DS18B20_WP
    }

    public enum HumiditySensorModel {
        DHT11
    }

    private SensorType sensorType;
    private TempSensorModel tempSensorModel;
    private HumiditySensorModel humiditySensorModel;

    private String deviceId;
    private String identifier;
    private String name;

    /**
     * 
     */
    public SensorSetting() {
        super();
    }

    /**
     * @param name
     * @param gpioPinNumber
     */
    public SensorSetting(String name, String deviceId) {
        super();
        this.name = name;
        this.deviceId = deviceId;
        this.identifier = deviceId;
    }

    /**
     * @return the deviceId
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId
     * the deviceId to set
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     * the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier
     * the identifier to set
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * @return the sensorType
     */
    public SensorType getSensorType() {
        return sensorType;
    }

    /**
     * @param sensorType
     * the sensorType to set
     */
    public void setSensorType(SensorType sensorType) {
        this.sensorType = sensorType;
    }

    /**
     * @return the tempSensorModel
     */
    public TempSensorModel getTempSensorModel() {
        return tempSensorModel;
    }

    /**
     * @param tempSensorModel
     * the tempSensorModel to set
     */
    public void setTempSensorModel(TempSensorModel tempSensorModel) {
        this.tempSensorModel = tempSensorModel;
    }

    /**
     * @return the humiditySensorModel
     */
    public HumiditySensorModel getHumiditySensorModel() {
        return humiditySensorModel;
    }

    /**
     * @param humiditySensorModel
     * the humiditySensorModel to set
     */
    public void setHumiditySensorModel(HumiditySensorModel humiditySensorModel) {
        this.humiditySensorModel = humiditySensorModel;
    }

}
