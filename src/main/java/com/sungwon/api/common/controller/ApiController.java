package com.sungwon.api.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/test/")
public class ApiController {

    @GetMapping("/process/{processNum}")
    public String process(@PathVariable int processNum) throws InterruptedException {
        log.info("========================== Start Process -> Process Number: {}", processNum);
        Thread.sleep(20000);
        log.info("========================== End Process -> Process Number: {}", processNum);

        return "Process Success !!";
    }

}
