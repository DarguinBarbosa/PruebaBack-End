package com.darguin.prueba_back_end.application.usecase.product;

import com.darguin.prueba_back_end.application.dto.request.UpdateStockRequest;
import com.darguin.prueba_back_end.application.dto.response.ProductResponse;
import com.darguin.prueba_back_end.application.exception.NotFoundException;
import com.darguin.prueba_back_end.domain.port.out.ProductRepositoryPort;
import reactor.core.publisher.Mono;

public class UpdateProductStockUseCase {

    private final ProductRepositoryPort productRepositoryPort;

    public UpdateProductStockUseCase(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    public Mono<ProductResponse> execute(Long productId, UpdateStockRequest request) {
        return productRepositoryPort.findById(productId)
                .switchIfEmpty(Mono.error(new NotFoundException("Producto no encontrado: " + productId)))
                .flatMap(p -> productRepositoryPort.updateStock(productId, request.getStock()))
                .map(p -> new ProductResponse(p.getId(), p.getName(), p.getStock(), p.getBranchId()));
    }
}
