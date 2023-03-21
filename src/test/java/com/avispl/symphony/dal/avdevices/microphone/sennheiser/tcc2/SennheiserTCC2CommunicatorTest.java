/*
 *  * Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.avispl.symphony.api.dal.dto.control.AdvancedControllableProperty;
import com.avispl.symphony.api.dal.dto.monitor.ExtendedStatistics;
import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.comom.SennheiserPropertiesList;

/**
 * SennheiserTCC2CommunicatorTest
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 3/15/2023
 * @since 1.0.0
 */
public class SennheiserTCC2CommunicatorTest {
	private ExtendedStatistics extendedStatistic;
	private SennheiserTCC2Communicator sennheiserTCC2Communicator;

	@BeforeEach
	void setUp() throws Exception {
		sennheiserTCC2Communicator = new SennheiserTCC2Communicator();
		sennheiserTCC2Communicator.setHost("10.34.41.131");
		sennheiserTCC2Communicator.init();
		sennheiserTCC2Communicator.connect();
	}

	@AfterEach
	void destroy() throws Exception {
		sennheiserTCC2Communicator.disconnect();
	}

	@Tag("RealDevice")
	@Test
	void testSennheiserTCC2CommunicatorGetStatistic() throws Exception {
		extendedStatistic = (ExtendedStatistics) sennheiserTCC2Communicator.getMultipleStatistics().get(0);
		Map<String, String> dynamicStatistic = extendedStatistic.getDynamicStatistics();
		List<AdvancedControllableProperty> advancedControllablePropertyList = extendedStatistic.getControllableProperties();
		Map<String, String> statistics = extendedStatistic.getStatistics();
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.VENDOR.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.PRODUCT_NAME.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.SERIAL_NUMBER.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.HARDWARE_REVISION.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.FIRMWARE_VERSION.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.OSC_VERSION.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.MAC_ADDRESS.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.IPV4_ADDRESS.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.IPV4_INTERFACE_NAME.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.IPV4_NETMASK.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.IPV4_DEFAULT_GATEWAY.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.DEVICE_DATE.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.DEVICE_TIME.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.DEVICE_INFORMATION.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.DEVICE_POSITION.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.DEVICE_NAME.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.DEVICE_LOCATION.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.DEVICE_LANGUAGE.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.ROOM_IN_USE.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.BEAM_AZIMUTH.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.BEAM_ELEVATION.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.INPUT_PEAK_LEVEL.getName()));
		Assert.assertNotNull(statistics.get(SennheiserPropertiesList.DANTE_AEC_REFERENCE_RMS_LEVEL.getName()));
	}
}
