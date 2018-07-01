/**
 * Copyright (C) 2008-2017 Dexero Inc.
 *
 * This program is licensed under DEXERO Sustainable Enterprise License (DSEL).
 *
 * This program is distributed in the hope that it will be useful, but AS-IS and
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE, TITLE, or NONINFRINGEMENT. Redistribution,
 * except as permitted by DSEL, is prohibited.
 *
 * This program and the accompanying materials are made available under the
 * terms of the DSEL which accompanies this distribution, and is available at
 * http://www.dexero.com/license/dsel/
 *
 * Any modifications to this file must keep this entire header intact.
 */
package io.github.smiousse.jarpit.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.math.BigDecimal;

/**
 * @author smiousse
 * @version $Rev$ $Date$
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "identifier", "decimalValue", "stringValue" })
public class SensorReading {

    @JsonProperty("identifier")
    private String identifier;

    @JsonProperty("decimalValue")
    private BigDecimal decimalValue;

    @JsonProperty("stringValue")
    private String stringValue;

    /**
     * @param identifier
     * @param decimalValue
     */
    public SensorReading(String identifier, BigDecimal decimalValue) {
        super();
        this.identifier = identifier;
        this.decimalValue = decimalValue;
    }

    /**
     * @param identifier
     * @param stringValue
     */
    public SensorReading(String identifier, String stringValue) {
        super();
        this.identifier = identifier;
        this.stringValue = stringValue;
    }

    /**
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier
     * the identifier to set
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * @return the decimalValue
     */
    public BigDecimal getDecimalValue() {
        return decimalValue;
    }

    /**
     * @param decimalValue
     * the decimalValue to set
     */
    public void setDecimalValue(BigDecimal decimalValue) {
        this.decimalValue = decimalValue;
    }

    /**
     * @return the stringValue
     */
    public String getStringValue() {
        return stringValue;
    }

    /**
     * @param stringValue
     * the stringValue to set
     */
    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

}
