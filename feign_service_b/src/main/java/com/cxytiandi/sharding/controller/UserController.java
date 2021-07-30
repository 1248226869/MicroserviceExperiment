package com.cxytiandi.sharding.controller;

import com.cxytiandi.sharding.po.User;
import com.cxytiandi.sharding.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/users")
    public Object list() {
        return userService.list();
    }

    @GetMapping("/add")
    public Object add(String name, String city, Integer flag) {
        User user=new User();
        user.setCity(city);
        user.setName(name);
        user.setFlag(flag);
        userService.add(user);
        return "success";
    }

    @GetMapping("/users/{id}")
    public Object get(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("/users/query")
    public Object get(String name) {
        return userService.findByName(name);
    }

}
