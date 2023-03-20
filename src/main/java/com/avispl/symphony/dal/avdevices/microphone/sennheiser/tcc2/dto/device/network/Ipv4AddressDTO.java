/*
 *  * Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.device.network;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *  Ipv4AddressDTO class provides during the monitoring and controlling process
 *  Ipv4AddressDTO class includes the ipv4 addresses list , netmask list and gateways list
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 3/15/2023
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ipv4AddressDTO {
	@JsonProperty("ipaddr")
	private ArrayList<String> ipAddresses;
	@JsonProperty("netmask")
	private ArrayList<String> netMasks;
	@JsonProperty("gateway")
	private ArrayList<String> gateWays;

	/**
	 * Retrieves {@code {@link #ipAddresses }}
	 *
	 * @return value of {@link #ipAddresses}
	 */
	public ArrayList<String> getIpAddresses() {
		return ipAddresses;
	}

	/**
	 * Sets {@code ipAddress}
	 *
	 * @param ipAddresses the {@code java.lang.String} field
	 */
	public void setIpAddresses(ArrayList<String> ipAddresses) {
		this.ipAddresses = ipAddresses;
	}

	/**
	 * Retrieves {@code {@link #netMasks }}
	 *
	 * @return value of {@link #netMasks}
	 */
	public ArrayList<String> getNetMasks() {
		return netMasks;
	}

	/**
	 * Sets {@code netMask}
	 *
	 * @param netMasks the {@code java.util.ArrayList<java.lang.String>} field
	 */
	public void setNetMasks(ArrayList<String> netMasks) {
		this.netMasks = netMasks;
	}

	/**
	 * Retrieves {@code {@link #gateWays }}
	 *
	 * @return value of {@link #gateWays}
	 */
	public ArrayList<String> getGateWays() {
		return gateWays;
	}

	/**
	 * Sets {@code gateWay}
	 *
	 * @param gateWays the {@code java.util.ArrayList<java.lang.String>} field
	 */
	public void setGateWays(ArrayList<String> gateWays) {
		this.gateWays = gateWays;
	}
}
