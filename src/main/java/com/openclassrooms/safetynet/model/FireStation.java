package com.openclassrooms.safetynet.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class FireStation {
	
	private final Set<String> addresses = new HashSet<>();
	private final String stationNumber;
	
	public FireStation(String stationNumber) {
		this.stationNumber = stationNumber;
	}
	
	public FireStation addAddress(String address) {
		addresses.add(address);
		return this;
	}
	
	public String getStationNumber() {
		return stationNumber;
	}
	
	public Set<String> getAddresses() {
		return addresses.stream().collect(Collectors.toSet());
	}
	
	@Override
	public String toString() {
		return stationNumber.concat(": ") + String.join(", ", addresses);
	}

}


