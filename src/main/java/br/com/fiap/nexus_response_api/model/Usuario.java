package br.com.fiap.nexus_response_api.model;

import br.com.fiap.nexus_response_api.model.enuns.UsuarioRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "TB_NEXUS_RESPONSE_USUARIO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "email inválido")
    @NotBlank(message = "email obrigatorio")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "senha obrigatoria")
    @Size(min = 5)
    private String senha;

    @NotNull(message = "campo obrigatório")
    @Enumerated(EnumType.STRING)
    private UsuarioRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    public String getUsername() {
        return email;
    }

}
