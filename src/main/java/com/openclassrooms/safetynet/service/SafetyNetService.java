package com.openclassrooms.safetynet.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.LoadJson;

@Service
public class SafetyNetService {

    @Autowired
    private LoadJson repository;

    public Map<String, Object> getPersonsByStation(int stationNumber) {
        List<FireStation> fireStations = repository.getFireStations();
        Set<String> addresses = fireStations.stream()
                .filter(fs -> fs.getStationNumber() == stationNumber)
                .flatMap(fs -> fs.getAddresses().stream()) // Correctly handle Set<String>
                .collect(Collectors.toSet());

        List<Person> allPersons = repository.getPersons();
        List<Map<String, String>> persons = new ArrayList<>();
        int adultCount = 0;
        int childCount = 0;

        for (Person p : allPersons) {
            if (addresses.contains(p.getAddress())) {
                int age = p.getAge();
                if (age <= 18) childCount++; else adultCount++; // Consistent with controller

                Map<String, String> info = new HashMap<>();
                info.put("firstName", p.getFirstName());
                info.put("lastName", p.getLastName());
                info.put("address", p.getAddress());
                info.put("phone", p.getPhone());
                persons.add(info);
            }
        }

        return Map.of("persons", persons, "adults", adultCount, "children", childCount);
    }

    public Object getChildrenByAddress(String address) {
        List<Person> allPersons = repository.getPersons();
        List<Person> residents = allPersons.stream()
                .filter(p -> p.getAddress().equalsIgnoreCase(address))
                .collect(Collectors.toList());

        List<Map<String, Object>> children = new ArrayList<>();
        List<Map<String, String>> otherResidents = new ArrayList<>();

        for (Person p : residents) {
            if (p.getAge() <= 18) { // Changed to <= 18 for consistency
                children.add(Map.of("firstName", p.getFirstName(), "lastName", p.getLastName(), "age", p.getAge()));
            } else {
                otherResidents.add(Map.of("firstName", p.getFirstName(), "lastName", p.getLastName()));
            }
        }

        if (children.isEmpty()) return "";
        return Map.of("children", children, "otherResidents", otherResidents);
    }

    public List<String> getPhoneNumbersByStation(int stationNumber) {
        Set<String> addresses = repository.getFireStations().stream()
                .filter(fs -> fs.getStationNumber() == stationNumber)
                .flatMap(fs -> fs.getAddresses().stream()) // Correctly handle Set<String>
                .collect(Collectors.toSet());

        return repository.getPersons().stream()
                .filter(p -> addresses.contains(p.getAddress()))
                .map(Person::getPhone)
                .distinct()
                .collect(Collectors.toList());
    }

    public Map<String, Object> getFireData(String address) {
        int stationNumber = repository.getFireStations().stream()
                .filter(fs -> fs.getAddresses().contains(address.toLowerCase()))
                .map(FireStation::getStationNumber)
                .findFirst()
                .orElse(-1);

        List<Map<String, Object>> persons = repository.getPersons().stream()
        .filter(p -> p.getAddress().equalsIgnoreCase(address))
        .map(p -> {
            Map<String, Object> personMap = new HashMap<>();
            personMap.put("name", p.getFirstName() + " " + p.getLastName());
            personMap.put("phone", p.getPhone());
            personMap.put("age", p.getAge());
            personMap.put("medications", p.getMedications());
            personMap.put("allergies", p.getAllergies());
            return personMap;
        })
        .collect(Collectors.toList());

        return Map.of("stationNumber", stationNumber, "residents", persons);
    }

    public Map<String, List<Map<String, Object>>> getFloodData(List<Integer> stationNumbers) {
        Map<String, List<Map<String, Object>>> result = new HashMap<>();

        List<FireStation> stations = repository.getFireStations();
        List<Person> persons = repository.getPersons();

        for (int station : stationNumbers) {
            Set<String> addresses = stations.stream()
                    .filter(fs -> fs.getStationNumber() == station)
                    .flatMap(fs -> fs.getAddresses().stream()) // Correctly handle Set<String>
                    .collect(Collectors.toSet());

            for (String addr : addresses) {
                List<Map<String, Object>> residents = persons.stream()
                .filter(p -> p.getAddress().equalsIgnoreCase(addr))
                .map(p -> {
                    Map<String, Object> personMap = new HashMap<>();
                    personMap.put("name", p.getFirstName() + " " + p.getLastName());
                    personMap.put("phone", p.getPhone());
                    personMap.put("age", p.getAge());
                    personMap.put("medications", p.getMedications());
                    personMap.put("allergies", p.getAllergies());
                    return personMap;
                })
                .collect(Collectors.toList());

                result.put(addr, residents);
            }
        }

        return result;
    }

    public List<Map<String, Object>> getPersonInfo(String firstName, String lastName) {
        return repository.getPersons().stream()
        .filter(p -> p.getFirstName().equalsIgnoreCase(firstName) && p.getLastName().equalsIgnoreCase(lastName))
        .map(p -> {
            Map<String, Object> personMap = new HashMap<>();
            personMap.put("name", p.getFirstName() + " " + p.getLastName());
            personMap.put("address", p.getAddress());
            personMap.put("age", p.getAge());
            personMap.put("email", p.getEmail());
            personMap.put("medications", p.getMedications());
            personMap.put("allergies", p.getAllergies());
            return personMap;
        })
        .collect(Collectors.toList());
    }

    public List<String> getEmailsByCity(String city) {
        return repository.getPersons().stream()
                .filter(p -> p.getCity().equalsIgnoreCase(city))
                .map(Person::getEmail)
                .distinct()
                .collect(Collectors.toList());
    }
}