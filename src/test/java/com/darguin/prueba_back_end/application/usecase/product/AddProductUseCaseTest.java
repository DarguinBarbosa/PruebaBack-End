package com.darguin.prueba_back_end.application.usecase.product;

import com.darguin.prueba_back_end.application.dto.request.AddProductRequest;
import com.darguin.prueba_back_end.application.exception.NotFoundException;
import com.darguin.prueba_back_end.domain.model.Branch;
import com.darguin.prueba_back_end.domain.model.Product;
import com.darguin.prueba_back_end.domain.port.out.BranchRepositoryPort;
import com.darguin.prueba_back_end.domain.port.out.ProductRepositoryPort;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class AddProductUseCaseTest {

    private final BranchRepositoryPort branchRepo = mock(BranchRepositoryPort.class);
    private final ProductRepositoryPort productRepo = mock(ProductRepositoryPort.class);
    private final AddProductUseCase useCase = new AddProductUseCase(branchRepo, productRepo);

    @Test
    void agrega_producto_cuando_sucursal_existe() {
        when(branchRepo.findById(5L)).thenReturn(Mono.just(new Branch(5L, "Centro", 1L)));
        when(productRepo.save(any(Product.class)))
                .thenReturn(Mono.just(new Product(99L, "Pan", 50, 5L)));

        StepVerifier.create(useCase.execute(5L, new AddProductRequest("Pan", 50)))
                .expectNextMatches(r -> r.getId().equals(99L)
                        && r.getName().equals("Pan")
                        && r.getStock() == 50
                        && r.getBranchId().equals(5L))
                .verifyComplete();
    }

    @Test
    void falla_cuando_sucursal_no_existe() {
        when(branchRepo.findById(99L)).thenReturn(Mono.empty());

        StepVerifier.create(useCase.execute(99L, new AddProductRequest("X", 1)))
                .expectError(NotFoundException.class)
                .verify();

        verifyNoInteractions(productRepo);
    }
}
