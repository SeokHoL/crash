package com.seokho.crash.controller;


import com.seokho.crash.model.user.User;
import com.seokho.crash.model.user.UserAuthenticationResponse;
import com.seokho.crash.model.user.UserLoginRequestBody;
import com.seokho.crash.model.user.UserSignUpRequestBody;
import com.seokho.crash.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> singUp(
            @Valid @RequestBody UserSignUpRequestBody userSignUpRequestBody
            ){
        var user = userService.signUp(userSignUpRequestBody);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserAuthenticationResponse> authenticate(
            @Valid @RequestBody UserLoginRequestBody userLoginRequestBody
            ){
        var response =  userService.authenticate(userLoginRequestBody);
        return ResponseEntity.ok(response);


    }
}
