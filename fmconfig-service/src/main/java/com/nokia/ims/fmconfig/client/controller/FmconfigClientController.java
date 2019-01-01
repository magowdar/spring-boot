package com.nokia.ims.fmconfig.client.controller;


import com.nokia.ims.fmconfig.client.bean.Fmconfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FmconfigClientController {

    @Autowired
    private Fmconfig fmconfig;

    @GetMapping("/fmconfig")
    public Fmconfig retrieveFmConfigurations(){
        return fmconfig;
    }
}
