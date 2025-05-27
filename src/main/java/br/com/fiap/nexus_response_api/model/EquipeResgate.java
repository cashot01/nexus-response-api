package br.com.fiap.nexus_response_api.model;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EquipeResgate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "nome da equipe obrigatorio")
    private String nome;

    @NotBlank(message = "nome da equipe obrigatorio")
    private String contato;

}
