/*
 *  * Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.audio.output;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.device.network.NetworkDTO;

/**
 * OutputDTO includes Network Object
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 3/27/2023
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutputDTO {
	private NetworkDTO network;

	/**
	 * Retrieves {@code {@link #network}}
	 *
	 * @return value of {@link #network}
	 */
	public NetworkDTO getNetwork() {
		return network;
	}

	/**
	 * Sets {@code network}
	 *
	 * @param network the {@code com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.device.network.NetworkDTO} field
	 */
	public void setNetwork(NetworkDTO network) {
		this.network = network;
	}
}
