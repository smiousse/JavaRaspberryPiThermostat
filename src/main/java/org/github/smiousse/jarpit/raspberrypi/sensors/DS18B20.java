package org.github.smiousse.jarpit.raspberrypi.sensors;

import java.math.BigDecimal;
import java.util.List;

import org.github.smiousse.jarpit.api.sensors.TempSensor;
import org.github.smiousse.jarpit.model.SensorSetting.TempSensorModel;

import com.pi4j.component.temperature.TemperatureSensor;
import com.pi4j.component.temperature.impl.TmpDS18B20DeviceType;
import com.pi4j.io.w1.W1Device;
import com.pi4j.io.w1.W1Master;

/**
 * @author smiousse
 *
 */
public class DS18B20 implements TempSensor {

    private String info;
    private String deviceId;
    private TemperatureSensor device = null;

    /**
     * @param info
     */
    public DS18B20(String info, String deviceId) {
        super();
        this.info = info;
        this.deviceId = deviceId;
        this.init();
    }

    private void init() {
        W1Master master = new W1Master();
        List<W1Device> w1Devices = master.getDevices(TmpDS18B20DeviceType.FAMILY_CODE);
        for (W1Device device : w1Devices) {
            if (device.getId().equals(deviceId) && device instanceof TemperatureSensor) {
                this.device = (TemperatureSensor) device;
                break;
            }
        }
        if (this.device == null) {
            throw new RuntimeException("Cant find sensor for deviceId " + deviceId);
        }
    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.github.smiousse.jarpit.api.sensors.TemperatureSensor#getTemperature()
     */
    @Override
    public BigDecimal getTemperature() {
        return BigDecimal.valueOf(this.device.getTemperature());
    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.github.smiousse.jarpit.api.sensors.TemperatureSensor#getInfo()
     */
    @Override
    public String getInfo() {
        return this.info;
    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.github.smiousse.jarpit.api.sensors.TemperatureSensor#getModel()
     */
    @Override
    public TempSensorModel getTempSensorModel() {
        return TempSensorModel.DS18B20_WP;
    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.github.smiousse.jarpit.api.sensors.TemperatureSensor#updateTemperature()
     */
    @Override
    public boolean updateReadings() {

        return true;
    }

}
