package org.github.smiousse.jarpit.raspberrypi.sensors;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.github.smiousse.jarpit.api.sensors.HumiditySensor;
import org.github.smiousse.jarpit.api.sensors.TempSensor;
import org.github.smiousse.jarpit.model.SensorSetting;
import org.github.smiousse.jarpit.model.SensorSetting.HumiditySensorModel;
import org.github.smiousse.jarpit.model.SensorSetting.TempSensorModel;
import org.github.smiousse.jarpit.utils.Logger;

import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.GpioUtil;

public class DHT11 extends AbstractSensor implements TempSensor, HumiditySensor {

    // GPIO Stuff
    private final int[] dht11_dat = { 0, 0, 0, 0, 0 };
    private int pin;
    private static final int MAXTIMINGS = 85;

    // Temerature Vlaues
    private double temperatureInCelsius;
    private double temperatureInFarenheit;
    private double humidity;

    // Error handling
    private int errorCount = 0;
    private Logger log = new Logger();

    /**
     * Creates a DHT11 object.
     */
    public DHT11(SensorSetting sensorSetting) {
        super(sensorSetting);
        this.pin = sensorSetting.getGpioPin();
        if (Gpio.wiringPiSetup() == -1) {
            this.log.alert("[ERROR] GPIO setup failed.", "An error occured when creating a DHT11 object");
            this.log.add("[ERROR]", "GPIO setup failed.");
            return;
        }

        GpioUtil.export(3, GpioUtil.DIRECTION_OUT);
    }

    /**
     * Updates the temperature and humidity data from the sensor.
     * 
     * @param int
     * - GPIO pin sensor is connected to.
     */
    public boolean updateReadings() {
        do {
            int laststate = Gpio.HIGH;
            int j = 0;
            dht11_dat[0] = dht11_dat[1] = dht11_dat[2] = dht11_dat[3] = dht11_dat[4] = 0;

            Gpio.pinMode(pin, Gpio.OUTPUT);
            Gpio.digitalWrite(pin, Gpio.LOW);
            Gpio.delay(18);

            Gpio.digitalWrite(pin, Gpio.HIGH);
            Gpio.pinMode(pin, Gpio.INPUT);

            for (int i = 0; i < MAXTIMINGS; i++) {
                int counter = 0;

                while (Gpio.digitalRead(pin) == laststate) {
                    counter++;
                    Gpio.delayMicroseconds(1);
                    if (counter == 255) {
                        break;
                    }
                }

                laststate = Gpio.digitalRead(pin);

                if (counter == 255) {
                    break;
                }

                try {
                    // Ignore first 3 transitions.
                    if (i >= 4 && i % 2 == 0) {
                        dht11_dat[j / 8] <<= 1; // Shove each bit into the storage bytes.

                        if (counter > 16)
                            dht11_dat[j / 8] |= 1;

                        j++;
                    }
                }
                catch (Exception e) {
                    // Send alert email only one time so we don't get flagged as spam.
                    if (this.errorCount == 0)
                        this.log.alert("[ERROR] DHT11 Exception",
                                "An error occured when getting data from the DHT11 tempetemperature sensor. " + e.getMessage());

                    this.errorCount++;

                    if (this.errorCount > 3)
                        break;

                    this.updateReadings();
                }
            }

            // check we read 40 bits (8bit x 5 ) + verify checksum in the last byte.
            if (j >= 40 && checkParity()) {
                double h = (float) ((dht11_dat[0] << 8) + dht11_dat[1]) / 10;
                // System.out.println("dht11_dat[0] = " + dht11_dat[0]);
                // System.out.println("dht11_dat[1] = " + dht11_dat[1]);
                // System.out.println("dht11_dat[2] = " + dht11_dat[2]);
                // System.out.println("dht11_dat[3] = " + dht11_dat[3]);
                if (h > 100) {
                    try {
                        h = new BigDecimal(dht11_dat[0] + "." + dht11_dat[1]).setScale(1, RoundingMode.HALF_EVEN).doubleValue(); // for DHT11
                    }
                    catch (Exception e) {
                        h = dht11_dat[0];// for DHT11
                    }
                }

                double c = (float) (((dht11_dat[2] & 0x7F) << 8) + dht11_dat[3]) / 10;

                if (c > 125) {
                    c = dht11_dat[2]; // for DHT11
                }

                if ((dht11_dat[2] & 0x80) != 0) {
                    c = -c;
                }

                try {
                    c = new BigDecimal(c + "." + dht11_dat[3]).setScale(1, RoundingMode.HALF_EVEN).doubleValue();
                }
                catch (Exception e) {

                }

                this.temperatureInCelsius = c;

                final double f = this.temperatureInCelsius * 1.8f + 32;
                this.temperatureInFarenheit = f;
                this.humidity = h;
            } else {
                this.temperatureInCelsius = Double.MAX_VALUE;
                this.humidity = Double.MAX_VALUE;
            }
        } while (this.temperatureInCelsius == Double.MAX_VALUE || this.humidity == Double.MAX_VALUE);

        // Rest error handeling variable.
        this.errorCount = 0;

        return true;
    }

    /**
     * @return the temperatureInCelsius
     */
    public double getTemperatureInCelsius() {
        return temperatureInCelsius;
    }

    /**
     * @return the temperatureInFarenheit
     */
    public double getTemperatureInFarenheit() {
        return temperatureInFarenheit;
    }

    /**
     * Returns the humidity from the sensor.
     * 
     * @return Double - humidity as a double.
     */
    public BigDecimal getHumidity() {
        return BigDecimal.valueOf(this.humidity);
    }

    /**
     * Returns the humidity from the sensor.
     * 
     * @return Boolean - True of false depending of temperature data array.
     */
    private boolean checkParity() {
        return dht11_dat[4] == (dht11_dat[0] + dht11_dat[1] + dht11_dat[2] + dht11_dat[3] & 0xFF);
    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.github.smiousse.jarpit.api.sensors.TemperatureSensor#getTemperature()
     */
    @Override
    public BigDecimal getTemperature() {
        return BigDecimal.valueOf(temperatureInCelsius);
    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.github.smiousse.jarpit.api.sensors.TemperatureSensor#getModel()
     */
    @Override
    public TempSensorModel getTempSensorModel() {
        return TempSensorModel.DHT11;
    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.github.smiousse.jarpit.api.sensors.HumiditySensor#getHumiditySensorModel()
     */
    @Override
    public HumiditySensorModel getHumiditySensorModel() {
        return HumiditySensorModel.DHT11;
    }

}
