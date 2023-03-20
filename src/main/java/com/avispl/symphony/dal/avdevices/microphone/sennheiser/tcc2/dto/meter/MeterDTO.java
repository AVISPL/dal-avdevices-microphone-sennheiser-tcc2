/*
 *  * Copyright (c) 2023 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.meter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.meter.beam.BeamDTO;
import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.meter.input.InputDTO;
import com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.meter.reference.ReferenceDTO;

/**
 * MeterDTO class provides during the monitoring and controlling process
 * MeterDTO class includes beam object, input object and reference object
 *
 * @author Kevin / Symphony Dev Team<br>
 * Created on 3/15/2023
 * @since 1.0.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MeterDTO {
	private BeamDTO beam;
	@JsonProperty("in1")
	private InputDTO input;
	@JsonProperty("ref1")
	private ReferenceDTO reference;

	/**
	 * Retrieves {@code {@link #beam}}
	 *
	 * @return value of {@link #beam}
	 */
	public BeamDTO getBeam() {
		return beam;
	}

	/**
	 * Sets {@code beam}
	 *
	 * @param beam the {@code com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.meterDTO.BeamDTO} field
	 */
	public void setBeam(BeamDTO beam) {
		this.beam = beam;
	}

	/**
	 * Retrieves {@code {@link #input}}
	 *
	 * @return value of {@link #input}
	 */
	public InputDTO getInput() {
		return input;
	}

	/**
	 * Sets {@code input}
	 *
	 * @param input the {@code com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.meterDTO.InputDTO} field
	 */
	public void setInput(InputDTO input) {
		this.input = input;
	}

	/**
	 * Retrieves {@code {@link #reference}}
	 *
	 * @return value of {@link #reference}
	 */
	public ReferenceDTO getReference() {
		return reference;
	}

	/**
	 * Sets {@code reference}
	 *
	 * @param reference the {@code com.avispl.symphony.dal.avdevices.microphone.sennheiser.tcc2.dto.meterDTO.ReferenceDTO} field
	 */
	public void setReference(ReferenceDTO reference) {
		this.reference = reference;
	}
}
