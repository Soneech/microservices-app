package org.soneech.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 3, max = 100)
    private String username;

    @NotEmpty
    @Size(min = 11, max = 12)
    @Column(name = "phone_number")
    private String phoneNumber;

    @Email
    @NotEmpty
    @Size(min = 2, max = 100)
    private String email;

    @NotEmpty
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    @JsonIgnore
    private Role role;
}
