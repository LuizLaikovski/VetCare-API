package com.vetcare.petmeds.modules.images.entity;

import com.vetcare.petmeds.modules.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    @OneToOne(mappedBy = "image")
    @ToString.Exclude
    private UserEntity user;


    public ImageEntity(String nome, String tipoConteudo, byte[] dados) {
        this.nome = nome;
        this.tipoConteudo = tipoConteudo;
        this.dados = dados;
    }
}
