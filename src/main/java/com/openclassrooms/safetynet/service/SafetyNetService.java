package com.openclassrooms.safetynet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.LoadJson;

@Service
public class SafetyNetService {
    LoadJson loadJson;

    @Autowired
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
}
