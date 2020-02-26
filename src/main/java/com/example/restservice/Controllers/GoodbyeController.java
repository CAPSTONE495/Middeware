package com.example.restservice.Controllers;
import java.util.concurrent.atomic.AtomicLong;

import com.example.restservice.Representation_Classes.GreetingJson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodbyeController {
    private static final String template = "goodbye, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping(value="/gone")
    public GreetingJson greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new GreetingJson(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping(value="/")
    public void greeting() {
        throw new RuntimeException("test");
    }
}
