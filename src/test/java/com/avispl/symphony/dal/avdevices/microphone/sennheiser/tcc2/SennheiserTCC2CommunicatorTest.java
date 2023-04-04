/*
 *  * Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.avispl.symphony.api.dal.dto.control.ControllableProperty;
import com.avispl.symphony.api.dal.dto.monitor.ExtendedStatistics;
import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.comom.SennheiserConstant;
import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.comom.SennheiserPropertiesList;

/**
 * SennheiserTCC2CommunicatorTest
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 3/15/2023
 * @since 1.0.0
 */
@Tag("RealDevice")
public class SennheiserTCC2CommunicatorTest {
	private ExtendedStatistics extendedStatistic;
	private SennheiserTCC2Communicator sennheiserTCC2Communicator;

	@BeforeEach
	void setUp() throws Exception {
		sennheiserTCC2Communicator = new SennheiserTCC2Communicator();
		sennheiserTCC2Communicator.setHost("127.0.0.1");
		sennheiserTCC2Communicator.init();
		sennheiserTCC2Communicator.connect();
	}

	@AfterEach
	void destroy() throws Exception {
		sennheiserTCC2Communicator.disconnect();
	}

	/**
	 * Test SennheiserTCC2Communicator.getMultipleStatistics get Statistic and DynamicStatistic success
	 * Expected retrieve monitoring data
	 */
	@Test
	void testSennheiserTCC2CommunicatorGetStatistic() throws Exception {
		sennheiserTCC2Communicator.setConfigManagement("true");
		extendedStatistic = (ExtendedStatistics) sennheiserTCC2Communicator.getMultipleStatistics().get(0);
		Map<String, String> statistics = extendedStatistic.getStatistics();
		Assert.assertEquals(36, statistics.size());
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.MANUFACTURER.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.PRODUCT_NAME.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.SERIAL_NUMBER.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.HARDWARE_REVISION.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.FIRMWARE_VERSION.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.OSC_VERSION.getName()));
		Assert.assertNotNull(statistics.get(SennheiserConstant.NETWORK + SennheiserConstant.HASH + SennheiserPropertiesList.MAC_ADDRESS.getName()));
		Assert.assertNotNull(statistics.get(SennheiserConstant.NETWORK + SennheiserConstant.HASH + SennheiserPropertiesList.IPV4_ADDRESS.getName()));
		Assert.assertNotNull(statistics.get(SennheiserConstant.NETWORK + SennheiserConstant.HASH + SennheiserPropertiesList.IPV4_INTERFACE_NAME.getName()));
		Assert.assertNotNull(statistics.get(SennheiserConstant.NETWORK + SennheiserConstant.HASH + SennheiserPropertiesList.IPV4_NETMASK.getName()));
		Assert.assertNotNull(statistics.get(SennheiserConstant.NETWORK + SennheiserConstant.HASH + SennheiserPropertiesList.IPV4_DEFAULT_GATEWAY.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.DEVICE_DATE.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.DEVICE_TIME.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.DEVICE_INFORMATION.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.DEVICE_POSITION.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.DEVICE_NAME.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.DEVICE_LOCATION.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.DEVICE_LANGUAGE.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.ROOM_IN_USE.getName()));
		Assert.assertNotNull(statistics.get(SennheiserConstant.AUDIO_SETTINGS + SennheiserConstant.HASH + SennheiserPropertiesList.BEAM_AZIMUTH.getName()));
		Assert.assertNotNull(statistics.get(SennheiserConstant.AUDIO_SETTINGS + SennheiserConstant.HASH + SennheiserPropertiesList.BEAM_ELEVATION.getName()));
		Assert.assertNotNull(statistics.get(SennheiserConstant.AUDIO_SETTINGS + SennheiserConstant.HASH + SennheiserPropertiesList.INPUT_PEAK_LEVEL.getName()));
		Assert.assertNotNull(statistics.get(SennheiserConstant.AUDIO_SETTINGS + SennheiserConstant.HASH + SennheiserPropertiesList.DANTE_AEC_REFERENCE_RMS_LEVEL.getName()));
	}

	/**
	 * Test SennheiserTCC2Communicator.getMultipleStatistics control Unmute success
	 * Expected control Unmute success
	 */
	@Test
	void tesControlUnMute() throws Exception {
		sennheiserTCC2Communicator.setConfigManagement("true");
		extendedStatistic = (ExtendedStatistics) sennheiserTCC2Communicator.getMultipleStatistics().get(0);
		String property = SennheiserConstant.AUDIO_SETTINGS + SennheiserConstant.HASH + SennheiserConstant.AUDIO_MUTE;

		String value = "0";
		ControllableProperty controllableProperty = new ControllableProperty();
		controllableProperty.setProperty(property);
		controllableProperty.setValue(value);
		sennheiserTCC2Communicator.controlProperty(controllableProperty);

		extendedStatistic = (ExtendedStatistics) sennheiserTCC2Communicator.getMultipleStatistics().get(0);
		Map<String, String> statistics = extendedStatistic.getStatistics();
		Assertions.assertEquals(value, statistics.get(property));
	}

	/**
	 * Test SennheiserTCC2Communicator.getMultipleStatistics control VoiceLift success
	 * Expected control VoiceLift success
	 */
	@Test
	void tesControlVoiceLift() throws Exception {
		sennheiserTCC2Communicator.setConfigManagement("true");
		extendedStatistic = (ExtendedStatistics) sennheiserTCC2Communicator.getMultipleStatistics().get(0);

		String property = SennheiserConstant.AUDIO_SETTINGS + SennheiserConstant.HASH + SennheiserConstant.TRU_VOICE_LIFT;
		String value = "0";
		ControllableProperty controllableProperty = new ControllableProperty();
		controllableProperty.setProperty(property);
		controllableProperty.setValue(value);
		sennheiserTCC2Communicator.controlProperty(controllableProperty);

		extendedStatistic = (ExtendedStatistics) sennheiserTCC2Communicator.getMultipleStatistics().get(0);
		Map<String, String> statistics = extendedStatistic.getStatistics();
		Assertions.assertEquals(value, statistics.get(property));
	}

	/**
	 * Test SennheiserTCC2Communicator.getMultipleStatistics control FarEndActiveLedMode success
	 * Expected control FarEndActiveLedMode success
	 */
	@Test
	void tesControlFarEndActiveLedMode() throws Exception {
		sennheiserTCC2Communicator.setConfigManagement("true");
		extendedStatistic = (ExtendedStatistics) sennheiserTCC2Communicator.getMultipleStatistics().get(0);

		String property = SennheiserConstant.AUDIO_SETTINGS + SennheiserConstant.HASH + SennheiserConstant.FAR_END_ACTIVITY_LED_MODE;
		String value = "0";
		ControllableProperty controllableProperty = new ControllableProperty();
		controllableProperty.setProperty(property);
		controllableProperty.setValue(value);
		sennheiserTCC2Communicator.controlProperty(controllableProperty);

		extendedStatistic = (ExtendedStatistics) sennheiserTCC2Communicator.getMultipleStatistics().get(0);
		Map<String, String> statistics = extendedStatistic.getStatistics();
		Assertions.assertEquals(value, statistics.get(property));
	}

	/**
	 * Test SennheiserTCC2Communicator.getMultipleStatistics control IdentifyDevice success
	 * Expected control IdentifyDevice success
	 */
	@Test
	void tesControlIdentifyDevice() throws Exception {
		sennheiserTCC2Communicator.setConfigManagement("true");
		extendedStatistic = (ExtendedStatistics) sennheiserTCC2Communicator.getMultipleStatistics().get(0);

		ControllableProperty controllableProperty = new ControllableProperty();
		String property = SennheiserConstant.DEVICE_SETTINGS + SennheiserConstant.HASH + SennheiserConstant.IDENTIFY_DEVICE;
		String value = "1";
		controllableProperty.setProperty(property);
		controllableProperty.setValue(value);
		sennheiserTCC2Communicator.controlProperty(controllableProperty);

		extendedStatistic = (ExtendedStatistics) sennheiserTCC2Communicator.getMultipleStatistics().get(0);
		Map<String, String> statistics = extendedStatistic.getStatistics();
		Assertions.assertEquals(value, statistics.get(property));
	}

	/**
	 * Test SennheiserTCC2Communicator.getMultipleStatistics control InputLevelGain success
	 * Expected control InputLevelGain success with min value
	 */
	@Test
	void tesControlInputLevelGainWithMinValue() throws Exception {
		sennheiserTCC2Communicator.setConfigManagement("true");
		extendedStatistic = (ExtendedStatistics) sennheiserTCC2Communicator.getMultipleStatistics().get(0);

		ControllableProperty controllableProperty = new ControllableProperty();
		String property = SennheiserConstant.AUDIO_SETTINGS + SennheiserConstant.HASH + SennheiserConstant.INPUT_LEVEL_GAIN_PRESET;
		String value = "-60";
		controllableProperty.setProperty(property);
		controllableProperty.setValue(value);
		sennheiserTCC2Communicator.controlProperty(controllableProperty);

		extendedStatistic = (ExtendedStatistics) sennheiserTCC2Communicator.getMultipleStatistics().get(0);
		Map<String, String> statistics = extendedStatistic.getStatistics();
		Assertions.assertEquals("-60", statistics.get(property));
	}

	/**
	 * Test SennheiserTCC2Communicator.getMultipleStatistics control InputLevelGain success
	 * Expected control InputLevelGain success with max value
	 */
	@Test
	void tesControlInputLevelGainWithMaxValue() throws Exception {
		sennheiserTCC2Communicator.setConfigManagement("true");
		extendedStatistic = (ExtendedStatistics) sennheiserTCC2Communicator.getMultipleStatistics().get(0);

		ControllableProperty controllableProperty = new ControllableProperty();
		String property = SennheiserConstant.AUDIO_SETTINGS + SennheiserConstant.HASH + SennheiserConstant.INPUT_LEVEL_GAIN_PRESET;
		String value = "10";
		controllableProperty.setProperty(property);
		controllableProperty.setValue(value);
		sennheiserTCC2Communicator.controlProperty(controllableProperty);

		extendedStatistic = (ExtendedStatistics) sennheiserTCC2Communicator.getMultipleStatistics().get(0);
		Map<String, String> statistics = extendedStatistic.getStatistics();
		Assertions.assertEquals("10", statistics.get(property));
	}

	/**
	 * Test SennheiserTCC2Communicator.getMultipleStatistics control LEDBrightness success
	 * Expected control LEDBrightness success
	 */
	@Test
	void tesControlLEDBrightnessWithOff() throws Exception {
		sennheiserTCC2Communicator.setConfigManagement("true");
		extendedStatistic = (ExtendedStatistics) sennheiserTCC2Communicator.getMultipleStatistics().get(0);

		ControllableProperty controllableProperty = new ControllableProperty();
		String property = SennheiserConstant.DEVICE_SETTINGS + SennheiserConstant.HASH + SennheiserConstant.LED_BRIGHTNESS;
		String value = "0";
		controllableProperty.setProperty(property);
		controllableProperty.setValue(value);
		sennheiserTCC2Communicator.controlProperty(controllableProperty);

		extendedStatistic = (ExtendedStatistics) sennheiserTCC2Communicator.getMultipleStatistics().get(0);
		Map<String, String> statistics = extendedStatistic.getStatistics();
		Assertions.assertEquals("Off", statistics.get(property));
	}

	/**
	 * Test SennheiserTCC2Communicator.getMultipleStatistics control LEDBrightness success
	 * Expected control LEDBrightness success
	 */
	@Test
	void tesControlLEDBrightness() throws Exception {
		sennheiserTCC2Communicator.setConfigManagement("true");
		extendedStatistic = (ExtendedStatistics) sennheiserTCC2Communicator.getMultipleStatistics().get(0);

		ControllableProperty controllableProperty = new ControllableProperty();
		String property = SennheiserConstant.DEVICE_SETTINGS + SennheiserConstant.HASH + SennheiserConstant.LED_BRIGHTNESS;
		String value = "4.0";
		controllableProperty.setProperty(property);
		controllableProperty.setValue(value);
		sennheiserTCC2Communicator.controlProperty(controllableProperty);

		extendedStatistic = (ExtendedStatistics) sennheiserTCC2Communicator.getMultipleStatistics().get(0);
		Map<String, String> statistics = extendedStatistic.getStatistics();
		Assertions.assertEquals("4", statistics.get(property));
	}

	/**
	 * Test SennheiserTCC2Communicator.getMultipleStatistics control MicOnLedColor success
	 * Expected control MicOnLedColor success
	 */
	@Test
	void tesControlMicOnLedColor() throws Exception {
		sennheiserTCC2Communicator.setConfigManagement("true");
		extendedStatistic = (ExtendedStatistics) sennheiserTCC2Communicator.getMultipleStatistics().get(0);

		ControllableProperty controllableProperty = new ControllableProperty();
		String property = SennheiserConstant.DEVICE_SETTINGS + SennheiserConstant.HASH + SennheiserConstant.MIC_ON_LED_COLOR;

		String value = "Red";
		controllableProperty.setProperty(property);
		controllableProperty.setValue(value);
		sennheiserTCC2Communicator.controlProperty(controllableProperty);
		extendedStatistic = (ExtendedStatistics) sennheiserTCC2Communicator.getMultipleStatistics().get(0);
		Map<String, String> statistics = extendedStatistic.getStatistics();
		Assertions.assertEquals(value, statistics.get(property));
	}

	/**
	 * Test SennheiserTCC2Communicator.getMultipleStatistics control CustomLedColor success
	 * Expected control MicCustomLedColor success
	 */
	@Test
	void testControlDeviceRestart() throws Exception {
		sennheiserTCC2Communicator.setConfigManagement("true");
		extendedStatistic = (ExtendedStatistics) sennheiserTCC2Communicator.getMultipleStatistics().get(0);

		ControllableProperty controllableProperty = new ControllableProperty();
		String property = SennheiserConstant.DEVICE_SETTINGS + SennheiserConstant.HASH + SennheiserConstant.DEVICE_RESTART;
		String value = "1";
		controllableProperty.setProperty(property);
		controllableProperty.setValue(value);
		sennheiserTCC2Communicator.controlProperty(controllableProperty);

		extendedStatistic = (ExtendedStatistics) sennheiserTCC2Communicator.getMultipleStatistics().get(0);
		Map<String, String> statistics = extendedStatistic.getStatistics();
		Assertions.assertEquals(value, statistics.get(property));
	}
}
