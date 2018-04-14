package org.github.smiousse.raspberrypi.hvac;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;

/**
 * @author smiousse
 *
 */
public class HvacControllerSetting {

    private Pin pinFan = RaspiPin.GPIO_17;
    private Pin pinCompressor = RaspiPin.GPIO_27;
    private Pin pinCooling = RaspiPin.GPIO_22;
    private Pin pinHeatElement = RaspiPin.GPIO_22;

    private int toggleDelay = 1;
    private int inTheBlindTime = 300;
    private int compressorRecoveryTime = 60;
    private int compressorStickTime = 60;
    private int fanRecoveryTime = 2;

    /**
     * @return the pinFan
     */
    public Pin getPinFan() {
        return pinFan;
    }

    /**
     * @param pinFan
     * the pinFan to set
     */
    public void setPinFan(Pin pinFan) {
        this.pinFan = pinFan;
    }

    /**
     * @return the pinCompressor
     */
    public Pin getPinCompressor() {
        return pinCompressor;
    }

    /**
     * @param pinCompressor
     * the pinCompressor to set
     */
    public void setPinCompressor(Pin pinCompressor) {
        this.pinCompressor = pinCompressor;
    }

    /**
     * @return the pinCooling
     */
    public Pin getPinCooling() {
        return pinCooling;
    }

    /**
     * @param pinCooling
     * the pinCooling to set
     */
    public void setPinCooling(Pin pinCooling) {
        this.pinCooling = pinCooling;
    }

    /**
     * @return the pinHeatElement
     */
    public Pin getPinHeatElement() {
        return pinHeatElement;
    }

    /**
     * @param pinHeatElement
     * the pinHeatElement to set
     */
    public void setPinHeatElement(Pin pinHeatElement) {
        this.pinHeatElement = pinHeatElement;
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

}
