package com.bgdevs.madness.service.employee;

import com.bgdevs.madness.dao.entities.employee.Employee;
import com.bgdevs.madness.dao.repositories.EmployeeRepository;
import com.bgdevs.madness.service.employee.exceptions.EmployeeNotFoundException;
import com.bgdevs.madness.service.employee.model.CreateEmployeeModel;
import com.bgdevs.madness.service.employee.model.EmployeeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Nikita Shaldenkov
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeModel create(CreateEmployeeModel employee) {
        Employee created = this.employeeRepository.save(toEntity(employee));
        return toModel(created);
    }

    public EmployeeModel findById(long id) {
        return this.employeeRepository.findById(id)
                .map(this::toModel)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    private EmployeeModel toModel(Employee created) {
        return null;
    }

    private Employee toEntity(CreateEmployeeModel employee) {
        return null;
    }

    public EmployeeModel delete(long id) {
        EmployeeModel employeeModel = this.employeeRepository.findById(id)
                .map(this::toModel)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        this.employeeRepository.deleteById(id);
        return employeeModel;
    }
}
