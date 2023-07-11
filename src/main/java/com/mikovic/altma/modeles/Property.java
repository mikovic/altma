package com.mikovic.altma.modeles;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "properties")
@Data
@NoArgsConstructor
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @NotNull(message = "")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @NotNull(message = "")
    @JoinColumn(name = "property_id")
    private Property property;

    @OneToOne
    @NotNull(message = "")
    @JoinColumn(name = "adress_id")
    private Adress adress;

    @Column(name = "area")
    private double area;

    @Column(name = "price")
    private double price;

    @Column(name = "create_at")
    @CreationTimestamp
    private LocalDateTime createAt;

    @Column(name = "update_at")
    @UpdateTimestamp
    private LocalDateTime updateAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "property")
    private List<Image> images;


}
