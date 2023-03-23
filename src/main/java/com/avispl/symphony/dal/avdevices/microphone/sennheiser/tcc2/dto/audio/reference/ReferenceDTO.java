/*
 *  Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.audio.reference;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * ReferenceDTO class includes the gain value
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 3/16/2023
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReferenceDTO {
	private int gain;

	/**
	 * Retrieves {@code {@link #gain}}
	 *
	 * @return value of {@link #gain}
	 */
	public int getGain() {
		return gain;
	}

	/**
	 * Sets {@code gain}
	 *
	 * @param gain the {@code int} field
	 */
	public void setGain(int gain) {
		this.gain = gain;
	}
}
