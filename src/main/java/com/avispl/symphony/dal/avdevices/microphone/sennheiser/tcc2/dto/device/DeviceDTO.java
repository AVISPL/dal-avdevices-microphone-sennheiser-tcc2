/*
 *  * Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.device;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.device.identification.IdentificationDTO;
import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.device.identity.IdentityDTO;
import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.device.led.LedDTO;
import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.device.network.NetworkDTO;

/**
 * Device class provides during the monitoring and controlling process
 * Device class includes Identity Object, network object and some device information such as : time, date, system, position, name, location and language.
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 3/14/2023
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceDTO {
	private IdentityDTO identity;
	private NetworkDTO network;
	private long time;
	private String date;
	private String system;
	private String position;
	private String name;
	private String location;
	private String language;
	private IdentificationDTO identification;
	private LedDTO led;
	private boolean restart;


	/**
	 * Retrieves {@link #identity}
	 *
	 * @return value of {@link #identity}
	 */
	public IdentityDTO getIdentity() {
		return identity;
	}

	/**
	 * Sets {@link #identity} value
	 *
	 * @param identity new value of {@link #identity}
	 */
	public void setIdentity(IdentityDTO identity) {
		this.identity = identity;
	}

	/**
	 * Retrieves {@code {@link #time}}
	 *
	 * @return value of {@link #time}
	 */
	public long getTime() {
		return time;
	}

	/**
	 * Sets {@code time}
	 *
	 * @param time the {@code long} field
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * Retrieves {@code {@link #date}}
	 *
	 * @return value of {@link #date}
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Sets {@code date}
	 *
	 * @param date the {@code java.lang.String} field
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Retrieves {@code {@link #system}}
	 *
	 * @return value of {@link #system}
	 */
	public String getSystem() {
		return system;
	}

	/**
	 * Sets {@code system}
	 *
	 * @param system the {@code java.lang.String} field
	 */
	public void setSystem(String system) {
		this.system = system;
	}

	/**
	 * Retrieves {@code {@link #position}}
	 *
	 * @return value of {@link #position}
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * Sets {@code position}
	 *
	 * @param position the {@code java.lang.String} field
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * Retrieves {@code {@link #name}}
	 *
	 * @return value of {@link #name}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets {@code name}
	 *
	 * @param name the {@code java.lang.String} field
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Retrieves {@code {@link #location}}
	 *
	 * @return value of {@link #location}
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Sets {@code location}
	 *
	 * @param location the {@code java.lang.String} field
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Retrieves {@code {@link #language}}
	 *
	 * @return value of {@link #language}
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Sets {@code language}
	 *
	 * @param language the {@code java.lang.String} field
	 */
	public void setLanguage(String language) {
		this.language = language;
	}

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

	/**
	 * Retrieves {@code {@link #identification}}
	 *
	 * @return value of {@link #identification}
	 */
	public IdentificationDTO getIdentification() {
		return identification;
	}

	/**
	 * Sets {@code identification}
	 *
	 * @param identification the {@code com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.device.identification.IdentificationDTO} field
	 */
	public void setIdentification(IdentificationDTO identification) {
		this.identification = identification;
	}

	/**
	 * Retrieves {@code {@link #led}}
	 *
	 * @return value of {@link #led}
	 */
	public LedDTO getLed() {
		return led;
	}

	/**
	 * Sets {@code led}
	 *
	 * @param led the {@code com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.device.led.LedDTO} field
	 */
	public void setLed(LedDTO led) {
		this.led = led;
	}

	/**
	 * Retrieves {@code {@link #restart}}
	 *
	 * @return value of {@link #restart}
	 */
	public boolean isRestart() {
		return restart;
	}

	/**
	 * Sets {@code restart}
	 *
	 * @param restart the {@code boolean} field
	 */
	public void setRestart(boolean restart) {
		this.restart = restart;
	}
}