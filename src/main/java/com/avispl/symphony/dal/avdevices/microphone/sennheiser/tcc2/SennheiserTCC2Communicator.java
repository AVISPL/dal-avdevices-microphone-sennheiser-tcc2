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
import java.util.Date;
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
import java.util.concurrent.locks.Condition;
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
import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.comom.SennheiserLEDColorEnum;
import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.comom.SennheiserMetricGroup;
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
	private final Condition condition = reentrantLock.newCondition();
	private boolean isEmergencyDelivery;
	private final Set<String> failedMonitor = new HashSet<>();
	private ExecutorService fetchingDataExSer;
	private ExecutorService timeoutManagementExSer;
	/**
	 * Pool for keeping all the async operations in, to track any operations in progress and cancel them if needed
	 */
	private final List<Future> devicesExecutionPool = new CopyOnWriteArrayList<>();
	/**
	 * Apply default delay in between of all the commands performed by the adapter.
	 */
	private long commandsCoolDownDelay = 200;
	private long lastCommandTimestamp;
	/**
	 * store configManagement adapter properties
	 */
	private String configManagement;
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
	 * {@inheritDoc}
	 */
	@Override
	protected void internalInit() throws Exception {
		fetchingDataExSer = Executors.newFixedThreadPool(1);
		timeoutManagementExSer = Executors.newFixedThreadPool(1);
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
		//isConfigManagement = false;
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
	 * {@inheritDoc}
	 *
	 * Override to send() method to add a cool down delay time after every send command
	 */
	@Override
	public byte[] send(byte[] data) throws Exception {
		try {
			long currentTime = System.currentTimeMillis() - lastCommandTimestamp;
			//check next command wait commandsCoolDownDelay time
			if (currentTime < commandsCoolDownDelay) {
				Thread.sleep(commandsCoolDownDelay);
			}
			lastCommandTimestamp = System.currentTimeMillis();
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("Issuing command %s, timestamp: %s", data, lastCommandTimestamp));
			}
			return super.send(data);
		} finally {
			logger.debug("send data command successfully");
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
			failedMonitor.clear();
			retrieveMonitoringAndControllingData();
			populateMonitoringAndControllingData(stats, advancedControllableProperties);

			extendedStatistics.setStatistics(stats);
			extendedStatistics.setControllableProperties(advancedControllableProperties);
			localExtendedStatistics = extendedStatistics;
		}
		isEmergencyDelivery = false;
		return Collections.singletonList(localExtendedStatistics);
	}

	/**
	 * Using multi thread to implement get request
	 * Thread 1 : Retrieve data
	 * Thread 2 : Waiting for status of thread 1
	 */
	private void retrieveMonitoringAndControllingData() {
		List<SennheiserPropertiesList> commands = Arrays.asList(SennheiserPropertiesList.values());
		Future manageTimeOutWorkerThread;
		for (int i = 0; i < commands.size(); i++) {
			SennheiserPropertiesList commandIndex = commands.get(i);

			devicesExecutionPool.add(fetchingDataExSer.submit(() -> {
				retrieveDataByCommandName(commandIndex);
			}));

			manageTimeOutWorkerThread = timeoutManagementExSer.submit(() -> {
				int timeoutCount = 1;
				while (!devicesExecutionPool.get(devicesExecutionPool.size() - SennheiserConstant.ORDINAL_TO_INDEX_CONVERT_FACTOR).isDone() && timeoutCount <= 10) {
					try {
						Thread.sleep(100);
						// The thread waits until the controlProperty() method successfully controls.
						if (isEmergencyDelivery) {
							condition.wait();
						}
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
			updateCachedDeviceData(localCacheMapOfPropertyNameAndValue, command.getName(), SennheiserConstant.NONE);
			return SennheiserConstant.NONE;
		}
	}

	/**
	 * populate monitoring and controlling data
	 *
	 * @param stats the stats are list of Statistics
	 * @param advancedControllableProperties the advancedControllableProperties are list AdvancedControllableProperty instance
	 */
	private void populateMonitoringAndControllingData(Map<String, String> stats, List<AdvancedControllableProperty> advancedControllableProperties) {
		String deviceSettingsGroup = SennheiserMetricGroup.DEVICE_SETTINGS.getName() + SennheiserConstant.HASH;
		String audioSettingsGroup = SennheiserMetricGroup.AUDIO_SETTINGS.getName() + SennheiserConstant.HASH;
		String nameProperty;
		String namePropertyCurrent;
		String value;

		for (SennheiserPropertiesList command : SennheiserPropertiesList.values()) {
			namePropertyCurrent = command.getName();
			value = localCacheMapOfPropertyNameAndValue.get(namePropertyCurrent);
			if (StringUtils.isNotNullOrEmpty(value)) {
				switch (command) {
					case IDENTIFY_DEVICE:
						nameProperty = deviceSettingsGroup + namePropertyCurrent;
						addAdvanceControlProperties(advancedControllableProperties, stats, createButton(nameProperty, SennheiserConstant.BLINK, SennheiserConstant.BLINKING, SennheiserConstant.GRACE_PERIOD));
						break;
					case LED_BRIGHTNESS:
						nameProperty = deviceSettingsGroup + namePropertyCurrent;
						addAdvanceControlProperties(advancedControllableProperties, stats,
								createSlider(stats, nameProperty, SennheiserConstant.MIN_LEB_BRIGHTNESS_LABEL, SennheiserConstant.MAX_LEB_BRIGHTNESS_LABEL, SennheiserConstant.MIN_LEB_BRIGHTNESS_VALUE,
										SennheiserConstant.MAX_LEB_BRIGHTNESS_VALUE, Float.parseFloat(value)));
						if (Integer.parseInt(value.split(SennheiserConstant.POINT)[0]) == 0) {
							value = SennheiserConstant.OFF;
						}
						stats.put(SennheiserConstant.DEVICE_SETTINGS_LED_BRIGHTNESS_CURRENT_VALUE, value);
						break;
					case DEVICE_RESTART:
						nameProperty = deviceSettingsGroup + namePropertyCurrent;
						addAdvanceControlProperties(advancedControllableProperties, stats, createButton(nameProperty, SennheiserConstant.RESTART, SennheiserConstant.RESTARTING, SennheiserConstant.GRACE_PERIOD));
						break;
					case INPUT_LEVEL_GAIN:
						nameProperty = audioSettingsGroup + namePropertyCurrent;
						addAdvanceControlProperties(advancedControllableProperties, stats,
								createSlider(stats, nameProperty, SennheiserConstant.MIN_INPUT_LEVEL_GAIN_LABEL, SennheiserConstant.MAX_INPUT_LEVEL_GAIN_LABEL, SennheiserConstant.MIN_INPUT_LEVEL_GAIN_VALUE,
										SennheiserConstant.MAX_INPUT_LEVEL_GAIN_VALUE, Float.parseFloat(value)));
						stats.put(SennheiserConstant.AUDIO_SETTINGS_INPUT_LEVEL_GAIN_CURRENT_VALUE, value);
						break;
					case MIC_ON_LED_COLOR:
					case MIC_MUTE_LED_COLOR:
					case LED_CUSTOM_COLOR:
						String[] colorArray = Arrays.stream(SennheiserLEDColorEnum.values()).map(SennheiserLEDColorEnum::getName).toArray(String[]::new);
						nameProperty = deviceSettingsGroup + namePropertyCurrent;
						addAdvanceControlProperties(advancedControllableProperties, stats, createDropdown(nameProperty, colorArray, SennheiserLEDColorEnum.getNameByValue(value)));
						break;
					case AUDIO_MUTE:
					case VOICE_LIFT:
					case FAR_END_ACTIVITY_LED_MODE:
						nameProperty = audioSettingsGroup + namePropertyCurrent;
						addAdvanceControlProperties(advancedControllableProperties, stats, createSwitch(nameProperty, convertBooleanToNumber(value), SennheiserConstant.OFF, SennheiserConstant.ON));
						break;
					default:
						stats.put(namePropertyCurrent, localCacheMapOfPropertyNameAndValue.get(namePropertyCurrent));
				}
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
	 * Add advancedControllableProperties if advancedControllableProperties different empty
	 *
	 * @param advancedControllableProperties advancedControllableProperties is the list that store all controllable properties
	 * @param stats store all statistics
	 * @param property the property is item advancedControllableProperties
	 * @return String response
	 * @throws IllegalStateException when exception occur
	 */
	private void addAdvanceControlProperties(List<AdvancedControllableProperty> advancedControllableProperties, Map<String, String> stats, AdvancedControllableProperty property) {
		if (property != null) {
			for (AdvancedControllableProperty controllableProperty : advancedControllableProperties) {
				if (controllableProperty.getName().equals(property.getName())) {
					advancedControllableProperties.remove(controllableProperty);
					break;
				}
			}
			stats.put(property.getName(), SennheiserConstant.EMPTY);
			advancedControllableProperties.add(property);
		}
	}

	/**
	 * Create switch is control property for metric
	 *
	 * @param name the name of property
	 * @param status initial status (0|1)
	 * @return AdvancedControllableProperty switch instance
	 */
	private AdvancedControllableProperty createSwitch(String name, int status, String labelOff, String labelOn) {
		AdvancedControllableProperty.Switch toggle = new AdvancedControllableProperty.Switch();
		toggle.setLabelOff(labelOff);
		toggle.setLabelOn(labelOn);

		AdvancedControllableProperty advancedControllableProperty = new AdvancedControllableProperty();
		advancedControllableProperty.setName(name);
		advancedControllableProperty.setValue(status);
		advancedControllableProperty.setType(toggle);
		advancedControllableProperty.setTimestamp(new Date());

		return advancedControllableProperty;
	}

	/**
	 * Create a button.
	 *
	 * @param name name of the button
	 * @param label label of the button
	 * @param labelPressed label of the button after pressing it
	 * @param gracePeriod grace period of button
	 * @return This returns the instance of {@link AdvancedControllableProperty} type Button.
	 */
	private AdvancedControllableProperty createButton(String name, String label, String labelPressed, long gracePeriod) {
		AdvancedControllableProperty.Button button = new AdvancedControllableProperty.Button();
		button.setLabel(label);
		button.setLabelPressed(labelPressed);
		button.setGracePeriod(gracePeriod);
		return new AdvancedControllableProperty(name, new Date(), button, "");
	}

	/***
	 * Create AdvancedControllableProperty slider instance
	 *
	 * @param stats extended statistics
	 * @param name name of the control
	 * @param initialValue initial value of the control
	 * @return AdvancedControllableProperty slider instance
	 */
	private AdvancedControllableProperty createSlider(Map<String, String> stats, String name, String labelStart, String labelEnd, Float rangeStart, Float rangeEnd, Float initialValue) {
		stats.put(name, initialValue.toString());
		AdvancedControllableProperty.Slider slider = new AdvancedControllableProperty.Slider();
		slider.setLabelStart(labelStart);
		slider.setLabelEnd(labelEnd);
		slider.setRangeStart(rangeStart);
		slider.setRangeEnd(rangeEnd);

		return new AdvancedControllableProperty(name, new Date(), slider, initialValue);
	}

	/***
	 * Create dropdown advanced controllable property
	 *
	 * @param name the name of the control
	 * @param initialValue initial value of the control
	 * @return AdvancedControllableProperty dropdown instance
	 */
	private AdvancedControllableProperty createDropdown(String name, String[] values, String initialValue) {
		AdvancedControllableProperty.DropDown dropDown = new AdvancedControllableProperty.DropDown();
		dropDown.setOptions(values);
		dropDown.setLabels(values);

		return new AdvancedControllableProperty(name, new Date(), dropDown, initialValue);
	}

	/**
	 * convert value type boolean to number
	 * If value is True then return 1 and False is 0
	 */
	private int convertBooleanToNumber(String value) {
		return SennheiserConstant.TRUE.equals(value) ? 1 : 0;
	}
}