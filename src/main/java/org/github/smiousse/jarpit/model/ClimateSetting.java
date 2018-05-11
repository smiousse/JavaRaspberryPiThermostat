package org.github.smiousse.jarpit.model;

import org.github.smiousse.jarpit.raspberrypi.hvac.ClimateManager.ClimateMode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author smiousse
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "climateMode", "temperatureMax", "temperatureMin", "temperatureThreshold" })
public class ClimateSetting {

    @JsonProperty("climateMode")
    private ClimateMode climateMode;

    @JsonProperty("temperatureMax")
    private double temperatureMax = 24.5;

    @JsonProperty("temperatureMin")
    private double temperatureMin = 19.5;

    @JsonProperty("temperatureThreshold")
    private double temperatureThreshold = 0.5;

    /**
     * 
     */
    public ClimateSetting() {
        super();
    }

    /**
     * @return the temperatureMax
     */
    public double getTemperatureMax() {
        return temperatureMax;
    }

    /**
     * @param temperatureMax
     * the temperatureMax to set
     */
    public void setTemperatureMax(double temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    /**
     * @return the temperatureMin
     */
    public double getTemperatureMin() {
        return temperatureMin;
    }

    /**
     * @param temperatureMin
     * the temperatureMin to set
     */
    public void setTemperatureMin(double temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    /**
     * @return the temperatureThreshold
     */
    public double getTemperatureThreshold() {
        return temperatureThreshold;
    }

    /**
     * @param temperatureThreshold
     * the temperatureThreshold to set
     */
    public void setTemperatureThreshold(double temperatureThreshold) {
        this.temperatureThreshold = temperatureThreshold;
    }

    /**
     * @return the climateMode
     */
    public ClimateMode getClimateMode() {
        return climateMode;
    }

    /**
     * @param climateMode
     * the climateMode to set
     */
    public void setClimateMode(ClimateMode climateMode) {
        this.climateMode = climateMode;
    }

    /**
     * @return
     */
    public String hash() {

        StringBuilder sb = new StringBuilder();
        sb.append(this.temperatureMax);
        sb.append(this.temperatureMin);
        sb.append(this.temperatureThreshold);
        return sb.toString();
    }

}
