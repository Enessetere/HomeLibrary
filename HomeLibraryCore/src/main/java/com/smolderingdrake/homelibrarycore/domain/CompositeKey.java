package com.smolderingdrake.homelibrarycore.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompositeKey implements Serializable {
    private String firstName;
    private String lastName;
}
