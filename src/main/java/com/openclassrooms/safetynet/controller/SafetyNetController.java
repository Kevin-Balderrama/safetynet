package com.openclassrooms.safetynet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public String person() {
		//pulling JSON
		String x = "Get People:\n"+safetyNetService.getAllPersons();
		System.out.println(x);
		return x;
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