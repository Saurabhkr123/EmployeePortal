package com.employee.portal.controller;

import com.employee.portal.model.EmployeeData;
import com.employee.portal.model.EmployeeLoginResponse;
import com.employee.portal.model.EmployeeSignInInfo;
import com.employee.portal.model.EmployeeSignUpResponse;
import com.employee.portal.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
@RestController
@CrossOrigin(origins = "http://localhost:3000")
//@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    public Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @PostMapping("/signup")
    public EmployeeSignUpResponse signUp(@RequestBody EmployeeData model) throws ParseException {
        LOGGER.info("Entering signup");
        return employeeService.signUpService(model);
    }

    @PostMapping("/login")
    public EmployeeLoginResponse login(@RequestBody EmployeeSignInInfo model){
        LOGGER.info("Entering login");
    return employeeService.login(model);
    }
}




