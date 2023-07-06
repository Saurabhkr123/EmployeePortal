package com.employee.portal.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ValidationMsgTest {

    @Test
    public void testConstructorAndGetters() {
        String message = "Invalid input";
        boolean isValid = false;

        ValidationMsg validationMsg = new ValidationMsg(message, isValid);

        Assertions.assertEquals(message, validationMsg.getMessage());
        Assertions.assertEquals(isValid, validationMsg.isValid());
    }

    @Test
    public void testSetters() {
        ValidationMsg validationMsg = new ValidationMsg();

        String message = "Invalid input";
        boolean isValid = false;

        validationMsg.setMessage(message);
        validationMsg.setValid(isValid);

        Assertions.assertEquals(message, validationMsg.getMessage());
        Assertions.assertEquals(isValid, validationMsg.isValid());
    }
}
