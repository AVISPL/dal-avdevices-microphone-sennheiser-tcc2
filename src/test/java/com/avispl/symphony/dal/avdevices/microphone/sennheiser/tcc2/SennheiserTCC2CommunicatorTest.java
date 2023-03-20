/*
 *  * Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.avispl.symphony.api.dal.dto.control.AdvancedControllableProperty;
import com.avispl.symphony.api.dal.dto.monitor.ExtendedStatistics;

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
		extendedStatistic = (ExtendedStatistics) sennheiserTCC2Communicator.getMultipleStatistics().get(0);
		Map<String, String> dynamicStatistic = extendedStatistic.getDynamicStatistics();
		List<AdvancedControllableProperty> advancedControllablePropertyList = extendedStatistic.getControllableProperties();
		Map<String, String> statistics = extendedStatistic.getStatistics();
	}
}
