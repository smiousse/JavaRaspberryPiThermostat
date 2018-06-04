package io.github.smiousse.jarpit.services;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.pi4j.io.gpio.RaspiPin;

import io.github.smiousse.jarpit.model.ClimateSetting;
import io.github.smiousse.jarpit.model.HvacControllerSetting;
import io.github.smiousse.jarpit.model.SensorSetting;
import io.github.smiousse.jarpit.model.SensorSetting.HumiditySensorModel;
import io.github.smiousse.jarpit.model.SensorSetting.SensorType;
import io.github.smiousse.jarpit.model.SensorSetting.TempSensorModel;
import io.github.smiousse.jarpit.model.Settings;

public class MasterControllerJob implements Job {

    private static MasterController master;

    static {
        master = new MasterController(getDefaultSetting());

    }

    @Override
    public void execute(JobExecutionContext paramJobExecutionContext) throws JobExecutionException {
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
                "outside_temp_sensor_1", "Outside temperature sensor 1");
        settings.addSensor(outSideSensor);

        // Outside humidity sensor
        SensorSetting humiditySensor = new SensorSetting(SensorType.HUMIDITY, HumiditySensorModel.DHT11, "outside_humidity_sensor_1",
                "Outside humidity sensor 1", RaspiPin.GPIO_25.getAddress());
        settings.addSensor(humiditySensor);

        // Inside temprature sensor
        SensorSetting insideSensor = new SensorSetting(SensorType.TEMPERATURE, TempSensorModel.DS18B20_WP, "28-0117b12203ff",
                "inside_temp_sensor_1", "Inside temperature sensor 1");
        settings.addSensor(insideSensor);

        settings.setMasterMainFloorTempSensorIdentifier(insideSensor.getIdentifier());
        settings.setMasterOutsideTempSensorIdentifier(outSideSensor.getIdentifier());
        settings.setMasterOutsideHumiditySensorIdentifier(humiditySensor.getIdentifier());

        return settings;

    }

}
