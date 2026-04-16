package com.darguin.prueba_back_end.application.usecase.product;

import com.darguin.prueba_back_end.application.dto.response.TopStockProductResponse;
import com.darguin.prueba_back_end.application.exception.NotFoundException;
import com.darguin.prueba_back_end.domain.model.Franchise;
import com.darguin.prueba_back_end.domain.port.out.FranchiseRepositoryPort;
import com.darguin.prueba_back_end.domain.port.out.ProductRepositoryPort;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetTopStockProductsUseCaseTest {

    private final FranchiseRepositoryPort franchiseRepo = mock(FranchiseRepositoryPort.class);
    private final ProductRepositoryPort productRepo = mock(ProductRepositoryPort.class);
    private final GetTopStockProductsUseCase useCase = new GetTopStockProductsUseCase(franchiseRepo, productRepo);

    @Test
    void devuelve_top_stock_por_sucursal() {
        TopStockProductResponse a = new TopStockProductResponse(1L, "A", 80, 10L, "Centro");
        TopStockProductResponse b = new TopStockProductResponse(2L, "B", 120, 20L, "Norte");

        when(franchiseRepo.findById(1L)).thenReturn(Mono.just(new Franchise(1L, "F")));
        when(productRepo.findTopStockProductsByFranchiseId(1L)).thenReturn(Flux.just(a, b));

        StepVerifier.create(useCase.execute(1L))
                .expectNext(a, b)
                .verifyComplete();
    }

    @Test
    void falla_cuando_franquicia_no_existe() {
        when(franchiseRepo.findById(99L)).thenReturn(Mono.empty());
        when(productRepo.findTopStockProductsByFranchiseId(99L)).thenReturn(Flux.empty());

        StepVerifier.create(useCase.execute(99L))
                .expectError(NotFoundException.class)
                .verify();
    }
}
