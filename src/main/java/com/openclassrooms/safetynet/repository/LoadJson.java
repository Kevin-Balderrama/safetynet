package com.openclassrooms.safetynet.repository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private final String filePath = "src/main/resources/data.json";
       
    public LoadJson(){
        Any any = null;
        try {
        byte[] bytesFile = Files.readAllBytes(new File(this.filePath).toPath());
        
        JsonIterator iter = JsonIterator.parse(bytesFile);
        
        
            any = iter.readAny();
        } catch (IOException ex) {
        }
        //persons
        Any personAny = any.get("persons");
        
        personAny.forEach(a -> this.persons.add(new Person.PersonBuilder().firstName(a.get("firstName").toString())
                .address(a.get("address").toString())
                .city(a.get("city").toString())
                .lastName(a.get("lastName").toString())
                .phone(a.get("phone").toString())
                .zip(a.get("zip").toString())
                .email(a.get("email").toString())
                .build()));
        persons.forEach(b -> System.out.println(b.firstName + b.lastName));        

        //fireStations
        Map<String, FireStation> fireStationMap = new HashMap<>();
        Any fireStationAny = any.get("firestations");
        fireStationAny.forEach(anyStation -> {
            fireStationMap.compute(anyStation.get("station").toString(), 
                    (_, v) -> v == null ?
                            new FireStation(anyStation.get("station").toString()).addAddress(anyStation.get("address").toString()) :
                            v.addAddress(anyStation.get("address").toString()));
        });
        this.fireStations = fireStationMap.values().stream().collect(Collectors.toList());
        fireStations.forEach(b -> System.out.println(b.getStationNumber())); 
        //medicalRecords
        Any medicalAny = any.get("medicalrecords");
    	medicalAny.forEach(medicalRecord -> {
            Any allergiesAny = medicalRecord.get("allergies");
            List<String> allergiesList = new ArrayList<>();
            allergiesAny.forEach(allergy -> allergiesList.add(allergy.toString()));
            String[] allergiesArray = allergiesList.toArray(String[]::new);
            
            Any medicationsAny = medicalRecord.get("medications");
            List<String> medicationsList = new ArrayList<>();
            medicationsAny.forEach(medication -> medicationsList.add(medication.toString()));
            String[] medicationsArray = medicationsList.toArray(String[]::new);
            
            medicalRecords.add(new MedicalRecord(allergiesArray, medicalRecord.get("birthdate").toString(), medicalRecord.get("firstName").toString(), medicalRecord.get("lastName").toString(), medicationsArray));
        });
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