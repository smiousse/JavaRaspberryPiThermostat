package org.github.smiousse.raspberrypi.hvac;

/**
 * @author smiousse
 *
 */
public class ClimateSetting {

    private double observedTemperature;
    private double temperatureMax;
    private double temperatureMin;
    private double temperatureThreshold;

    /**
     * 
     */
    public ClimateSetting() {
        super();
    }

    /**
     * @return the observedTemperature
     */
    public double getObservedTemperature() {
        return observedTemperature;
    }

    /**
     * @param observedTemperature
     * the observedTemperature to set
     */
    public void setObservedTemperature(double observedTemperature) {
        this.observedTemperature = observedTemperature;
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

}
