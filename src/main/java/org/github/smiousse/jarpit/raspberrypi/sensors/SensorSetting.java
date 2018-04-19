package org.github.smiousse.jarpit.raspberrypi.sensors;

public class SensorSetting {

    private int gpioPinNumber;
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
    public SensorSetting(String name, int gpioPinNumber) {
        super();
        this.name = name;
        this.gpioPinNumber = gpioPinNumber;
    }

    /**
     * @return the gpioPinNumber
     */
    public int getGpioPinNumber() {
        return gpioPinNumber;
    }

    /**
     * @param gpioPinNumber
     * the gpioPinNumber to set
     */
    public void setGpioPinNumber(int gpioPinNumber) {
        this.gpioPinNumber = gpioPinNumber;
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

}
