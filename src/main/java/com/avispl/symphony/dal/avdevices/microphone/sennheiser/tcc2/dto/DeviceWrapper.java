/*
 *  * Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.comom.SennheiserPropertiesList;
import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.audio.AudioDTO;
import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.device.DeviceDTO;
import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.meter.MeterDTO;
import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.osc.OscDTO;

/**
 * Device Wrapper class provides during the monitoring and controlling process
 * Device Wrapper class includes device , osc , audio , meter information
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 3/14/2023
 * @since 1.0.0
 */
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

	public String getObjectByName(SennheiserPropertiesList commandEnum) {
		switch (commandEnum) {
			case SERIAL_NUMBER:
				return device.getIdentity().getSerial();
			case PRODUCT_NAME:
				return device.getIdentity().getProduct();
			case VENDOR:
				return device.getIdentity().getVendor();
			case FIRMWARE_VERSION:
				return device.getIdentity().getFirmware();
			case HARDWARE_REVISION:
				return device.getIdentity().getHwRevision();
			case OSC_VERSION:
				return osc.getVersion();
			case DEVICE_DATE:
				return device.getDate();
			case DEVICE_TIME:
				return convertNumberOfSecondsToTimeStamp(device.getTime());
			case DEVICE_INFORMATION:
				return device.getSystem();
			case DEVICE_POSITION:
				return device.getPosition();
			case DEVICE_NAME:
				return device.getName();
			case DEVICE_LOCATION:
				return device.getLocation();
			case DEVICE_LANGUAGE:
				return device.getLanguage();
			case ROOM_IN_USE:
				return String.valueOf(audio.isRoomInUse());
			case BEAM_ELEVATION:
				return String.valueOf(meter.getBeam().getElevation());
			case BEAM_AZIMUTH:
				return String.valueOf(meter.getBeam().getAzimuth());
			case INPUT_PEAK_LEVEL:
				return String.valueOf(meter.getInput().getPeak());
			case DANTE_AEC_REFERENCE_RMS_LEVEL:
				return String.valueOf(meter.getReference().getRms());
			case IPV4_ADDRESS:
				return device.getNetwork().getIpv4Address().getIpAddresses().get(0);
			case IPV4_DEFAULT_GATEWAY:
				return device.getNetwork().getIpv4Address().getGateWays().get(0);
			case IPV4_NETMASK:
				return device.getNetwork().getIpv4Address().getNetMasks().get(0);
			case MAC_ADDRESS:
				return device.getNetwork().getEthernet().getMacAddresses().get(0);
			case IPV4_INTERFACE_NAME:
				return device.getNetwork().getEthernet().getInterfaceNames().get(0);
		}
		return null;
	}

	/**
	 * convert number of seconds from 1/1/2000 to now
	 */
	private String convertNumberOfSecondsToTimeStamp(long numberOfSeconds) {
		LocalDateTime startDate = LocalDateTime.of(2000, Month.JANUARY, 1, 0, 0, 0);
		Duration duration = Duration.ofSeconds(numberOfSeconds);
		LocalDateTime resultDate = startDate.plus(duration);
		ZonedDateTime zonedDateTime = resultDate.atZone(ZoneId.systemDefault());
		return DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss z yyyy", Locale.US).format(zonedDateTime);
	}
}