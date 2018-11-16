package com.bgdevs.madness.service.employee;

import com.bgdevs.madness.dao.entities.employee.Employee;
import com.bgdevs.madness.dao.repositories.EmployeeRepository;
import com.bgdevs.madness.service.employee.model.CreateEmployeeModel;
import com.bgdevs.madness.service.employee.model.EmployeeModel;
import com.bgdevs.madness.service.employee.model.EmployeeModelMapper;
import com.bgdevs.madness.service.exceptions.ElementNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.bgdevs.madness.service.employee.model.EmployeeModelMapper.toModel;

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
                .secondName(employee.getSecondName())
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

    public Page<EmployeeModel> findAll(Pageable pageable) {
        return this.employeeRepository.findAll(pageable)
                .map(EmployeeModelMapper::toModel);
    }
}
