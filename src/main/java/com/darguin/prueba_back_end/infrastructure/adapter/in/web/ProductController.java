package com.darguin.prueba_back_end.infrastructure.adapter.in.web;

import com.darguin.prueba_back_end.application.dto.request.UpdateNameRequest;
import com.darguin.prueba_back_end.application.dto.request.UpdateStockRequest;
import com.darguin.prueba_back_end.application.dto.response.ProductResponse;
import com.darguin.prueba_back_end.application.usecase.product.RemoveProductUseCase;
import com.darguin.prueba_back_end.application.usecase.product.UpdateProductNameUseCase;
import com.darguin.prueba_back_end.application.usecase.product.UpdateProductStockUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final RemoveProductUseCase removeProductUseCase;
    private final UpdateProductStockUseCase updateProductStockUseCase;
    private final UpdateProductNameUseCase updateProductNameUseCase;

    public ProductController(RemoveProductUseCase removeProductUseCase,
                             UpdateProductStockUseCase updateProductStockUseCase,
                             UpdateProductNameUseCase updateProductNameUseCase) {
        this.removeProductUseCase = removeProductUseCase;
        this.updateProductStockUseCase = updateProductStockUseCase;
        this.updateProductNameUseCase = updateProductNameUseCase;
    }

    @DeleteMapping("/branches/{branchId}/products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProduct(@PathVariable Long branchId, @PathVariable Long productId) {
        return removeProductUseCase.execute(branchId, productId);
    }

    @PatchMapping("/products/{productId}/stock")
    public Mono<ProductResponse> updateStock(@PathVariable Long productId,
                                             @Validated @RequestBody UpdateStockRequest request) {
        return updateProductStockUseCase.execute(productId, request);
    }

    @PutMapping("/products/{id}/name")
    public Mono<ProductResponse> updateProductName(@PathVariable Long id,
                                                    @Validated @RequestBody UpdateNameRequest request) {
        return updateProductNameUseCase.execute(id, request);
    }
}
