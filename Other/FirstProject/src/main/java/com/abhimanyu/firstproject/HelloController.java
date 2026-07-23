package com.abhimanyu.firstproject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String Hello() {
        return "Hello peepss!!!!";
    }

    @PostMapping("/hello")
    public String postHello(@RequestBody String name) {
        return "Hello"+ name + "peepss!!!!";
    }
}
