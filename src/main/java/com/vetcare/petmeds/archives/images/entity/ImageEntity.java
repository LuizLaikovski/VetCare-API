package com.vetcare.petmeds.archives.images.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_image")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ima_id")
    private Long id;

    @Column(name = "ima_nome", nullable = false)
    private String nome;

    @Column(name = "ima_tipo_conteudo", nullable = false)
    private String tipoConteudo;

    @Column(name = "ima_dados", nullable = false, columnDefinition = "BYTEA")
    private byte[] dados;

    public ImageEntity(String nome, String tipoConteudo, byte[] dados) {
        this.nome = nome;
        this.tipoConteudo = tipoConteudo;
        this.dados = dados;
    }
}
