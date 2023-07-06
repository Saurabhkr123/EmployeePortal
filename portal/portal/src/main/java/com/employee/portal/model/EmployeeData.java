package com.employee.portal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeData {

    private String userName;
    private String password;
    private String email;
    private String dob;
    private String firstName;
    private String lastName;

}


