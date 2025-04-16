package com.openclassrooms.safetynet.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}