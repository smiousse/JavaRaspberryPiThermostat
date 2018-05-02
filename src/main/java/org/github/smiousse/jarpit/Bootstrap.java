package org.github.smiousse.jarpit;

import org.github.smiousse.jarpit.model.ClimateSetting;
import org.github.smiousse.jarpit.model.HvacControllerSetting;
import org.github.smiousse.jarpit.model.SensorSetting;
import org.github.smiousse.jarpit.model.SensorSetting.HumiditySensorModel;
import org.github.smiousse.jarpit.model.SensorSetting.SensorType;
import org.github.smiousse.jarpit.model.SensorSetting.TempSensorModel;
import org.github.smiousse.jarpit.model.Settings;
import org.github.smiousse.jarpit.services.MasterController;

import com.pi4j.io.gpio.RaspiPin;

/**
 * @author smiousse
 *
 */
public class Bootstrap {

    public static String APP_HOME = "../";

    private Settings settings;

    public static void main(String[] args) {

        // System.out.println(new JSONObject(getDefaultSetting()).toString(2));

        MasterController master = new MasterController(getDefaultSetting());

        master.logSensorReadings();
        master.pushJarpitStatus();
        master.dispose();
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
