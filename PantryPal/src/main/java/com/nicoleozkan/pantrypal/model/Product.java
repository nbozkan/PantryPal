package com.nicoleozkan.pantrypal.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@Table(name = "product")
public class Product implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @Column
    private String productNotes;

    @Column
    private String amountUsed;

    @Column
    private String totalAmount;

    @Column
    private String unit;
}