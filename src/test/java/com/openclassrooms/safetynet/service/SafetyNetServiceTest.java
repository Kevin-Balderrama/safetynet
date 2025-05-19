package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.model.*;
import com.openclassrooms.safetynet.repository.LoadJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SafetyNetServiceTest {

    @Mock
    private LoadJson repository;

    @InjectMocks
    private SafetyNetService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testGetPersonsByStation() {
        // Mock data
        FireStation fireStation = new FireStation("1").addAddress("1509 Culver St");
        List<FireStation> fireStations = List.of(fireStation);

        MedicalRecord medicalRecord = new MedicalRecord(
            new String[]{"peanut", "shellfish"},
            "2010-01-01",
            "John",
            "Doe",
            new String[]{"aspirin:500mg"}
        );
        Person person = new Person("John", "Doe", "123-456-7890", "12345", "1509 Culver St", "Culver", "john.doe@example.com", medicalRecord);
        List<Person> persons = List.of(person);

        when(repository.getFireStations()).thenReturn(fireStations);
        when(repository.getPersons()).thenReturn(persons);

        // Execute
        Map<String, Object> result = service.getPersonsByStation(1);

        // Verify
        assertNotNull(result);
        assertEquals(1, ((List<?>) result.get("persons")).size());
        assertEquals(1, result.get("children"));
        assertEquals(0, result.get("adults"));
        verify(repository, times(1)).getFireStations();
        verify(repository, times(1)).getPersons();
    }

    @Test
    void testGetChildrenByAddress() {
        // Mock data
        MedicalRecord childRecord = new MedicalRecord(
            new String[]{"peanut"},
            "2010-01-01",
            "John",
            "Doe",
            new String[]{"aspirin:500mg"}
        );
        MedicalRecord adultRecord = new MedicalRecord(
            new String[]{"shellfish"},
            "1990-01-01",
            "Jane",
            "Doe",
            new String[]{"ibuprofen:200mg"}
        );
        Person child = new Person("John", "Doe", "123-456-7890", "12345", "1509 Culver St", "Culver", "john.doe@example.com", childRecord);
        Person adult = new Person("Jane", "Doe", "987-654-3210", "12345", "1509 Culver St", "Culver", "jane.doe@example.com", adultRecord);
        List<Person> persons = List.of(child, adult);

        when(repository.getPersons()).thenReturn(persons);

        // Execute
        Object result = service.getChildrenByAddress("1509 Culver St");

        // Verify
        assertNotNull(result);
        assertTrue(result instanceof Map);
        Map<?, ?> resultMap = (Map<?, ?>) result;
        assertEquals(1, ((List<?>) resultMap.get("children")).size());
        assertEquals(1, ((List<?>) resultMap.get("otherResidents")).size());
        verify(repository, times(1)).getPersons();
    }

    @Test
    void testGetPhoneNumbersByStation() {
        // Mock data
        FireStation fireStation = new FireStation("1").addAddress("1509 Culver St");
        List<FireStation> fireStations = List.of(fireStation);

        Person person = new Person("John", "Doe", "123-456-7890", "12345", "1509 Culver St", "Culver", "john.doe@example.com", null);
        List<Person> persons = List.of(person);

        when(repository.getFireStations()).thenReturn(fireStations);
        when(repository.getPersons()).thenReturn(persons);

        // Execute
        List<String> result = service.getPhoneNumbersByStation(1);

        // Verify
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("123-456-7890", result.get(0));
        verify(repository, times(1)).getFireStations();
        verify(repository, times(1)).getPersons();
    }

    @Test
    void testGetFireData() {
        // Mock data
        FireStation fireStation = new FireStation("1").addAddress("1509 Culver St");
        List<FireStation> fireStations = List.of(fireStation);

        MedicalRecord medicalRecord = new MedicalRecord(
            new String[]{"peanut"},
            "2010-01-01",
            "John",
            "Doe",
            new String[]{"aspirin:500mg"}
        );
        Person person = new Person("John", "Doe", "123-456-7890", "12345", "1509 Culver St", "Culver", "john.doe@example.com", medicalRecord);
        List<Person> persons = List.of(person);

        when(repository.getFireStations()).thenReturn(fireStations);
        when(repository.getPersons()).thenReturn(persons);

        // Execute
        Map<String, Object> result = service.getFireData("1509 Culver St");

        // Verify
        assertNotNull(result);
        assertEquals(-1, result.get("stationNumber"));
        assertEquals(1, ((List<?>) result.get("residents")).size());
        verify(repository, times(1)).getFireStations();
        verify(repository, times(1)).getPersons();
    }

    @Test
    void testGetFloodData() {
        // Mock data
        FireStation fireStation = new FireStation("1").addAddress("1509 Culver St");
        List<FireStation> fireStations = List.of(fireStation);

        MedicalRecord medicalRecord = new MedicalRecord(
            new String[]{"peanut"},
            "2010-01-01",
            "John",
            "Doe",
            new String[]{"aspirin:500mg"}
        );
        Person person = new Person("John", "Doe", "123-456-7890", "12345", "1509 Culver St", "Culver", "john.doe@example.com", medicalRecord);
        List<Person> persons = List.of(person);

        when(repository.getFireStations()).thenReturn(fireStations);
        when(repository.getPersons()).thenReturn(persons);

        // Execute
        Map<String, List<Map<String, Object>>> result = service.getFloodData(List.of(1));

        // Verify
        assertNotNull(result);
        assertTrue(result.containsKey("1509 Culver St"));
        assertEquals(1, result.get("1509 Culver St").size());
        verify(repository, times(1)).getFireStations();
        verify(repository, times(1)).getPersons();
    }

    @Test
    void testGetPersonInfo() {
        // Mock data
        MedicalRecord medicalRecord = new MedicalRecord(
            new String[]{"peanut"},
            "2010-01-01",
            "John",
            "Doe",
            new String[]{"aspirin:500mg"}
        );
        Person person = new Person("John", "Doe", "123-456-7890", "12345", "1509 Culver St", "Culver", "john.doe@example.com", medicalRecord);
        List<Person> persons = List.of(person);

        when(repository.getPersons()).thenReturn(persons);

        // Execute
        List<Map<String, Object>> result = service.getPersonInfo("John", "Doe");

        // Verify
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).get("name"));
        verify(repository, times(1)).getPersons();
    }

    @Test
    void testGetEmailsByCity() {
        // Mock data
        Person person = new Person("John", "Doe", "123-456-7890", "12345", "1509 Culver St", "Culver", "john.doe@example.com", null);
        List<Person> persons = List.of(person);

        when(repository.getPersons()).thenReturn(persons);

        // Execute
        List<String> result = service.getEmailsByCity("Culver");

        // Verify
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("john.doe@example.com", result.get(0));
        verify(repository, times(1)).getPersons();
    }
    @Test
    void testAddPerson() {
        // Mock data
        MedicalRecord medicalRecord = new MedicalRecord(
            new String[]{"peanut", "shellfish"}, 
            "1990-01-01", 
            "John", 
            "Doe", 
            new String[]{"aspirin:500mg"}
        );
        Person person = new Person("John", "Doe", "123-456-7890", "12345", "1509 Culver St", "Culver", "john.doe@example.com", medicalRecord);

        when(repository.getPersons()).thenReturn(new ArrayList<>());

        // Execute
        String result = service.addPerson(person);

        // Verify
        assertEquals("Person added successfully.", result);
        verify(repository, times(1)).getPersons();
    }
    @Test
    void testUpdatePerson() {
        // Mock data
        MedicalRecord medicalRecord = new MedicalRecord(
            new String[]{"peanut", "shellfish"},
            "1990-01-01",
            "John",
            "Doe",
            new String[]{"aspirin:500mg"}
        );
        Person person = new Person("John", "Doe", "123-456-7890", "12345", "1509 Culver St", "Culver", "john.doe@example.com", medicalRecord);

        List<Person> persons = new ArrayList<>();
        persons.add(person);

        when(repository.getPersons()).thenReturn(persons);

        // Update details
        person.setPhone("987-654-3210");
        String result = service.updatePerson(person);

        // Verify
        assertEquals("Person updated successfully.", result);
        verify(repository, times(1)).getPersons();
    }

    @Test
    void testDeletePerson() {
        // Mock data
        MedicalRecord medicalRecord = new MedicalRecord(
            new String[]{"peanut", "shellfish"},
            "1990-01-01",
            "John",
            "Doe",
            new String[]{"aspirin:500mg"}
        );
        Person person = new Person("John", "Doe", "123-456-7890", "12345", "1509 Culver St", "Culver", "john.doe@example.com", medicalRecord);

        List<Person> persons = new ArrayList<>();
        persons.add(person);

        when(repository.getPersons()).thenReturn(persons);

        // Execute
        String result = service.deletePerson("John", "Doe");

        // Verify
        assertEquals("Person deleted successfully.", result);
        verify(repository, times(1)).getPersons();
    }
    @Test
    void testAddFireStation() {
        // Mock data
        FireStation fireStation = new FireStation("1").addAddress("1509 Culver St");

        when(repository.getFireStations()).thenReturn(new ArrayList<>());

        // Execute
        String result = service.addFireStation(fireStation);

        // Verify
        assertEquals("FireStation added successfully.", result);
        verify(repository, times(1)).getFireStations();
    }

    @Test
    void testUpdateFireStation() {
        // Mock data
        FireStation fireStation = new FireStation("1");
        fireStation.addAddress("1509 Culver St");

        List<FireStation> fireStations = new ArrayList<>();
        fireStations.add(fireStation);

        when(repository.getFireStations()).thenReturn(fireStations);

        // Execute
        String result = service.updateFireStation("1509 Culver St", 2);

        // Verify
        assertEquals("FireStation updated successfully.", result);
        verify(repository, times(1)).getFireStations();
    }

    @Test
    void testDeleteFireStation() {
        // Mock data
        FireStation fireStation = new FireStation("1").addAddress("1509 Culver St");

        List<FireStation> fireStations = new ArrayList<>();
        fireStations.add(fireStation);

        when(repository.getFireStations()).thenReturn(fireStations);

        // Execute
        String result = service.deleteFireStation("1509 Culver St");

        // Verify
        assertEquals("FireStation deleted successfully.", result);
        verify(repository, times(1)).getFireStations();
    }

    @Test
    void testAddMedicalRecord() {
        // Mock data
        MedicalRecord medicalRecord = new MedicalRecord(
            new String[]{"peanut", "shellfish"},
            "1990-01-01",
            "John",
            "Doe",
            new String[]{"aspirin:500mg"}
        );

        when(repository.getMedicalRecords()).thenReturn(new ArrayList<>());

        // Execute
        String result = service.addMedicalRecord(medicalRecord);

        // Verify
        assertEquals("MedicalRecord added successfully.", result);
        verify(repository, times(1)).getMedicalRecords();
    }

    @Test
    void testUpdateMedicalRecord() {
        // Mock data
        MedicalRecord medicalRecord = new MedicalRecord(
            new String[]{"peanut", "shellfish"},
            "1990-01-01",
            "John",
            "Doe",
            new String[]{"aspirin:500mg"}
        );

        List<MedicalRecord> medicalRecords = new ArrayList<>();
        medicalRecords.add(medicalRecord);

        when(repository.getMedicalRecords()).thenReturn(medicalRecords);

        // Update details
        medicalRecord.setMedications(new String[]{"ibuprofen:200mg"});
        String result = service.updateMedicalRecord(medicalRecord);

        // Verify
        assertEquals("MedicalRecord updated successfully.", result);
        verify(repository, times(1)).getMedicalRecords();
    }

    @Test
    void testDeleteMedicalRecord() {
        // Mock data
        MedicalRecord medicalRecord = new MedicalRecord(
            new String[]{"peanut", "shellfish"},
            "1990-01-01",
            "John",
            "Doe",
            new String[]{"aspirin:500mg"}
        );

        List<MedicalRecord> medicalRecords = new ArrayList<>();
        medicalRecords.add(medicalRecord);

        when(repository.getMedicalRecords()).thenReturn(medicalRecords);

        // Execute
        String result = service.deleteMedicalRecord("John", "Doe");

        // Verify
        assertEquals("MedicalRecord deleted successfully.", result);
        verify(repository, times(1)).getMedicalRecords();
    }
}