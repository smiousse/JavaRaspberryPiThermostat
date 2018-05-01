package org.github.smiousse.jarpit.model;

import org.github.smiousse.jarpit.raspberrypi.hvac.ClimateManager.ClimateMode;

/**
 * @author smiousse
 *
 */
public class ClimateSetting {

    private ClimateMode climateMode;

    private double temperatureMax = 24.5;
    private double temperatureMin = 19.5;
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

}
