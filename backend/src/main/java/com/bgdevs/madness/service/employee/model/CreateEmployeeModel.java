package com.bgdevs.madness.service.employee.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author Nikita Shaldenkov
 */
@Data
public class CreateEmployeeModel {

    @NotNull
    private String firstName;

    @NotNull
    private String secondName;

    @NotNull
    private LocalDate birthdayDate;

    @NotNull
    private String passportNumber;
}
