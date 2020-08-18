package com.relay42.iot.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import  com.relay42.iot.model.SensorData;

public interface SensorRepository extends MongoRepository<SensorData, String> { 
	
	public Optional<SensorData> findById(String Id);
	
	//FIND MAX Value
	SensorData findTopByValue(String sensorId);

	// Find MIN Value
	SensorData findBottomByValue(String sensorId);

}