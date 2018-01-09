package com.starter.springboot.services;

import org.springframework.stereotype.Service;

@Service
public class ExampleService {

    public Boolean isUsernameInUse(String username) {
        return false;
    }

}
