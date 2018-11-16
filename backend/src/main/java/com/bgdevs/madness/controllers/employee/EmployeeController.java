package com.bgdevs.madness.controllers.employee;

import com.bgdevs.madness.service.employee.EmployeeService;
import com.bgdevs.madness.service.employee.model.CreateEmployeeModel;
import com.bgdevs.madness.service.employee.model.EmployeeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static java.net.URI.create;

/**
 * @author Nikita Shaldenkov
 */
@RestController
@RequestMapping("/employees")
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<?> getEmployees(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(this.employeeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable long id) {
        return ResponseEntity.ok(this.employeeService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable long id) {
        return ResponseEntity.ok(this.employeeService.delete(id));
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@Valid @RequestBody CreateEmployeeModel employee) {
        EmployeeModel createdEmployee = this.employeeService.create(employee);
        return ResponseEntity.created(create("/employees/" + createdEmployee.getId())).body(createdEmployee);
    }
}
