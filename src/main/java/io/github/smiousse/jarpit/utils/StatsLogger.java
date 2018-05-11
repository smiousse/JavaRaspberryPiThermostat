package io.github.smiousse.jarpit.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.github.smiousse.jarpit.model.SensorSetting;

public class StatsLogger {

    public enum StatsType {
        TEMPERATURE, HUMIDITY, FAN, HEATING, COOLING
    };

    private BolluxClient bolluxClient;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * @param logFile
     */
    public StatsLogger(BolluxClient bolluxClient) {
        this.bolluxClient = bolluxClient;

    }

    /**
     * @param statsType
     * @param value
     * @param deviceIdentifier
     */
    public void log(StatsType statsType, Object value, String deviceIdentifier) {
        this.log(statsType, value, deviceIdentifier, null, new Date(System.currentTimeMillis()));
    }

    /**
     * @param statsType
     * @param value
     * @param info
     */
    public void log(StatsType statsType, Object value, SensorSetting sensorSetting) {
        this.log(statsType, value, sensorSetting.getIdentifier(), null, new Date(System.currentTimeMillis()));
    }

    /**
     * @param statsType
     * @param value
     * @param info
     * @param extras
     */
    public void log(StatsType statsType, Object value, SensorSetting sensorSetting, String extras) {
        this.log(statsType, value, sensorSetting.getIdentifier(), null, new Date(System.currentTimeMillis()));
    }

    /**
     * @param statsType
     * @param value
     * @param info
     * @param extras
     * @param date
     */
    public void log(StatsType statsType, Object value, String deviceIdentifier, String extras, Date date) {
        if (statsType != null && value != null) {
            this.bolluxClient.log(statsType, value, deviceIdentifier, extras, sdf.format(date));
        }
    }

}
