package top.calvinhaynes.demo06log.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
public class HelloSpringBoot3Controller {

    @GetMapping("/hello")
    public String hello(){
        log.debug("This is a debug message");
        log.info("This is an info message");
        log.warn("This is a warn message");
        log.error("This is an error message");
        return "Hello Spring Boot 3!";
    }
}
