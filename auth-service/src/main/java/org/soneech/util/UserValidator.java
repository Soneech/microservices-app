package org.soneech.util;

import org.soneech.model.User;

import org.soneech.serivce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class UserValidator implements Validator {
    private final UserService userService;
    @Value("${app.messages.email}")
    private String emailMessage;
    @Value("${app.messages.phone-number}")
    private String phoneNumberMessage;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        Optional<User> foundByPhoneNumber = userService.findByPhoneNumber(user.getPhoneNumber());
        if (foundByPhoneNumber.isPresent())
            errors.rejectValue("phoneNumber", "", phoneNumberMessage);

        Optional<User> fondByEmail = userService.findByEmail(user.getEmail());
        if (fondByEmail.isPresent())
            errors.rejectValue("email", emailMessage);
    }
}
