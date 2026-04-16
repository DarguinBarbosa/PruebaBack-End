package com.darguin.prueba_back_end.application.usecase.product;

import com.darguin.prueba_back_end.application.dto.request.UpdateStockRequest;
import com.darguin.prueba_back_end.application.exception.NotFoundException;
import com.darguin.prueba_back_end.domain.model.Product;
import com.darguin.prueba_back_end.domain.port.out.ProductRepositoryPort;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UpdateProductStockUseCaseTest {

    private final ProductRepositoryPort repository = mock(ProductRepositoryPort.class);
    private final UpdateProductStockUseCase useCase = new UpdateProductStockUseCase(repository);

    @Test
    void actualiza_stock_cuando_producto_existe() {
        when(repository.findById(1L)).thenReturn(Mono.just(new Product(1L, "P", 10, 5L)));
        when(repository.updateStock(1L, 200)).thenReturn(Mono.just(new Product(1L, "P", 200, 5L)));

        StepVerifier.create(useCase.execute(1L, new UpdateStockRequest(200)))
                .expectNextMatches(r -> r.getStock() == 200)
                .verifyComplete();
    }

    @Test
    void falla_cuando_producto_no_existe() {
        when(repository.findById(99L)).thenReturn(Mono.empty());

        StepVerifier.create(useCase.execute(99L, new UpdateStockRequest(10)))
                .expectError(NotFoundException.class)
                .verify();
    }
}
