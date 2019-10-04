package com.example.foosball.foosball.controller.dto;

import com.example.foosball.foosball.util.Court;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class FoosballEvent {
    @NotNull
    private Court court;

    @NotNull
    @NotBlank
    private String foosballTableId;
}
