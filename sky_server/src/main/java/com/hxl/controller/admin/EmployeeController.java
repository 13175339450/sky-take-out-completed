package com.hxl.controller.admin;

import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/admin/employee")
public class EmployeeController {

    @GetMapping("login")
    public String login(){
        return "login";
    }
}
