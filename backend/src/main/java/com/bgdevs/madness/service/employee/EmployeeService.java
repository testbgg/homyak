package com.bgdevs.madness.service.employee;

import com.bgdevs.madness.dao.entities.employee.Employee;
import com.bgdevs.madness.dao.exceptions.ElementNotFoundException;
import com.bgdevs.madness.dao.repositories.EmployeeRepository;
import com.bgdevs.madness.service.employee.model.CreateEmployeeModel;
import com.bgdevs.madness.service.employee.model.EmployeeModel;
import com.bgdevs.madness.service.employee.model.EmployeeModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bgdevs.madness.service.employee.model.EmployeeModelMapper.toModel;
import static java.util.stream.Collectors.toList;

/**
 * @author Nikita Shaldenkov
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional
    public EmployeeModel create(CreateEmployeeModel employee) {
        Employee created = this.employeeRepository.save(toEntity(employee));
        return toModel(created);
    }

    @Transactional(readOnly = true)
    public EmployeeModel findById(long id) {
        return this.employeeRepository.findById(id)
                .map(EmployeeModelMapper::toModel)
                .orElseThrow(() -> new ElementNotFoundException("Unable to find employee with id:" + id));
    }

    private Employee toEntity(CreateEmployeeModel employee) {
        return Employee.builder()
                .firstName(employee.getFirstName())
                .lastName(employee.getSecondName())
                .birthdayDate(employee.getBirthdayDate())
                .passportNumber(employee.getPassportNumber())
                .build();
    }

    @Transactional
    public EmployeeModel delete(long id) {
        EmployeeModel employeeModel = this.employeeRepository.findById(id)
                .map(EmployeeModelMapper::toModel)
                .orElseThrow(() -> new ElementNotFoundException("Unable to find employee with id:" + id));
        this.employeeRepository.deleteById(id);
        return employeeModel;
    }

    @Transactional(readOnly = true)
    public List<EmployeeModel> findAll() {
        return this.employeeRepository.findAll().stream()
                .map(EmployeeModelMapper::toModel)
                .collect(toList());
    }
}
