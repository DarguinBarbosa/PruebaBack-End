package com.darguin.prueba_back_end.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class AddProductRequest {

    @NotBlank(message = "El nombre del producto no puede estar vacío")
    private String name;

    @Min(value = 0, message = "El stock no puede ser negativo")
    private int stock;

    public AddProductRequest() {}

    public AddProductRequest(String name, int stock) {
        this.name = name;
        this.stock = stock;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
