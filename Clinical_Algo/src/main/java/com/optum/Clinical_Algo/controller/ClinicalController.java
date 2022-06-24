package com.optum.Clinical_Algo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClinicalController {

    @GetMapping("/test")
    public ResponseEntity test(){
        System.out.println("hello test");
       return new ResponseEntity<String>(HttpStatus.OK);
    }
}
