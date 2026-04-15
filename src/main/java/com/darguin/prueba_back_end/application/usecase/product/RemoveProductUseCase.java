package com.darguin.prueba_back_end.application.usecase.product;

import com.darguin.prueba_back_end.application.exception.NotFoundException;
import com.darguin.prueba_back_end.domain.port.out.ProductRepositoryPort;
import reactor.core.publisher.Mono;

public class RemoveProductUseCase {

    private final ProductRepositoryPort productRepositoryPort;

    public RemoveProductUseCase(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    public Mono<Void> execute(Long branchId, Long productId) {
        return productRepositoryPort.findById(productId)
                .switchIfEmpty(Mono.error(new NotFoundException("Producto no encontrado: " + productId)))
                .flatMap(p -> {
                    if (!p.getBranchId().equals(branchId)) {
                        return Mono.error(new NotFoundException(
                                "Producto " + productId + " no pertenece a la sucursal " + branchId));
                    }
                    return productRepositoryPort.deleteById(productId);
                });
    }
}
