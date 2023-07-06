package com.employee.portal.repository;

import com.employee.portal.repository.entity.TEmployee;
import com.employee.portal.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class EmployeeRepositoryTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByUserName() {
        // Arrange
        String userName = "johndoe";
        TEmployee employee = new TEmployee();
        employee.setUserName(userName);

        Mockito.when(employeeRepository.findByUserName(userName))
                .thenReturn(employee);

        // Act
        TEmployee result = employeeRepository.findByUserName(userName);

        // Assert
        Assertions.assertEquals(employee, result);
    }
}
