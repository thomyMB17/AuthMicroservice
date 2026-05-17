package bg.inventory.auth.service;

import bg.inventory.auth.client.UserClient;
import bg.inventory.auth.dto.response.AuthResponse;
import bg.inventory.auth.dto.request.LoginRequest;
import bg.inventory.auth.dto.request.RegisterRequest;
import bg.inventory.auth.dto.response.UserClientResponse;
import bg.inventory.auth.exception.InvalidCredentialsException;
import bg.inventory.auth.jwt.JwtService;
import bg.inventory.auth.model.TokenAuth;
import bg.inventory.auth.repository.ITokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserClient userClient;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final ITokenRepository tokenRepository;

    @Transactional
    public AuthResponse login(LoginRequest request) {
        // 1 — busca el usuario por email en ms-usuarios
        UserClientResponse user = userClient.getUserByEmail(request.getEmail());

        // 2 — compara la password con BCrypt
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new InvalidCredentialsException();
        }

        // 3 — genera el token con id y rol como claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRolUsuario());
        claims.put("email", user.getEmail());
        String token = jwtService.getToken(claims, user.getEmail());

        // 4 — elimina tokens anteriores del mismo usuario
        tokenRepository.deleteByIdUsuario(user.getId());

        // 5 — guarda el nuevo token en BD
        tokenRepository.save(TokenAuth.builder()
                .token(token)
                .tipoToken("Bearer")
                .fechaExp(LocalDateTime.now().plusDays(1))
                .idUsuario(user.getId())
                .build());

        return AuthResponse.builder().token(token).build();
    }

    public AuthResponse register(RegisterRequest request) {
        // 1 — crea el usuario en ms-usuarios
        userClient.createUser(request);

        // 2 — obtiene el usuario recién creado
        UserClientResponse user = userClient.getUserByEmail(request.getEmail());

        // 3 — genera el token
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRolUsuario());
        claims.put("email", user.getEmail());
        String token = jwtService.getToken(claims, user.getEmail());

        // 4 — guarda el token en BD
        tokenRepository.save(TokenAuth.builder()
                .token(token)
                .tipoToken("Bearer")
                .fechaExp(LocalDateTime.now().plusDays(1))
                .idUsuario(user.getId())
                .build());

        return AuthResponse.builder().token(token).build();
    }
}
