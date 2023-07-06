package com.employee.portal.controller;

import com.employee.portal.model.EmployeeData;
import com.employee.portal.model.EmployeeLoginResponse;
import com.employee.portal.model.EmployeeSignInInfo;
import com.employee.portal.model.EmployeeSignUpResponse;
import com.employee.portal.service.EmployeeService;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    private EmployeeController employeeController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        employeeController = new EmployeeController(employeeService);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    void signUp_shouldReturnSignUpResponse() throws Exception {
        // Mocking the employeeService.signUpService() method
        EmployeeSignUpResponse expectedResponse = new EmployeeSignUpResponse();
        when(employeeService.signUpService(any(EmployeeData.class))).thenReturn(expectedResponse);

        // Prepare the request
        EmployeeData employee = new EmployeeData();
        employee.setUserName("john_doe1279");
        employee.setPassword("pZWR4lfzZ56FPmNj+K10gg==");
        employee.setEmail("john.doe@exampledk6.com");
        employee.setDob("1990-05-15");
        employee.setFirstName("John");
        employee.setLastName("Doe");

        // Set the required fields in employeeData object

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(employee));

        // Perform the request
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Verify the response
        String responseJson = result.getResponse().getContentAsString();
        EmployeeSignUpResponse actualResponse = asObject(responseJson, EmployeeSignUpResponse.class);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void login_shouldReturnLoginResponse() throws Exception {
        // Mocking the employeeService.login() method
        EmployeeLoginResponse expectedResponse = new EmployeeLoginResponse();
        when(employeeService.login(any(EmployeeSignInInfo.class))).thenReturn(expectedResponse);

        // Prepare the request
        EmployeeSignInInfo signInInfo = new EmployeeSignInInfo();
        // Set the required fields in signInInfo object
        signInInfo.setUserName("john_doe1279");
        signInInfo.setPassword("pZWR4lfzZ56FPmNj+K10gg==");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(signInInfo));

        // Perform the request
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // Verify the response
        String responseJson = result.getResponse().getContentAsString();
        EmployeeLoginResponse actualResponse = asObject(responseJson, EmployeeLoginResponse.class);
        assertEquals(expectedResponse, actualResponse);
    }

    // Utility method to convert object to JSON string
    private static String asJsonString(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Utility method to convert JSON string to object
    private static <T> T asObject(String jsonString, Class<T> objectClass) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonString, objectClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
