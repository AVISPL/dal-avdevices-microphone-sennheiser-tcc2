/*
 *  * Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.audio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.audio.output.OutputDTO;
import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.audio.reference.ReferenceDTO;
import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.audio.voice.VoiceLiftDTO;

/**
 * AudioDTO class includes mute, room in use value and voice lift , reference object
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 3/15/2023
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AudioDTO {
	@JsonProperty("room_in_use")
	private Boolean roomInUse;

	@JsonProperty("out2")
	private OutputDTO output;
	private Boolean mute;

	@JsonProperty("voice_lift")
	private VoiceLiftDTO voiceLift;

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
	 * Retrieves {@code {@link #voiceLift}}
	 *
	 * @return value of {@link #voiceLift}
	 */
	public VoiceLiftDTO getVoiceLift() {
		return voiceLift;
	}

	/**
	 * Sets {@code voiceLift}
	 *
	 * @param voiceLift the {@code com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.audio.voice.VoiceLiftDTO} field
	 */
	public void setVoiceLift(VoiceLiftDTO voiceLift) {
		this.voiceLift = voiceLift;
	}

	/**
	 * Retrieves {@code {@link #mute}}
	 *
	 * @return value of {@link #mute}
	 */
	public Boolean isMute() {
		return mute;
	}

	/**
	 * Sets {@code mute}
	 *
	 * @param mute the {@code boolean} field
	 */
	public void setMute(Boolean mute) {
		this.mute = mute;
	}

	/**
	 * Retrieves {@code {@link #roomInUse }}
	 *
	 * @return value of {@link #roomInUse}
	 */
	public Boolean isRoomInUse() {
		return roomInUse;
	}

	/**
	 * Sets {@code audioInUse}
	 *
	 * @param roomInUse the {@code boolean} field
	 */
	public void setRoomInUse(Boolean roomInUse) {
		this.roomInUse = roomInUse;
	}

	/**
	 * Retrieves {@code {@link #output}}
	 *
	 * @return value of {@link #output}
	 */
	public OutputDTO getOutput() {
		return output;
	}

	/**
	 * Sets {@code output}
	 *
	 * @param output the {@code com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.audio.output.OutputDTO} field
	 */
	public void setOutput(OutputDTO output) {
		this.output = output;
	}
}
