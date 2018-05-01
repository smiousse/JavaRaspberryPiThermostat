package org.github.smiousse.jarpit.services;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.github.smiousse.jarpit.api.sensors.HumiditySensor;
import org.github.smiousse.jarpit.api.sensors.Sensor;
import org.github.smiousse.jarpit.api.sensors.TempSensor;
import org.github.smiousse.jarpit.model.SensorSetting;
import org.github.smiousse.jarpit.model.SensorSetting.SensorType;
import org.github.smiousse.jarpit.model.Settings;
import org.github.smiousse.jarpit.raspberrypi.hvac.ClimateManager;
import org.github.smiousse.jarpit.raspberrypi.sensors.DHT11;
import org.github.smiousse.jarpit.raspberrypi.sensors.DS18B20;
import org.github.smiousse.jarpit.utils.StatsLogger;
import org.github.smiousse.jarpit.utils.StatsLogger.StatsType;

public class MasterController {

    private final Map<String, Sensor> registredSensors = new LinkedHashMap<>();

    private final StatsLogger statsLogger = new StatsLogger("http://192.168.2.22:8080/rest/stats/add/");
    private Settings settings = null;
    private ClimateManager climateManager = null;

    public MasterController() {

    }

    /**
     * @param settings
     */
    public MasterController(Settings settings) {
        if (settings != null) {
            this.settings = settings;
            for (SensorSetting sensorSetting : settings.getSensorSettings()) {
                switch (sensorSetting.getSensorType()) {
                case TEMPERATURE:
                    switch (sensorSetting.getTempSensorModel()) {
                    case DHT11:
                        registerSensor(new DHT11(sensorSetting));
                        break;
                    case DS18B20:
                    case DS18B20_WP:
                        registerSensor(new DS18B20(sensorSetting));
                        break;

                    default:
                        break;
                    }
                    break;
                case HUMIDITY:
                    switch (sensorSetting.getHumiditySensorModel()) {
                    case DHT11:
                        registerSensor(new DHT11(sensorSetting));
                        break;

                    default:
                        break;
                    }
                    break;
                case PRESSURE:

                    break;

                default:
                    break;
                }
            }

            // this.climateManager = new ClimateManager(settings.getClimateSetting(), settings.getHvacControllerSetting(), statsLogger);
        }
    }

    /**
     * 
     */
    public void checkClimate() {
        this.climateManager.checkClimate(this.getInsideTemperature().doubleValue());
    }

    /**
     * 
     */
    public void logSensorReadings() {
        Sensor sensor = null;
        for (String deviceIdentifier : registredSensors.keySet()) {
            sensor = registredSensors.get(deviceIdentifier);
            sensor.updateReadings();
            if (sensor.getSensorSetting().getSensorType().equals(SensorType.TEMPERATURE) && sensor instanceof TempSensor) {
                statsLogger.log(StatsType.TEMPERATURE, ((TempSensor) sensor).getTemperature(), sensor.getSensorSetting());
            } else if (sensor.getSensorSetting().getSensorType().equals(SensorType.HUMIDITY) && sensor instanceof HumiditySensor) {
                statsLogger.log(StatsType.HUMIDITY, ((HumiditySensor) sensor).getHumidity(), sensor.getSensorSetting());
            }

        }

    }

    /**
     * @return
     */
    public BigDecimal getInsideTemperature() {
        if (settings != null && settings.getMasterInsideTempSensorIdentifier() != null) {
            Sensor sensor = registredSensors.get(settings.getMasterInsideTempSensorIdentifier());
            if (sensor != null && sensor instanceof TempSensor) {
                sensor.updateReadings();
                return ((TempSensor) sensor).getTemperature();
            }

        }
        return BigDecimal.valueOf(22);
    }

    /**
     * @return
     */
    public BigDecimal getOutsideTemperature() {
        if (settings != null && settings.getMasterOutsideTempSensorIdentifier() != null) {
            Sensor sensor = registredSensors.get(settings.getMasterOutsideTempSensorIdentifier());
            if (sensor != null && sensor instanceof TempSensor) {
                sensor.updateReadings();
                return ((TempSensor) sensor).getTemperature();
            }

        }
        return BigDecimal.valueOf(22);
    }

    /**
     * @return
     */
    public Collection<Sensor> getSensors() {
        return registredSensors.values();
    }

    /**
     * @param sensor
     */
    private void registerSensor(Sensor sensor) {
        this.registredSensors.put(sensor.getSensorSetting().getIdentifier(), sensor);
    }

    /**
     * 
     */
    public void dispose() {
        statsLogger.dispose();
        this.registredSensors.clear();
    }

}
