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
import org.springframework.web.server.ResponseStatusException;

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
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<LoginResponseDTO> login(LoginRequestDTO loginRequestDTO) {
        User user;

        try{
            user = (User) userRepository.findByEmailUser(loginRequestDTO.email());
        }catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email não encontrado");
        }

        if(passwordEncoder.matches(loginRequestDTO.password(), user.getPassword())){
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new LoginResponseDTO(token));
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<?> register(@Valid RegisterUserDTO registerUserDTO) {

        if(userRepository.findByEmail(registerUserDTO.email()) != null) return ResponseEntity.badRequest().body("Email já está em uso");

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerUserDTO.password());
        User newUser = new User(registerUserDTO.nome(),registerUserDTO.email(), encryptedPassword, Role.USER);
        String token = this.tokenService.generateToken(newUser);
        userRepository.save(newUser);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
    }
