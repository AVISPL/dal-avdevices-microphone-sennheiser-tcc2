/*
 *  * Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.device.network;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *  EthernetDTO class provides during the monitoring and controlling process
 *  EthernetDTO class includes the mac addresses list and interface names list
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 3/15/2023
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EthernetDTO {
	@JsonProperty("macs")
	private ArrayList<String> macAddresses;

	@JsonProperty("interfaces")
	private ArrayList<String> interfaceNames;

	/**
	 * Retrieves {@code {@link #macAddresses}}
	 *
	 * @return value of {@link #macAddresses}
	 */
	public ArrayList<String> getMacAddresses() {
		return macAddresses;
	}

	/**
	 * Sets {@code macAddresses}
	 *
	 * @param macAddresses the {@code java.util.ArrayList<java.lang.String>} field
	 */
	public void setMacAddresses(ArrayList<String> macAddresses) {
		this.macAddresses = macAddresses;
	}

	/**
	 * Retrieves {@code {@link #interfaceNames}}
	 *
	 * @return value of {@link #interfaceNames}
	 */
	public ArrayList<String> getInterfaceNames() {
		return interfaceNames;
	}

	/**
	 * Sets {@code interfaceNames}
	 *
	 * @param interfaceNames the {@code java.util.ArrayList<java.lang.String>} field
	 */
	public void setInterfaceNames(ArrayList<String> interfaceNames) {
		this.interfaceNames = interfaceNames;
	}
}
