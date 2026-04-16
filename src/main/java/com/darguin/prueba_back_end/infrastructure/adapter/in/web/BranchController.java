package com.darguin.prueba_back_end.infrastructure.adapter.in.web;

import com.darguin.prueba_back_end.application.dto.request.AddProductRequest;
import com.darguin.prueba_back_end.application.dto.request.UpdateNameRequest;
import com.darguin.prueba_back_end.application.dto.response.BranchResponse;
import com.darguin.prueba_back_end.application.dto.response.ProductResponse;
import com.darguin.prueba_back_end.application.usecase.branch.UpdateBranchNameUseCase;
import com.darguin.prueba_back_end.application.usecase.product.AddProductUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/branches")
public class BranchController {

    private final UpdateBranchNameUseCase updateBranchNameUseCase;
    private final AddProductUseCase addProductUseCase;

    public BranchController(UpdateBranchNameUseCase updateBranchNameUseCase,
                            AddProductUseCase addProductUseCase) {
        this.updateBranchNameUseCase = updateBranchNameUseCase;
        this.addProductUseCase = addProductUseCase;
    }

    @PutMapping("/{id}/name")
    public Mono<BranchResponse> updateBranchName(@PathVariable Long id,
                                                  @Validated @RequestBody UpdateNameRequest request) {
        return updateBranchNameUseCase.execute(id, request);
    }

    @PostMapping("/{branchId}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductResponse> addProduct(@PathVariable Long branchId,
                                            @Validated @RequestBody AddProductRequest request) {
        return addProductUseCase.execute(branchId, request);
    }
}
