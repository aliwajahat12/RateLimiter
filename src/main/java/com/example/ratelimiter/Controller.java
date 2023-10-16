package com.example.ratelimiter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/limited")
    @ResponseBody
    public String LimitedRequests() {
        return "Limited, don't over use me!";
    }

    @GetMapping("/unlimited")
    @ResponseBody
    public String UnlimitedRequests() {
        return "Unlimited! Let's Go!";
    }
}
