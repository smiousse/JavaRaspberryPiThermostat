package org.github.smiousse.raspberrypi.hvac;

/**
 * @author smiousse
 *
 */
public class ClimateSetting {

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

}
