package com.langrsoft.controller;

import com.langrsoft.service.library.PatronService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patrons")
public class PatronController {
    private PatronService service = new PatronService();

    @PostMapping
    public String add(@RequestBody PatronRequest patronRequest) {
        return service.add(patronRequest.getName());
    }

    @GetMapping
    public List<PatronRequest> retrieveAll() {
        return service.allPatrons().stream()
                .map(PatronRequest::new)
                .toList();
    }

    @GetMapping(value = "{patronId}")
    public PatronRequest retrieve(@PathVariable("patronId") String patronId) {
        return new PatronRequest(service.find(patronId));
    }
}
