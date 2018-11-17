package com.bgdevs.madness.config;

import com.bgdevs.madness.dao.entities.User;
import com.bgdevs.madness.dao.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Nikita Shaldenkov
 */
@Service
public class UserCreator implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        User user = new User("admin", passwordEncoder.encode("123"), null);
        this.userRepository.save(user);
    }
}
