/*
 *  * Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.comom;

/**
 * SennheiserCommands class provides the commands list during the monitoring and controlling process
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 3/14/2023
 * @since 1.0.0
 */
public class SennheiserCommands {
	public static final String SERIAL_NUMBER = "{\"device\":{\"identity\":{\"serial\":null}}}\"";
	public static final String PRODUCT_NAME = "{\"device\":{\"identity\":{\"product\":null}}}\"";
	public static final String MANUFACTURER = "{\"device\":{\"identity\":{\"vendor\":null}}}\"";
	public static final String HARDWARE_REVISION = "{\"device\":{\"identity\":{\"hw_revision\":null}}}\"";
	public static final String FIRMWARE_VERSION = "{\"device\":{\"identity\":{\"version\":null}}}\"";
	public static final String OSC_VERSION = "{\"osc\":{\"version\":null}}\"";
	public static final String DEVICE_TIME = "{\"device\":{\"time\":null}}\"";
	public static final String DEVICE_DATE = "{\"device\":{\"date\":null}}\"";
	public static final String DEVICE_INFORMATION = "{\"device\":{\"system\":null}}\"";
	public static final String DEVICE_POSITION = "{\"device\":{\"position\":null}}\"";
	public static final String DEVICE_NAME = "{\"device\":{\"name\":null}}\"";
	public static final String DEVICE_LOCATION = "{\"device\":{\"location\":null}}\"";
	public static final String DEVICE_LANGUAGE = "{\"device\":{\"language\":null}}\"";
	public static final String ROOM_IN_USE = "{\"audio\":{\"room_in_use\":null}}\"";
	public static final String BEAM_ELEVATION = "{\"m\":{\"beam\":{\"elevation\":null}}}\"";
	public static final String BEAM_AZIMUTH = "{\"m\":{\"beam\":{\"azimuth\":null}}}\"";
	public static final String INPUT_PEAK_LEVEL = "{\"m\":{\"in1\":{\"peak\":null}}}\"";
	public static final String DANTE_AEC_REFERENCE_RMS_LEVEL = "{\"m\":{\"ref1\":{\"rms\":null}}}\"";
	public static final String IPV4_ADDRESS = "{\"device\":{\"network\":{\"ipv4\":{\"ipaddr\":null}}}}\"";
	public static final String IPV4_NETMASK = "{\"device\":{\"network\":{\"ipv4\":{\"netmask\":null}}}}\"";
	public static final String IPV4_DEFAULT_GATEWAY = "{\"device\":{\"network\":{\"ipv4\":{\"gateway\":null}}}}\"";
	public static final String MAC_ADDRESS = "{\"device\":{\"network\":{\"ether\":{\"macs\":null}}}}\"";
	public static final String IPV4_INTERFACE_NAME = "{\"device\":{\"network\":{\"ether\":{\"interfaces\":null}}}}\"";
	public static final String DANTE_IPV4_INTERFACE_NAME = "{\"audio\":{\"out2\":{\"network\":{\"ether\":{\"interfaces\":null}}}}}\"";
	public static final String DANTE_IPV4_ADDRESS = "{\"audio\":{\"out2\":{\"network\":{\"ipv4\":{\"ipaddr\":null}}}}}\"";
	public static final String DANTE_MAC_ADDRESS = "{\"audio\":{\"out2\":{\"network\":{\"ether\":{\"macs\":null}}}}}\"";
	public static final String DANTE_IPV4_DEFAULT_GATEWAY = "{\"audio\":{\"out2\":{\"network\":{\"ipv4\":{\"gateway\":null}}}}}\"";
	public static final String DANTE_IPV4_NETMASK = "{\"audio\":{\"out2\":{\"network\":{\"ipv4\":{\"netmask\":null}}}}}\"";
	public static final String DANTE_IP_MODE = "{\"audio\":{\"out2\":{\"network\":{\"ipv4\":{\"auto\":null}}}}}\"";
	public static final String IP_MODE = "{\"device\":{\"network\":{\"ipv4\":{\"auto\":null}}}}\"";
	public static final String IDENTIFY_DEVICE_CONTROLLING = "{\"device\":{\"identification\":{\"visual\":null}}}\"";
	public static final String LED_BRIGHTNESS_CONTROLLING = "{\"device\":{\"led\":{\"brightness\":null}}}\"";
	public static final String DEVICE_RESTART_CONTROLLING = "{\"device\":{\"restart\":null}}\"";
	public static final String AUDIO_MUTE_CONTROLLING = "{\"audio\":{\"mute\":null}}\"";
	public static final String VOICE_LIFT_CONTROLLING = "{\"audio\":{\"voice_lift\":{\"active\":null}}}\"";
	public static final String INPUT_LEVEL_GAIN_STATUS_CONTROLLING = "{\"audio\":{\"ref1\":{\"farend_auto_adjust_enable\":null}}}\"";
	public static final String INPUT_LEVEL_GAIN_CONTROLLING = "{\"audio\":{\"ref1\":{\"gain\":null}}}\"";
	public static final String MIC_ON_LED_COLOR_CONTROLLING = "{\"device\":{\"led\":{\"mic_on\":{\"color\":null}}}}\"";
	public static final String MIC_MUTE_LED_COLOR_CONTROLLING = "{\"device\":{\"led\":{\"mic_mute\":{\"color\":null}}}}\"";
	public static final String LED_CUSTOM_COLOR_CONTROLLING = "{\"device\":{\"led\":{\"custom\":{\"color\":null}}}}\"";
	public static final String FAR_END_ACTIVITY_LED_MODE_CONTROLLING = "{\"device\":{\"led\":{\"show_farend_activity\":null}}}\"";
}