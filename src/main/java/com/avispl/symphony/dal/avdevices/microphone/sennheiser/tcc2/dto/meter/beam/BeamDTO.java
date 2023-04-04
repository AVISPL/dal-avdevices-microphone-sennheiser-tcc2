/*
 *  * Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.meter.beam;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * BeamDTO class includes information such as elevation value and azimuth value
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 3/15/2023
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BeamDTO {
	private Integer elevation;
	private Integer azimuth;

	/**
	 * Retrieves {@code {@link #elevation}}
	 *
	 * @return value of {@link #elevation}
	 */
	public Integer getElevation() {
		return elevation;
	}

	/**
	 * Sets {@code elevation}
	 *
	 * @param elevation the {@code int} field
	 */
	public void setElevation(Integer elevation) {
		this.elevation = elevation;
	}

	/**
	 * Retrieves {@code {@link #azimuth}}
	 *
	 * @return value of {@link #azimuth}
	 */
	public Integer getAzimuth() {
		return azimuth;
	}

	/**
	 * Sets {@code azimuth}
	 *
	 * @param azimuth the {@code int} field
	 */
	public void setAzimuth(Integer azimuth) {
		this.azimuth = azimuth;
	}
}
