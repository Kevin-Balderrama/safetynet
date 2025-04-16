package com.openclassrooms.safetynet.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Person {
    public final String firstName;
    public final String lastName;
    public final String phone;
    public final String zip;
    public final String address;
    public final String city;
    public final String email;
    public final MedicalRecord records;

    public String getZip() {
        return zip;
    }

    public static class PersonBuilder {
        private String firstName;
        private String lastName;
        private String phone;
        private String zip;
        private String address;
        private String city;
        private String email;
        private MedicalRecord records;

        public PersonBuilder() {
        }

        public PersonBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public PersonBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public PersonBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public PersonBuilder zip(String zip) {
            this.zip = zip;
            return this;
        }

        public PersonBuilder address(String address) {
            this.address = address;
            return this;
        }

        public PersonBuilder city(String city) {
            this.city = city;
            return this;
        }

        public PersonBuilder email(String email) {
            this.email = email;
            return this;
        }

        public PersonBuilder records(MedicalRecord records) {
            this.records = records;
            return this;
        }

        public Person build() {
            return new Person(firstName, lastName, phone, zip, address, city, email, records);
        }
    }

    public Person(String firstName, String lastName, String phone, String zip, String address, String city, String email, MedicalRecord records) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.zip = zip;
        this.address = address;
        this.city = city;
        this.email = email;
        this.records = records;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getAddress() {
        return this.address;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getCity() {
        return this.city;
    }

    public String getEmail() {
        return this.email;
    }

    public int getAge() {
        if (records == null) {
            return 0;
        }
        String birthdate = records.getBirthdate();
        if (birthdate == null || birthdate.isEmpty()) {
            return 0;
        }
        try {
            return Period.between(LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy")), LocalDate.now()).getYears();
        } catch (Exception e) {
            return 0; // Fallback for invalid format
        }
    }

    public String[] getMedications() {
        return records == null ? new String[0] : records.getMedications();
    }

    public String[] getAllergies() {
        return records == null ? new String[0] : records.getAllergies();
    }
}