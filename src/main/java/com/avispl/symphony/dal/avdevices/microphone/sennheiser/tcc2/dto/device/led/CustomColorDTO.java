/*
 *  Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.device.led;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * CustomColorDTO class provides during the monitoring and controlling process
 * CustomColorDTO class includes color value
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 3/16/2023
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomColorDTO {
	private String color;

	/**
	 * Retrieves {@code {@link #color}}
	 *
	 * @return value of {@link #color}
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Sets {@code color}
	 *
	 * @param color the {@code java.lang.String} field
	 */
	public void setColor(String color) {
		this.color = color;
	}
}
