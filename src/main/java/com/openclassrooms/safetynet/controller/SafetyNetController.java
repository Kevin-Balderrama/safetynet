package com.openclassrooms.safetynet.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.SafetyNetService;

@RestController
public class SafetyNetController {

    @Autowired
    private SafetyNetService service;

    @GetMapping("/firestation")
    public Map<String, Object> getPersonsByStation(@RequestParam int stationNumber) {
        return service.getPersonsByStation(stationNumber);
    }

    @GetMapping("/childAlert")
    public Object getChildrenByAddress(@RequestParam String address) {
        return service.getChildrenByAddress(address);
    }

    @GetMapping("/phoneAlert")
    public List<String> getPhonesByStation(@RequestParam int firestation) {
        return service.getPhoneNumbersByStation(firestation);
    }

    @GetMapping("/fire")
    public Map<String, Object> getFireData(@RequestParam String address) {
        return service.getFireData(address);
    }

    @GetMapping("/flood/stations")
    public Map<String, List<Map<String, Object>>> getFloodData(@RequestParam List<Integer> stations) {
        return service.getFloodData(stations);
    }

    @GetMapping("/personInfo")
    public List<Map<String, Object>> getPersonInfo(@RequestParam String firstName, @RequestParam String lastName) {
        return service.getPersonInfo(firstName, lastName);
    }

    @GetMapping("/communityEmail")
    public List<String> getEmailsByCity(@RequestParam String city) {
        return service.getEmailsByCity(city);
    }
    // Person Endpoints
    @PostMapping("/person")
    public String addPerson(@RequestBody Person person) {
        return service.addPerson(person);
    }

    @PutMapping("/person")
    public String updatePerson(@RequestBody Person person) {
        return service.updatePerson(person);
    }

    @DeleteMapping("/person")
    public String deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        return service.deletePerson(firstName, lastName);
    }

    // Firestation Endpoints
    @PostMapping("/firestation")
    public String addFireStation(@RequestBody FireStation fireStation) {
        return service.addFireStation(fireStation);
    }

    @PutMapping("/firestation")
    public String updateFireStation(@RequestParam String address, @RequestParam int stationNumber) {
        return service.updateFireStation(address, stationNumber);
    }

    @DeleteMapping("/firestation")
    public String deleteFireStation(@RequestParam String address) {
        return service.deleteFireStation(address);
    }

    // MedicalRecord Endpoints
    @PostMapping("/medicalRecord")
    public String addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        return service.addMedicalRecord(medicalRecord);
    }

    @PutMapping("/medicalRecord")
    public String updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        return service.updateMedicalRecord(medicalRecord);
    }

    @DeleteMapping("/medicalRecord")
    public String deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
        return service.deleteMedicalRecord(firstName, lastName);
    }
    
}