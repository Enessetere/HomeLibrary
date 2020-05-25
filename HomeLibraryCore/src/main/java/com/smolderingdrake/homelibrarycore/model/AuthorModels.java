package com.smolderingdrake.homelibrarycore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorModels {

    private List<AuthorModel> authors;

}
