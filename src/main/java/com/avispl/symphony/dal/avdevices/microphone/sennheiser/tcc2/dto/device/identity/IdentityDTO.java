/*
 *  * Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.device.identity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * IdentityDTO class includes the serial number, the product name and vendor
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 3/14/2023
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdentityDTO {
	private String serial;
	private String product;
	private String vendor;

	/**
	 * hardware revision
	 */
	@JsonProperty("hw_revision")
	private String hardware;

	/**
	 * firmware version
	 */
	@JsonProperty("version")
	private String firmware;

	/**
	 * Retrieves {@link #serial}
	 *
	 * @return value of {@link #serial}
	 */
	public String getSerial() {
		return serial;
	}

	/**
	 * Sets {@link #serial} value
	 *
	 * @param serial new value of {@link #serial}
	 */
	public void setSerial(String serial) {
		this.serial = serial;
	}

	/**
	 * Retrieves {@link #product}
	 *
	 * @return value of {@link #product}
	 */
	public String getProduct() {
		return product;
	}

	/**
	 * Sets {@link #product} value
	 *
	 * @param product new value of {@link #product}
	 */
	public void setProduct(String product) {
		this.product = product;
	}

	/**
	 * Retrieves {@code {@link #vendor}}
	 *
	 * @return value of {@link #vendor}
	 */
	public String getVendor() {
		return vendor;
	}

	/**
	 * Sets {@code vendor}
	 *
	 * @param vendor the {@code java.lang.String} field
	 */
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	/**
	 * Retrieves {@code {@link #hardware }}
	 *
	 * @return value of {@link #hardware}
	 */
	public String getHwRevision() {
		return hardware;
	}

	/**
	 * Sets {@code hardware}
	 *
	 * @param hwRevision the {@code java.lang.String} field
	 */
	public void setHwRevision(String hwRevision) {
		this.hardware = hwRevision;
	}

	/**
	 * Retrieves {@code {@link #firmware }}
	 *
	 * @return value of {@link #firmware}
	 */
	public String getFirmware() {
		return firmware;
	}

	/**
	 * Sets {@code firmware}
	 *
	 * @param firmware the {@code java.lang.String} field
	 */
	public void setFirmware(String firmware) {
		this.firmware = firmware;
	}
}