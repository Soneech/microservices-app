package org.soneech.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {
    private Long id;
    private String username;

    @JsonProperty("phone_number")
    private String phoneNumber;
    private String email;
    private String role;
}
