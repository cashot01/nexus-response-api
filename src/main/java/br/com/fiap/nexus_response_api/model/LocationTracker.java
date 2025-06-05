package br.com.fiap.nexus_response_api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "TB_NEXUS_LOCATION_TRACKER")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_location")
    private Long idLocation;

    @NotBlank(message = "latidute obrigatoria")
    @Column(name = "latitude", nullable = false, precision = 9, scale = 6)
    private Double latitude;

    @NotBlank(message = "longitude obrigatoria")
    @Column(name = "longitude", nullable = false, precision = 9, scale = 6)
    private Double longitude;

    @Column(name = "data", nullable = false)
    @NotNull(message = "Data obrigatoria")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "A data de cadastro não pode ser futura")
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "locationTracker", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EnvironmentalNode> environmentalNodes;
}
