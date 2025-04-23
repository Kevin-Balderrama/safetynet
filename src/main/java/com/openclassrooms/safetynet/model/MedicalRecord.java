package com.openclassrooms.safetynet.model;

public class MedicalRecord {
    private final String firstName;
    private final String lastName;
    private String birthdate;
    private String[] medications;
    private String[] allergies;

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

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setMedications(String[] medications) {
        this.medications = medications;
    }

    public void setAllergies(String[] allergies) {
        this.allergies = allergies;
    }
    

}
