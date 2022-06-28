package com.nicoleozkan.pantrypal.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "user_product")
public class UserProduct implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userProductId;

    @JoinColumn(name = "productId")
    private Long productId;

    @JoinColumn(name = "userId")
    private Long userId;

    @Column
    @Size(max = 250, message = "Product notes must be less than 250 characters.")
    private String productNotes;

    @Column
    private String totalAmount;

    @Column
    @Size(max = 20, message = "Units must be less than 20 characters.")
    private String unit;

    @ManyToOne
    @JoinColumn(name = "productId", insertable = false, updatable = false)
    @Valid
    private Product product;

    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    @Valid
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProduct that = (UserProduct) o;
        return Objects.equals(userProductId, that.userProductId) && Objects.equals(productId, that.productId) && Objects.equals(userId, that.userId) && Objects.equals(productNotes, that.productNotes) && Objects.equals(totalAmount, that.totalAmount) && Objects.equals(unit, that.unit) && Objects.equals(product, that.product) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userProductId, productId, userId, productNotes, totalAmount, unit, product, user);
    }

    @Override
    public String toString() {
        return "UserProduct{" +
                "userProductId=" + userProductId +
                ", productId=" + productId +
                ", userId=" + userId +
                ", productNotes='" + productNotes + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", unit='" + unit + '\'' +
                ", product=" + product +
                ", user=" + user +
                '}';
    }
}