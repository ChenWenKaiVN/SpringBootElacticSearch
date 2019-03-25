package com.vehicle;

import java.io.Serializable;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Document(indexName = "vehicle_list", type = "vehicle")
public class VehicleList implements Serializable {
	
	private String   id;
	private int  ifOnline;
	private long onlineTime;
	private int ifActive;
	private long activeTime;
	
	public VehicleList(){}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIfOnline() {
		return ifOnline;
	}

	public void setIfOnline(int ifOnline) {
		this.ifOnline = ifOnline;
	}

	public long getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(long onlineTime) {
		this.onlineTime = onlineTime;
	}

	public int getIfActive() {
		return ifActive;
	}

	public void setIfActive(int ifActive) {
		this.ifActive = ifActive;
	}

	public long getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(long activeTime) {
		this.activeTime = activeTime;
	}

}
