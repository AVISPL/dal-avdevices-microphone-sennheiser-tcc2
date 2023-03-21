/*
 *  Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.comom;

import java.util.Arrays;

public enum SennheiserLEDColorEnum {
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

	SennheiserLEDColorEnum(String name) {
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
	public static String getNameByValue(String value){
		return Arrays.stream(SennheiserLEDColorEnum.values()).filter(item->item.getName().equalsIgnoreCase(value)).findFirst().orElse(null).getName();
	}

}
