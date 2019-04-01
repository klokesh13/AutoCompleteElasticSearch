package com.org.elasticsearch.dao;

public class Record {
	
	private String name;
	
	private String 	email;
	
	private String vehicleNumber;
	
	private String accountId;
	
	private String systemId;
	
	private String vehicleAccountId;
	
	private String vehicleSystemId;
	
	

	public String getVehicleAccountId() {
		return vehicleAccountId;
	}

	public void setVehicleAccountId(String vehicleAccountId) {
		this.vehicleAccountId = vehicleAccountId;
	}

	public String getVehicleSystemId() {
		return vehicleSystemId;
	}

	public void setVehicleSystemId(String vehicleSystemId) {
		this.vehicleSystemId = vehicleSystemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	@Override
	public String toString() {
		return "Record [name=" + name + ", email=" + email + ", vehicleNumber=" + vehicleNumber + ", accountId="
				+ accountId + ", systemId=" + systemId + "]";
	}
	
	
	

}
