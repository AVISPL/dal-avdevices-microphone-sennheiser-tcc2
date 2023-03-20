/*
 *  * Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.comom;

import java.util.Arrays;
import java.util.Optional;

/**
 * ListOfPropertyName class defined the enum for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 3/14/2023
 * @since 1.0.0
 */
public enum SennheiserPropertiesList {
	SERIAL_NUMBER(SennheiserConstant.SERIAL_NUMBER, SennheiserCommandList.SERIAL_NUMBER),
	PRODUCT_NAME(SennheiserConstant.PRODUCT_NAME, SennheiserCommandList.PRODUCT_NAME),
	VENDOR(SennheiserConstant.VENDOR, SennheiserCommandList.VENDOR),
	HARDWARE_REVISION(SennheiserConstant.HARDWARE_REVISION, SennheiserCommandList.HARDWARE_REVISION),
	FIRMWARE_VERSION(SennheiserConstant.FIRMWARE_VERSION, SennheiserCommandList.FIRMWARE_VERSION),
	OSC_VERSION(SennheiserConstant.OSC_VERSION, SennheiserCommandList.OSC_VERSION),
	DEVICE_TIME(SennheiserConstant.DEVICE_TIME, SennheiserCommandList.DEVICE_TIME),
	DEVICE_DATE(SennheiserConstant.DEVICE_DATE, SennheiserCommandList.DEVICE_DATE),
	DEVICE_INFORMATION(SennheiserConstant.DEVICE_INFORMATION, SennheiserCommandList.DEVICE_INFORMATION),
	DEVICE_POSITION(SennheiserConstant.DEVICE_POSITION, SennheiserCommandList.DEVICE_POSITION),
	DEVICE_NAME(SennheiserConstant.DEVICE_NAME, SennheiserCommandList.DEVICE_NAME),
	DEVICE_LOCATION(SennheiserConstant.DEVICE_LOCATION, SennheiserCommandList.DEVICE_LOCATION),
	DEVICE_LANGUAGE(SennheiserConstant.DEVICE_LANGUAGE, SennheiserCommandList.DEVICE_LANGUAGE),
	ROOM_IN_USE(SennheiserConstant.ROOM_IN_USE, SennheiserCommandList.ROOM_IN_USE),
	BEAM_ELEVATION(SennheiserConstant.BEAM_ELEVATION, SennheiserCommandList.BEAM_ELEVATION),
	BEAM_AZIMUTH(SennheiserConstant.BEAM_AZIMUTH, SennheiserCommandList.BEAM_AZIMUTH),
	INPUT_PEAK_LEVEL(SennheiserConstant.INPUT_PEAK_LEVEL, SennheiserCommandList.INPUT_PEAK_LEVEL),
	DANTE_AEC_REFERENCE_RMS_LEVEL(SennheiserConstant.DANTE_AEC_REFERENCE_RMS_LEVEL, SennheiserCommandList.DANTE_AEC_REFERENCE_RMS_LEVEL),
	IPV4_ADDRESS(SennheiserConstant.IPV4_ADDRESS, SennheiserCommandList.IPV4_ADDRESS),
	IPV4_DEFAULT_GATEWAY(SennheiserConstant.IPV4_DEFAULT_GATEWAY, SennheiserCommandList.IPV4_DEFAULT_GATEWAY),
	IPV4_NETMASK(SennheiserConstant.IPV4_NETMASK, SennheiserCommandList.IPV4_NETMASK),
	MAC_ADDRESS(SennheiserConstant.MAC_ADDRESS, SennheiserCommandList.MAC_ADDRESS),
	IPV4_INTERFACE_NAME(SennheiserConstant.IPV4_INTERFACE_NAME, SennheiserCommandList.IPV4_INTERFACE_NAME),
	;
	private final String name;
	private final String command;

	SennheiserPropertiesList(String name, String command) {
		this.name = name;
		this.command = command;
	}

	/**
	 * Retrieves {@link #name}
	 *
	 * @return value of {@link #name}
	 */
	public String getName() {
		return name;
	}

	/**
	 * Retrieves {@link #command}
	 *
	 * @return value of {@link #command}
	 */
	public String getCommand() {
		return command;
	}
	/**
	 * This method is used to get device metric group by name
	 *
	 * @param name is the name of device metric group that want to get
	 * @return DevicesMetricGroup is the device metric group that want to get
	 */
	public static SennheiserPropertiesList getByName(String name) {
		Optional<SennheiserPropertiesList> property = Arrays.stream(SennheiserPropertiesList.values()).filter(group -> group.getName().equals(name)).findFirst();
		if (property.isPresent()) {
			return property.get();
		} else {
			throw new IllegalStateException(String.format("control group %s is not supported.", name));
		}
	}
}