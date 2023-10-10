package com.picpaysimplificado.controllers;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dto.UserDTO;
import com.picpaysimplificado.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO ){
        User newUser = userService.createUser(userDTO);
        return new ResponseEntity<>( newUser, HttpStatus.CREATED );
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll(){
       List<User> getAllUser = userService.findAllUser();
       return new ResponseEntity<>(getAllUser, HttpStatus.OK);
    }
}
