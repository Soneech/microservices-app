package org.soneech.serivce;

import org.soneech.exception.RoleException;
import org.soneech.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UserService userService, RoleService roleService,
                               PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(User user) throws RoleException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleService.findByName("ROLE_USER"));
        userService.save(user);
    }
}
