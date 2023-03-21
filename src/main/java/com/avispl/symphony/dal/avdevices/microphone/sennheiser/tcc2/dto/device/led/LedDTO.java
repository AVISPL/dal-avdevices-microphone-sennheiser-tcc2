/*
 *  Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.device.led;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * LedDTO class provides during the monitoring and controlling process
 * LedDTO class includes the brightness, active value,mic on color, mic mute color and custom color object
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 3/16/2023
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LedDTO {
	private int brightness;
	@JsonProperty("mic_on")
	private MicOnColorDTO micOnColor;
	@JsonProperty("mic_mute")
	private MicMuteColorDTO micMuteColor;
	@JsonProperty("custom")
	private CustomColorDTO customColor;

	@JsonProperty("show_farend_activity")
	private boolean activity;

	/**
	 * Retrieves {@code {@link #activity}}
	 *
	 * @return value of {@link #activity}
	 */
	public boolean isActivity() {
		return activity;
	}

	/**
	 * Sets {@code activity}
	 *
	 * @param activity the {@code boolean} field
	 */
	public void setActivity(boolean activity) {
		this.activity = activity;
	}

	/**
	 * Retrieves {@code {@link #micOnColor }}
	 *
	 * @return value of {@link #micOnColor}
	 */
	public MicOnColorDTO getMicOnColor() {
		return micOnColor;
	}

	/**
	 * Sets {@code micOn}
	 *
	 * @param micOnColor the {@code com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.device.led.MicOnDTO} field
	 */
	public void setMicOnColor(MicOnColorDTO micOnColor) {
		this.micOnColor = micOnColor;
	}

	/**
	 * Retrieves {@code {@link #micMuteColor }}
	 *
	 * @return value of {@link #micMuteColor}
	 */
	public MicMuteColorDTO getMicMuteColor() {
		return micMuteColor;
	}

	/**
	 * Sets {@code micMute}
	 *
	 * @param micMuteColor the {@code com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.device.led.MicMuteDTO} field
	 */
	public void setMicMuteColor(MicMuteColorDTO micMuteColor) {
		this.micMuteColor = micMuteColor;
	}

	/**
	 * Retrieves {@code {@link #customColor }}
	 *
	 * @return value of {@link #customColor}
	 */
	public CustomColorDTO getCustomColor() {
		return customColor;
	}

	/**
	 * Sets {@code custom}
	 *
	 * @param customColor the {@code com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.device.led.CustomDTO} field
	 */
	public void setCustomColor(CustomColorDTO customColor) {
		this.customColor = customColor;
	}

	/**
	 * Retrieves {@code {@link #brightness}}
	 *
	 * @return value of {@link #brightness}
	 */
	public int getBrightness() {
		return brightness;
	}

	/**
	 * Sets {@code brightness}
	 *
	 * @param brightness the {@code int} field
	 */
	public void setBrightness(int brightness) {
		this.brightness = brightness;
	}
}
