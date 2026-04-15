package com.darguin.prueba_back_end.application.usecase.product;

import com.darguin.prueba_back_end.application.dto.response.TopStockProductResponse;
import com.darguin.prueba_back_end.application.exception.NotFoundException;
import com.darguin.prueba_back_end.domain.port.out.FranchiseRepositoryPort;
import com.darguin.prueba_back_end.domain.port.out.ProductRepositoryPort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GetTopStockProductsUseCase {

    private final FranchiseRepositoryPort franchiseRepositoryPort;
    private final ProductRepositoryPort productRepositoryPort;

    public GetTopStockProductsUseCase(FranchiseRepositoryPort franchiseRepositoryPort,
                                      ProductRepositoryPort productRepositoryPort) {
        this.franchiseRepositoryPort = franchiseRepositoryPort;
        this.productRepositoryPort = productRepositoryPort;
    }

    public Flux<TopStockProductResponse> execute(Long franchiseId) {
        return franchiseRepositoryPort.findById(franchiseId)
                .switchIfEmpty(Mono.error(new NotFoundException("Franquicia no encontrada: " + franchiseId)))
                .thenMany(productRepositoryPort.findTopStockProductsByFranchiseId(franchiseId));
    }
}
