package com.example.kottabi.controller;


import com.example.kottabi.config.UserService;
import com.example.kottabi.models.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public Page<UserEntity> findAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return userService.findAllUsers(page, size);
    }

    @GetMapping("{id}")
    public UserEntity findUserById(
            @PathVariable long id
    ) {
        return userService.findUserById(id);
    }

    @DeleteMapping("{id}")
    public void supprimerUser(
            @PathVariable long id
    ) {
        userService.supprimerUser(id);
    }
}