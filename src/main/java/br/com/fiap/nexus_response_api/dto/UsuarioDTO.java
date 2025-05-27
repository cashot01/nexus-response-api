package br.com.fiap.nexus_response_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    @JsonProperty("id")
    private Long id;

    @NotBlank(message = "email obrigatório")
    @Email(message = "email inválido")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "senha obrigatória")
    @Size(min = 5, message = "A senha deve ter no mínimo 5 caracteres")
    @JsonProperty("senha")
    private String senha;

    @NotNull(message = "campo obrigatório")
    @JsonProperty("role")
    private String role;



}
