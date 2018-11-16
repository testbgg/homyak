package com.bgdevs.madness.dao.entities;

import com.bgdevs.madness.dao.entities.invoice.Invoice;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

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

    @OneToMany(mappedBy = "owner")
    private List<Invoice> invoices;


}
