package br.com.fiap.nexus_response_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_NEXUS_NIVEL_URGENCIA")
public class NivelUrgencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nivel_urgencia")
    private Long idNivelUrgencia;

    @Column(name = "descricao_nivel", nullable = false, length = 50)
    private TipoNivelUrgencia descricaoNivel;

    @OneToMany(mappedBy = "nivelUrgencia", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EnvironmentalNode> environmentalNodes;
}
