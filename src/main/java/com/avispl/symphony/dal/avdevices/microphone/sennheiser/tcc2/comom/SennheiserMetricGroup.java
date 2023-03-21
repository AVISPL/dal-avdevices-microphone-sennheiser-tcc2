/*
 *  Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.comom;

import java.util.Arrays;
import java.util.Optional;

/**
 * SennheiserMetricGroup
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 3/16/2023
 * @since 1.0.0
 */
public enum SennheiserMetricGroup {
	DEVICE_SETTINGS("DeviceSettings"),
	AUDIO_SETTINGS("AudioSettings");
	private final String name;

	/**
	 * Parameterized constructor
	 *
	 * @param name Name of monitoring metric group
	 */
	SennheiserMetricGroup(String name) {
		this.name = name;

	}

	/**
	 * retrieve {@code {@link #name}}
	 *
	 * @return value of {@link #name}
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * This method is used to get device metric group by name
	 *
	 * @param name is the name of device metric group that want to get
	 * @return DevicesMetricGroup is the device metric group that want to get
	 */
	public static SennheiserMetricGroup getByName(String name) {
		Optional<SennheiserMetricGroup> devicesMetricGroup = Arrays.stream(SennheiserMetricGroup.values()).filter(group -> group.getName().equals(name)).findFirst();
		if (devicesMetricGroup.isPresent()) {
			return devicesMetricGroup.get();
		} else {
			throw new IllegalStateException(String.format("control group %s is not supported.", name));
		}
	}
}
