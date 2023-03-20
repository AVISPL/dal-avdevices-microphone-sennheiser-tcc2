/*
 *  * Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2;

import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.avispl.symphony.api.dal.control.Controller;
import com.avispl.symphony.api.dal.dto.control.AdvancedControllableProperty;
import com.avispl.symphony.api.dal.dto.control.ControllableProperty;
import com.avispl.symphony.api.dal.dto.monitor.ExtendedStatistics;
import com.avispl.symphony.api.dal.dto.monitor.Statistics;
import com.avispl.symphony.api.dal.monitor.Monitorable;
import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.comom.SennheiserConstant;
import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.comom.SennheiserPropertiesList;
import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.DeviceWrapper;
import com.avispl.symphony.dal.communicator.SocketCommunicator;
import com.avispl.symphony.dal.util.StringUtils;

/**
 * An implementation of SennheiserTCC2Communicator to provide communication and interaction with SennheiserTCC2 device
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 3/13/2022
 * @since 1.0.0
 */
public class SennheiserTCC2Communicator extends SocketCommunicator implements Monitorable, Controller {

	ObjectMapper objectMapper = new ObjectMapper();
	private ExtendedStatistics localExtendedStatistics;
	private final ReentrantLock reentrantLock = new ReentrantLock();
	private boolean isEmergencyDelivery;
	private int currentGetMultipleInPollingInterval = 0;
	private int pollingIntervalInIntValue;
	private int currentCommandIndex = 0;
	private final Set<String> failedMonitor = new HashSet<>();
	private int localCachedFailedMonitor = 0;
	private ExecutorService fetchingDataExSer;
	private ExecutorService timeoutManagementExSer;
	private int defaultConfigTimeout;
	private int currentCachingLifetime;
	private int countControlUnavailable = 0;
	/**
	 * Pool for keeping all the async operations in, to track any operations in progress and cancel them if needed
	 */
	private final List<Future> devicesExecutionPool = new CopyOnWriteArrayList<>();
	/**
	 * store pollingInterval adapter properties
	 */
	private String pollingInterval;
	/**
	 * Apply default delay in between of all the commands performed by the adapter.
	 */
	private long commandsCoolDownDelay = 200;
	/**
	 * a variable to check the adapter init
	 */
	private boolean isFirstInit;
	/**
	 * store configManagement adapter properties
	 */
	private String configManagement;
	/**
	 * store cachingLifetime adapter properties
	 */
	private String cachingLifetime;
	/**
	 * store delayTimeInterVal adapter properties
	 */
	private String coolDownDelay;
	/**
	 * store configTimeout adapter properties
	 */
	private String configTimeout;
	/**
	 * Local cache stores data after a period of time
	 */
	private final Map<String, String> localCacheMapOfPropertyNameAndValue = new HashMap<>();

	/**
	 * Retrieves {@code {@link #configManagement}}
	 *
	 * @return value of {@link #configManagement}
	 */
	public String getConfigManagement() {
		return configManagement;
	}

	/**
	 * Sets {@code configManagement}
	 *
	 * @param configManagement the {@code java.lang.String} field
	 */
	public void setConfigManagement(String configManagement) {
		this.configManagement = configManagement;
	}

	/**
	 * Retrieves {@code {@link #cachingLifetime}}
	 *
	 * @return value of {@link #cachingLifetime}
	 */
	public String getCachingLifetime() {
		return cachingLifetime;
	}

	/**
	 * Sets {@code cachingLifetime}
	 *
	 * @param cachingLifetime the {@code java.lang.String} field
	 */
	public void setCachingLifetime(String cachingLifetime) {
		this.cachingLifetime = cachingLifetime;
	}

	/**
	 * Retrieves {@code {@link #coolDownDelay}}
	 *
	 * @return value of {@link #coolDownDelay}
	 */
	public String getCoolDownDelay() {
		return coolDownDelay;
	}

	/**
	 * Sets {@code coolDownDelay}
	 *
	 * @param coolDownDelay the {@code java.lang.String} field
	 */
	public void setCoolDownDelay(String coolDownDelay) {
		this.coolDownDelay = coolDownDelay;
	}

	/**
	 * Retrieves {@code {@link #configTimeout}}
	 *
	 * @return value of {@link #configTimeout}
	 */
	public String getConfigTimeout() {
		return configTimeout;
	}

	/**
	 * Sets {@code configTimeout}
	 *
	 * @param configTimeout the {@code java.lang.String} field
	 */
	public void setConfigTimeout(String configTimeout) {
		this.configTimeout = configTimeout;
	}

	/**
	 * Retrieves {@code {@link #pollingInterval}}
	 *
	 * @return value of {@link #pollingInterval}
	 */
	public String getPollingInterval() {
		return pollingInterval;
	}

	/**
	 * Sets {@code pollingInterval}
	 *
	 * @param pollingInterval the {@code java.lang.String} field
	 */
	public void setPollingInterval(String pollingInterval) {
		this.pollingInterval = pollingInterval;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void internalInit() throws Exception {
		fetchingDataExSer = Executors.newFixedThreadPool(1);
		timeoutManagementExSer = Executors.newFixedThreadPool(1);
		isFirstInit = false;
		super.internalInit();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void internalDestroy() {
		if (localExtendedStatistics != null && localExtendedStatistics.getStatistics() != null && localExtendedStatistics.getControllableProperties() != null) {
			localExtendedStatistics.getStatistics().clear();
			localExtendedStatistics.getControllableProperties().clear();
		}
		if (!localCacheMapOfPropertyNameAndValue.isEmpty()) {
			localCacheMapOfPropertyNameAndValue.clear();
		}
		failedMonitor.clear();
		try {
			fetchingDataExSer.shutdownNow();
			timeoutManagementExSer.shutdownNow();
		} catch (Exception e) {
			logger.warn("Unable to end the TCP connection.", e);
		} finally {
			super.internalDestroy();
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 *
	 * Check for available devices before retrieving the value
	 * ping latency information to Symphony
	 */
	@Override
	public int ping() throws Exception {
		if (isInitialized()) {
			long pingResultTotal = 0L;

			for (int i = 0; i < this.getPingAttempts(); i++) {
				long startTime = System.currentTimeMillis();

				try (Socket puSocketConnection = new Socket(this.host, this.getPort())) {
					puSocketConnection.setSoTimeout(this.getPingTimeout());
					if (puSocketConnection.isConnected()) {
						long pingResult = System.currentTimeMillis() - startTime;
						pingResultTotal += pingResult;
						if (this.logger.isTraceEnabled()) {
							this.logger.trace(String.format("PING OK: Attempt #%s to connect to %s on port %s succeeded in %s ms", i + 1, host, this.getPort(), pingResult));
						}
					} else {
						if (this.logger.isDebugEnabled()) {
							this.logger.debug(String.format("PING DISCONNECTED: Connection to %s did not succeed within the timeout period of %sms", host, this.getPingTimeout()));
						}
						return this.getPingTimeout();
					}
				} catch (SocketTimeoutException | ConnectException tex) {
					if (this.logger.isDebugEnabled()) {
						this.logger.error(String.format("PING TIMEOUT: Connection to %s did not succeed within the timeout period of %sms", host, this.getPingTimeout()));
					}
					throw new SocketTimeoutException("Connection timed out");
				} catch (Exception e) {
					if (this.logger.isDebugEnabled()) {
						this.logger.error(String.format("PING TIMEOUT: Connection to %s did not succeed, UNKNOWN ERROR %s: ", host, e.getMessage()));
					}
					return this.getPingTimeout();
				}
			}
			return Math.max(1, Math.toIntExact(pingResultTotal / this.getPingAttempts()));
		} else {
			throw new IllegalStateException("Cannot use device class without calling init() first");
		}
	}

	/**
	 * Constructor set the TCP/IP port to be used as well the default
	 */
	public SennheiserTCC2Communicator() {
		super();
		this.setPort(45);

		// set list of command success strings (included at the end of response when command succeeds, typically ending with command prompt)
		this.setCommandSuccessList(Collections.singletonList("\r\n"));
		// set list of error response strings (included at the end of response when command fails, typically ending with command prompt)
		this.setCommandErrorList(Collections.singletonList("\r\n"));
	}

	@Override
	public void controlProperty(ControllableProperty controllableProperty) throws Exception {
		reentrantLock.lock();
		try {
			if (localExtendedStatistics == null) {
				return;
			}

		} finally {
			reentrantLock.unlock();
		}
	}

	/**
	 * This method is recalled by Symphony to control a list of properties
	 *
	 * @param controllableProperties This is the list of properties to be controlled
	 * @return byte This returns the calculated xor checksum.
	 */
	@Override
	public void controlProperties(List<ControllableProperty> controllableProperties) throws Exception {
		if (CollectionUtils.isEmpty(controllableProperties)) {
			throw new IllegalArgumentException("ControllableProperties can not be null or empty");
		}
		for (ControllableProperty p : controllableProperties) {
			try {
				controlProperty(p);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Statistics> getMultipleStatistics() throws Exception {
		ExtendedStatistics extendedStatistics = new ExtendedStatistics();
		Map<String, String> stats = new HashMap<>();
		List<AdvancedControllableProperty> advancedControllableProperties = new ArrayList<>();
		if (!isEmergencyDelivery) {
			convertCacheLifetime();
			convertDelayTime();
			convertConfigTimeout();
			convertPollingInterval();
			failedMonitor.clear();
			populateData();

			if (!isFirstInit && pollingIntervalInIntValue >1 && currentGetMultipleInPollingInterval<pollingIntervalInIntValue ) {
				isFirstInit = true;
				return Collections.singletonList(new ExtendedStatistics());
			}
			if (localCachedFailedMonitor == currentCommandIndex && currentGetMultipleInPollingInterval == pollingIntervalInIntValue) {
				//Handle the case where all properties receive an error response and the case where 2 connections run in parallel to the device
				isFirstInit = false;
				countControlUnavailable++;
				if (countControlUnavailable > currentCachingLifetime) {
					localCacheMapOfPropertyNameAndValue.clear();
				}
			}
			else {
				populateMonitoringAndControllingData(stats);
				countControlUnavailable = 0;
			}
			extendedStatistics.setStatistics(stats);
			extendedStatistics.setControllableProperties(advancedControllableProperties);
			localExtendedStatistics = extendedStatistics;
		}

		return Collections.singletonList(localExtendedStatistics);
	}

	/**
	 * Using multi thread to implement get request
	 * Thread 1 : Retrieve data
	 * Thread 2 : Waiting for status of thread 1
	 */
	private void populateData() {
		List<SennheiserPropertiesList> commands = Arrays.asList(SennheiserPropertiesList.values());
		Future manageTimeOutWorkerThread;
		int range = 0;
		if (currentGetMultipleInPollingInterval == pollingIntervalInIntValue - 1) {
			range = commands.size();
		}
		if (currentGetMultipleInPollingInterval == pollingIntervalInIntValue) {
			devicesExecutionPool.clear();
			currentGetMultipleInPollingInterval = 0;
			localCachedFailedMonitor = 0;
			range = 0;
			currentCommandIndex = 0;
		}
		int intervalIndex = currentGetMultipleInPollingInterval * commands.size() / pollingIntervalInIntValue;
		if (range == 0) {
			range = (currentGetMultipleInPollingInterval + SennheiserConstant.NUMBER_ONE) * commands.size() / pollingIntervalInIntValue;
		}
		for (int i = intervalIndex; i < range; i++) {
			SennheiserPropertiesList commandIndex = commands.get(i);
			currentCommandIndex++;

			devicesExecutionPool.add(fetchingDataExSer.submit(() -> {
				retrieveDataByCommandName(commandIndex);
			}));

			manageTimeOutWorkerThread = timeoutManagementExSer.submit(() -> {
				int timeoutCount = 1;
				while (!devicesExecutionPool.get(devicesExecutionPool.size() - SennheiserConstant.ORDINAL_TO_INDEX_CONVERT_FACTOR).isDone() && timeoutCount <= defaultConfigTimeout) {
					try {
						Thread.sleep(100);
						// The thread waits until the controlProperty() method successfully controls.
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					timeoutCount++;
				}
				//If the Future is not completed after the defaultConfigTimeout =>  update the failedMonitor and destroy the connection.
				int lastIndex = devicesExecutionPool.size() - 1;
				if (!devicesExecutionPool.get(lastIndex).isDone()) {
					failedMonitor.add(commandIndex.getName());
					destroyChannel();
					devicesExecutionPool.get(lastIndex).cancel(true);
				}
			});
			try {
				while (!manageTimeOutWorkerThread.isDone()) {
					Thread.sleep(100);
				}
				manageTimeOutWorkerThread.get();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		logger.debug("Get data success with getMultipleTime: " + currentGetMultipleInPollingInterval);
		currentGetMultipleInPollingInterval++;
		localCachedFailedMonitor = localCachedFailedMonitor + failedMonitor.size();
	}

	/**
	 * Retrieve data by command name
	 *
	 * @param command the command is command to send the request get the data
	 * @return String is data response from the device or None if response fail
	 */
	private String retrieveDataByCommandName(SennheiserPropertiesList command) {
		try {
			byte[] response = send(command.getCommand().getBytes(StandardCharsets.UTF_8));
			DeviceWrapper deviceWrapper = objectMapper.readValue(response, DeviceWrapper.class);
			String value = deviceWrapper.getObjectByName(command);
			updateCachedDeviceData(localCacheMapOfPropertyNameAndValue, command.getName(), value);
			return value;
		} catch (Exception e) {
			logger.error(String.format("Error when retrieving property name: %s", command.getName()), e);
			return SennheiserConstant.NA;
		}
	}

	/**
	 * populate monitoring and controlling data
	 *
	 * @param stats the stats are list of Statistics
	 */
	private void populateMonitoringAndControllingData(Map<String, String> stats) {
		String namePropertyCurrent;
		String value;

		for (SennheiserPropertiesList command : SennheiserPropertiesList.values()) {
			namePropertyCurrent = command.getName();
			value = localCacheMapOfPropertyNameAndValue.get(namePropertyCurrent);
			if (StringUtils.isNotNullOrEmpty(value)) {
				stats.put(namePropertyCurrent, localCacheMapOfPropertyNameAndValue.get(namePropertyCurrent));
			}
		}
	}

	/**
	 * Update cache device data
	 *
	 * @param cacheMapOfPropertyNameAndValue the cacheMapOfPropertyNameAndValue are map key and value of it
	 * @param property the key is property name
	 * @param value the value is String value
	 */
	private void updateCachedDeviceData(Map<String, String> cacheMapOfPropertyNameAndValue, String property, String value) {
		cacheMapOfPropertyNameAndValue.remove(property);
		cacheMapOfPropertyNameAndValue.put(property, value);
	}

	/**
	 * This method is used to convert or validate the user input
	 */
	private void convertCacheLifetime() {
		try {
			currentCachingLifetime = Integer.parseInt(this.cachingLifetime);
			if (currentCachingLifetime <= SennheiserConstant.ZERO) {
				currentCachingLifetime = SennheiserConstant.DEFAULT_CACHING_LIFETIME;
			}
		} catch (Exception e) {
			currentCachingLifetime = SennheiserConstant.DEFAULT_CACHING_LIFETIME;
		}
	}

	/**
	 * This method is used to validate input delay time from user
	 */
	private void convertDelayTime() {
		try {
			commandsCoolDownDelay = Integer.parseInt(this.coolDownDelay);
			if (SennheiserConstant.MIN_DELAY_TIME >= commandsCoolDownDelay) {
				commandsCoolDownDelay = SennheiserConstant.MIN_DELAY_TIME;
			}
			if (SennheiserConstant.MAX_DELAY_TIME <= commandsCoolDownDelay) {
				commandsCoolDownDelay = SennheiserConstant.MAX_DELAY_TIME;
			}
		} catch (Exception e) {
			commandsCoolDownDelay = SennheiserConstant.DEFAULT_DELAY_TIME;
		}
	}

	/**
	 * This method is used to validate input config timeout from user
	 */
	private void convertConfigTimeout() {
		int configTimeout;
		try {
			configTimeout = Integer.parseInt(this.configTimeout);
			if (SennheiserConstant.DEFAULT_CONFIG_TIMEOUT >= configTimeout) {
				configTimeout = SennheiserConstant.DEFAULT_CONFIG_TIMEOUT;
			}
			if (SennheiserConstant.MAX_CONFIG_TIMEOUT <= configTimeout) {
				configTimeout = SennheiserConstant.MAX_CONFIG_TIMEOUT;
			}
		} catch (Exception e) {
			configTimeout = SennheiserConstant.DEFAULT_CONFIG_TIMEOUT;
		}
		defaultConfigTimeout = configTimeout / 100;
	}

	/**
	 * This method is used to validate input config timeout from user
	 */
	private void convertPollingInterval() {
		int pollingIntervalValue;
		try {
			pollingIntervalValue = Integer.parseInt(this.pollingInterval);
			if (pollingIntervalValue < SennheiserConstant.DEFAULT_POLLING_INTERVAL) {
				pollingIntervalValue = SennheiserConstant.DEFAULT_POLLING_INTERVAL;
			}
		} catch (Exception e) {
			pollingIntervalValue = SennheiserConstant.DEFAULT_POLLING_INTERVAL;
		}
		pollingIntervalInIntValue = pollingIntervalValue;
	}

}