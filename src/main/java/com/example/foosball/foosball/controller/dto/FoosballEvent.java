package com.example.foosball.foosball.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class FoosballEvent {
    @NotNull
    @NotBlank
    private String team;

}
