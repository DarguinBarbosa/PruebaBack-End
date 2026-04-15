package com.darguin.prueba_back_end.application.dto.request;

import jakarta.validation.constraints.Min;

public class UpdateStockRequest {

    @Min(value = 0, message = "El stock no puede ser negativo")
    private int stock;

    public UpdateStockRequest() {}

    public UpdateStockRequest(int stock) { this.stock = stock; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
