package com.employee.portal.service;

import com.employee.portal.model.EmployeeData;
import com.employee.portal.model.ValidationMsg;
import com.employee.portal.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.text.ParseException;

public class PasswordValidationTest {

    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;
    @BeforeEach
    public void setup() {
        // Initialize the EmployeeService (you may need to provide the necessary dependencies)
        employeeService = new EmployeeService(employeeRepository);
    }

    @Test
    public void testValidPassword() throws ParseException {
        EmployeeData employeeData = createValidEmployeeData();
        ValidationMsg result = employeeService.validatePassword(employeeData);
        Assertions.assertTrue(result.isValid());
        Assertions.assertNull(result.getMessage());
    }

    @Test
    public void testInvalidPasswordLength() throws ParseException {
        EmployeeData employeeData = createValidEmployeeData();
        employeeData.setPassword("pass");  // Password length is less than 8 characters

        ValidationMsg result = employeeService.validatePassword(employeeData);

        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals("Password length should be between 8 and 20 characters", result.getMessage());
    }

    @Test
    public void testInvalidPasswordContent() throws ParseException {
        EmployeeData employeeData = createValidEmployeeData();
        employeeData.setPassword("abcd1234");  // Password does not contain a symbol

        ValidationMsg result = employeeService.validatePassword(employeeData);

        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals("Password should contain an uppercase,  a lowercase, a number, and a symbol", result.getMessage());
    }

    @Test
    public void testInvalidRepeatedNumbers() throws ParseException {
        EmployeeData employeeData = createValidEmployeeData();
        employeeData.setPassword("Abc1123!@#");  // Password contains repeated numbers

        ValidationMsg result = employeeService.validatePassword(employeeData);

        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals("Password should not contain repeated numbers for more than 3", result.getMessage());
    }

    @Test
    public void testInvalidPasswordContainsUsername() throws ParseException {
        EmployeeData employeeData = createValidEmployeeData();
        employeeData.setPassword("John_doe1@");  // Password contains the username

        ValidationMsg result = employeeService.validatePassword(employeeData);

        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals("Password should not contain the username", result.getMessage());
    }

    @Test
    public void testInvalidPasswordContainsSequence() throws ParseException {
        EmployeeData employeeData = createValidEmployeeData();
        employeeData.setPassword("Abc123!@#");  // Password contains a sequence of numbers

        ValidationMsg result = employeeService.validatePassword(employeeData);

        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals("Password should not contain sequence of numbers", result.getMessage());
    }

    @Test
    public void testInvalidPasswordContainsNameCombination() throws ParseException {
        EmployeeData employeeData = createValidEmployeeData();
        employeeData.setPassword("JohnDoe1&2gn");  // Password is a combination of first name and last name

        ValidationMsg result = employeeService.validatePassword(employeeData);

        Assertions.assertFalse(result.isValid());
        Assertions.assertEquals("Password should not be a combination of first name and last name", result.getMessage());
    }

    // Helper method to create a valid EmployeeData object for testing
    private EmployeeData createValidEmployeeData() throws ParseException {
        EmployeeData employeeData = new EmployeeData();
        employeeData.setFirstName("John");
        employeeData.setLastName("Doe1");
        employeeData.setUserName("John_doe");
        employeeData.setEmail("john.doe@example.com");
        employeeData.setPassword("Abc1anbcd!@#");
        employeeData.setDob("2000-01-01");  // Set a valid date of birth
        return employeeData;
    }
}

