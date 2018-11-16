package com.bgdevs.madness.dao.entities;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

/**
 * @author Nikita Shaldenkov
 */
public class BaseEntityListener {

    @PrePersist
    public void setCreatedAndUpdatedDate(BaseEntity baseEntity) {
        baseEntity.setCreatedDate(LocalDateTime.now());
        baseEntity.setUpdatedDate(LocalDateTime.now());
    }

    @PreUpdate
    public void setUpdatedDate(BaseEntity baseEntity) {
        baseEntity.setUpdatedDate(LocalDateTime.now());
    }

}
