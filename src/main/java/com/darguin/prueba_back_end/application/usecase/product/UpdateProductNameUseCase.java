package com.darguin.prueba_back_end.application.usecase.product;

import com.darguin.prueba_back_end.application.dto.request.UpdateNameRequest;
import com.darguin.prueba_back_end.application.dto.response.ProductResponse;
import com.darguin.prueba_back_end.application.exception.NotFoundException;
import com.darguin.prueba_back_end.domain.port.out.ProductRepositoryPort;
import reactor.core.publisher.Mono;

public class UpdateProductNameUseCase {

    private final ProductRepositoryPort productRepositoryPort;

    public UpdateProductNameUseCase(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    public Mono<ProductResponse> execute(Long id, UpdateNameRequest request) {
        return productRepositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Producto no encontrado: " + id)))
                .flatMap(p -> productRepositoryPort.updateName(id, request.getName()))
                .map(p -> new ProductResponse(p.getId(), p.getName(), p.getStock(), p.getBranchId()));
    }
}
