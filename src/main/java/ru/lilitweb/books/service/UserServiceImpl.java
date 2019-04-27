package ru.lilitweb.books.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lilitweb.books.domain.Authority;
import ru.lilitweb.books.domain.User;
import ru.lilitweb.books.dto.UserDto;
import ru.lilitweb.books.repostory.UserRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private PasswordEncoder passwordEncoder;

    private UserRepository userRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public User registerNewUserAccount(UserDto accountDto)
            throws EmailExistsException {

        if (emailExists(accountDto.getEmail())) {
            throw new EmailExistsException();
        }
        User user = new User();
        user.setFullname(accountDto.getFullname());
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());
        Authority userRole = new Authority("ROLE_USER");
        user.setAuthorities(new HashSet<>(Collections.singletonList(userRole)));
        return userRepository.save(user);
    }

    private boolean emailExists(String email) {
        User user = userRepository.findByEmail(email);
        return user != null;
    }

    public void addRoleAdmin(User user) {
        Set<Authority> authorities = user.getAuthorities();
        authorities = authorities != null ? authorities : new HashSet<>();
        authorities.add(new Authority("ROLE_ADMIN"));
        user.setAuthorities(new HashSet<>(authorities));
        userRepository.save(user);
    }
}
