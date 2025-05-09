// package com.rentalsbackend.services;

// import com.rentalsbackend.dto.LoginRequest;
// import com.rentalsbackend.dto.LoginResponse;
// import com.rentalsbackend.dto.RegisterRequest;
// import com.rentalsbackend.dto.RegisterResponse;
// import com.rentalsbackend.entity.User;
// import com.rentalsbackend.repository.UserRepository;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.oauth2.jwt.JwtEncoder;
// import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
// import org.springframework.security.oauth2.jwt.JwsHeader;
// import org.springframework.security.oauth2.jwt.JwtClaimsSet;
// import org.springframework.stereotype.Service;

// import java.time.Instant;
// import java.time.temporal.ChronoUnit;
// import java.util.Optional;

// @Service
// public class UserService {

//     private final UserRepository userRepository;
//     private final BCryptPasswordEncoder passwordEncoder;
//     private final JwtEncoder jwtEncoder;

//     public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtEncoder jwtEncoder) {
//         this.userRepository = userRepository;
//         this.passwordEncoder = passwordEncoder;
//         this.jwtEncoder = jwtEncoder;
//     }

//     public RegisterResponse registerUser(RegisterRequest registerRequest) {
//         if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
//             return new RegisterResponse(null);
//         }

//         User newUser = new User();
//         newUser.setName(registerRequest.getName());
//         newUser.setEmail(registerRequest.getEmail());
//         newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

//         userRepository.save(newUser);

//         String token = generateToken(newUser.getEmail());
//         return new RegisterResponse(token);
//     }

//     public LoginResponse loginUser(LoginRequest loginRequest) {
//         Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());

//         if (userOpt.isPresent()) {
//             User user = userOpt.get();

//             if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
//                 String token = generateToken(user.getEmail());
//                 return new LoginResponse(token);
//             }
//         }

//         return new LoginResponse(null);
//     }

//     private String generateToken(String subject) {
//         Instant now = Instant.now();
//         JwtClaimsSet claims = JwtClaimsSet.builder()
//                 .issuer("self")
//                 .issuedAt(now)
//                 .expiresAt(now.plus(1, ChronoUnit.DAYS))
//                 .subject(subject)
//                 .build();

//         return jwtEncoder.encode(JwtEncoderParameters.from(JwsHeader.with(org.springframework.security.oauth2.jose.jws.MacAlgorithm.HS256).build(), claims)).getTokenValue();
//     }
// }

package com.rentalsbackend.services;

import com.rentalsbackend.dto.LoginRequest;
import com.rentalsbackend.dto.LoginResponse;
import com.rentalsbackend.dto.RegisterRequest;
import com.rentalsbackend.dto.RegisterResponse;
import com.rentalsbackend.entity.User;
import com.rentalsbackend.errors.exceptions.BadRequestException;
import com.rentalsbackend.errors.exceptions.UnauthorizedException;
import com.rentalsbackend.errors.exceptions.UserAlreadyExistsException;
import com.rentalsbackend.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtEncoder jwtEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    public RegisterResponse registerUser(RegisterRequest request) {
        if (request.getName() == null || request.getEmail() == null || request.getPassword() == null) {
            throw new BadRequestException("Tous les champs sont obligatoires");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Un utilisateur avec cet email existe déjà");
        }

        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(newUser);

        String token = generateToken(newUser.getEmail());
        return new RegisterResponse(token);
    }

    public LoginResponse loginUser(LoginRequest request) {
        if (request.getEmail() == null || request.getPassword() == null) {
            throw new BadRequestException("Email et mot de passe sont obligatoires");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Email ou mot de passe invalide"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Email ou mot de passe invalide");
        }

        String token = generateToken(user.getEmail());
        return new LoginResponse(token);
    }

    private String generateToken(String subject) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(subject)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(
                JwsHeader.with(org.springframework.security.oauth2.jose.jws.MacAlgorithm.HS256).build(),
                claims)).getTokenValue();
    }
}



