/*
 *  Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.device.identification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * IdentificationDTO class includes the visual value
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 3/16/2023
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdentificationDTO {
	private Boolean visual;

	/**
	 * Retrieves {@code {@link #visual}}
	 *
	 * @return value of {@link #visual}
	 */
	public Boolean isVisual() {
		return visual;
	}

	/**
	 * Sets {@code visual}
	 *
	 * @param visual the {@code boolean} field
	 */
	public void setVisual(Boolean visual) {
		this.visual = visual;
	}
}
