package com.openclassrooms.safetynet.service;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.LoadJson;

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
        FireStation fireStation = new FireStation("1");
        fireStation.addAddress("1509 Culver St");
        when(repository.getFireStations()).thenReturn(List.of(fireStation));

        Person adult = new Person.PersonBuilder().firstName("John").lastName("Doe").address("1509 Culver St").build();
        Person child = new Person.PersonBuilder().firstName("Jane").lastName("Doe").address("1509 Culver St").build();

        when(repository.getPersons()).thenReturn(List.of(adult, child));

        Map<String, Object> result = service.getPersonsByStation(1);

        assertEquals(2, ((List<?>) result.get("persons")).size());
        assertEquals(1, result.get("adults"));
        assertEquals(1, result.get("children"));
    }

    @Test
    void testGetChildrenByAddress() {
        Person child = new Person.PersonBuilder().firstName("Jane").lastName("Doe").address("1509 Culver St").build();
        Person adult = new Person.PersonBuilder().firstName("John").lastName("Doe").address("1509 Culver St").build();

        when(repository.getPersons()).thenReturn(List.of(child, adult));

        Object result = service.getChildrenByAddress("1509 Culver St");

        assertEquals(1, ((Map<?, ?>) result).get("children"));
    }

    @Test
    void testGetPhoneNumbersByStation() {
        FireStation fireStation = new FireStation("1");
        fireStation.addAddress("1509 Culver St");
        when(repository.getFireStations()).thenReturn(List.of(fireStation));

        Person person = new Person.PersonBuilder().phone("123-456-7890").address("1509 Culver St").build();
        when(repository.getPersons()).thenReturn(List.of(person));

        List<String> result = service.getPhoneNumbersByStation(1);

        assertEquals(1, result.size());
        assertEquals("123-456-7890", result.get(0));
    }

    @Test
    void testGetFireData() {
        FireStation fireStation = new FireStation("1");
        fireStation.addAddress("1509 Culver St");
        when(repository.getFireStations()).thenReturn(List.of(fireStation));

        Person person = new Person.PersonBuilder().firstName("John").lastName("Doe").address("1509 Culver St").build();
        when(repository.getPersons()).thenReturn(List.of(person));

        Map<String, Object> result = service.getFireData("1509 Culver St");

        assertEquals(1, result.get("stationNumber"));
    }
}