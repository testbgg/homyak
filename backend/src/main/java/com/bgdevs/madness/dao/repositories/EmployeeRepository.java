package com.bgdevs.madness.dao.repositories;

import com.bgdevs.madness.dao.entities.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nikita Shaldenkov
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
