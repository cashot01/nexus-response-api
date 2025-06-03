package br.com.fiap.nexus_response_api.model;

import br.com.fiap.nexus_response_api.model.enuns.UsuarioRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "TB_NEXUS_USUARIO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "nome obrigatorio")
    @Size(max = 100)
    private String nome;

    @NotBlank(message = "CPF obrigatorio")
    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    @Size(max = 11)
    private Number cpf;

    @Email(message = "email inválido")
    @NotBlank(message = "email obrigatorio")
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @NotBlank(message = "senha obrigatoria")
    @Size(max = 20)
    @Column(name = "senha", nullable = false, length = 20)
    private String senha;

    @NotNull(message = "campo obrigatório")
    @Enumerated(EnumType.STRING)
    private UsuarioRole role;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EnvironmentalNode> environmentalNodes;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    public String getUsername() {
        return email;
    }

}
