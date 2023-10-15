package org.soneech.controller;

import org.soneech.dto.UserResponseDTO;
import org.soneech.mapper.DefaultMapper;
import org.soneech.model.User;
import org.soneech.serivce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final DefaultMapper mapper;

    @Autowired
    public UserController(UserService userService, DefaultMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll().stream().map(mapper::convertToResponseDTO).toList());
    }
}
