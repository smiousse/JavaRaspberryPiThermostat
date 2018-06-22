package io.github.smiousse.jarpit.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import io.github.smiousse.jarpit.api.sensors.HumiditySensor;
import io.github.smiousse.jarpit.api.sensors.Sensor;
import io.github.smiousse.jarpit.api.sensors.TempSensor;
import io.github.smiousse.jarpit.model.JarpitStatus;
import io.github.smiousse.jarpit.model.SensorSetting;
import io.github.smiousse.jarpit.model.SensorSetting.SensorType;
import io.github.smiousse.jarpit.model.Settings;
import io.github.smiousse.jarpit.raspberrypi.hvac.ClimateManager;
import io.github.smiousse.jarpit.raspberrypi.sensors.DHT11;
import io.github.smiousse.jarpit.raspberrypi.sensors.DS18B20;
import io.github.smiousse.jarpit.utils.ApplicationPropertyManager;
import io.github.smiousse.jarpit.utils.BolluxClient;
import io.github.smiousse.jarpit.utils.StatsLogger;
import io.github.smiousse.jarpit.utils.StatsLogger.StatsType;

public class MasterController {

    private static final Logger log = LoggerFactory.getLogger(MasterController.class);

    private final Map<String, Sensor> registredSensors = new LinkedHashMap<>();

    private StatsLogger statsLogger;
    private Settings settings = null;
    private ClimateManager climateManager = null;
    private final JarpitStatus jarpitStatus = new JarpitStatus();

    private BolluxClient bolluxClient;

    public MasterController() {
        this.bolluxClient = new BolluxClient(ApplicationPropertyManager.getApplicationPropertyValue("bollux.server.url"));
        this.statsLogger = new StatsLogger(bolluxClient);
    }

    /**
     * @param settings
     */
    public MasterController(Settings defaultSettings) {
        this();
        settings = bolluxClient.getSettings();
        if (settings == null) {
            this.settings = defaultSettings;
        }
        loadSettings();

        // this.climateManager = new ClimateManager(settings.getClimateSetting(), settings.getHvacControllerSetting(), statsLogger);

    }

    /**
     * 
     */
    public void loadSettings() {
        for (SensorSetting sensorSetting : settings.getSensorSettings()) {
            try {
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
            catch (RuntimeException re) {
                log.error("loadSettings", re);
            }
            catch (Exception e) {
                log.error("loadSettings", e);
            }

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
    public void refreshSettings() {
        log.debug("Refresh setings");
        Settings settings = bolluxClient.getSettings();
        if (settings != null && this.isSettingsChanged(settings)) {
            log.debug("Clear old settings, and reload");
            this.registredSensors.clear();
            try {
                log.info("new settings = "
                        + new String(new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).writeValueAsBytes(jarpitStatus)));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            this.settings = settings;
            this.loadSettings();
        }
    }

    /**
     * 
     */
    public void pushJarpitStatus() {
        log.debug("pushJarpitStatus");
        try {
            log.info("jarpitStatus = "
                    + new String(new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).writeValueAsBytes(jarpitStatus)));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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

    /**
     * @param sensor
     * @return
     */
    private boolean isMasterOutsideTempSensor(Sensor sensor) {
        if (sensor != null && sensor instanceof TempSensor
                && sensor.getSensorSetting().getIdentifier().equals(settings.getMasterOutsideTempSensorIdentifier())) {
            return true;
        }
        return false;
    }

    /**
     * @param sensor
     * @return
     */
    private boolean isMasterMainFloorTempSensor(Sensor sensor) {
        if (sensor != null && sensor instanceof TempSensor
                && sensor.getSensorSetting().getIdentifier().equals(settings.getMasterMainFloorTempSensorIdentifier())) {
            return true;
        }
        return false;
    }

    /**
     * @param sensor
     * @return
     */
    private boolean isMasterBasementTempSensor(Sensor sensor) {
        if (sensor != null && sensor instanceof TempSensor
                && sensor.getSensorSetting().getIdentifier().equals(settings.getMasterBasementTempSensorIdentifier())) {
            return true;
        }
        return false;
    }

    /**
     * @param sensor
     * @return
     */
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
     * @param settings
     * @return
     */
    private boolean isSettingsChanged(Settings settings) {
        return !this.settings.hash().equals(settings.hash());
    }

    /**
     * 
     */
    public void dispose() {
        bolluxClient.dispose();
        this.registredSensors.clear();
        this.settings = null;
        this.statsLogger = null;
    }

}
