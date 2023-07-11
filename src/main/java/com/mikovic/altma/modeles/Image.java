package com.mikovic.altma.modeles;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "imagies")
@Data
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "title")
    private String title;

    @Column(name = "photo")
    private byte[] photo;

    @ManyToOne
    @NotNull(message = "")
    @JoinColumn(name = "property_id")
    private Property property;
}
