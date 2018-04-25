package org.github.smiousse.jarpit;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.github.smiousse.jarpit.raspberrypi.hvac.ClimateSetting;
import org.github.smiousse.jarpit.raspberrypi.hvac.HvacControllerSetting;
import org.github.smiousse.jarpit.raspberrypi.sensors.DHT11;
import org.github.smiousse.jarpit.raspberrypi.sensors.DS18B20;
import org.github.smiousse.jarpit.utils.StatsLogger;
import org.github.smiousse.jarpit.utils.StatsLogger.StatsType;

import com.m32dn.nrf24pi.Nrf24Controller;
import com.m32dn.nrf24pi.Nrf24Factory;
import com.m32dn.nrf24pi.example.SimpleTransmitter;
import com.m32dn.nrf24pi.exception.NrfException;
import com.m32dn.nrf24pi.impl.AddressImpl;

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

        StatsLogger statsLogger = new StatsLogger(new File("./stats_" + sdf.format(new Date()) + ".csv"));

        statsLogger.log(StatsType.TEMPERATURE, sensor.getTemperature(), "Salle des machines");
        statsLogger.dispose();
    }

    private static void testDS18B20Sensor() {
        DS18B20 sensor = new DS18B20("Master", "28-0117b12203ff");
        sensor.updateTemperature();
        System.out.println("Temperatue in Celsius = " + sensor.getTemperature());

    }

    private static void testDTH11Sensor() {
        DHT11 sensor = new DHT11(7, "Master");
        sensor.updateTemperature();
        System.out.println("Temperatue in Celsius = " + sensor.getTemperatureInCelsius());
        System.out.println("Temperatue in Farenheit = " + sensor.getTemperatureInFarenheit());
        System.out.println("humidity = " + sensor.getHumidity());
    }

    public static void testwt440Sniffer() {
        try {
            Process p = Runtime.getRuntime().exec("gpio load spi");
            p.waitFor();
            byte[] a = { (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x41,
                    (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x41,
                    (byte) 0x41, (byte) 0x41, (byte) 0x41, (byte) 0x41 };
            Nrf24Controller c = Nrf24Factory.getInstance();
            c.start();
            c.sendSynchronized(new AddressImpl(0x41, 0x41, 0x41, 0x41, 0x41), ByteBuffer.wrap(a));
            c.stop();
        }
        catch (NrfException | IOException | InterruptedException ex) {
            Logger.getLogger(SimpleTransmitter.class.getName()).log(Level.SEVERE, null, ex);
        }

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
