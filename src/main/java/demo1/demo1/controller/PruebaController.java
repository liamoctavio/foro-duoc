package demo1.demo1.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PruebaController {

    @GetMapping("/api/prueba/ping")
    public String ping() {
        return "pong desde prueba";
    }
}

