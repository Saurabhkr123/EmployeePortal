package com.employee.portal.service;

import com.employee.portal.model.EmployeeData;
import com.employee.portal.model.EmployeeLoginResponse;
import com.employee.portal.model.EmployeeSignUpResponse;
import com.employee.portal.model.EmployeeSignInInfo;
import com.employee.portal.repository.EmployeeRepository;
import com.employee.portal.repository.entity.TEmployee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void signUpService_ValidData_ReturnsSuccessResponse() throws ParseException {
        // Arrange
        EmployeeData employeeData = createEmployeeData();
        when(employeeRepository.findByUserName(any())).thenReturn(null);
        when(employeeRepository.saveAndFlush(any())).thenReturn(new TEmployee());

        // Act
        EmployeeSignUpResponse response = employeeService.signUpService(employeeData);

        // Assert
        assertTrue(response.getMessage().contains("Employee added successfully!"));
        assertNull(response.getErrorMessage());
    }

    @Test
    void signUpService_InvalidPassword_ReturnsErrorResponse() throws ParseException {
        // Arrange
        EmployeeData employeeData = createEmployeeData();
        employeeData.setPassword("BYjIxmxUxt6Lo08Ss32AsQnyvltBSuB5Mjpgbs1f1yA="); // Set an invalid password
        when(employeeRepository.findByUserName(any())).thenReturn(null);

        // Act
        EmployeeSignUpResponse response = employeeService.signUpService(employeeData);

        // Assert
        assertNotNull(response.getErrorMessage());
        assertFalse(response.getErrorMessage().isValid());
        assertEquals("Password length should be between 8 and 20 characters", response.getErrorMessage().getMessage());
    }

    @Test
    void signUpService_ExistingUserName_ReturnsErrorResponse() throws ParseException {
        // Arrange
        EmployeeData employeeData = createEmployeeData();
        when(employeeRepository.findByUserName(any())).thenReturn(new TEmployee());

        // Act
        EmployeeSignUpResponse response = employeeService.signUpService(employeeData);

        // Assert
        assertNotNull(response.getErrorMessage());
        assertFalse(response.getErrorMessage().isValid());
        assertEquals("An Employee with the same name already exists!", response.getErrorMessage().getMessage());
    }

    @Test
    void login_ValidCredentials_ReturnsSuccessResponse() {
        // Arrange
        String userName = "john_doe1279";
        String password = "pZWR4lfzZ56FPmNj+K10gg==";
        TEmployee employee = createTEmployee(userName, password);
        EmployeeSignInInfo signInInfo = new EmployeeSignInInfo();
        signInInfo.setUserName(userName);
        signInInfo.setPassword(password);

        when(employeeRepository.findByUserName(userName)).thenReturn(employee);

        // Act
        EmployeeLoginResponse response = employeeService.login(signInInfo);

        // Assert
        assertTrue(response.isValid());
        assertEquals("Logged In", response.getMessage());
    }

    @Test
    void login_InvalidPassword_ReturnsErrorResponse() {
        // Arrange
        String userName = "john_doe1279";
        String password = "pZWR4lfzZ56FPmNj+K10gg==";
        TEmployee employee = createTEmployee(userName, password);
        EmployeeSignInInfo signInInfo = new EmployeeSignInInfo();
        signInInfo.setUserName(userName);
        signInInfo.setPassword("incorrect"); // Set an incorrect password

        when(employeeRepository.findByUserName(userName)).thenReturn(employee);

        // Act
        EmployeeLoginResponse response = employeeService.login(signInInfo);

        // Assert
        assertFalse(response.isValid());
        assertEquals("Incorrect Password", response.getMessage());
    }

    @Test
    void login_UserNotFound_ReturnsErrorResponse() {
        // Arrange
        String userName = "testuser";
        EmployeeSignInInfo signInInfo = new EmployeeSignInInfo();
        signInInfo.setUserName(userName);

        when(employeeRepository.findByUserName(userName)).thenReturn(null);

        // Act
        EmployeeLoginResponse response = employeeService.login(signInInfo);

        // Assert
        assertFalse(response.isValid());
        assertEquals("User not found", response.getMessage());
    }

    private EmployeeData createEmployeeData() throws ParseException {
        EmployeeData employeeData = new EmployeeData();
        employeeData.setFirstName("John");
        employeeData.setLastName("Doe");
        employeeData.setUserName("john_doe1279");
        employeeData.setEmail("johndoe@example.com");
        employeeData.setPassword("pZWR4lfzZ56FPmNj+K10gg==");
        employeeData.setDob("1990-01-01");
        return employeeData;
    }

    @Test
    public void testEncodePassword() {
        String password = "Abc123!@#";
        String encodedPassword = EmployeeService.encodePassword(password);
        Assertions.assertNotNull(encodedPassword);
        Assertions.assertNotEquals(password, encodedPassword);
    }
    private TEmployee createTEmployee(String userName, String password) {
        TEmployee employee = new TEmployee();
        employee.setUserName(userName);
        employee.setPassword(passwordEncoder.encode(password));
        return employee;
    }
}
