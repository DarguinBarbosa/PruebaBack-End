package com.darguin.prueba_back_end.application.dto.response;

public class TopStockProductResponse {

    private Long productId;
    private String productName;
    private int stock;
    private Long branchId;
    private String branchName;

    public TopStockProductResponse() {}

    public TopStockProductResponse(Long productId, String productName, int stock,
                                   Long branchId, String branchName) {
        this.productId = productId;
        this.productName = productName;
        this.stock = stock;
        this.branchId = branchId;
        this.branchName = branchName;
    }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public Long getBranchId() { return branchId; }
    public void setBranchId(Long branchId) { this.branchId = branchId; }

    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }
}
