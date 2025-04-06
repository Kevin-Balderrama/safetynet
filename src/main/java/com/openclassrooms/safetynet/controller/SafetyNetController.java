package com.openclassrooms.safetynet.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.SafetyNetService;



@RestController
public class SafetyNetController {
	private final SafetyNetService safetyNetService;

	//@Autowired
	public SafetyNetController(SafetyNetService safetyNetService){
		super();
		this.safetyNetService = safetyNetService;
	}

	@GetMapping("/person")
	public List<Person> person() {
		//pulling JSON
		return safetyNetService.getAllPersons();
	}
	@GetMapping("/firestation")
    public List<FireStation> firestation(@RequestParam(name = "stationNumber") int stationNumber) {
		List<FireStation> allStations = safetyNetService.getAllFireStations();
		return allStations.stream().filter(station -> station.getStationNumber() == stationNumber).collect(Collectors.toList());
    }
	@GetMapping("/medicalRecord")
    public List<MedicalRecord> medicalRecord() {
		return safetyNetService.getAllMedicalRecords();
    }
	@GetMapping("/childAlert")
	public String getChildrenAtAddress(@RequestParam(name = "address") String address) {
		return safetyNetService.getChildrenAtAddress(address);
	}
	
	
}