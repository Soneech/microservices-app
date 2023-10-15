package org.soneech.serivce;

import org.soneech.exception.RoleException;
import org.soneech.model.Role;
import org.soneech.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    @Value("${app.messages.not-found.role}")
    private String roleNotFoundMessage;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String name) {
        Optional<Role> foundRole = roleRepository.findByName(name);
        if (foundRole.isEmpty())
            throw new RoleException(roleNotFoundMessage, HttpStatus.NOT_FOUND);
        return foundRole.get();
    }
}
