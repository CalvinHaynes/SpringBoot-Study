package top.calvinhaynes.demo01hellospringboot3.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloSpringBoot3Controller {

    @GetMapping("/hello")
    public String helloSpringBoot3() {
        return "Hello Spring Boot 3!";
    }
}
