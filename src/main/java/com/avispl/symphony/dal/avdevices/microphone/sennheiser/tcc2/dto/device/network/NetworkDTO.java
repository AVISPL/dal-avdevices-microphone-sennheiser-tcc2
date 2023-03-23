/*
 *  * Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.device.network;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *  NetworkDTO class includes Ipv4Address object and Ethernet object
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 3/15/2023
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NetworkDTO {
	@JsonProperty("ipv4")
	private Ipv4AddressDTO ipv4Address;

	@JsonProperty("ether")
	private EthernetDTO ethernet;

	/**
	 * Retrieves {@code {@link #ipv4Address }}
	 *
	 * @return value of {@link #ipv4Address}
	 */
	public Ipv4AddressDTO getIpv4Address() {
		return ipv4Address;
	}

	/**
	 * Sets {@code ipAddress}
	 *
	 * @param ipv4Address the {@code com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.network.Ipv4AddressDTO} field
	 */
	public void setIpv4Address(Ipv4AddressDTO ipv4Address) {
		this.ipv4Address = ipv4Address;
	}

	/**
	 * Retrieves {@code {@link #ethernet}}
	 *
	 * @return value of {@link #ethernet}
	 */
	public EthernetDTO getEthernet() {
		return ethernet;
	}

	/**
	 * Sets {@code ethernet}
	 *
	 * @param ethernet the {@code com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.device.network.EthernetDTO} field
	 */
	public void setEthernet(EthernetDTO ethernet) {
		this.ethernet = ethernet;
	}
}
