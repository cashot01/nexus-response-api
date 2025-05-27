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
@Table(name = "TB_NEXUS_RESPONSE_CENARIO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cenario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cenario")
    private Long id;

    @NotBlank(message = "A localização não pode estar em branco")
    @Column(name = "ds_localizacao", nullable = false)
    private String localizacao; // Pode ser coordenadas, endereço, etc.

    @Column(name = "ds_cenario", length = 1000) // Descrição mais longa
    private String descricao;

    @NotNull(message = "A data/hora do reporte não pode ser nula")
    @PastOrPresent(message = "A data/hora do reporte deve estar no passado ou presente")
    @Column(name = "dt_reporte", nullable = false)
    private LocalDateTime dataHoraReporte;

    @NotNull(message = "O status não pode ser nulo")
    @Enumerated(EnumType.STRING) // Grava o nome do enum (ex: "REPORTADO")
    @Column(name = "st_cenario", nullable = false)
    private StatusCenario status = StatusCenario.REPORTADO; // Status padrão inicial

    @Enumerated(EnumType.STRING)
    @Column(name = "cd_nivel_urgencia") // Pode ser nulo inicialmente
    private NivelUrgencia nivelUrgencia;

    @Column(name = "nm_equipe_designada") // Nome da equipe que atenderá (se houver)
    private String equipeDesignada;

    // Relacionamento Um-para-Muitos com LeituraSensor
    @OneToMany(mappedBy = "cenario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LeituraSensor> leiturasSensor = new ArrayList<>();

    // Relacionamento Um-para-Muitos com RelatoUsuario
    @OneToMany(mappedBy = "cenario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Relato> relatosUsuario = new ArrayList<>();

}
