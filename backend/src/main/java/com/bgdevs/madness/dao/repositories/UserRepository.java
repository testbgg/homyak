package com.bgdevs.madness.dao.repositories;

import com.bgdevs.madness.dao.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nikita Shaldenkov <nikita.shaldenkov@bostongene.com>
 */
public interface UserRepository extends JpaRepository<User, String> {
}
