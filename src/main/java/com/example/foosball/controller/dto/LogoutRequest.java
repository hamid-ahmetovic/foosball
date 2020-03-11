package com.example.foosball.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LogoutRequest {
    @NotNull
    @NotBlank
    private String playerId;

    @NotNull
    @NotBlank
    private String foosballTableId;
}
