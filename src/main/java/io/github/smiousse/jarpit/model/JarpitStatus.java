package io.github.smiousse.jarpit.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "outsideHumidity", "outsideTemp", "mainFloorTemp", "basementTemp", "fanOn", "heatingCompressorOn", "heatingElementOn",
        "coolingCompressorOn" })
public class JarpitStatus {

    @JsonProperty("outsideHumidity")
    private BigDecimal outsideHumidity;
    @JsonProperty("outsideTemp")
    private BigDecimal outsideTemp;

    @JsonProperty("mainFloorTemp")
    private BigDecimal mainFloorTemp;

    @JsonProperty("basementTemp")
    private BigDecimal basementTemp;

    @JsonProperty("fanOn")
    private boolean fanOn = false;

    @JsonProperty("heatingCompressorOn")
    private boolean heatingCompressorOn = false;

    @JsonProperty("heatingElementOn")
    private boolean heatingElementOn = false;

    @JsonProperty("coolingCompressorOn")
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

    /**
     * @param fromJarpitStatus
     */
    public void update(JarpitStatus fromJarpitStatus) {
        this.setBasementTemp(fromJarpitStatus.getBasementTemp());
        this.setCoolingCompressorOn(fromJarpitStatus.isCoolingCompressorOn());
        this.setFanOn(fromJarpitStatus.isFanOn());
        this.setHeatingCompressorOn(fromJarpitStatus.isHeatingCompressorOn());
        this.setHeatingElementOn(fromJarpitStatus.isHeatingElementOn());
        this.setMainFloorTemp(fromJarpitStatus.getMainFloorTemp());
        this.setOutsideHumidity(fromJarpitStatus.getOutsideHumidity());
        this.setOutsideTemp(fromJarpitStatus.getOutsideTemp());
    }

}
