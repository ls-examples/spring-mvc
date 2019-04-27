package ru.lilitweb.books.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.lilitweb.books.domain.User;
import ru.lilitweb.books.model.UserDetailsImpl;
import ru.lilitweb.books.repostory.UserRepository;

import java.util.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;


    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return new UserDetailsImpl(user);
        } else {
            throw new UsernameNotFoundException("username not found");
        }
    }
}
