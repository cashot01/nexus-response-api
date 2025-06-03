package br.com.fiap.nexus_response_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_NEXUS_STATUS_AGUA")
public class StatusAgua {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_status_agua")
    private Long idStatusAgua;

    @NotBlank(message = "descrição agua obrigatorio")
    @Column(name = "descricao_agua", nullable = false, length = 20)
    private TipoStatusAgua descricaoAgua;

    @OneToMany(mappedBy = "statusAgua", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EnvironmentalNode> environmentalNodes;
}
