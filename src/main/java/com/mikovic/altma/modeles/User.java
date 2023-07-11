package com.mikovic.altma.modeles;

import com.mikovic.altma.enums.AuthProvider;
import com.mikovic.altma.enums.StatusOfUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.*;



@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "users",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"nick_name", "email"})}
)
public class User implements Serializable {

    private static final long serialVersionUID = 695787153791934595L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "nick_name", nullable = false)
    private String nickName;

    @Column(name = "password")
    private String password;

    @Email
    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "email_verified")
    private Boolean emailVerified = false;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusOfUser status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles = new HashSet<>();

    @Column(name = "image_url")
    private String imageUrl;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Property> properties;


    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;
    public void addRole(Role role) {
        getRoles().add(role);
    }

    /**
     * @return - information about user by nick
     */
    public Map<String, String> getUserInfo() {
        Map<String, String> info = new HashMap<>();
        info.put("first_name", firstName);
        info.put("last_name", lastName);
        info.put("nick_name", nickName);
        info.put("email", email);
        /*info.put("role", StatusOfUser.valueOf("name").toString());*/
        return info;
    }


}

