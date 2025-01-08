package br.com.trabalho.filaprojetos.controller;

import br.com.trabalho.filaprojetos.dto.LoginRequestDTO;
import br.com.trabalho.filaprojetos.dto.RegisterUserDTO;
import br.com.trabalho.filaprojetos.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO dados){
        return authService.login(dados);
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterUserDTO dados){
        return authService.register(dados);
    }
}
