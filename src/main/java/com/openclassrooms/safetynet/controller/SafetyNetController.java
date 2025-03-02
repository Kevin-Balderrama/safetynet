package com.openclassrooms.safetynet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.service.SafetyNetService;



@RestController
public class SafetyNetController {
	private final SafetyNetService safetyNetService;

	@Autowired
	public SafetyNetController(SafetyNetService safetyNetService){
		super();
		this.safetyNetService = safetyNetService;
		System.out.println("Get People:\n"+safetyNetService.getAllPersons());
		System.out.println("Get FireStation:\n"+safetyNetService.getAllFireStations());
		System.out.println("Get MedicalRecords:\n"+safetyNetService.getAllMedicalRecords());
	}

	@GetMapping("/safetynet")
	public String person() {
		//pulling JSON
		
		return "person!";
	}
	@GetMapping("/firestation")
    public String firestation() {
        return "firestation!";
    }
	@GetMapping("/medicalRecord")
    public String medicalRecord() {
        return "medicalRecord!";
    }
}