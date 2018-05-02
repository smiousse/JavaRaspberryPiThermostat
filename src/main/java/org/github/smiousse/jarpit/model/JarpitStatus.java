package org.github.smiousse.jarpit.model;

import java.math.BigDecimal;

public class JarpitStatus {

    private BigDecimal outsideHumidity;
    private BigDecimal outsideTemp;
    private BigDecimal mainFloorTemp;
    private BigDecimal basementTemp;

    private boolean fanOn = false;
    private boolean heatingCompressorOn = false;
    private boolean heatingElementOn = false;
    private boolean coolingCompressorOn = false;

    /**
     * 
     */
    public JarpitStatus() {
        super();
    }

    /**
     * @return the outsideHumidity
     */
    public BigDecimal getOutsideHumidity() {
        return outsideHumidity;
    }

    /**
     * @param outsideHumidity
     * the outsideHumidity to set
     */
    public void setOutsideHumidity(BigDecimal outsideHumidity) {
        this.outsideHumidity = outsideHumidity;
    }

    /**
     * @return the outsideTemp
     */
    public BigDecimal getOutsideTemp() {
        return outsideTemp;
    }

    /**
     * @param outsideTemp
     * the outsideTemp to set
     */
    public void setOutsideTemp(BigDecimal outsideTemp) {
        this.outsideTemp = outsideTemp;
    }

    /**
     * @return the mainFloorTemp
     */
    public BigDecimal getMainFloorTemp() {
        return mainFloorTemp;
    }

    /**
     * @param mainFloorTemp
     * the mainFloorTemp to set
     */
    public void setMainFloorTemp(BigDecimal mainFloorTemp) {
        this.mainFloorTemp = mainFloorTemp;
    }

    /**
     * @return the basementTemp
     */
    public BigDecimal getBasementTemp() {
        return basementTemp;
    }

    /**
     * @param basementTemp
     * the basementTemp to set
     */
    public void setBasementTemp(BigDecimal basementTemp) {
        this.basementTemp = basementTemp;
    }

    /**
     * @return the fanOn
     */
    public boolean isFanOn() {
        return fanOn;
    }

    /**
     * @param fanOn
     * the fanOn to set
     */
    public void setFanOn(boolean fanOn) {
        this.fanOn = fanOn;
    }

    /**
     * @return the heatingCompressorOn
     */
    public boolean isHeatingCompressorOn() {
        return heatingCompressorOn;
    }

    /**
     * @param heatingCompressorOn
     * the heatingCompressorOn to set
     */
    public void setHeatingCompressorOn(boolean heatingCompressorOn) {
        this.heatingCompressorOn = heatingCompressorOn;
    }

    /**
     * @return the heatingElementOn
     */
    public boolean isHeatingElementOn() {
        return heatingElementOn;
    }

    /**
     * @param heatingElementOn
     * the heatingElementOn to set
     */
    public void setHeatingElementOn(boolean heatingElementOn) {
        this.heatingElementOn = heatingElementOn;
    }

    /**
     * @return the coolingCompressorOn
     */
    public boolean isCoolingCompressorOn() {
        return coolingCompressorOn;
    }

    /**
     * @param coolingCompressorOn
     * the coolingCompressorOn to set
     */
    public void setCoolingCompressorOn(boolean coolingCompressorOn) {
        this.coolingCompressorOn = coolingCompressorOn;
    }

}
