package org.soneech.controller;

import jakarta.validation.Valid;
import org.soneech.dto.AuthenticationDTO;
import org.soneech.dto.RegistrationDTO;
import org.soneech.exception.AuthException;
import org.soneech.mapper.DefaultMapper;
import org.soneech.model.User;
import org.soneech.security.JWTUtil;
import org.soneech.serivce.RegistrationService;
import org.soneech.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.soneech.util.ErrorsUtil.prepareFieldsErrorMessage;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserValidator userValidator;
    private final RegistrationService registrationService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final DefaultMapper mapper;

    @Autowired
    public AuthController(UserValidator userValidator, RegistrationService registrationService,
                          JWTUtil jwtUtil, AuthenticationManager authenticationManager, DefaultMapper mapper) {
        this.userValidator = userValidator;
        this.registrationService = registrationService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.mapper = mapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<Map<String, String>> performRegistration(@RequestBody @Valid RegistrationDTO registrationDTO,
                                                                   BindingResult bindingResult) {
        System.out.println("registr");
        User user = mapper.convertToUser(registrationDTO);
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            throw new AuthException(prepareFieldsErrorMessage(bindingResult));

        registrationService.register(user);
        String token = jwtUtil.generateToken(user.getUsername());
        return new ResponseEntity<>(Map.of("jwt_token", token), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> performLogin(@RequestBody AuthenticationDTO authenticationDTO)
            throws AuthenticationException {

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        authenticationDTO.getUsername(),
                        authenticationDTO.getPassword()
                );
        try {
            authenticationManager.authenticate(authToken);
        } catch (AuthenticationException exception) {
            throw new AuthException("Неверный логин или пароль");
        }

        String token = jwtUtil.generateToken(authenticationDTO.getUsername());
        return new ResponseEntity<>(Map.of("jwt_token", token), HttpStatus.OK);
    }
}
