package org.soneech.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDTO {
    @NotEmpty
    @Size(min = 3, max = 100, message = "имя пользователя должно содержать от 3 до 100 символов")
    private String username;

    @NotEmpty
    @Size(min = 11, max = 12, message = "номер телефона должен содержать от 11 до 12 символов")
    @Column(name = "phone_number")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @Email
    @NotEmpty
    @Size(min = 2, max = 100, message = "почта должна содержать от 2 до 100 символов")
    private String email;

    @NotEmpty
    private String password;
}
