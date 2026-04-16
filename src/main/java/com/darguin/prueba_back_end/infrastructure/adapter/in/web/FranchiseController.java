package com.darguin.prueba_back_end.infrastructure.adapter.in.web;

import com.darguin.prueba_back_end.application.dto.request.AddBranchRequest;
import com.darguin.prueba_back_end.application.dto.request.CreateFranchiseRequest;
import com.darguin.prueba_back_end.application.dto.request.UpdateNameRequest;
import com.darguin.prueba_back_end.application.dto.response.BranchResponse;
import com.darguin.prueba_back_end.application.dto.response.FranchiseResponse;
import com.darguin.prueba_back_end.application.dto.response.TopStockProductResponse;
import com.darguin.prueba_back_end.application.usecase.branch.AddBranchUseCase;
import com.darguin.prueba_back_end.application.usecase.franchise.CreateFranchiseUseCase;
import com.darguin.prueba_back_end.application.usecase.franchise.UpdateFranchiseNameUseCase;
import com.darguin.prueba_back_end.application.usecase.product.GetTopStockProductsUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/franchises")
public class FranchiseController {

    private final CreateFranchiseUseCase createFranchiseUseCase;
    private final UpdateFranchiseNameUseCase updateFranchiseNameUseCase;
    private final AddBranchUseCase addBranchUseCase;
    private final GetTopStockProductsUseCase getTopStockProductsUseCase;

    public FranchiseController(CreateFranchiseUseCase createFranchiseUseCase,
                               UpdateFranchiseNameUseCase updateFranchiseNameUseCase,
                               AddBranchUseCase addBranchUseCase,
                               GetTopStockProductsUseCase getTopStockProductsUseCase) {
        this.createFranchiseUseCase = createFranchiseUseCase;
        this.updateFranchiseNameUseCase = updateFranchiseNameUseCase;
        this.addBranchUseCase = addBranchUseCase;
        this.getTopStockProductsUseCase = getTopStockProductsUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FranchiseResponse> createFranchise(@Validated @RequestBody CreateFranchiseRequest request) {
        return createFranchiseUseCase.execute(request);
    }

    @PutMapping("/{id}/name")
    public Mono<FranchiseResponse> updateFranchiseName(@PathVariable Long id,
                                                        @Validated @RequestBody UpdateNameRequest request) {
        return updateFranchiseNameUseCase.execute(id, request);
    }

    @PostMapping("/{franchiseId}/branches")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BranchResponse> addBranch(@PathVariable Long franchiseId,
                                          @Validated @RequestBody AddBranchRequest request) {
        return addBranchUseCase.execute(franchiseId, request);
    }

    @GetMapping("/{franchiseId}/top-stock-products")
    public Flux<TopStockProductResponse> getTopStockProducts(@PathVariable Long franchiseId) {
        return getTopStockProductsUseCase.execute(franchiseId);
    }
}
