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
	}

	@GetMapping("/safetynet")
	public String person() {
		//pulling JSON
		System.out.println("Get People:\n"+safetyNetService.getAllPersons());
		return "person!";
	}
	@GetMapping("/firestation")
    public String firestation() {
        System.out.println("Get FireStation:\n"+safetyNetService.getAllFireStations());
		return "firestation!";
    }
	@GetMapping("/medicalRecord")
    public String medicalRecord() {
		System.out.println("Get MedicalRecords:\n"+safetyNetService.getAllMedicalRecords());
		return "medicalRecord!";
    }
}