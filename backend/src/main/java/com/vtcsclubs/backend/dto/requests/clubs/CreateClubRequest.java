package com.vtcsclubs.backend.dto.requests.clubs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateClubRequest(
        @NotNull
        Long clubId,
        @NotBlank(message = "Name cannot be blank.")
        @Size(min = 5, max = 75, message = "Club's name must be between 5 and 75 characters.")
        String name,
        @NotBlank(message = "Club's info cannot be blank.")
        @Size(min = 5, max = 500, message = "Club's info must be between 50 and 500 characters.")
        @NotNull
        String info,
        @NotBlank(message = "Contact email cannot be blank.")
        @NotNull
        String contactEmail) {
}
