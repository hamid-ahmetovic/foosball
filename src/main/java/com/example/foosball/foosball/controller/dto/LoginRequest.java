package com.example.foosball.foosball.controller.dto;

import com.example.foosball.foosball.util.Court;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {
    @NotNull
    @NotBlank
    private String playerId;

    @NotNull
    @NotBlank
    private String foosballTableId;

    @NotNull
    @NotBlank
    private Court court;
}
