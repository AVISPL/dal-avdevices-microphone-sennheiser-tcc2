/*
 *  * Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.comom.SennheiserConstant;
import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.comom.SennheiserPropertiesList;
import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.audio.AudioDTO;
import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.device.DeviceDTO;
import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.device.identity.IdentityDTO;
import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.device.led.CustomColorDTO;
import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.device.led.MicMuteColorDTO;
import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.device.led.MicOnColorDTO;
import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.meter.MeterDTO;
import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.osc.OscDTO;

/**
 * Device Wrapper class includes device , osc , audio , meter information
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 3/14/2023
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceWrapper {
	private DeviceDTO device;
	private OscDTO osc;
	private AudioDTO audio;

	@JsonProperty("m")
	private MeterDTO meter;

	/**
	 * Retrieves {@link #device}
	 *
	 * @return value of {@link #device}
	 */
	public DeviceDTO getDevice() {
		return device;
	}

	/**
	 * Sets {@link #device} value
	 *
	 * @param device new value of {@link #device}
	 */
	public void setDevice(DeviceDTO device) {
		this.device = device;
	}

	/**
	 * Retrieves {@code {@link #osc}}
	 *
	 * @return value of {@link #osc}
	 */
	public OscDTO getOsc() {
		return osc;
	}

	/**
	 * Sets {@code osc}
	 *
	 * @param osc the {@code com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.osc.OscDTO} field
	 */
	public void setOsc(OscDTO osc) {
		this.osc = osc;
	}

	/**
	 * Retrieves {@code {@link #audio}}
	 *
	 * @return value of {@link #audio}
	 */
	public AudioDTO getAudio() {
		return audio;
	}

	/**
	 * Sets {@code audio}
	 *
	 * @param audio the {@code com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.audio.AudioDTO} field
	 */
	public void setAudio(AudioDTO audio) {
		this.audio = audio;
	}

	/**
	 * Retrieves {@code {@link #meter }}
	 *
	 * @return value of {@link #meter}
	 */
	public MeterDTO getMeter() {
		return meter;
	}

	/**
	 * Sets {@code beam}
	 *
	 * @param meter the {@code com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.meterDTO.BeamDTO} field
	 */
	public void setMeter(MeterDTO meter) {
		this.meter = meter;
	}

	/**
	 * get Object value by command Enum
	 *
	 * @param commandEnum command Enum value
	 * @return value of object
	 */
	public String getObjectByName(SennheiserPropertiesList commandEnum) {
		switch (commandEnum) {
			case SERIAL_NUMBER:
				return Optional.ofNullable(device.getIdentity()).map(IdentityDTO::getSerial).orElse(SennheiserConstant.NONE);
			case PRODUCT_NAME:
				return Optional.ofNullable(device.getIdentity()).map(IdentityDTO::getProduct).orElse(SennheiserConstant.NONE);
			case MANUFACTURER:
				return Optional.ofNullable(device.getIdentity()).map(IdentityDTO::getVendor).orElse(SennheiserConstant.NONE);
			case FIRMWARE_VERSION:
				return Optional.ofNullable(device.getIdentity()).map(IdentityDTO::getFirmware).orElse(SennheiserConstant.NONE);
			case HARDWARE_REVISION:
				return Optional.ofNullable(device.getIdentity()).map(IdentityDTO::getHwRevision).orElse(SennheiserConstant.NONE);
			case OSC_VERSION:
				return Optional.ofNullable(osc).map(OscDTO::getVersion).orElse(SennheiserConstant.NONE);
			case DEVICE_DATE:
				return Optional.ofNullable(device).map(DeviceDTO::getDate).orElse(SennheiserConstant.NONE);
			case DEVICE_INFORMATION:
				return Optional.ofNullable(device).map(DeviceDTO::getSystem).orElse(SennheiserConstant.NONE);
			case DEVICE_POSITION:
				return capitalizeFirstLetter(Optional.ofNullable(device).map(DeviceDTO::getPosition).orElse(SennheiserConstant.NONE));
			case DEVICE_NAME:
				return Optional.ofNullable(device).map(DeviceDTO::getName).orElse(SennheiserConstant.NONE);
			case DEVICE_LOCATION:
				return Optional.ofNullable(device).map(DeviceDTO::getLocation).orElse(SennheiserConstant.NONE);
			case DEVICE_LANGUAGE:
				return Optional.ofNullable(device).map(DeviceDTO::getLanguage).orElse(SennheiserConstant.NONE);
			case ROOM_IN_USE:
				return capitalizeFirstLetter(Optional.ofNullable(audio.isRoomInUse()).map(Object::toString).orElse(SennheiserConstant.NONE));
			case BEAM_ELEVATION:
				return Optional.ofNullable(meter.getBeam().getElevation()).map(Object::toString).orElse(SennheiserConstant.NONE);
			case BEAM_AZIMUTH:
				return Optional.ofNullable(meter.getBeam().getAzimuth()).map(Object::toString).orElse(SennheiserConstant.NONE);
			case INPUT_PEAK_LEVEL:
				return Optional.ofNullable(meter.getInput().getPeak()).map(Object::toString).orElse(SennheiserConstant.NONE);
			case DANTE_AEC_REFERENCE_RMS_LEVEL:
				return Optional.ofNullable(meter.getReference().getRms()).map(Object::toString).orElse(SennheiserConstant.NONE);
			case IPV4_ADDRESS:
				return convertListToString(device.getNetwork().getIpv4Address().getIpAddresses());
			case IPV4_DEFAULT_GATEWAY:
				return convertListToString(device.getNetwork().getIpv4Address().getGateWays());
			case IPV4_NETMASK:
				return convertListToString(device.getNetwork().getIpv4Address().getNetMasks());
			case MAC_ADDRESS:
				return convertListToString(device.getNetwork().getEthernet().getMacAddresses());
			case IPV4_INTERFACE_NAME:
				return convertListToString(device.getNetwork().getEthernet().getInterfaceNames());
			case IP_MODE:
				return convertListToString(device.getNetwork().getIpv4Address().getIpModes());
			case IDENTIFY_DEVICE:
				return Optional.ofNullable(device.getIdentification().isVisual()).map(Object::toString).orElse(SennheiserConstant.NONE);
			case LED_BRIGHTNESS:
				return Optional.ofNullable(device.getLed().getBrightness()).map(Object::toString).orElse(SennheiserConstant.NONE);
			case DEVICE_RESTART:
				return Optional.ofNullable(device.isRestart()).map(Object::toString).orElse(SennheiserConstant.NONE);
			case AUDIO_MUTE:
				return Optional.ofNullable(audio.isMute()).map(Object::toString).orElse(SennheiserConstant.NONE);
			case TRU_VOICE_LIFT:
				return Optional.ofNullable(audio.getVoiceLift().isActive()).map(Object::toString).orElse(SennheiserConstant.NONE);
			case MIC_MUTE_LED_COLOR:
				return Optional.ofNullable(device.getLed().getMicMuteColor()).map(MicMuteColorDTO::getColor).orElse(SennheiserConstant.NONE);
			case MIC_ON_LED_COLOR:
				return Optional.ofNullable(device.getLed().getMicOnColor()).map(MicOnColorDTO::getColor).orElse(SennheiserConstant.NONE);
			case LED_CUSTOM_COLOR:
				return Optional.ofNullable(device.getLed().getCustomColor()).map(CustomColorDTO::getColor).orElse(SennheiserConstant.NONE);
			case FAR_END_ACTIVITY_LED_MODE:
				return Optional.ofNullable(device.getLed().isActivity()).map(Object::toString).orElse(SennheiserConstant.NONE);
			case INPUT_LEVEL_GAIN_STATUS:
				return Optional.ofNullable(audio.getReference().isGainStatus()).map(Object::toString).orElse(SennheiserConstant.NONE);
			case INPUT_LEVEL_GAIN_PRESET:
				return Optional.ofNullable(audio.getReference().getGain()).map(Object::toString).orElse(SennheiserConstant.NONE);
			case DANTE_IPV4_ADDRESS:
				return convertListToString(audio.getOutput().getNetwork().getIpv4Address().getIpAddresses());
			case DANTE_IPV4_DEFAULT_GATEWAY:
				return convertListToString(audio.getOutput().getNetwork().getIpv4Address().getGateWays());
			case DANTE_IPV4_NETMASK:
				return convertListToString(audio.getOutput().getNetwork().getIpv4Address().getNetMasks());
			case DANTE_MAC_ADDRESS:
				return convertListToString(audio.getOutput().getNetwork().getEthernet().getMacAddresses());
			case DANTE_IPV4_INTERFACE_NAME:
				return convertListToString(audio.getOutput().getNetwork().getEthernet().getInterfaceNames());
			case DANTE_IP_MODE:
				return convertListToString(audio.getOutput().getNetwork().getIpv4Address().getIpModes());
		}
		return null;
	}

	/**
	 * convert value list to String
	 *
	 * @param list value list
	 * @return String after converting
	 */
	private String convertListToString(List<String> list) {
		if (list == null || list.isEmpty()) {
			return SennheiserConstant.NONE;
		}

		StringBuilder result = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if (i == list.size() - 1) {
				result.append(list.get(i));
			} else {
				result.append(list.get(i)).append(SennheiserConstant.COMMA);
			}
		}
		return result.toString();
	}

	/**
	 * capitalize the first letter of the word
	 *
	 * @param input value input
	 * @return string after converting
	 */
	public static String capitalizeFirstLetter(String input) {
		if (SennheiserConstant.NONE.equals(input)) {
			return input;
		}
		return input.substring(0, 1).toUpperCase() + input.substring(1);
	}
}