package br.com.fiap.nexus_response_api.model;

import br.com.fiap.nexus_response_api.model.enuns.NivelUrgencia;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    /*@NotNull(message = "A data/hora do reporte não pode ser nula")
    @PastOrPresent(message = "A data/hora do reporte deve estar no passado ou presente")
    @Column(name = "dt_reporte", nullable = false)
    private LocalDateTime data; */
    @NotNull(message = "Data obrigatoria")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "A data de cadastro não pode ser futura")
    private LocalDate dataReporte;

    @NotNull(message = "nivel de urgencia obrigatorio")
    private NivelUrgencia nivelUrgencia;

    /*@Column(name = "nm_equipe_designada") // Nome da equipe que atenderá (se houver)
    private String equipeDesignada; */

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TB_NEXUS_RESPONSE_EQUIPE_RESGATE", referencedColumnName = "id")
    private EquipeResgate equipeResgate;


    // Relacionamento Um-para-Muitos com LeituraSensor
    /*@OneToMany(mappedBy = "cenario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LeituraSensor> leiturasSensor = new ArrayList<>();*/

    // Relacionamento Um-para-Muitos com RelatoUsuario
    @OneToMany(mappedBy = "cenario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RelatoUsuario> relatosUsuario = new ArrayList<>();

}
