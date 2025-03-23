package com.openclassrooms.safetynet.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;

@Repository
public class LoadJson {

    private List<FireStation> fireStations = new ArrayList<>();
    private final List<Person> persons = new ArrayList<>();
    private final List<MedicalRecord> medicalRecords = new ArrayList<>();
    private final String filePath = "data.json";

    public LoadJson() {
        // Load JSON data directly in constructor
        loadJsonData();
    }

    private void loadJsonData() {
        try {
            byte[] bytesFile = Files.readAllBytes(new ClassPathResource(filePath).getFile().toPath());
            Any jsonData = JsonIterator.parse(bytesFile).readAny();

            parsePersons(jsonData.get("persons"));
            parseFireStations(jsonData.get("firestations"));
            parseMedicalRecords(jsonData.get("medicalrecords"));

        } catch (IOException ex) {
            System.err.println("Error reading JSON file: " + ex.getMessage());
        }
    }

    private void parsePersons(Any personAny) {
        if (personAny != null && personAny.size() > 0) {
            personAny.forEach(a -> persons.add(new Person.PersonBuilder()
                    .firstName(a.get("firstName").toString())
                    .lastName(a.get("lastName").toString())
                    .address(a.get("address").toString())
                    .city(a.get("city").toString())
                    .zip(a.get("zip").toString())
                    .phone(a.get("phone").toString())
                    .email(a.get("email").toString())
                    .build()));
        }
    }

    private void parseFireStations(Any fireStationAny) {
        if (fireStationAny != null && fireStationAny.size() > 0) {
            Map<String, FireStation> fireStationMap = new HashMap<>();
            fireStationAny.forEach(anyStation -> {
                fireStationMap.compute(anyStation.get("station").toString(),
                        (_, v) -> v == null
                                ? new FireStation(anyStation.get("station").toString()).addAddress(anyStation.get("address").toString())
                                : v.addAddress(anyStation.get("address").toString()));
            });
            this.fireStations = new ArrayList<>(fireStationMap.values());
        }
    }

    private void parseMedicalRecords(Any medicalAny) {
        if (medicalAny != null && medicalAny.size() > 0) {
            medicalAny.forEach(medicalRecord -> {
                List<String> allergiesList = new ArrayList<>();
                Any allergiesAny = medicalRecord.get("allergies");
                if (allergiesAny != null && allergiesAny.size() > 0) {
                    allergiesAny.forEach(allergy -> allergiesList.add(allergy.toString()));
                }

                List<String> medicationsList = new ArrayList<>();
                Any medicationsAny = medicalRecord.get("medications");
                if (medicationsAny != null && medicationsAny.size() > 0) {
                    medicationsAny.forEach(medication -> medicationsList.add(medication.toString()));
                }

                medicalRecords.add(new MedicalRecord(
                        allergiesList.toArray(String[]::new),
                        medicalRecord.get("birthdate").toString(),
                        medicalRecord.get("firstName").toString(),
                        medicalRecord.get("lastName").toString(),
                        medicationsList.toArray(String[]::new)
                ));
            });
        }
    }

    public List<FireStation> getFireStations() {
        return fireStations;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public List<MedicalRecord> getMedicalRecords() {
        return medicalRecords;
    }
}