package com.employee.portal.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmployeeDataTest {

    @Test
    public void testConstructorAndGetters() {
        String userName = "john_doe";
        String password = "password123";
        String email = "john.doe@example.com";
        String dob = "1990-01-01";
        String firstName = "John";
        String lastName = "Doe";

        EmployeeData employee = new EmployeeData(userName, password, email, dob, firstName, lastName);

        Assertions.assertEquals(userName, employee.getUserName());
        Assertions.assertEquals(password, employee.getPassword());
        Assertions.assertEquals(email, employee.getEmail());
        Assertions.assertEquals(dob, employee.getDob());
        Assertions.assertEquals(firstName, employee.getFirstName());
        Assertions.assertEquals(lastName, employee.getLastName());
    }

    @Test
    public void testSetters() {
        EmployeeData employee = new EmployeeData();

        String userName = "john_doe";
        String password = "password123";
        String email = "john.doe@example.com";
        String dob = "1990-01-01";
        String firstName = "John";
        String lastName = "Doe";

        employee.setUserName(userName);
        employee.setPassword(password);
        employee.setEmail(email);
        employee.setDob(dob);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);

        Assertions.assertEquals(userName, employee.getUserName());
        Assertions.assertEquals(password, employee.getPassword());
        Assertions.assertEquals(email, employee.getEmail());
        Assertions.assertEquals(dob, employee.getDob());
        Assertions.assertEquals(firstName, employee.getFirstName());
        Assertions.assertEquals(lastName, employee.getLastName());
    }
}
