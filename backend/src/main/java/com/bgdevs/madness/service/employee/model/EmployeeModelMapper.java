package com.bgdevs.madness.service.employee.model;

import com.bgdevs.madness.dao.entities.employee.Employee;
import com.bgdevs.madness.service.card.model.CardModelMapper;

import static java.util.stream.Collectors.toList;

/**
 * @author Nikita Shaldenkov
 */
public class EmployeeModelMapper {
    public static EmployeeModel toModel(Employee employee) {
        return EmployeeModel.builder()
                .firstName(employee.getFirstName())
                .secondName(employee.getSecondName())
                .birthdayDate(employee.getBirthdayDate())
                .id(employee.getId())
                .cards(employee.getCards().stream()
                        .map(CardModelMapper::toModel)
                        .collect(toList()))
                .build();
    }
}
