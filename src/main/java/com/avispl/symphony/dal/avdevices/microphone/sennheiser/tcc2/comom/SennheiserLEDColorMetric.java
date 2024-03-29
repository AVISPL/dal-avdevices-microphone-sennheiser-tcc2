/*
 *  Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.comom;

import java.util.Arrays;
import java.util.Optional;

/**
 * SennheiserLEDColorMetric enum defined the colors in controlling device for monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 3/14/2023
 * @since 1.0.0
 */
public enum SennheiserLEDColorMetric {
	BLUE("Blue"),
	CYAN("Cyan"),
	GREEN("Green"),
	ORANGE("Orange"),
	PINK("Pink"),
	RED("Red"),
	LIGHT_GREEN("Light green"),
	YELLOW("Yellow"),
	;
	private final String name;

	/**
	 * SennheiserLEDColorEnum instantiation
	 *
	 * @param name color
	 */
	SennheiserLEDColorMetric(String name) {
		this.name = name;
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
	 * get the uppercase value from the lowercase value in color enum
	 *
	 * @param value color value
	 * @return uppercase value
	 */
	public static String getNameByValue(String value) {
		Optional<SennheiserLEDColorMetric> sennheiserLEDColorMetric = Arrays.stream(SennheiserLEDColorMetric.values()).filter(item -> item.getName().equalsIgnoreCase(value)).findFirst();
		return sennheiserLEDColorMetric.isPresent() ? sennheiserLEDColorMetric.get().getName() : SennheiserConstant.NONE;
	}
}
