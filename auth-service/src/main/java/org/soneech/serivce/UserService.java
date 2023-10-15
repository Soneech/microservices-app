package org.soneech.serivce;

import org.soneech.exception.UserException;
import org.soneech.model.User;
import org.soneech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Value("${app.messages.not-found.user}")
    private String userNotFoundMessage;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isEmpty())
            throw new UserException(userNotFoundMessage, HttpStatus.NOT_FOUND);

        return foundUser;
    }

    public Optional<User> findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
