package com.relay42.iot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.relay42.iot.service.SensorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(path = "/")
@CrossOrigin(origins = "*", maxAge = 3600)
@Api(value = "IOT Querying system")
public class IOTController {

	private static Logger logger = LoggerFactory.getLogger(IOTController.class);
	private final String CONTROLLER_NAME = this.getClass().getSimpleName();

	@Autowired
	private SensorService sensorService;
	// ------------------------------------------------------------
	// Status constants
	// ------------------------------------------------------------
	private final String CORE_SERVICE_UP = "Relay42 IOT backend is up!!";
	private final String SUCCESS_STATUS = "Success!";
	private final String FAILURE_STATUS = "Failure";
	private final String VALID_STATUS = "Valid";
	private final String INVALID_STATUS = "Invalid";

	/**
	 * Checks if iotcore is up
	 * 
	 * @return ResponseEntity<String>
	 */
	@ApiOperation(value = "Checks if the core service is up and running", response = ResponseEntity.class)
	@GetMapping("/sensordata")
	public ResponseEntity<String> getIotCoreStatus() {
		return new ResponseEntity<String>(SUCCESS_STATUS + " " + CORE_SERVICE_UP, HttpStatus.OK);
	}

	@ApiOperation(value = "Fetches sensor maximum value based on sensor id/s and time frame for the reading", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Sensor Maximum Details Retrieved", response = ResponseEntity.class),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 404, message = "Data not found") })
	@GetMapping(value = "/readSensorMaximumValue/{sensorId}/{startDate}/{endDate}", produces = "application/json")
	public ResponseEntity<?> readSensorMaximumValue(@PathVariable int[] sensorId, @PathVariable String startDate,
			@PathVariable String endDate) throws Exception {
		ResponseEntity<?> responseEntity = null;
		responseEntity = sensorService.getSensorMaximumValue(sensorId, startDate, endDate);
		return responseEntity;
	}

	@ApiOperation(value = "Fetches sensor minimum value based on sensor id/s and time frame for the reading", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Sensor minimum Details Retrieved", response = ResponseEntity.class),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 404, message = "Data not found") })
	@GetMapping(value = "/readSensorMinimumValue/{sensorId}/{startDate}/{endDate}", produces = "application/json")
	public ResponseEntity<?> readSensorMinimumValue(@PathVariable int[] sensorId, @PathVariable String startDate,
			@PathVariable String endDate) throws Exception {
		ResponseEntity<?> responseEntity = null;
		responseEntity = sensorService.getSensorMinimumValue(sensorId, startDate, endDate);
		return responseEntity;
	}

	@ApiOperation(value = "Fetches sensor average value based on sensor id/s and time frame for the reading", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Sensor minimum Details Retrieved", response = ResponseEntity.class),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 404, message = "Data not found") })
	@GetMapping(value = "/readSensorAverageValue/{sensorId}/{startDate}/{endDate}", produces = "application/json")
	public ResponseEntity<?> readSensorAverageValue(@PathVariable int[] sensorId, @PathVariable String startDate,
			@PathVariable String endDate) throws Exception {
		ResponseEntity<?> responseEntity = null;
		responseEntity = sensorService.getSensorAverageValue(sensorId, startDate, endDate);
		return responseEntity;
	}

	@ApiOperation(value = "Fetches sensor median value based on sensor id and time frame for the reading", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Sensor minimum Details Retrieved", response = ResponseEntity.class),
			@ApiResponse(code = 500, message = "Internal Server Error"),
			@ApiResponse(code = 404, message = "Data not found") })
	@GetMapping(value = "/readSensorMedianValue/{sensorId}/{startDate}/{endDate}", produces = "application/json")
	public ResponseEntity<?> readSensorMedianValue(@PathVariable int[] sensorId, @PathVariable String startDate,
			@PathVariable String endDate) throws Exception {
		ResponseEntity<?> responseEntity = null;
		responseEntity = sensorService.getSensorMedianValue(sensorId, startDate, endDate);
		return responseEntity;
	}

	@ApiOperation(value = "Fetches all of the sensor value based on sensor id", response = ResponseEntity.class)
	@GetMapping(value = "/readAllSensorValues", produces = "application/json")
	public ResponseEntity<?> readAllSensorValues() throws Exception {
		ResponseEntity<?> responseEntity = null;
		responseEntity = sensorService.getAllSensorValues();
		return responseEntity;
	}

}
