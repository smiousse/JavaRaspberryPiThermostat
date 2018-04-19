package org.github.smiousse.jarpit.raspberrypi.hvac;

/**
 * @author smiousse
 *
 */
public class HvacControllerSetting {

    public enum FanMode {
        ON, OFF, AUTO
    }

    private int pinFanNumber = 17;
    private int pinCompressorNumber = 27;
    private int pinCoolingNumber = 22;
    private int pinHeatElementNumber = 22;

    private int toggleDelay = 1;
    private int inTheBlindTime = 300;
    private int compressorRecoveryTime = 60;
    private int compressorStickTime = 60;
    private int fanRecoveryTime = 2;

    private FanMode fanMode = FanMode.AUTO;

    /**
     * @return the pinFanNumber
     */
    public int getPinFanNumber() {
        return pinFanNumber;
    }

    /**
     * @param pinFanNumber
     * the pinFanNumber to set
     */
    public void setPinFanNumber(int pinFanNumber) {
        this.pinFanNumber = pinFanNumber;
    }

    /**
     * @return the pinCompressorNumber
     */
    public int getPinCompressorNumber() {
        return pinCompressorNumber;
    }

    /**
     * @param pinCompressorNumber
     * the pinCompressorNumber to set
     */
    public void setPinCompressorNumber(int pinCompressorNumber) {
        this.pinCompressorNumber = pinCompressorNumber;
    }

    /**
     * @return the pinCoolingNumber
     */
    public int getPinCoolingNumber() {
        return pinCoolingNumber;
    }

    /**
     * @param pinCoolingNumber
     * the pinCoolingNumber to set
     */
    public void setPinCoolingNumber(int pinCoolingNumber) {
        this.pinCoolingNumber = pinCoolingNumber;
    }

    /**
     * @return the pinHeatElementNumber
     */
    public int getPinHeatElementNumber() {
        return pinHeatElementNumber;
    }

    /**
     * @param pinHeatElementNumber
     * the pinHeatElementNumber to set
     */
    public void setPinHeatElementNumber(int pinHeatElementNumber) {
        this.pinHeatElementNumber = pinHeatElementNumber;
    }

    /**
     * @return the toggleDelay
     */
    public int getToggleDelay() {
        return toggleDelay;
    }

    /**
     * @param toggleDelay
     * the toggleDelay to set
     */
    public void setToggleDelay(int toggleDelay) {
        this.toggleDelay = toggleDelay;
    }

    /**
     * @return the inTheBlindTime
     */
    public int getInTheBlindTime() {
        return inTheBlindTime;
    }

    /**
     * @param inTheBlindTime
     * the inTheBlindTime to set
     */
    public void setInTheBlindTime(int inTheBlindTime) {
        this.inTheBlindTime = inTheBlindTime;
    }

    /**
     * @return the compressorRecoveryTime
     */
    public int getCompressorRecoveryTime() {
        return compressorRecoveryTime;
    }

    /**
     * @param compressorRecoveryTime
     * the compressorRecoveryTime to set
     */
    public void setCompressorRecoveryTime(int compressorRecoveryTime) {
        this.compressorRecoveryTime = compressorRecoveryTime;
    }

    /**
     * @return the compressorStickTime
     */
    public int getCompressorStickTime() {
        return compressorStickTime;
    }

    /**
     * @param compressorStickTime
     * the compressorStickTime to set
     */
    public void setCompressorStickTime(int compressorStickTime) {
        this.compressorStickTime = compressorStickTime;
    }

    /**
     * @return the fanRecoveryTime
     */
    public int getFanRecoveryTime() {
        return fanRecoveryTime;
    }

    /**
     * @param fanRecoveryTime
     * the fanRecoveryTime to set
     */
    public void setFanRecoveryTime(int fanRecoveryTime) {
        this.fanRecoveryTime = fanRecoveryTime;
    }

    /**
     * @return the fanMode
     */
    public FanMode getFanMode() {
        return fanMode;
    }

    /**
     * @param fanMode
     * the fanMode to set
     */
    public void setFanMode(FanMode fanMode) {
        this.fanMode = fanMode;
    }

}
