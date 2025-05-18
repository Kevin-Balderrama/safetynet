package com.openclassrooms.safetynet.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private static final Logger logger = LogManager.getLogger(SafetyNetController.class);

    @Autowired
    private SafetyNetService service;

    @GetMapping("/firestation")
    public Map<String, Object> getPersonsByStation(@RequestParam int stationNumber) {
        logger.info("Fetching persons by station number: {}", stationNumber);
        Map<String, Object> response = service.getPersonsByStation(stationNumber);
        if (response == null) {
            logger.warn("No data found for station number: {}", stationNumber);
            return Map.of("error", "No data found for the given station number.");
        }else{
            logger.info("Data found for station number: {}", stationNumber);
        }
        return response;
    }

    @GetMapping("/childAlert")
    public Object getChildrenByAddress(@RequestParam String address) {
        logger.info("Fetching children by address: {}", address);
        Object response = service.getChildrenByAddress(address);
        if (response == null) {
            logger.warn("No data found for address: {}", address);
            return Map.of("error", "No data found for the given address.");
        }else{
            logger.info("Data found for address: {}", address);
        }
        return response;
    }

    @GetMapping("/phoneAlert")
    public List<String> getPhonesByStation(@RequestParam int firestation) {
        logger.info("Fetching phones by firestation: {}", firestation);
        List<String> response = service.getPhoneNumbersByStation(firestation);
        if (response == null || response.isEmpty()) {
            logger.warn("No data found for firestation: {}", firestation);
            return List.of("No data found for the given firestation.");
        }else{
            logger.info("Phone number(s) found for firestation: {}", firestation);
        }
        return response;
    }

    @GetMapping("/fire")
    public Map<String, Object> getFireData(@RequestParam String address) {
        logger.info("Fetching fire data by address: {}", address);
        Map<String, Object> response = service.getFireData(address);
        if (response == null) {
            logger.warn("No data found for address: {}", address);
            return Map.of("error", "No data found for the given address.");
        }else{
            logger.info("Data found for address: {}", address);
        }
        return response;
    }

    @GetMapping("/flood/stations")
    public Map<String, List<Map<String, Object>>> getFloodData(@RequestParam List<Integer> stations) {
        logger.info("Fetching flood data for stations: {}", stations);
        Map<String, List<Map<String, Object>>> response = service.getFloodData(stations);
        if (response == null || response.isEmpty()) {
            logger.warn("No data found for stations: {}", stations);
            return Collections.emptyMap();
        }else{
            logger.info("Data found for stations: {}", stations);
        }
        return response;
    }

    @GetMapping("/personInfo")
    public List<Map<String, Object>> getPersonInfo(@RequestParam String firstName, @RequestParam String lastName) {
        logger.info("Fetching person info for: {} {}", firstName, lastName);
        List<Map<String, Object>> response = service.getPersonInfo(firstName, lastName);
        if (response == null || response.isEmpty()) {
            logger.warn("No data found for person: {} {}", firstName, lastName);
            return List.of(Map.of("error", "No data found for the given person."));
        }else{
            logger.info("Data found for person: {} {}", firstName, lastName);
        }
        return response;
    }

    @GetMapping("/communityEmail")
    public List<String> getEmailsByCity(@RequestParam String city) {
        logger.info("Fetching emails by city: {}", city);
        List<String> response = service.getEmailsByCity(city);
        if (response == null || response.isEmpty()) {
            logger.warn("No data found for city: {}", city);
            return List.of("No data found for the given city.");
        }else{
            logger.info("Email(s) found for city: {}", city);
        }
        return response;
    }
    // Person Endpoints
    @PostMapping("/person")
    public String addPerson(@RequestBody Person person) {
        logger.info("Adding person: {}", person);
        String response = service.addPerson(person);
        if (response == null) {
            logger.warn("Failed to add person: {}", person);
            return "Failed to add person.";
        }else{
            logger.info("Person added successfully: {}", person);
        }
        return response;
    }

    @PutMapping("/person")
    public String updatePerson(@RequestBody Person person) {
        logger.info("Updating person: {}", person);
        String response = service.updatePerson(person);
        if (response == null) {
            logger.warn("Failed to update person: {}", person);
            return "Failed to update person.";
        }else{
            logger.info("Person updated successfully: {}", person);
        }
        return response;
    }

    @DeleteMapping("/person")
    public String deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        logger.info("Deleting person: {} {}", firstName, lastName);
        String response = service.deletePerson(firstName, lastName);
        if (response == null) {
            logger.warn("Failed to delete person: {} {}", firstName, lastName);
            return "Failed to delete person.";
        }else{
            logger.info("Person deleted successfully: {} {}", firstName, lastName);
        }
        return response;
    }

    // Firestation Endpoints
    @PostMapping("/firestation")
    public String addFireStation(@RequestBody FireStation fireStation) {
        logger.info("Adding fire station: {}", fireStation);
        String response = service.addFireStation(fireStation);
        if (response == null) {
            logger.warn("Failed to add fire station: {}", fireStation);
            return "Failed to add fire station.";
        }else{
            logger.info("Fire station added successfully: {}", fireStation);
        }
        return response;
    }

    @PutMapping("/firestation")
    public String updateFireStation(@RequestParam String address, @RequestParam int stationNumber) {
        logger.info("Updating fire station for address: {} to station number: {}", address, stationNumber);
        String response = service.updateFireStation(address, stationNumber);
        if (response == null) {
            logger.warn("Failed to update fire station for address: {} to station number: {}", address, stationNumber);
            return "Failed to update fire station.";
        }else{
            logger.info("Fire station updated successfully for address: {} to station number: {}", address, stationNumber);
        }
        return response;
    }

    @DeleteMapping("/firestation")
    public String deleteFireStation(@RequestParam String address) {
        logger.info("Deleting fire station for address: {}", address);
        String response = service.deleteFireStation(address);
        if (response == null) {
            logger.warn("Failed to delete fire station for address: {}", address);
            return "Failed to delete fire station.";
        }else{
            logger.info("Fire station deleted successfully for address: {}", address);
        }
        return response;
    }

    // MedicalRecord Endpoints
    @PostMapping("/medicalRecord")
    public String addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        logger.info("Adding medical record: {}", medicalRecord);
        String response = service.addMedicalRecord(medicalRecord);
        if (response == null) {
            logger.warn("Failed to add medical record: {}", medicalRecord);
            return "Failed to add medical record.";
        }else{
            logger.info("Medical record added successfully: {}", medicalRecord);
        }
        return response;
    }

    @PutMapping("/medicalRecord")
    public String updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        logger.info("Updating medical record: {}", medicalRecord);
        String response = service.updateMedicalRecord(medicalRecord);
        if (response == null) {
            logger.warn("Failed to update medical record: {}", medicalRecord);
            return "Failed to update medical record.";
        }else{
            logger.info("Medical record updated successfully: {}", medicalRecord);
        }
        return response;
    }

    @DeleteMapping("/medicalRecord")
    public String deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
        logger.info("Deleting medical record for: {} {}", firstName, lastName);
        String response = service.deleteMedicalRecord(firstName, lastName);
        if (response == null) {
            logger.warn("Failed to delete medical record for: {} {}", firstName, lastName);
            return "Failed to delete medical record.";
        }else{
            logger.info("Medical record deleted successfully for: {} {}", firstName, lastName);
        }
        return response;
    }
    
}