package org.github.smiousse.jarpit;

import org.github.smiousse.jarpit.raspberrypi.hvac.ClimateSetting;
import org.github.smiousse.jarpit.raspberrypi.hvac.HvacControllerSetting;
import org.json.JSONObject;

/**
 * @author smiousse
 *
 */
public class Bootstrap {

    public static String APP_HOME = "../";

    private Settings settings;

    public static void main(String[] args) {

        System.out.println(new JSONObject(getDefaultSetting()).toString(2));
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
