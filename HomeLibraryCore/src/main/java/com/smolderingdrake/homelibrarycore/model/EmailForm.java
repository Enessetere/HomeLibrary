package com.smolderingdrake.homelibrarycore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailForm {

    @NotNull
    private String from;

    @NotNull
    private String subject;

    @NotNull
    private String message;
}
