package com.relay42.iot.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "SensorData")
public class SensorData {
	
	  public String id;
	  public int value;
	  public String timestamp;
	  public int sensorId;
	  
		public int getSensorId() {
			return sensorId;
		}
	
		public void setSensorId(int sensorId) {
			this.sensorId = sensorId;
		}

		public String getId() {
			return id;
		}
		
		public void setId(String id) {
			this.id = id;
		}
		
		public int getValue() {
			return value;
		}
		
		public void setValue(int value) {
			this.value = value;
		}
		
		public String gettimestamp() {
			return timestamp;
		}
		
		public void settimestamp(String timestamp) {
			this.timestamp = timestamp;
		}

	  public SensorData() {}

	  public SensorData(int value, String timestamp) {
	    this.value = value;
	    this.timestamp = timestamp;
	  }

	  @Override
	  public String toString() {
	    return String.format(
	        "SensorData[id=%s, value='%s', time stamp='%s']",
	        id, value, timestamp);
	  }

}
