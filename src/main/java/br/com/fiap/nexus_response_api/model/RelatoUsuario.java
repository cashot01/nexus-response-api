package br.com.fiap.nexus_response_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_NEXUS_RESPONSE_RELATO_USUARIO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelatoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_relato_usuario")
    private Long id;

    @NotNull(message = "A data/hora do relato não pode ser nula")
    @PastOrPresent(message = "A data/hora do relato deve estar no passado ou presente")
    @Column(name = "dt_relato", nullable = false)
    private LocalDateTime dataHoraRelato;

    @NotBlank(message = "A mensagem do relato não pode estar em branco")
    @Column(name = "ds_mensagem", nullable = false, length = 2000) // Mensagem mais longa
    private String mensagem;
}
