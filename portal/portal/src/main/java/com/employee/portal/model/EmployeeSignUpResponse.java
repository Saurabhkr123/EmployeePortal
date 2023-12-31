package com.employee.portal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSignUpResponse {
    private ValidationMsg errorMessage;
    private String message;
}
