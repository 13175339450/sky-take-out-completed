package com.hxl.controller.admin;

import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/admin/employee")
public class EmployeeController {

    @RequestMapping("test")
    public String test(){
        return "hello";
    }
}
