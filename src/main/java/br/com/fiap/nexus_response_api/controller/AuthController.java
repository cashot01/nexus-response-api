package br.com.fiap.nexus_response_api.controller;

import br.com.fiap.nexus_response_api.model.Credentials;
import br.com.fiap.nexus_response_api.model.Token;
import br.com.fiap.nexus_response_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Token login(@RequestBody @Valid Credentials credentials) {
        log.info("Logando como: " + credentials);
        return authService.login(credentials);
    }
}
