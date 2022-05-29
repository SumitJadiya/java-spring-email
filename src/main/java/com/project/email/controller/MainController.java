package com.project.email.controller;

import com.project.email.services.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    private MainService service;

    @RequestMapping(value = "/sendEmail")
    public String sendEmail() {
        try {
            service.sendEmail();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "email sent successfully";
    }
}
