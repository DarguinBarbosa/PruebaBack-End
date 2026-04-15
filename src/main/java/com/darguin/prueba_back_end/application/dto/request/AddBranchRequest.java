package com.darguin.prueba_back_end.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public class AddBranchRequest {

    @NotBlank(message = "El nombre de la sucursal no puede estar vacío")
    private String name;

    public AddBranchRequest() {}

    public AddBranchRequest(String name) { this.name = name; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
