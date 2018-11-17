package com.bgdevs.madness.service.employee;

import com.bgdevs.madness.dao.entities.employee.Employee;
import com.bgdevs.madness.dao.repositories.EmployeeRepository;
import com.bgdevs.madness.service.employee.model.CreateEmployeeModel;
import com.bgdevs.madness.service.employee.model.EmployeeModel;
import com.bgdevs.madness.service.employee.model.EmployeeModelMapper;
import com.bgdevs.madness.service.exceptions.ElementNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public EmployeeModel create(CreateEmployeeModel employee) {
        Employee created = this.employeeRepository.save(toEntity(employee));
        return toModel(created);
    }

    public EmployeeModel findById(long id) {
        return this.employeeRepository.findById(id)
                .map(EmployeeModelMapper::toModel)
                .orElseThrow(() -> new ElementNotFoundException(id));
    }

    private Employee toEntity(CreateEmployeeModel employee) {
        return Employee.builder()
                .firstName(employee.getFirstName())
                .lastName(employee.getSecondName())
                .birthdayDate(employee.getBirthdayDate())
                .passportNumber(employee.getPassportNumber())
                .build();
    }

    public EmployeeModel delete(long id) {
        EmployeeModel employeeModel = this.employeeRepository.findById(id)
                .map(EmployeeModelMapper::toModel)
                .orElseThrow(() -> new ElementNotFoundException(id));
        this.employeeRepository.deleteById(id);
        return employeeModel;
    }

    public List<EmployeeModel> findAll() {
        return this.employeeRepository.findAll().stream()
                .map(EmployeeModelMapper::toModel)
                .collect(toList());
    }
}
