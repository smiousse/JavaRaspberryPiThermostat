package io.github.smiousse.jarpit.raspberrypi.sensors;

import java.math.BigDecimal;
import java.util.List;

import com.pi4j.component.temperature.TemperatureSensor;
import com.pi4j.component.temperature.impl.TmpDS18B20DeviceType;
import com.pi4j.io.w1.W1Device;
import com.pi4j.io.w1.W1Master;

import io.github.smiousse.jarpit.api.sensors.TempSensor;
import io.github.smiousse.jarpit.model.SensorSetting;
import io.github.smiousse.jarpit.model.SensorSetting.TempSensorModel;

/**
 * @author smiousse
 *
 */
public class DS18B20 extends AbstractSensor implements TempSensor {

    private TemperatureSensor device = null;

    /**
     * @param info
     */
    public DS18B20(SensorSetting sensorSetting) {
        super(sensorSetting);
        this.init();
    }

    private void init() {
        W1Master master = new W1Master();
        List<W1Device> w1Devices = master.getDevices(TmpDS18B20DeviceType.FAMILY_CODE);
        for (W1Device device : w1Devices) {
            if (device.getId().equals(this.getSensorSetting().getDeviceId()) && device instanceof TemperatureSensor) {
                this.device = (TemperatureSensor) device;
                break;
            }
        }
        if (this.device == null) {
            throw new RuntimeException("Cant find sensor for deviceId " + this.getSensorSetting().getDeviceId());
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
     * @see org.github.smiousse.jarpit.api.sensors.TemperatureSensor#getModel()
     */
    @Override
    public TempSensorModel getTempSensorModel() {
        return TempSensorModel.DS18B20_WP;
    }

}
