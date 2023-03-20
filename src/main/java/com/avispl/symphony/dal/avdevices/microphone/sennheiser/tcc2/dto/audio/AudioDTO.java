/*
 *  * Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.audio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.audio.reference.ReferenceDTO;

/**
 * AudioDTO class provides during the monitoring and controlling process
 * AudioDTO class includes mute, room in use value and voice lift , reference object
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 3/15/2023
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AudioDTO {
	@JsonProperty("room_in_use")
	private boolean roomInUse;
	private boolean mute;
	@JsonProperty("ref1")
	private ReferenceDTO reference;

	/**
	 * Retrieves {@code {@link #reference}}
	 *
	 * @return value of {@link #reference}
	 */
	public ReferenceDTO getReference() {
		return reference;
	}

	/**
	 * Sets {@code reference}
	 *
	 * @param reference the {@code com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.audio.reference.ReferenceDTO} field
	 */
	public void setReference(ReferenceDTO reference) {
		this.reference = reference;
	}

	/**
	 * Retrieves {@code {@link #mute}}
	 *
	 * @return value of {@link #mute}
	 */
	public boolean isMute() {
		return mute;
	}

	/**
	 * Sets {@code mute}
	 *
	 * @param mute the {@code boolean} field
	 */
	public void setMute(boolean mute) {
		this.mute = mute;
	}

	/**
	 * Retrieves {@code {@link #roomInUse }}
	 *
	 * @return value of {@link #roomInUse}
	 */
	public boolean isRoomInUse() {
		return roomInUse;
	}

	/**
	 * Sets {@code audioInUse}
	 *
	 * @param roomInUse the {@code boolean} field
	 */
	public void setRoomInUse(boolean roomInUse) {
		this.roomInUse = roomInUse;
	}
}
