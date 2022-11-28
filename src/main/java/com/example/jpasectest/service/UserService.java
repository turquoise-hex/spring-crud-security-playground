package com.example.jpasectest.service;

import com.example.jpasectest.model.Role;
import com.example.jpasectest.model.Status;
import com.example.jpasectest.model.User;
import com.example.jpasectest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private MailSenderService mailSenderService;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByEmail(String email){
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exist"));
        return user;
    }

    public boolean checkIfUserExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean registerNewUserAccount(User user) {
        Optional<User> userExists = userRepository.findByEmail(user.getEmail());
        if (userExists.isPresent()){
            return false;
        }

        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setRole(Role.USER);
        user.setStatus(Status.ACTIVE);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setActivated(false);

        userRepository.save(user);

        String message = String.format(
                "Hello, %s! \n" +
                        "Welcome! Please visit this link: http://localhost:8080/register/activate/%s",
                user.getName(),
                user.getActivationCode()
        );
        mailSenderService.send(user.getEmail(), "Activation code", message);

        return true;
    }


    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);
        if (user == null){
            return false;
        }

        user.setActivationCode(null);
        user.setActivated(true);

        userRepository.save(user);

        return true;
    }
}
