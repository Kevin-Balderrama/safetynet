package com.openclassrooms.safetynet.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.LoadJson;

@Service
public class SafetyNetService {
    private final LoadJson loadJson;

    //@Autowired
    public SafetyNetService(LoadJson loadJson){
        this.loadJson = loadJson;
    }

    //People Mover
    public List<Person> getAllPersons(){
        return loadJson.getPersons();
    }
    
    public Person getPersonByName(String firstName, String lastName){
        List<Person> people = getAllPersons();
        for(Person p: people){
            if(p.firstName.equals(firstName) && p.lastName.equals(lastName)){
                return p;
            }
        }
        return null;
    }

    //FireStation Wrangler
    public List<FireStation> getAllFireStations(){
        return loadJson.getFireStations();
    }

    //MedicalRecord Library
    public List<MedicalRecord> getAllMedicalRecords(){
        return loadJson.getMedicalRecords();
    }

    //
    public List<Person> getChildrenAtAddress(String address){
        List<Person> people = getAllPersons();

        // Get all residents at the given address
        List<Person> residents = people.stream()
                .filter(person -> person.getAddress().equalsIgnoreCase(address))
                .toList();

        // Check if there is at least one child (age < 18) at the address
        boolean hasChildren = residents.stream().anyMatch(person -> person.getAge() < 18);

        // If there is a child, return all residents; otherwise, return an empty list
        return hasChildren ? residents : List.of();
    }
}
