package io.github.smiousse.jarpit;

import com.pi4j.io.gpio.RaspiPin;

import io.github.smiousse.jarpit.model.ClimateSetting;
import io.github.smiousse.jarpit.model.HvacControllerSetting;
import io.github.smiousse.jarpit.model.SensorSetting;
import io.github.smiousse.jarpit.model.Settings;
import io.github.smiousse.jarpit.model.SensorSetting.HumiditySensorModel;
import io.github.smiousse.jarpit.model.SensorSetting.SensorType;
import io.github.smiousse.jarpit.model.SensorSetting.TempSensorModel;
import io.github.smiousse.jarpit.services.MasterController;
import io.github.smiousse.jarpit.utils.ApplicationPropertyManager;

/**
 * @author smiousse
 *
 */
public class Bootstrap {

    public static String APP_HOME = "../";

    public static void main(String[] args) {

        intEnvProperties();

        MasterController master = new MasterController(getDefaultSetting());

        master.logSensorReadings();
        master.pushJarpitStatus();
        master.dispose();
    }

    /**
     * 
     */
    private static void intEnvProperties() {

        try {
            if (System.getProperty(ApplicationPropertyManager.ENV_HOME_LOCATION) == null
                    || System.getProperty(ApplicationPropertyManager.ENV_HOME_LOCATION).isEmpty()) {
                System.setProperty(ApplicationPropertyManager.ENV_HOME_LOCATION, Bootstrap.class.getResource("../../../../").getFile());
            }
            System.setProperty(ApplicationPropertyManager.ENV_PROPERTY_FILE_LOCATION,
                    System.getProperty(ApplicationPropertyManager.ENV_HOME_LOCATION) + "application.properties");

            System.out.println("ENV_HOME_LOCATION = " + System.getProperty(ApplicationPropertyManager.ENV_HOME_LOCATION));
            System.out.println("ENV_PROPERTY_FILE_LOCATION = " + System.getProperty(ApplicationPropertyManager.ENV_PROPERTY_FILE_LOCATION));

            ApplicationPropertyManager.loadApplicationProperties();
        }
        catch (Exception e) {
            // TSLT: handle exception
        }
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
