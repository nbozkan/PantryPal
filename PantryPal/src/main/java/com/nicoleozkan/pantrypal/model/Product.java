package com.nicoleozkan.pantrypal.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "product")
public class Product implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    @Size(max = 50, message = "Product name must be less than 50 characters.")
    private String productName;

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE)
    private List<UserProduct> userProducts = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productId, product.productId) && Objects.equals(productName, product.productName) && Objects.equals(userProducts, product.userProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, userProducts);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", userProducts=" + userProducts +
                '}';
    }
}