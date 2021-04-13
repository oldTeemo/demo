package com.taojingwei.demo.controller;

import com.taojingwei.demo.pojo.User;
import com.taojingwei.demo.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private IUserService userService;

	@RequestMapping("/test22")
    public String test(){
        return "hello world";
    }

    @RequestMapping("/addUser/{id}/{name}")
    public String addUser(@PathVariable("id") String id,@PathVariable("name") String name){
        User user = new User();
        user.setId(id);
        user.setName(name);
        userService.addUser(user);
        return "success";
    }

}
