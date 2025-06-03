package br.com.fiap.nexus_response_api.service;

import br.com.fiap.nexus_response_api.model.Token;
import br.com.fiap.nexus_response_api.model.Usuario;
import br.com.fiap.nexus_response_api.model.UsuarioRole;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private final Long DURATION = 120L; // 120 minutos
    private Algorithm ALG;

    @Value("${jwt.secret}")
    private String secret;

    @PostConstruct
    public void init() {
        ALG = Algorithm.HMAC256(secret);
    }

    public Token createToken(Usuario usuario) {
        var token = JWT.create()
                .withSubject(usuario.getId().toString())
                .withClaim("email", usuario.getEmail())
                .withClaim("papel", usuario.getPapel().toString())
                .withExpiresAt(LocalDateTime.now().plusMinutes(DURATION).toInstant(ZoneOffset.ofHours(-3)))
                .sign(ALG);
        return new Token(token, 21315656L, "Bearer", usuario.getPapel().toString());
    }

    public Usuario getUserFromToken(String token) {
        var verifiedToken = JWT.require(ALG)
                .build()
                .verify(token);

        return Usuario.builder()
                .id(Long.parseLong(verifiedToken.getSubject()))
                .email(verifiedToken.getClaim("email").asString()) // Corrigido para usar asString()
                .papel(UsuarioRole.valueOf(verifiedToken.getClaim("papel").asString())) // Corrigido para usar asString()
                .build();
    }
}
