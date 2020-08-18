package com.relay42.iot.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.relay42.iot.model.CustomType;
import com.relay42.iot.model.SensorData;
import com.relay42.iot.repository.SensorRepository;

@Service
public class SensorServiceImpl implements SensorService {
	
	@Autowired
	private SensorRepository sensorRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public ResponseEntity<?> getSensorMaximumValue(int[] sensorId, String startDate, String endDate) throws Exception {
		List<String> sensorData = new ArrayList<String>();
		for(int singleId: sensorId) {
			System.out.println("singleId is =="+singleId);
			Query query = new Query();
			 query.limit(1);
			Criteria criteria = Criteria.where("sensorId").is(singleId);
			if (!isNullOrEmpty(startDate) && isNullOrEmpty(endDate)) {
				criteria = criteria.and("timestamp").gte(startDate).lt(endDate);
			}
			query.addCriteria(criteria).with(Sort.by(Sort.Direction.DESC, "value"));;
			List<SensorData> singleSensorData = mongoTemplate.find(query, SensorData.class);
			System.out.println("singleSensorData size is =="+singleSensorData.size());
			System.out.println("singleSensorData is =="+singleSensorData.toString());	
			if(singleSensorData.size()>0) {
				for(SensorData sensor: singleSensorData) {
					sensorData.add(sensor.getSensorId() + ": " + sensor.getValue());
				}
			}else {
				sensorData.add(singleId + ": 0");
			}
			singleSensorData.clear();
		}
		
		if (null != sensorData && !sensorData.isEmpty()) {
			return new ResponseEntity<>(sensorData, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new CustomType("Data Not Found"), HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<?> getSensorMinimumValue(int[] sensorId, String startDate, String endDate) throws Exception {
		List<String> sensorData = new ArrayList<String>();
		for(int singleId: sensorId) {
			System.out.println("singleId is =="+singleId);
			Query query = new Query();
			query.limit(1);
			Criteria criteria = Criteria.where("sensorId").is(singleId);
			if (!isNullOrEmpty(startDate) && isNullOrEmpty(endDate)) {
				criteria = criteria.and("timestamp").gte(startDate).lt(endDate);
			}
			query.addCriteria(criteria).with(Sort.by(Sort.Direction.ASC, "value"));
			List<SensorData> singleSensorData = mongoTemplate.find(query, SensorData.class);
			System.out.println("singleSensorData size is =="+singleSensorData.size());
			if(singleSensorData.size()>0) {
				for(SensorData sensor: singleSensorData) {
					sensorData.add(sensor.getSensorId() + ": " + sensor.getValue());
				}
			}else {
				sensorData.add(singleId + ": 0");
			}
			singleSensorData.clear();
		}
		if (null != sensorData && !sensorData.isEmpty()) {
			return new ResponseEntity<>(sensorData, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new CustomType("Data Not Found"), HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<?> getSensorMedianValue(int[] sensorId, String startDate, String endDate) throws Exception {
		List<String> sensorData = new ArrayList<String>();
		for(int singleId: sensorId) {
			System.out.println("singleId is =="+singleId);
			Query query = new Query();
			Criteria criteria = Criteria.where("sensorId").is(singleId);
			if (!isNullOrEmpty(startDate) && isNullOrEmpty(endDate)) {
				criteria = criteria.and("timestamp").gte(startDate).lt(endDate);
			}
			query.addCriteria(criteria).with(Sort.by(Sort.Direction.ASC, "value"));;
			List<SensorData> singleSensorData = mongoTemplate.find(query, SensorData.class);
			System.out.println("singleSensorData size is =="+singleSensorData.size());
			System.out.println("singleSensorData is =="+singleSensorData.toString());	
			int median = 0;
			if(singleSensorData.size()>0) {
				if(singleSensorData.size() % 2 ==0) {
					 median = (singleSensorData.size()/2) - 1;
				}else {
					median = singleSensorData.size()/2;
				}
				
				if(median!=0) {
					SensorData sensor = singleSensorData.get(median);
					sensorData.add(sensor.getSensorId() + ": " + sensor.getValue());
				}
			}else {
					sensorData.add(singleId + ": " +median);
			}
		}
		if (null != sensorData && !sensorData.isEmpty()) {
			return new ResponseEntity<>(sensorData, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new CustomType("Data Not Found"), HttpStatus.OK);
		}
	}

	@Override
	public ResponseEntity<?> getSensorAverageValue(int[] sensorId, String startDate, String endDate) throws Exception {
		List<String> sensorData = new ArrayList<String>();
		for(int singleId: sensorId) {
			System.out.println("singleId is =="+singleId);				
			Query query = new Query();
			Criteria criteria = Criteria.where("sensorId").is(singleId);
			if (!isNullOrEmpty(startDate) && isNullOrEmpty(endDate)) {
				criteria = criteria.and("timestamp").gte(startDate).lt(endDate);
			}
System.out.println("Criteria is =="+singleId);	
System.out.println("startDate is =="+startDate);	
System.out.println("endDate is =="+endDate);	
			query.addCriteria(criteria);
			List<SensorData> singleSensorDataList = mongoTemplate.find(query, SensorData.class);
			System.out.println("singleSensorDataList size is =="+singleSensorDataList.size());
				if(singleSensorDataList.size()>0) {
					int totalValue = 0;
					for(SensorData sensor : singleSensorDataList) {
						totalValue += sensor.getValue();
					}
					sensorData.add(singleId + ": " + totalValue/singleSensorDataList.size());
				}else {
					sensorData.add(singleId + ": 0");
				}
				singleSensorDataList.clear();
			}
		if (null != sensorData && !sensorData.isEmpty()) {
			return new ResponseEntity<>(sensorData, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new CustomType("Data Not Found"), HttpStatus.OK);
		}
	}
	
	@Override
	public ResponseEntity<?> getAllSensorValues() throws Exception {
		System.out.println(sensorRepository.findAll());
		List<SensorData> sensorData = sensorRepository.findAll();
		return new ResponseEntity<>(sensorData, HttpStatus.OK);
	}
	
	public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }

}
