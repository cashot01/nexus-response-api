package br.com.fiap.nexus_response_api.service;

import br.com.fiap.nexus_response_api.model.Credentials;
import br.com.fiap.nexus_response_api.model.Token;
import br.com.fiap.nexus_response_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username).orElseThrow(() ->
                new ResourceNotFoundException("Usuário não encontrado com email: " + username));
    }

    public Token login(Credentials credentials) {
        var user = repository.findByEmail(credentials.email())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (!passwordEncoder.matches(credentials.password(), user.getPassword())) {
            throw new RuntimeException("Senha incorreta");
        }

        return tokenService.createToken(user);
    }
}
