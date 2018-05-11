package org.github.smiousse.jarpit.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "sensorType", "tempSensorModel", "humiditySensorModel", "deviceId", "identifier", "name", "gpioPin" })
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

    @JsonProperty("sensorType")
    private SensorType sensorType;

    @JsonProperty("tempSensorModel")
    private TempSensorModel tempSensorModel;

    @JsonProperty("humiditySensorModel")
    private HumiditySensorModel humiditySensorModel;

    @JsonProperty("deviceId")
    private String deviceId;

    @JsonProperty("identifier")
    private String identifier;

    @JsonProperty("name")
    private String name;

    @JsonProperty("gpioPin")
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

    /**
     * @return
     */
    public String hash() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.deviceId);
        sb.append(this.gpioPin);
        sb.append(this.identifier);
        sb.append(this.name);
        sb.append(this.humiditySensorModel);
        sb.append(this.sensorType);
        sb.append(this.tempSensorModel);
        return sb.toString();
    }

}
