package io.github.dev_emanuelpereira.resourceserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceServerController {

    @GetMapping("public")
    public ResponseEntity<String> publicEndpoint() {
        return ResponseEntity.ok("PUBLIC ENDPOINT");
    }
}
