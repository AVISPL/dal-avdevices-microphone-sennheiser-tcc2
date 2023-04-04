/*
 *  Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.audio.voice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * VoiceLiftDTO class provides during the monitoring and controlling process
 * VoiceLiftDTO class includes the active value
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 3/16/2023
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VoiceLiftDTO {
	private Boolean active;

	/**
	 * Retrieves {@code {@link #active}}
	 *
	 * @return value of {@link #active}
	 */
	public Boolean isActive() {
		return active;
	}

	/**
	 * Sets {@code active}
	 *
	 * @param active the {@code boolean} field
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}
}
