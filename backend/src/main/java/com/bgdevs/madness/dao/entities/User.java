package com.bgdevs.madness.dao.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

/**
 * @author Nikita Shaldenkov
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class User extends BaseEntity {

    private String username;

    private String password;

    private String contractNumber;


}
