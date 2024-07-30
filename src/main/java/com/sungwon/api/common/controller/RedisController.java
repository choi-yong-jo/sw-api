package com.sungwon.api.common.controller;

import com.sungwon.api.common.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    @Autowired
    private RedisService redisService;

    @PostMapping(value = "/getRedisStringValue")
    public void getRedisStringValue(String key) {
        redisService.getRedisStringValue(key);
    }

    @PostMapping(value = "/setRedisStringValue")
    public void setRedisServiceValue(String key, String value) {
        redisService.setRedisStringValue(key, value);
    }

}
