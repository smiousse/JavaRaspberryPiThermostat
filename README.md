# JavaRaspberryPiThermostat


### Raspberry Pi 2 Model B

![Raspberry Pi 2 Model B](https://github.com/smiousse/jarpit/blob/master/site/raspberry-pi-2-b-j8header.png)



### DHT11 Sensor

Temperature sensor and Humidity sensor

![ds18b20 sensor](https://github.com/smiousse/jarpit/blob/master/site/DHT11.jpg)

### DS18B20 Sensor

Temperature sensor

![ds18b20 sensor](https://github.com/smiousse/jarpit/blob/master/site/ds18b20.png)

### HVAC Relay module

Wired for HVAC Relay module

![ds18b20 sensor](https://github.com/smiousse/jarpit/blob/master/site/hvac_controller.png)

# HVAC 101

HVAC (heating, ventilating, and air conditioning) can be implemented in different ways within a house or apartment, and varies for different countries based on the wiring and such. I'm not an expert in HVAC systems, I pretty much just learned enough to get by for this project, but here's a pretty useful guide: [http://wiki.xtronics.com/index.php/Thermostat_signals_and_wiring](http://wiki.xtronics.com/index.php/Thermostat_signals_and_wiring)

My particular apartment uses a heat pump, which uses the very same compressor action for the air conditioner. The only difference is the air flow. This makes it possible to, in my case, use four wires from the HVAC panel behind the original thermostat for this project:

* **R (Red)**: This is the "common" wire, meaning that a circuit is completed when any of the other wires are connected to this one, hence the relay module.
* **G (Green)**: This is the signal for the fan, which becomes activated when connected to **R**.
* **Y (Yellow)**: This is the signal for the compressor. By default, the air flow is set to blow heat into the apartment.
* **O (Orange)**: This is the signal to reverse the air flow from the compressor. With **Y** and **O** both active, the flow changes to blow cool air into the apartment.

I broke this down to a pretty simple formula while I started writing the software for the controller:

**R** + **G** = Fan<br/>
**R** + **G** + **Y** = Heater<br/>
**R** + **G** + **Y** + **O** = Air conditioner

It's also very important to note that the wires coming out of my HVAC use a low voltage at 24V, not mains power. With a relay module completing the circuits, it should still work with mains electricity, but _**it is very dangerous and you should NOT mess with mains power unless you know exactly what you're doing! An accident involving high voltage and current can very easily kill you.**_

