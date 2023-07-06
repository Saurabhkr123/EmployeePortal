package com.employee.portal.repository;

import com.employee.portal.repository.entity.TEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<TEmployee,Long> {

    TEmployee findByUserName(String userName);
}
