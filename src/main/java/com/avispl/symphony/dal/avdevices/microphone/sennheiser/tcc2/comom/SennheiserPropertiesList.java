/*
 *  * Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.comom;

import java.util.Arrays;

/**
 * SennheiserPropertiesList enum defined the properties list for monitoring and controlling process
 * The property include name, command anh variable to check controlling or monitoring
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 3/14/2023
 * @since 1.0.0
 */
public enum SennheiserPropertiesList {
	SERIAL_NUMBER(SennheiserConstant.SERIAL_NUMBER, SennheiserCommands.SERIAL_NUMBER, false),
	PRODUCT_NAME(SennheiserConstant.PRODUCT_NAME, SennheiserCommands.PRODUCT_NAME, false),
	MANUFACTURER(SennheiserConstant.MANUFACTURER, SennheiserCommands.MANUFACTURER, false),
	HARDWARE_REVISION(SennheiserConstant.HARDWARE_REVISION, SennheiserCommands.HARDWARE_REVISION, false),
	FIRMWARE_VERSION(SennheiserConstant.FIRMWARE_VERSION, SennheiserCommands.FIRMWARE_VERSION, false),
	OSC_VERSION(SennheiserConstant.OSC_VERSION, SennheiserCommands.OSC_VERSION, false),
	DEVICE_TIME(SennheiserConstant.DEVICE_TIME, SennheiserCommands.DEVICE_TIME, false),
	DEVICE_DATE(SennheiserConstant.DEVICE_DATE, SennheiserCommands.DEVICE_DATE, false),
	DEVICE_INFORMATION(SennheiserConstant.DEVICE_INFORMATION, SennheiserCommands.DEVICE_INFORMATION, false),
	DEVICE_POSITION(SennheiserConstant.DEVICE_POSITION, SennheiserCommands.DEVICE_POSITION, false),
	DEVICE_NAME(SennheiserConstant.DEVICE_NAME, SennheiserCommands.DEVICE_NAME, false),
	DEVICE_LOCATION(SennheiserConstant.DEVICE_LOCATION, SennheiserCommands.DEVICE_LOCATION, false),
	DEVICE_LANGUAGE(SennheiserConstant.DEVICE_LANGUAGE, SennheiserCommands.DEVICE_LANGUAGE, false),
	ROOM_IN_USE(SennheiserConstant.ROOM_IN_USE, SennheiserCommands.ROOM_IN_USE, false),
	BEAM_ELEVATION(SennheiserConstant.BEAM_ELEVATION, SennheiserCommands.BEAM_ELEVATION, false),
	BEAM_AZIMUTH(SennheiserConstant.BEAM_AZIMUTH, SennheiserCommands.BEAM_AZIMUTH, false),
	INPUT_PEAK_LEVEL(SennheiserConstant.INPUT_PEAK_LEVEL, SennheiserCommands.INPUT_PEAK_LEVEL, false),
	DANTE_AEC_REFERENCE_RMS_LEVEL(SennheiserConstant.DANTE_AEC_REFERENCE_RMS_LEVEL, SennheiserCommands.DANTE_AEC_REFERENCE_RMS_LEVEL, false),
	IPV4_ADDRESS(SennheiserConstant.IPV4_ADDRESS, SennheiserCommands.IPV4_ADDRESS, false),
	IPV4_DEFAULT_GATEWAY(SennheiserConstant.IPV4_DEFAULT_GATEWAY, SennheiserCommands.IPV4_DEFAULT_GATEWAY, false),
	IPV4_NETMASK(SennheiserConstant.IPV4_NETMASK, SennheiserCommands.IPV4_NETMASK, false),
	MAC_ADDRESS(SennheiserConstant.MAC_ADDRESS, SennheiserCommands.MAC_ADDRESS, false),
	IPV4_INTERFACE_NAME(SennheiserConstant.IPV4_INTERFACE_NAME, SennheiserCommands.IPV4_INTERFACE_NAME, false),
	DANTE_IPV4_INTERFACE_NAME(SennheiserConstant.DANTE_IPV4_INTERFACE_NAME, SennheiserCommands.DANTE_IPV4_INTERFACE_NAME, false),
	DANTE_IPV4_ADDRESS(SennheiserConstant.DANTE_IPV4_ADDRESS, SennheiserCommands.DANTE_IPV4_ADDRESS, false),
	DANTE_MAC_ADDRESS(SennheiserConstant.DANTE_MAC_ADDRESS, SennheiserCommands.DANTE_MAC_ADDRESS, false),
	DANTE_IPV4_DEFAULT_GATEWAY(SennheiserConstant.DANTE_IPV4_DEFAULT_GATEWAY, SennheiserCommands.DANTE_IPV4_DEFAULT_GATEWAY, false),
	DANTE_IPV4_NETMASK(SennheiserConstant.DANTE_IPV4_NETMASK, SennheiserCommands.DANTE_IPV4_NETMASK, false),
	DANTE_IP_MODE(SennheiserConstant.DANTE_IP_MODE, SennheiserCommands.DANTE_IP_MODE, false),
	IP_MODE(SennheiserConstant.IP_MODE, SennheiserCommands.IP_MODE, false),
	IDENTIFY_DEVICE(SennheiserConstant.IDENTIFY_DEVICE, SennheiserCommands.IDENTIFY_DEVICE_CONTROLLING, true),
	LED_BRIGHTNESS(SennheiserConstant.LED_BRIGHTNESS, SennheiserCommands.LED_BRIGHTNESS_CONTROLLING, true),
	DEVICE_RESTART(SennheiserConstant.DEVICE_RESTART, SennheiserCommands.DEVICE_RESTART_CONTROLLING, true),
	AUDIO_MUTE(SennheiserConstant.AUDIO_MUTE, SennheiserCommands.AUDIO_MUTE_CONTROLLING, true),
	TRU_VOICE_LIFT(SennheiserConstant.TRU_VOICE_LIFT, SennheiserCommands.VOICE_LIFT_CONTROLLING, true),
	INPUT_LEVEL_GAIN(SennheiserConstant.INPUT_LEVEL_GAIN, SennheiserCommands.INPUT_LEVEL_GAIN_CONTROLLING, true),
	MIC_ON_LED_COLOR(SennheiserConstant.MIC_ON_LED_COLOR, SennheiserCommands.MIC_ON_LED_COLOR_CONTROLLING, true),
	MIC_MUTE_LED_COLOR(SennheiserConstant.MIC_MUTE_LED_COLOR, SennheiserCommands.MIC_MUTE_LED_COLOR_CONTROLLING, true),
	LED_CUSTOM_COLOR(SennheiserConstant.LED_CUSTOM_COLOR, SennheiserCommands.LED_CUSTOM_COLOR_CONTROLLING, true),
	FAR_END_ACTIVITY_LED_MODE(SennheiserConstant.FAR_END_ACTIVITY_LED_MODE, SennheiserCommands.FAR_END_ACTIVITY_LED_MODE_CONTROLLING, true),
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
		return Arrays.stream(SennheiserPropertiesList.values()).filter(group -> group.getName().equals(name)).findFirst()
				.orElseThrow(() -> new IllegalStateException(String.format("control group %s is not supported.", name)));
	}
}