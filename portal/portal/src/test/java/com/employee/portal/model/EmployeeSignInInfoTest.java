package com.employee.portal.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmployeeSignInInfoTest {

    @Test
    public void testConstructorAndGetters() {
        String userName = "john_doe";
        String password = "password123";

        EmployeeSignInInfo signInInfo = new EmployeeSignInInfo(userName, password);

        Assertions.assertEquals(userName, signInInfo.getUserName());
        Assertions.assertEquals(password, signInInfo.getPassword());
    }

    @Test
    public void testSetters() {
        EmployeeSignInInfo signInInfo = new EmployeeSignInInfo();

        String userName = "john_doe";
        String password = "password123";

        signInInfo.setUserName(userName);
        signInInfo.setPassword(password);

        Assertions.assertEquals(userName, signInInfo.getUserName());
        Assertions.assertEquals(password, signInInfo.getPassword());
    }
}
