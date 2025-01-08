package br.com.trabalho.filaprojetos.service;

import br.com.trabalho.filaprojetos.dto.LoginRequestDTO;
import br.com.trabalho.filaprojetos.dto.LoginResponseDTO;
import br.com.trabalho.filaprojetos.dto.RegisterUserDTO;
import br.com.trabalho.filaprojetos.model.Role;
import br.com.trabalho.filaprojetos.model.User;
import br.com.trabalho.filaprojetos.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Objects;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    @Autowired
    AuthenticationManager authenticationManager;

    public ResponseEntity<LoginResponseDTO> login(LoginRequestDTO loginRequestDTO) {
        UsernamePasswordAuthenticationToken usernamePassword =  new UsernamePasswordAuthenticationToken(loginRequestDTO.email(), loginRequestDTO.password());
        Authentication auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    public ResponseEntity<?> register(@Valid RegisterUserDTO registerUserDTO) {

        if(userRepository.findByEmail(registerUserDTO.email()) != null) return ResponseEntity.badRequest().body("Email já está em uso");

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerUserDTO.password());
        User newUser = new User(registerUserDTO.nome(),registerUserDTO.email(), encryptedPassword, Role.USER);

        userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
    }
