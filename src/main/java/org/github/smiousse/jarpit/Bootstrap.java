package org.github.smiousse.jarpit;

import java.text.SimpleDateFormat;

import org.github.smiousse.jarpit.model.ClimateSetting;
import org.github.smiousse.jarpit.model.HvacControllerSetting;
import org.github.smiousse.jarpit.model.Settings;
import org.github.smiousse.jarpit.raspberrypi.sensors.DHT11;
import org.github.smiousse.jarpit.raspberrypi.sensors.DS18B20;
import org.github.smiousse.jarpit.utils.StatsLogger;
import org.github.smiousse.jarpit.utils.StatsLogger.StatsType;

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

        StatsLogger statsLogger = new StatsLogger("http://192.168.2.22:8080/rest/stats/add/");

        statsLogger.log(StatsType.TEMPERATURE, sensor.getTemperature(), "Salle des machines");
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
