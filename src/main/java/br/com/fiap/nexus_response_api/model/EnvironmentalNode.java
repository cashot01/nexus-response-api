package br.com.fiap.nexus_response_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "TB_NEXUS_ENVIRONMENTAL_NODE")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnvironmentalNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_enviromental_node")
    private Long idEnvironmentalNode;

    @NotNull(message = "temperatura media obrigatoria")
    @Column(name = "temp_media", nullable = false)
    private Number tempMedia;

    @NotNull(message = "temperatura dispositivo obrigatorio")
    @Column(name = "temp_dispositivo", nullable = false)
    private Double tempDispositivo;

    @NotNull(message = "umidade obrigatoria")
    @Column(name = "umidade", nullable = false)
    private Double umidade;

    @NotNull(message = "nivel da agua obrigatoria")
    @Column(name = "nivel_agua", nullable = false)
    private Double nivelAgua;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_nivel_urgencia", nullable = false)
    private NivelUrgencia nivelUrgencia;

    @ManyToOne
    @JoinColumn(name = "id_status_agua", nullable = false)
    private StatusAgua statusAgua;

    @ManyToOne
    @JoinColumn(name = "id_location", nullable = false)
    private LocationTracker locationTracker;
}
