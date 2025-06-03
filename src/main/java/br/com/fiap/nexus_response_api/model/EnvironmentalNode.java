package br.com.fiap.nexus_response_api.model;

import br.com.fiap.nexus_response_api.model.enuns.TipoNivelUrgencia;
import br.com.fiap.nexus_response_api.model.enuns.TipoStatusAgua;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "TB_NEXUS_ENVIRONMENTAL_NODE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvironmentalNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_enviromental_node")
    private Long idEnvironmentalNode;

    @NotBlank(message = "temperatura media obrigatoria")
    @Column(name = "temp_media", nullable = false, precision = 5, scale = 2)
    private BigDecimal tempMedia;

    @NotBlank(message = "temperatura dispositivo obrigatorio")
    @Column(name = "temp_dispositivo", nullable = false, precision = 5, scale = 2)
    private BigDecimal tempDispositivo;

    @NotBlank(message = "umidade obrigatoria")
    @Column(name = "umidade", nullable = false, precision = 5, scale = 2)
    private BigDecimal umidade;

    @NotBlank(message = "nivel da agua obrigatoria")
    @Column(name = "nivel_agua", nullable = false, precision = 5, scale = 2)
    private BigDecimal nivelAgua;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_nivel_urgencia", nullable = false)
    private TipoNivelUrgencia nivelUrgencia;

    @ManyToOne
    @JoinColumn(name = "id_status_agua", nullable = false)
    private TipoStatusAgua statusAgua;

    @ManyToOne
    @JoinColumn(name = "id_location", nullable = false)
    private LocationTracker locationTracker;
}
