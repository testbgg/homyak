package com.bgdevs.madness.service.employee.model;

import com.bgdevs.madness.service.card.model.CardModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Nikita Shaldenkov
 */
@Data
@AllArgsConstructor
@Builder
public class EmployeeModel {

    private Long id;

    private String firstName;

    private String secondName;

    private LocalDate birthdayDate;

    private String passportNumber;

    private List<CardModel> cards;
}
