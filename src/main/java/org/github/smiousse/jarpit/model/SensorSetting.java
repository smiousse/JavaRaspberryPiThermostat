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
    private int gpioPin;

    /**
     * 
     */
    public SensorSetting() {
        super();
    }

    /**
     * @param sensorType
     * @param tempSensorModel
     * @param deviceId
     * @param identifier
     * @param name
     */
    public SensorSetting(SensorType sensorType, TempSensorModel tempSensorModel, String deviceId, String identifier, String name) {
        super();
        this.sensorType = sensorType;
        this.tempSensorModel = tempSensorModel;
        this.deviceId = deviceId;
        this.identifier = identifier;
        this.name = name;
    }

    /**
     * @param sensorType
     * @param humiditySensorModel
     * @param identifier
     * @param name
     * @param gpioPin
     */
    public SensorSetting(SensorType sensorType, HumiditySensorModel humiditySensorModel, String identifier, String name, int gpioPin) {
        super();
        this.sensorType = sensorType;
        this.humiditySensorModel = humiditySensorModel;
        this.identifier = identifier;
        this.name = name;
        this.gpioPin = gpioPin;
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

    /**
     * @return the gpioPin
     */
    public int getGpioPin() {
        return gpioPin;
    }

    /**
     * @param gpioPin
     * the gpioPin to set
     */
    public void setGpioPin(int gpioPin) {
        this.gpioPin = gpioPin;
    }

}
