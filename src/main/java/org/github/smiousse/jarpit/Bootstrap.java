package org.github.smiousse.jarpit;

import org.github.smiousse.jarpit.raspberrypi.hvac.ClimateSetting;
import org.github.smiousse.jarpit.raspberrypi.hvac.HvacControllerSetting;
import org.github.smiousse.jarpit.raspberrypi.sensors.DHT11;

/**
 * @author smiousse
 *
 */
public class Bootstrap {

    public static String APP_HOME = "../";

    private Settings settings;

    public static void main(String[] args) {

        // System.out.println(new JSONObject(getDefaultSetting()).toString(2));

        testDTH11Sensor();
    }

    private static void testDTH11Sensor() {
        DHT11 sensor = new DHT11(7);
        sensor.updateTemperature();
        double temperature = sensor.getTemperature();
        temperature = ((temperature - 32) * 5) / 9;

        System.out.println("Temperatue in Celsius = " + temperature);
        System.out.println("humidity = " + sensor.getHumidity());
    }

    /**
     * @return
     */
    private static Settings getDefaultSetting() {
        Settings settings = new Settings();
        settings.setHvacControllerSetting(new HvacControllerSetting());
        settings.setClimateSetting(new ClimateSetting());
        settings.addSensor("Master", 7);

        return settings;

    }

}
