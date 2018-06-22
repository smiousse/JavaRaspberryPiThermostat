package io.github.smiousse.jarpit.services;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.smiousse.jarpit.model.ClimateSetting;
import io.github.smiousse.jarpit.model.HvacControllerSetting;
import io.github.smiousse.jarpit.model.SensorSetting;
import io.github.smiousse.jarpit.model.SensorSetting.SensorType;
import io.github.smiousse.jarpit.model.SensorSetting.TempSensorModel;
import io.github.smiousse.jarpit.model.Settings;

public class MasterControllerJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(MasterControllerJob.class);
    private static MasterController master;

    static {
        master = new MasterController(getDefaultSetting());

    }

    @Override
    public void execute(JobExecutionContext paramJobExecutionContext) throws JobExecutionException {

        log.trace("MasterControllerJob execute");
        master.refreshSettings();
        master.logSensorReadings();
        master.pushJarpitStatus();
    }

    /**
     * @return
     */
    private static Settings getDefaultSetting() {
        Settings settings = new Settings();
        settings.setHvacControllerSetting(new HvacControllerSetting());
        settings.setClimateSetting(new ClimateSetting());

        // Outside temprature sensor
        SensorSetting outSideSensor = new SensorSetting(SensorType.TEMPERATURE, TempSensorModel.DS18B20_WP, "28-0117c13a71ff",
                "outside_temp_sensor_1", "Outside");
        settings.addSensor(outSideSensor);

        // Outside humidity sensor
        // SensorSetting humiditySensor = new SensorSetting(SensorType.HUMIDITY, HumiditySensorModel.DHT11, "outside_humidity_sensor_1",
        // "Outside humidity sensor 1", RaspiPin.GPIO_25.getAddress());
        // settings.addSensor(humiditySensor);

        // Inside temprature sensor
        SensorSetting basementSensor = new SensorSetting(SensorType.TEMPERATURE, TempSensorModel.DS18B20_WP, "28-0217c14218ff",
                "basement_temp_sensor_1", "Basement");
        settings.addSensor(basementSensor);

        SensorSetting mainFloorSensor = new SensorSetting(SensorType.TEMPERATURE, TempSensorModel.DS18B20_WP, "28-0117b12203ff",
                "main_floor_temp_sensor_1", "Main floor");
        settings.addSensor(mainFloorSensor);

        settings.setMasterMainFloorTempSensorIdentifier(mainFloorSensor.getIdentifier());
        settings.setMasterOutsideTempSensorIdentifier(outSideSensor.getIdentifier());
        // settings.setMasterOutsideHumiditySensorIdentifier(humiditySensor.getIdentifier());

        return settings;

    }

}
