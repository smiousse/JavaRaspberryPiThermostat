package org.github.smiousse.jarpit;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.github.smiousse.jarpit.model.ClimateSetting;
import org.github.smiousse.jarpit.model.HvacControllerSetting;
import org.github.smiousse.jarpit.model.Settings;
import org.github.smiousse.jarpit.raspberrypi.sensors.DHT11;
import org.github.smiousse.jarpit.raspberrypi.sensors.DS18B20;
import org.github.smiousse.jarpit.utils.StatsLogger.StatsType;
import org.github.smiousse.jarpit.utils.StatscsvLogger;

/**
 * @author smiousse
 *
 */
public class Bootstrap {

    public static String APP_HOME = "../";

    private Settings settings;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    public static void main(String[] args) {

        // System.out.println(new JSONObject(getDefaultSetting()).toString(2));

        // testDTH11Sensor();

        logTemprature();
    }

    private static void logTemprature() {
        DS18B20 sensor = new DS18B20("Master", "28-0117b12203ff");

        System.out.println("Temperatue in Celsius = " + sensor.getTemperature());

        StatscsvLogger statsLogger = new StatscsvLogger(new File("./stats_" + sdf.format(new Date()) + ".csv"));

        statsLogger.log(StatsType.TEMPERATURE, sensor.getTemperature(), "Salle des machines");

        DS18B20 outSideSensor = new DS18B20("Outside", "28-0117c13a71ff");

        System.out.println("Temperatue in Celsius = " + outSideSensor.getTemperature());
        statsLogger.log(StatsType.TEMPERATURE, outSideSensor.getTemperature(), "Outside");

        statsLogger.dispose();
    }

    private static void testDS18B20Sensor() {
        DS18B20 sensor = new DS18B20("Master", "28-0117b12203ff");
        sensor.updateReadings();
        System.out.println("Temperatue in Celsius = " + sensor.getTemperature());

    }

    private static void testDTH11Sensor() {
        DHT11 sensor = new DHT11(7, "Master");
        sensor.updateReadings();
        System.out.println("Temperatue in Celsius = " + sensor.getTemperatureInCelsius());
        System.out.println("Temperatue in Farenheit = " + sensor.getTemperatureInFarenheit());
        System.out.println("humidity = " + sensor.getHumidity());
    }

    /**
     * @return
     */
    private static Settings getDefaultSetting() {
        Settings settings = new Settings();
        settings.setHvacControllerSetting(new HvacControllerSetting());
        settings.setClimateSetting(new ClimateSetting());
        settings.addSensor("Master", Integer.toString(7));

        return settings;

    }

}
