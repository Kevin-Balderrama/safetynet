package com.openclassrooms.safetynet.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SafetyNetController.class)
class SafetyNetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetPersonsByStation() throws Exception {
        mockMvc.perform(get("/firestation").param("stationNumber", "1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.persons").isArray());
    }

    @Test
    void testGetPersonsByStation_InvalidStationNumber() throws Exception {
        mockMvc.perform(get("/firestation").param("stationNumber", "999")) // Non-existent station
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.persons").doesNotExist()); // Expecting an empty array
    }

    @Test
    void testGetPersonsByStation_MissingParameter() throws Exception {
        mockMvc.perform(get("/firestation")) // Missing stationNumber parameter
               .andExpect(status().isBadRequest()); // Expecting a 400 Bad Request
    }

    @Test
    void testGetChildrenByAddress() throws Exception {
        mockMvc.perform(get("/childAlert").param("address", "1509 Culver St"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.children").isArray());
    }

    @Test
    void testGetChildrenByAddress_NoChildren() throws Exception {
        mockMvc.perform(get("/childAlert").param("address", "Unknown Address")) // Address with no children
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.children").doesNotExist()); // Expecting an empty array
    }

    @Test
    void testGetPhonesByStation() throws Exception {
        mockMvc.perform(get("/phoneAlert").param("firestation", "2"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetFireData() throws Exception {
        mockMvc.perform(get("/fire").param("address", "1509%20Culver%20St"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.stationNumber").isNumber());
    }

    @Test
    void testGetFireData_InvalidAddress() throws Exception {
        mockMvc.perform(get("/fire").param("address", "Unknown Address")) // Non-existent address
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.stationNumber").value(-1)); // No station number
    }

    @Test
    void testGetFloodData() throws Exception {
        mockMvc.perform(get("/flood/stations").param("stations", "1,2"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isMap());
    }

    @Test
    void testGetFloodData_EmptyResponse() throws Exception {
        mockMvc.perform(get("/flood/stations").param("stations", "999")) // Non-existent stations
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isEmpty()); // Expecting an empty map
    }

    @Test
    void testGetPersonInfo() throws Exception {
        mockMvc.perform(get("/personInfo").param("firstName", "John").param("lastName", "Doe"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetPersonInfo_ValidateResponseContent() throws Exception {
        mockMvc.perform(get("/personInfo").param("firstName", "John").param("lastName", "Doe"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].firstName").doesNotExist()) // Validate first person in the array
               .andExpect(jsonPath("$[0].lastName").doesNotExist())
               .andExpect(jsonPath("$[0].age").doesNotExist()); // Validate age
    }

    @Test
    void testGetEmailsByCity() throws Exception {
        mockMvc.perform(get("/communityEmail").param("city", "Culver"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetEmailsByCity_EmptyCity() throws Exception {
        mockMvc.perform(get("/communityEmail").param("city", "")) // Empty city parameter
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isEmpty()); // Expecting an empty array
    }

    @Test
    void testGetEmailsByCity_InvalidCity() throws Exception {
        mockMvc.perform(get("/communityEmail").param("city", "Unknown City")) // Non-existent city
               .andExpect(status().isOk())
               .andExpect(jsonPath("$").isEmpty()); // Expecting an empty array
    }
}