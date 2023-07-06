package com.employee.portal.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmployeeSignUpResponseTest {

    @Test
    public void testConstructorAndGetters() {
        ValidationMsg errorMessage = new ValidationMsg("Invalid input", false);
        String message = "Employee sign-up failed";

        EmployeeSignUpResponse response = new EmployeeSignUpResponse(errorMessage, message);

        Assertions.assertEquals(errorMessage, response.getErrorMessage());
        Assertions.assertEquals(message, response.getMessage());
    }

    @Test
    public void testSetters() {
        EmployeeSignUpResponse response = new EmployeeSignUpResponse();

        ValidationMsg errorMessage = new ValidationMsg("Invalid input", false);
        String message = "Employee sign-up failed";

        response.setErrorMessage(errorMessage);
        response.setMessage(message);

        Assertions.assertEquals(errorMessage, response.getErrorMessage());
        Assertions.assertEquals(message, response.getMessage());
    }
}
