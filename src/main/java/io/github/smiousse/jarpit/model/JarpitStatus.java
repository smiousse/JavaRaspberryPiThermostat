package io.github.smiousse.jarpit.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "fanOn", "heatingCompressorOn", "heatingElementOn", "coolingCompressorOn", "sensorReadings" })
public class JarpitStatus {

    @JsonProperty("fanOn")
    private boolean fanOn = false;

    @JsonProperty("heatingCompressorOn")
    private boolean heatingCompressorOn = false;

    @JsonProperty("heatingElementOn")
    private boolean heatingElementOn = false;

    @JsonProperty("coolingCompressorOn")
    private boolean coolingCompressorOn = false;

    @JsonProperty("sensorReadings")
    private List<SensorReading> sensorReadings = new ArrayList<>();

    /**
     * 
     */
    public JarpitStatus() {
        super();
    }

    /**
     * @return the fanOn
     */
    public boolean isFanOn() {
        return fanOn;
    }

    /**
     * @param fanOn
     * the fanOn to set
     */
    public void setFanOn(boolean fanOn) {
        this.fanOn = fanOn;
    }

    /**
     * @return the heatingCompressorOn
     */
    public boolean isHeatingCompressorOn() {
        return heatingCompressorOn;
    }

    /**
     * @param heatingCompressorOn
     * the heatingCompressorOn to set
     */
    public void setHeatingCompressorOn(boolean heatingCompressorOn) {
        this.heatingCompressorOn = heatingCompressorOn;
    }

    /**
     * @return the heatingElementOn
     */
    public boolean isHeatingElementOn() {
        return heatingElementOn;
    }

    /**
     * @param heatingElementOn
     * the heatingElementOn to set
     */
    public void setHeatingElementOn(boolean heatingElementOn) {
        this.heatingElementOn = heatingElementOn;
    }

    /**
     * @return the coolingCompressorOn
     */
    public boolean isCoolingCompressorOn() {
        return coolingCompressorOn;
    }

    /**
     * @param coolingCompressorOn
     * the coolingCompressorOn to set
     */
    public void setCoolingCompressorOn(boolean coolingCompressorOn) {
        this.coolingCompressorOn = coolingCompressorOn;
    }

    /**
     * @return the sensorReadings
     */
    public List<SensorReading> getSensorReadings() {
        return sensorReadings;
    }

    /**
     * @param sensorReadings
     * the sensorReadings to set
     */
    public void setSensorReadings(List<SensorReading> sensorReadings) {
        this.sensorReadings = sensorReadings;
    }

    /**
     * @param fromJarpitStatus
     */
    public void update(JarpitStatus fromJarpitStatus) {

        this.setCoolingCompressorOn(fromJarpitStatus.isCoolingCompressorOn());
        this.setFanOn(fromJarpitStatus.isFanOn());
        this.setHeatingCompressorOn(fromJarpitStatus.isHeatingCompressorOn());
        this.setHeatingElementOn(fromJarpitStatus.isHeatingElementOn());
        this.setSensorReadings(fromJarpitStatus.getSensorReadings());
    }

}
