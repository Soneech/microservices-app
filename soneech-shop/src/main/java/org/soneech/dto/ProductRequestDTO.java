package org.soneech.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductRequestDTO {
    @NotEmpty(message = "название не может быть пустым")
    @Size(min = 2, max = 100, message = "название должно содержать от 2 до 100 символов")
    private String name;

    @Min(value = 1, message = "цена не может быть меньше 1 рубля")
    private double price;

    private String description;

    @JsonProperty("company_id")
    @Min(value = 0, message = "некорректный id")
    private Long companyId;
}
