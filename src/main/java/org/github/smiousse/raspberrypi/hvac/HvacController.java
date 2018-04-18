package org.github.smiousse.raspberrypi.hvac;

import java.util.HashMap;
import java.util.Map;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class HvacController {

    private HvacControllerSetting setting;

    private static Map<Integer, Pin> pinMappings = new HashMap<>();

    static {
        pinMappings.put(0, RaspiPin.GPIO_00);
        pinMappings.put(1, RaspiPin.GPIO_01);
        pinMappings.put(2, RaspiPin.GPIO_02);
        pinMappings.put(3, RaspiPin.GPIO_03);
        pinMappings.put(4, RaspiPin.GPIO_04);
        pinMappings.put(5, RaspiPin.GPIO_05);
        pinMappings.put(6, RaspiPin.GPIO_06);
        pinMappings.put(7, RaspiPin.GPIO_07);
        pinMappings.put(8, RaspiPin.GPIO_08);
        pinMappings.put(9, RaspiPin.GPIO_09);
        pinMappings.put(10, RaspiPin.GPIO_10);
        pinMappings.put(11, RaspiPin.GPIO_11);
        pinMappings.put(12, RaspiPin.GPIO_12);
        pinMappings.put(13, RaspiPin.GPIO_13);
        pinMappings.put(14, RaspiPin.GPIO_14);
        pinMappings.put(15, RaspiPin.GPIO_15);
        pinMappings.put(16, RaspiPin.GPIO_16);
        pinMappings.put(21, RaspiPin.GPIO_21);
        pinMappings.put(22, RaspiPin.GPIO_22);
        pinMappings.put(23, RaspiPin.GPIO_23);
        pinMappings.put(24, RaspiPin.GPIO_24);
        pinMappings.put(25, RaspiPin.GPIO_25);
        pinMappings.put(26, RaspiPin.GPIO_26);
        pinMappings.put(27, RaspiPin.GPIO_27);
        pinMappings.put(28, RaspiPin.GPIO_28);
        pinMappings.put(29, RaspiPin.GPIO_29);
        pinMappings.put(30, RaspiPin.GPIO_30);
        pinMappings.put(31, RaspiPin.GPIO_31);
    }

    private GpioPinDigitalOutput pinFan;
    private GpioPinDigitalOutput pinHeatingCompessor;
    private GpioPinDigitalOutput pinHeatingElement;
    private GpioPinDigitalOutput pinCooling;

    private boolean isFanOn = false;
    private boolean isHeatingCompressorOn = false;
    private boolean isHeatingElementOn = false;
    private boolean isCoolingOn = false;

    private long lastCompressorDisableTime = 0;
    private long lastCompressorEnableTime = 0;

    private long lastElementDisableTime = 0;
    private long lastElementEnableTime = 0;

    private long lastFanDisableTime = 0;
    private long lastFanEnableTime = 0;
    private long lastSettingsUpdate = 0;

    private GpioController gpio;

    /**
     * @param setting
     */
    public HvacController(HvacControllerSetting setting) {
        super();
        this.setting = setting;
        this.init();
    }

    /**
     * 
     */
    private void init() {
        gpio = GpioFactory.getInstance();

        pinFan = gpio.provisionDigitalOutputPin(pinMappings.get(setting.getPinFanNumber()), "Fan", PinState.LOW);
        pinFan.setShutdownOptions(true, PinState.LOW);

        pinHeatingCompessor = gpio.provisionDigitalOutputPin(pinMappings.get(setting.getPinCompressorNumber()), "Heanting compressor",
                PinState.LOW);
        pinHeatingCompessor.setShutdownOptions(true, PinState.LOW);

        pinHeatingElement = gpio.provisionDigitalOutputPin(pinMappings.get(setting.getPinHeatElementNumber()), "Heating element",
                PinState.LOW);
        pinHeatingElement.setShutdownOptions(true, PinState.LOW);

        pinCooling = gpio.provisionDigitalOutputPin(pinMappings.get(setting.getPinCoolingNumber()), "Cooling", PinState.LOW);
        pinCooling.setShutdownOptions(true, PinState.LOW);

    }

    /**
     * @param currentTime
     * @return
     */
    public boolean isCompressorStuck(long currentTime) {
        return currentTime < (lastCompressorEnableTime + setting.getCompressorStickTime());
    }

    /**
     * 
     */
    public void onColse() {
        this.cleanUp();
        this.writeStatus();
    }

    /**
     * 
     */
    protected void writeStatus() {

    }

    /**
     * 
     */
    protected void cleanUp() {
        setHeatingCompressor(false, true);
        setHeatingElement(false, true);
        setCooling(false, true);
        setFan(false, true);

    }

    /**
     * 
     */
    protected void delay() {
        try {
            Thread.sleep(setting.getToggleDelay() * 1000);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param toggleOn
     * @param force
     */
    protected void setCooling(boolean toggleOn, boolean force) {
        if (!force && toggleOn == isCoolingOn) {
            // writeVerbose('*** Cooling unchanged ('+('on' if coolingOn else 'off')+').',True);
            return;
        }

        if (toggleOn) {
            // # Cannot enable A/C if fan is off
            if (!isFanOn) {
                // writeVerbose('*** Cannot enable cooling if fan is disabled!',True);
                return;
            }

            // # Cannot enable A/C if heating is on
            if (isHeatingCompressorOn || isHeatingElementOn) {
                // writeVerbose('*** Cannot enable cooling if heating is on. Must disable heating first!',True);
                return;
            }

            if (System.currentTimeMillis() < (lastCompressorDisableTime + setting.getCompressorRecoveryTime())) {
                // writeVerbose('*** Cannot enable cooling, compressor in recovery.',True);
                return;
            }

            // writeVerbose('Enabling cooling...');
            pinHeatingCompessor.high();
            pinCooling.high();
            isCoolingOn = true;
            lastCompressorEnableTime = System.currentTimeMillis();
            // writeVerbose('Cooling enabled.', True);

        } else {
            // writeVerbose('Disabling cooling...');
            pinHeatingCompessor.low();
            pinCooling.low();

            if (isCoolingOn) {
                lastCompressorDisableTime = System.currentTimeMillis();
            }

            isCoolingOn = false;
            // writeVerbose('Cooling disabled.', True);
        }

        this.delay();
    }

    /**
     * @param toggleOn
     * @param force
     */
    protected void setFan(boolean toggleOn, boolean force) {

        if (!force && toggleOn == isFanOn) {
            // writeVerbose('*** Cooling unchanged ('+('on' if isFanOn else 'off')+').',True);
            return;
        }

        if (toggleOn) {

            if (System.currentTimeMillis() < (lastFanDisableTime + setting.getFanRecoveryTime())) {
                // writeVerbose('*** Cannot enable cooling, compressor in recovery.',True);
                return;
            }

            // writeVerbose('Enabling fan...');
            pinFan.high();
            isFanOn = true;
            // writeVerbose('Fan enabled.', True);

        } else {
            // writeVerbose('Disabling fan...');
            pinFan.low();
            if (isCoolingOn) {
                setCooling(false, true);
            }
            if (isHeatingCompressorOn) {
                setHeatingCompressor(false, true);
            }
            if (isHeatingElementOn) {
                setHeatingElement(false, true);
            }

            if (isFanOn) {
                lastFanDisableTime = System.currentTimeMillis();
            }

            isFanOn = false;
            // writeVerbose('Fan disabled.', True);
        }

        this.delay();

    }

    /**
     * @param toggleOn
     * @param force
     */
    protected void setHeatingCompressor(boolean toggleOn, boolean force) {
        if (!force && toggleOn == isHeatingCompressorOn) {
            // writeVerbose('*** Heating unchanged ('+('on' if heatingOn else 'off')+').',True);
            return;
        }

        if (toggleOn) {
            // # Cannot enable heating if fan is off
            if (!isFanOn) {
                // writeVerbose('*** Cannot enable heating if fan is disabled!',True);
                return;
            }

            // # Cannot enable heating if A/C is on
            if (isCoolingOn) {
                // writeVerbose('*** Cannot enable heating if cooling is on. Must disable cooling first!',True);
                return;
            }

            // # Cannot enable heating if A/C is on
            if (isHeatingElementOn) {
                // writeVerbose('*** Cannot enable heating if the heating element is on. Must disable heating element first!',True);
                return;
            }

            if (System.currentTimeMillis() < (lastCompressorDisableTime + setting.getCompressorRecoveryTime())) {
                // writeVerbose('*** Cannot enable heating, compressor in recovery.',True);
                return;
            }

            // writeVerbose('Enabling heating...');
            pinHeatingCompessor.high();
            pinCooling.low();
            lastCompressorEnableTime = System.currentTimeMillis();
            isHeatingCompressorOn = true;
            // writeVerbose('Heating enabled.', True);

        } else {
            // writeVerbose('Disabling heating...');
            pinHeatingCompessor.low();

            if (isHeatingCompressorOn) {
                lastCompressorDisableTime = System.currentTimeMillis();
            }

            isHeatingCompressorOn = false;
            // writeVerbose('Heating disabled.', True);
        }
        this.delay();
    }

    /**
     * @param toggleOn
     * @param force
     */
    protected void setHeatingElement(boolean toggleOn, boolean force) {
        if (!force && toggleOn == isHeatingElementOn) {
            // writeVerbose('*** Heating unchanged ('+('on' if heatingOn else 'off')+').',True);
            return;
        }

        if (toggleOn) {
            // # Cannot enable heating if fan is off
            if (!isFanOn) {
                // writeVerbose('*** Cannot enable heating if fan is disabled!',True);
                return;
            }

            // # Cannot enable heating if A/C is on
            if (isCoolingOn) {
                // writeVerbose('*** Cannot enable heating if cooling is on. Must disable cooling first!',True);
                return;
            }

            // # Cannot enable heating if A/C is on
            if (isHeatingCompressorOn) {
                // writeVerbose('*** Cannot enable heating if the compressor heating is on. Must disable compressor heating first!',True);
                return;
            }

            if (System.currentTimeMillis() < (lastElementDisableTime + setting.getCompressorRecoveryTime())) {
                // writeVerbose('*** Cannot enable heating, compressor in recovery.',True);
                return;
            }

            // writeVerbose('Enabling heating...');
            pinHeatingElement.high();
            lastElementEnableTime = System.currentTimeMillis();
            isHeatingElementOn = true;
            // writeVerbose('Heating enabled.', True);

        } else {
            // writeVerbose('Disabling heating...');
            pinHeatingElement.low();

            if (isHeatingElementOn) {
                lastElementDisableTime = System.currentTimeMillis();
            }

            isHeatingElementOn = false;
            // writeVerbose('Heating disabled.', True);
        }
        this.delay();
    }

    /**
     * @return the setting
     */
    public HvacControllerSetting getSetting() {
        return setting;
    }

    /**
     * @param setting
     * the setting to set
     */
    public void setSetting(HvacControllerSetting setting) {
        this.setting = setting;
    }

    /**
     * @return the isFanOn
     */
    public boolean isFanOn() {
        return isFanOn;
    }

    /**
     * @return the isHeatingCompressorOn
     */
    public boolean isHeatingCompressorOn() {
        return isHeatingCompressorOn;
    }

    /**
     * @return the isHeatingElementOn
     */
    public boolean isHeatingElementOn() {
        return isHeatingElementOn;
    }

    /**
     * @return the isCoolingOn
     */
    public boolean isCoolingOn() {
        return isCoolingOn;
    }

    /**
     * @return the lastCompressorDisableTime
     */
    public long getLastCompressorDisableTime() {
        return lastCompressorDisableTime;
    }

    /**
     * @return the lastCompressorEnableTime
     */
    public long getLastCompressorEnableTime() {
        return lastCompressorEnableTime;
    }

    /**
     * @return the lastElementDisableTime
     */
    public long getLastElementDisableTime() {
        return lastElementDisableTime;
    }

    /**
     * @return the lastElementEnableTime
     */
    public long getLastElementEnableTime() {
        return lastElementEnableTime;
    }

    /**
     * @return the lastFanDisableTime
     */
    public long getLastFanDisableTime() {
        return lastFanDisableTime;
    }

    /**
     * @return the lastFanEnableTime
     */
    public long getLastFanEnableTime() {
        return lastFanEnableTime;
    }

    /**
     * @return the lastSettingsUpdate
     */
    public long getLastSettingsUpdate() {
        return lastSettingsUpdate;
    }

}
