package com.nicoleozkan.pantrypal.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "user")
public class User implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<UserProduct> userProducts = new ArrayList<>();

    @Column(length = 20)
    @Size(max = 20, message = "First name must be less than 20 characters.")
    private String firstName;

    @Column(length = 20)
    @Size(max = 20, message = "Last name must be less than 20 characters.")
    private String lastName;

    @Column
    @Size(max = 50, message = "Address 1 must be less than 50 characters.")
    private String address1;

    @Column
    @Size(max = 50, message = "Address 2 must be less than 50 characters.")
    private String address2;

    @Column
    @Size(max = 50, message = "City must be less than 50 characters.")
    private String city;

    @Column
    @Size(min = 2, max = 2, message = "State must have 2 characters.")
    private String state;

    @Column
    @Pattern(regexp = "^(?:[0-9]{5}|)$", message = "Zipcode must contain exactly 5 numeric values.")
    private String zipcode;

    @Column
    @Pattern(regexp = "^(?:[0-9]{4}|)$", message = "Zip extension must contain exactly 4 numeric values.")
    private String zipExtension;

    @Column(nullable = false, unique = true, length = 45)
    @Size(max = 45, message = "Email must be less than 45 characters.")
    private String email;

    @Column
    @Pattern(regexp = "^(?:[0-9]{10}|)$", message = "Phone number must be a valid phone number.")
    private String phone;

    @Column(nullable = false, length = 64)
    @Size(max = 64, message = "Password must be less than 65 characters.")
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) && Objects.equals(userProducts, user.userProducts) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(address1, user.address1) && Objects.equals(address2, user.address2) && Objects.equals(city, user.city) && Objects.equals(state, user.state) && Objects.equals(zipcode, user.zipcode) && Objects.equals(zipExtension, user.zipExtension) && Objects.equals(email, user.email) && Objects.equals(phone, user.phone) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userProducts, firstName, lastName, address1, address2, city, state, zipcode, zipExtension, email, phone, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userProducts=" + userProducts +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", zipExtension='" + zipExtension + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}