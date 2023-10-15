package org.soneech.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "company")
@NoArgsConstructor
@Getter
@Setter
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String name;

    @Email
    @NotEmpty
    @Size(min = 2, max = 100)
    private String email;

    @Column(name = "phone_number")
    @NotEmpty
    @Size(min = 11, max = 12)
    private String phoneNumber;

    private String description;

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    private List<Product> products;
}
