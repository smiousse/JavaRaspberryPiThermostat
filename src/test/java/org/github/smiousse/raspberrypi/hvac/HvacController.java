package org.github.smiousse.raspberrypi.hvac;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;

public class HvacController {

    private HvacControllerSetting setting;
    private TemperatureSetting temperatureSetting;

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

    private void init() {
        gpio = GpioFactory.getInstance();

        pinFan = gpio.provisionDigitalOutputPin(setting.getPinFan(), "Fan", PinState.LOW);
        pinFan.setShutdownOptions(true, PinState.LOW);

        pinHeatingCompessor = gpio.provisionDigitalOutputPin(setting.getPinCompressor(), "Heanting compressor", PinState.LOW);
        pinHeatingCompessor.setShutdownOptions(true, PinState.LOW);

        pinHeatingElement = gpio.provisionDigitalOutputPin(setting.getPinHeatElement(), "Heating element", PinState.LOW);
        pinHeatingElement.setShutdownOptions(true, PinState.LOW);

        pinCooling = gpio.provisionDigitalOutputPin(setting.getPinCooling(), "Cooling", PinState.LOW);
        pinCooling.setShutdownOptions(true, PinState.LOW);

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

            if (System.currentTimeMillis() < (lastElementDisableTime + setting.getCompressorRecoveryTime())) {
                // writeVerbose('*** Cannot enable heating, compressor in recovery.',True);
                return;
            }

            // writeVerbose('Enabling heating...');
            pinHeatingCompessor.high();
            pinCooling.low();
            lastCompressorEnableTime = System.currentTimeMillis();
            isHeatingElementOn = true;
            // writeVerbose('Heating enabled.', True);

        } else {
            // writeVerbose('Disabling heating...');
            pinHeatingCompessor.low();

            if (isHeatingElementOn) {
                lastElementDisableTime = System.currentTimeMillis();
            }

            isHeatingElementOn = false;
            // writeVerbose('Heating disabled.', True);
        }
        this.delay();
    }

}
