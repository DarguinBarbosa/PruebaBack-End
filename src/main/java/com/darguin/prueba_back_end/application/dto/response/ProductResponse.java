package com.darguin.prueba_back_end.application.dto.response;

public class ProductResponse {

    private Long id;
    private String name;
    private int stock;
    private Long branchId;

    public ProductResponse() {}

    public ProductResponse(Long id, String name, int stock, Long branchId) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.branchId = branchId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public Long getBranchId() { return branchId; }
    public void setBranchId(Long branchId) { this.branchId = branchId; }
}
