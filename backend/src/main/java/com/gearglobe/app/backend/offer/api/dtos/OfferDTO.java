package com.gearglobe.app.backend.offer.api.dtos;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class OfferDTO {
    private Long id;

    @NotBlank(message = "Mark is mandatory")
    private String mark;

    @NotNull(message = "Production year is mandatory")
    @Min(value = 1900, message = "Production year must be greater than 1900")
    private Long productionYear;

    @NotNull(message = "Millage is mandatory")
    @Min(value = 0, message = "Millage must be greater or equal than 0")
    private Long millage;

    @NotNull(message = "Engine capacity is mandatory")
    @Min(value = 0, message = "Engine capacity must be greater or equal than 0")
    private Double engineCapacity;

    private String description;

    @NotNull(message = "Price is mandatory")
    @Min(value = 0, message = "Price must be greater or equal than 0")
    private Double price;

    private LocalDateTime createDate;

    @NotNull(message = "Status is mandatory")
    private OfferStatus status;
}