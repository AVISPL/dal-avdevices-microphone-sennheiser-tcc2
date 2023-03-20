/*
 *  * Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.osc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * OscDTO class provides during the monitoring and controlling process
 * OscDTO class includes the version value
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 3/15/2023
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OscDTO {
	private String version;
	private Object error;

	/**
	 * Retrieves {@code {@link #error}}
	 *
	 * @return value of {@link #error}
	 */
	public Object getError() {
		return error;
	}

	/**
	 * Sets {@code error}
	 *
	 * @param error the {@code java.lang.Object} field
	 */
	public void setError(Object error) {
		this.error = error;
	}

	/**
	 * Retrieves {@code {@link #version}}
	 *
	 * @return value of {@link #version}
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Sets {@code version}
	 *
	 * @param version the {@code java.lang.String} field
	 */
	public void setVersion(String version) {
		this.version = version;
	}
}
