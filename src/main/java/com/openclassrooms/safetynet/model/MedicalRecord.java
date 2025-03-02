package com.openclassrooms.safetynet.model;

public class MedicalRecord {
    private final String firstName;
    private final String lastName;
    private final String birthdate;
    private final String[] medications;
    private final String[] allergies;

    public MedicalRecord(String[] allergies, String birthdate, String firstName, String lastName, String[] medications) {
        this.allergies = allergies;
        this.birthdate = birthdate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.medications = medications;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String[] getMedications() {
        return medications;
    }

    public String[] getAllergies() {
        return allergies;
    }
    

}
