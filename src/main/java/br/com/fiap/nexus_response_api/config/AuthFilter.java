package br.com.fiap.nexus_response_api.config;


import br.com.fiap.nexus_response_api.model.Usuario;
//import br.com.fiap.nexus_response_api.TokenService;
import br.com.fiap.nexus_response_api.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("FILTER");

        // Verifica se o header de autenticação existe
        var header = request.getHeader("Authorization");
        if (header == null) {
            System.out.println("Sem header Authorization");
            filterChain.doFilter(request, response);
            return;
        }

        // Verifica se o token começa com "Bearer"
        if (!header.startsWith("Bearer ")) {
            System.out.println("Token não começa com Bearer");
            response.setStatus(401);
            response.getWriter().write("""
                {"message": "Token deve começar com Bearer" }
            """);
            return;
        }

        // Extrai o token e valida
        var token = header.replace("Bearer ", "");
        try {
            Usuario user = tokenService.getUserFromToken(token);

            // Autentica o usuário no contexto de segurança
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            System.out.println("Token inválido: " + e.getMessage());
            response.setStatus(401);
            response.getWriter().write("""
                {"message": "Token inválido" }
            """);
        }
    }
}
