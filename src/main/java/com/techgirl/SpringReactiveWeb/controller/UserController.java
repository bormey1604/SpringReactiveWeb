package com.techgirl.SpringReactiveWeb.controller;

import com.techgirl.SpringReactiveWeb.model.User;
import com.techgirl.SpringReactiveWeb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/fetch-api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public Mono<User> createUser(@RequestBody User user) {
        return userService.createUser(user);

    }

    @GetMapping("/{id}")
    public Mono<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }


    @GetMapping("/all")
    public Flux<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
