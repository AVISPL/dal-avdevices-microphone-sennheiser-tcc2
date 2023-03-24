/*
 *  * Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.meter.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * InputDTO class includes peak value
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 3/15/2023
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InputDTO {
	private int peak;

	/**
	 * Retrieves {@code {@link #peak}}
	 *
	 * @return value of {@link #peak}
	 */
	public int getPeak() {
		return peak;
	}

	/**
	 * Sets {@code peak}
	 *
	 * @param peak the {@code int} field
	 */
	public void setPeak(int peak) {
		this.peak = peak;
	}
}
