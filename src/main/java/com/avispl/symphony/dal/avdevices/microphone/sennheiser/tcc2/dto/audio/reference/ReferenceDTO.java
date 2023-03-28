/*
 *  Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.audio.reference;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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

	@JsonProperty("farend_auto_adjust_enable")
	private boolean gainStatus;

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

	/**
	 * Retrieves {@code {@link #gainStatus}}
	 *
	 * @return value of {@link #gainStatus}
	 */
	public boolean isGainStatus() {
		return gainStatus;
	}

	/**
	 * Sets {@code gainStatus}
	 *
	 * @param gainStatus the {@code boolean} field
	 */
	public void setGainStatus(boolean gainStatus) {
		this.gainStatus = gainStatus;
	}
}
