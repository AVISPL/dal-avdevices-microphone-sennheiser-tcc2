/*
 *  * Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.comom;

import java.util.Arrays;
import java.util.Optional;

/**
 * SennheiserPropertiesList enum defined the properties list for monitoring and controlling process
 * The property include name, command anh variable to check controlling or monitoring
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 3/14/2023
 * @since 1.0.0
 */
public enum SennheiserPropertiesList {
	SERIAL_NUMBER(SennheiserConstant.SERIAL_NUMBER, SennheiserCommandList.SERIAL_NUMBER, false),
	PRODUCT_NAME(SennheiserConstant.PRODUCT_NAME, SennheiserCommandList.PRODUCT_NAME, false),
	VENDOR(SennheiserConstant.VENDOR, SennheiserCommandList.VENDOR, false),
	HARDWARE_REVISION(SennheiserConstant.HARDWARE_REVISION, SennheiserCommandList.HARDWARE_REVISION, false),
	FIRMWARE_VERSION(SennheiserConstant.FIRMWARE_VERSION, SennheiserCommandList.FIRMWARE_VERSION, false),
	OSC_VERSION(SennheiserConstant.OSC_VERSION, SennheiserCommandList.OSC_VERSION, false),
	DEVICE_TIME(SennheiserConstant.DEVICE_TIME, SennheiserCommandList.DEVICE_TIME, false),
	DEVICE_DATE(SennheiserConstant.DEVICE_DATE, SennheiserCommandList.DEVICE_DATE, false),
	DEVICE_INFORMATION(SennheiserConstant.DEVICE_INFORMATION, SennheiserCommandList.DEVICE_INFORMATION, false),
	DEVICE_POSITION(SennheiserConstant.DEVICE_POSITION, SennheiserCommandList.DEVICE_POSITION, false),
	DEVICE_NAME(SennheiserConstant.DEVICE_NAME, SennheiserCommandList.DEVICE_NAME, false),
	DEVICE_LOCATION(SennheiserConstant.DEVICE_LOCATION, SennheiserCommandList.DEVICE_LOCATION, false),
	DEVICE_LANGUAGE(SennheiserConstant.DEVICE_LANGUAGE, SennheiserCommandList.DEVICE_LANGUAGE, false),
	ROOM_IN_USE(SennheiserConstant.ROOM_IN_USE, SennheiserCommandList.ROOM_IN_USE, false),
	BEAM_ELEVATION(SennheiserConstant.BEAM_ELEVATION, SennheiserCommandList.BEAM_ELEVATION, false),
	BEAM_AZIMUTH(SennheiserConstant.BEAM_AZIMUTH, SennheiserCommandList.BEAM_AZIMUTH, false),
	INPUT_PEAK_LEVEL(SennheiserConstant.INPUT_PEAK_LEVEL, SennheiserCommandList.INPUT_PEAK_LEVEL, false),
	DANTE_AEC_REFERENCE_RMS_LEVEL(SennheiserConstant.DANTE_AEC_REFERENCE_RMS_LEVEL, SennheiserCommandList.DANTE_AEC_REFERENCE_RMS_LEVEL, false),
	IPV4_ADDRESS(SennheiserConstant.IPV4_ADDRESS, SennheiserCommandList.IPV4_ADDRESS, false),
	IPV4_DEFAULT_GATEWAY(SennheiserConstant.IPV4_DEFAULT_GATEWAY, SennheiserCommandList.IPV4_DEFAULT_GATEWAY, false),
	IPV4_NETMASK(SennheiserConstant.IPV4_NETMASK, SennheiserCommandList.IPV4_NETMASK, false),
	MAC_ADDRESS(SennheiserConstant.MAC_ADDRESS, SennheiserCommandList.MAC_ADDRESS, false),
	IPV4_INTERFACE_NAME(SennheiserConstant.IPV4_INTERFACE_NAME, SennheiserCommandList.IPV4_INTERFACE_NAME, false),
	IDENTIFY_DEVICE(SennheiserConstant.IDENTIFY_DEVICE, SennheiserCommandList.IDENTIFY_DEVICE_CONTROLLING, true),
	LED_BRIGHTNESS(SennheiserConstant.LED_BRIGHTNESS, SennheiserCommandList.LED_BRIGHTNESS_CONTROLLING, true),
	DEVICE_RESTART(SennheiserConstant.DEVICE_RESTART, SennheiserCommandList.DEVICE_RESTART_CONTROLLING, true),
	AUDIO_MUTE(SennheiserConstant.AUDIO_MUTE, SennheiserCommandList.AUDIO_MUTE_CONTROLLING, true),
	VOICE_LIFT(SennheiserConstant.VOICE_LIFT, SennheiserCommandList.VOICE_LIFT_CONTROLLING, true),
	INPUT_LEVEL_GAIN(SennheiserConstant.INPUT_LEVEL_GAIN, SennheiserCommandList.INPUT_LEVEL_GAIN_CONTROLLING, true),
	MIC_ON_LED_COLOR(SennheiserConstant.MIC_ON_LED_COLOR, SennheiserCommandList.MIC_ON_LED_COLOR_CONTROLLING, true),
	MIC_MUTE_LED_COLOR(SennheiserConstant.MIC_MUTE_LED_COLOR, SennheiserCommandList.MIC_MUTE_LED_COLOR_CONTROLLING, true),
	LED_CUSTOM_COLOR(SennheiserConstant.LED_CUSTOM_COLOR, SennheiserCommandList.LED_CUSTOM_COLOR_CONTROLLING, true),
	FAR_END_ACTIVITY_LED_MODE(SennheiserConstant.FAR_END_ACTIVITY_LED_MODE, SennheiserCommandList.FAR_END_ACTIVITY_LED_MODE_CONTROLLING, true),
	;
	private final String name;
	private final String command;
	private boolean isControl;

	/**
	 * SennheiserPropertiesList instantiation
	 *
	 * @param name property name
	 * @param command property command
	 * @param isControl controlling or monitoring
	 */
	SennheiserPropertiesList(String name, String command, boolean isControl) {
		this.name = name;
		this.command = command;
		this.isControl = isControl;
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
	 * Retrieves {@code {@link #isControl}}
	 *
	 * @return value of {@link #isControl}
	 */
	public boolean isControl() {
		return isControl;
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