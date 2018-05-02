package org.github.smiousse.jarpit.services;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.github.smiousse.jarpit.api.sensors.HumiditySensor;
import org.github.smiousse.jarpit.api.sensors.Sensor;
import org.github.smiousse.jarpit.api.sensors.TempSensor;
import org.github.smiousse.jarpit.model.JarpitStatus;
import org.github.smiousse.jarpit.model.SensorSetting;
import org.github.smiousse.jarpit.model.SensorSetting.SensorType;
import org.github.smiousse.jarpit.model.Settings;
import org.github.smiousse.jarpit.raspberrypi.hvac.ClimateManager;
import org.github.smiousse.jarpit.raspberrypi.sensors.DHT11;
import org.github.smiousse.jarpit.raspberrypi.sensors.DS18B20;
import org.github.smiousse.jarpit.utils.BolluxClient;
import org.github.smiousse.jarpit.utils.StatsLogger;
import org.github.smiousse.jarpit.utils.StatsLogger.StatsType;

public class MasterController {

    private final Map<String, Sensor> registredSensors = new LinkedHashMap<>();

    private StatsLogger statsLogger;
    private Settings settings = null;
    private ClimateManager climateManager = null;
    private final JarpitStatus jarpitStatus = new JarpitStatus();

    private BolluxClient bolluxClient = new BolluxClient("http://192.168.2.22:8080");

    public MasterController() {
        this.statsLogger = new StatsLogger(bolluxClient);
    }

    /**
     * @param settings
     */
    public MasterController(Settings settings) {
        this();
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
        this.climateManager.checkClimate(this.getInsideTemperature().doubleValue(), jarpitStatus);
    }

    /**
     * 
     */
    public void pushJarpitStatus() {
        bolluxClient.updateJarpitStatus(jarpitStatus);
    }

    /**
     * 
     */
    public void logSensorReadings() {
        Sensor sensor = null;
        for (String deviceIdentifier : registredSensors.keySet()) {
            sensor = registredSensors.get(deviceIdentifier);
            sensor.updateReadings();
            BigDecimal value = null;
            if (sensor.getSensorSetting().getSensorType().equals(SensorType.TEMPERATURE) && sensor instanceof TempSensor) {
                value = ((TempSensor) sensor).getTemperature();
                statsLogger.log(StatsType.TEMPERATURE, value, sensor.getSensorSetting());
            } else if (sensor.getSensorSetting().getSensorType().equals(SensorType.HUMIDITY) && sensor instanceof HumiditySensor) {
                value = ((HumiditySensor) sensor).getHumidity();
                statsLogger.log(StatsType.HUMIDITY, value, sensor.getSensorSetting());
            }
            if (isMasterMainFloorTempSensor(sensor)) {
                jarpitStatus.setMainFloorTemp(value);
            } else if (isMasterBasementTempSensor(sensor)) {
                jarpitStatus.setBasementTemp(value);
            } else if (isMasterOutsideTempSensor(sensor)) {
                jarpitStatus.setOutsideTemp(value);
            } else if (isMasterOutsideHumiditySensor(sensor)) {
                jarpitStatus.setOutsideHumidity(value);
            }
        }

    }

    /**
     * @return
     */
    public BigDecimal getInsideTemperature() {
        if (settings != null && settings.getMasterMainFloorTempSensorIdentifier() != null) {
            Sensor sensor = registredSensors.get(settings.getMasterMainFloorTempSensorIdentifier());
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

    private boolean isMasterOutsideTempSensor(Sensor sensor) {
        if (sensor != null && sensor instanceof TempSensor
                && sensor.getSensorSetting().getIdentifier().equals(settings.getMasterOutsideTempSensorIdentifier())) {
            return true;
        }
        return false;
    }

    private boolean isMasterMainFloorTempSensor(Sensor sensor) {
        if (sensor != null && sensor instanceof TempSensor
                && sensor.getSensorSetting().getIdentifier().equals(settings.getMasterMainFloorTempSensorIdentifier())) {
            return true;
        }
        return false;
    }

    private boolean isMasterBasementTempSensor(Sensor sensor) {
        if (sensor != null && sensor instanceof TempSensor
                && sensor.getSensorSetting().getIdentifier().equals(settings.getMasterBasementTempSensorIdentifier())) {
            return true;
        }
        return false;
    }

    private boolean isMasterOutsideHumiditySensor(Sensor sensor) {
        if (sensor != null && sensor instanceof HumiditySensor
                && sensor.getSensorSetting().getIdentifier().equals(settings.getMasterOutsideHumiditySensorIdentifier())) {
            return true;
        }
        return false;
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
        bolluxClient.dispose();
        this.registredSensors.clear();
    }

}
