package com.employee.portal.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmployeeLoginResponseTest {

    @Test
    public void testConstructorAndGetters() {
        String message = "Login successful";
        boolean isValid = true;

        EmployeeLoginResponse response = new EmployeeLoginResponse(message, isValid);

        Assertions.assertEquals(message, response.getMessage());
        Assertions.assertEquals(isValid, response.isValid());
    }

    @Test
    public void testSetters() {
        EmployeeLoginResponse response = new EmployeeLoginResponse();

        String message = "Login successful";
        boolean isValid = true;

        response.setMessage(message);
        response.setValid(isValid);

        Assertions.assertEquals(message, response.getMessage());
        Assertions.assertEquals(isValid, response.isValid());
    }
}
