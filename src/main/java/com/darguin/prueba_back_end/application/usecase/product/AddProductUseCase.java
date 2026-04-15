package com.darguin.prueba_back_end.application.usecase.product;

import com.darguin.prueba_back_end.application.dto.request.AddProductRequest;
import com.darguin.prueba_back_end.application.dto.response.ProductResponse;
import com.darguin.prueba_back_end.application.exception.NotFoundException;
import com.darguin.prueba_back_end.domain.model.Product;
import com.darguin.prueba_back_end.domain.port.out.BranchRepositoryPort;
import com.darguin.prueba_back_end.domain.port.out.ProductRepositoryPort;
import reactor.core.publisher.Mono;

public class AddProductUseCase {

    private final BranchRepositoryPort branchRepositoryPort;
    private final ProductRepositoryPort productRepositoryPort;

    public AddProductUseCase(BranchRepositoryPort branchRepositoryPort,
                             ProductRepositoryPort productRepositoryPort) {
        this.branchRepositoryPort = branchRepositoryPort;
        this.productRepositoryPort = productRepositoryPort;
    }

    public Mono<ProductResponse> execute(Long branchId, AddProductRequest request) {
        return branchRepositoryPort.findById(branchId)
                .switchIfEmpty(Mono.error(new NotFoundException("Sucursal no encontrada: " + branchId)))
                .flatMap(b -> {
                    Product product = new Product(null, request.getName(), request.getStock(), branchId);
                    return productRepositoryPort.save(product);
                })
                .map(p -> new ProductResponse(p.getId(), p.getName(), p.getStock(), p.getBranchId()));
    }
}
