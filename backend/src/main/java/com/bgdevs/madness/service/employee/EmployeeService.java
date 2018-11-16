package com.bgdevs.madness.service.employee;

import com.bgdevs.madness.dao.entities.employee.Employee;
import com.bgdevs.madness.dao.repositories.EmployeeRepository;
import com.bgdevs.madness.service.employee.model.CreateEmployeeModel;
import com.bgdevs.madness.service.employee.model.EmployeeModel;
import com.bgdevs.madness.service.exceptions.ElementNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                .orElseThrow(() -> new ElementNotFoundException(id));
    }

    //todo
    private EmployeeModel toModel(Employee created) {
        return null;
    }

    //todo
    private Employee toEntity(CreateEmployeeModel employee) {
        return null;
    }

    public EmployeeModel delete(long id) {
        EmployeeModel employeeModel = this.employeeRepository.findById(id)
                .map(this::toModel)
                .orElseThrow(() -> new ElementNotFoundException(id));
        this.employeeRepository.deleteById(id);
        return employeeModel;
    }

    public Page<EmployeeModel> findAll(Pageable pageable) {
        return this.employeeRepository.findAll(pageable)
                .map(this::toModel);
    }
}
