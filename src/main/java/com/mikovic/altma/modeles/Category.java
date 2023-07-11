package com.mikovic.altma.modeles;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
@Entity
@Table(name = "properties")
@Data
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private short id;
    @Column(name = "title")
    @Size(min = 5, max = 250, message = "требуется минимум 5 символов")
    private String title;


    @OneToMany(cascade=CascadeType.ALL)
    private List<Property> properties;


}
