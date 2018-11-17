package com.bgdevs.madness.dao.entities;

import com.bgdevs.madness.dao.entities.invoice.Invoice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * @author Nikita Shaldenkov
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_users")
public class User extends BaseEntity {

    private String username;

    private String password;

    @OneToMany(mappedBy = "ownerId")
    private List<Invoice> invoices;


}
