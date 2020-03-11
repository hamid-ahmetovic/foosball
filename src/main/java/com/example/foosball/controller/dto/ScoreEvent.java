package com.example.foosball.controller.dto;

import com.example.foosball.util.Court;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ScoreEvent {
    @NotNull
    private Court court;

    @NotNull
    @NotBlank
    private String foosballTableId;
}
