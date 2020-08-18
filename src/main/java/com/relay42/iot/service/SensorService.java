package com.relay42.iot.service;

import java.util.Date;

import org.springframework.http.ResponseEntity;

public interface SensorService {
	
	public ResponseEntity<?> getSensorMaximumValue(int[] sensorId,String startDate, String endDate) throws Exception;
	
	public ResponseEntity<?> getSensorMinimumValue(int[] sensorId,String startDate, String endDate) throws Exception;
	
	public ResponseEntity<?> getSensorMedianValue(int[] sensorId,String startDate, String endDate) throws Exception;
	
	public ResponseEntity<?> getSensorAverageValue(int[] sensorId,String startDate, String endDate) throws Exception;
	
	public ResponseEntity<?> getAllSensorValues() throws Exception;

}
