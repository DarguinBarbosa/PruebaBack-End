package com.darguin.prueba_back_end.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CreateFranchiseRequest {

    @NotBlank(message = "El nombre de la franquicia no puede estar vacío")
    private String name;

    public CreateFranchiseRequest() {}

    public CreateFranchiseRequest(String name) { this.name = name; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
