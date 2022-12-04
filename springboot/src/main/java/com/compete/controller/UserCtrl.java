package com.compete.controller;

import com.compete.entity.SysUser;
import com.compete.service.UserService;
import com.compete.util.response.BookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/userCtrl"})
public class UserCtrl {

    @Autowired
    UserService userService;

    @PostMapping(value = "/login")
    public BookResponse login(@RequestBody SysUser sysUser) throws Exception {
        String userid = sysUser.getUserid().trim();
        String password = sysUser.getPassword().trim();
        return userService.login(userid, password);
    }

    @PostMapping(value = "/logout")
    public BookResponse logout(@RequestHeader HttpHeaders httpHeaders) throws Exception {
        String token = httpHeaders.getFirst("Authorization");
        return userService.logout(token);
    }

}
