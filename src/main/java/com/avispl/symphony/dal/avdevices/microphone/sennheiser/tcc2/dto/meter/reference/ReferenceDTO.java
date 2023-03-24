/*
 *  * Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.meter.reference;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * ReferenceDTO class includes rms value
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 3/15/2023
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReferenceDTO {
	private int rms;

	/**
	 * Retrieves {@code {@link #rms}}
	 *
	 * @return value of {@link #rms}
	 */
	public int getRms() {
		return rms;
	}

	/**
	 * Sets {@code rms}
	 *
	 * @param rms the {@code int} field
	 */
	public void setRms(int rms) {
		this.rms = rms;
	}
}
