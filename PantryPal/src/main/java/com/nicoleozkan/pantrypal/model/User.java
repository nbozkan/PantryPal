package com.nicoleozkan.pantrypal.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "user")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @ManyToMany(targetEntity = Product.class, fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_product", joinColumns = {@JoinColumn(name = "userId", referencedColumnName = "userId")}, inverseJoinColumns = {@JoinColumn(name = "productId", referencedColumnName = "productId")})
    private List<Product> products;

    @Column(length = 20)
    private String firstName;

    @Column(length = 20)
    private String lastName;

    @Column
    private String address1;

    @Column
    private String address2;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private String zipCode;

    @Column
    private String zipExtension;

    @Column(nullable = false, unique = true, length = 45)
    private String emailAddress;

    @Column
    private String phoneNumber;

    @Column(nullable = false, length = 64)
    private String password;
}