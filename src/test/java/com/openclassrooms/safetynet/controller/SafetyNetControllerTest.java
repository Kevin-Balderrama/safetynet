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
            void testGetChildrenByAddress() throws Exception {
                mockMvc.perform(get("/childAlert").param("address", "1509 Culver St"))
                       .andExpect(status().isOk())
                       .andExpect(jsonPath("$.children").isArray());
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
            void testGetFloodData() throws Exception {
                mockMvc.perform(get("/flood/stations").param("stations", "1,2"))
                       .andExpect(status().isOk())
                       .andExpect(jsonPath("$").isMap());
            }

            @Test
            void testGetPersonInfo() throws Exception {
                mockMvc.perform(get("/personInfo").param("firstName", "John").param("lastName", "Doe"))
                       .andExpect(status().isOk())
                       .andExpect(jsonPath("$").isArray());
            }

            @Test
            void testGetEmailsByCity() throws Exception {
                mockMvc.perform(get("/communityEmail").param("city", "Culver"))
                       .andExpect(status().isOk())
                       .andExpect(jsonPath("$").isArray());
            }

        }
    
