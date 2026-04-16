package com.darguin.prueba_back_end.infrastructure.config;

import com.darguin.prueba_back_end.application.usecase.branch.AddBranchUseCase;
import com.darguin.prueba_back_end.application.usecase.branch.UpdateBranchNameUseCase;
import com.darguin.prueba_back_end.application.usecase.franchise.CreateFranchiseUseCase;
import com.darguin.prueba_back_end.application.usecase.franchise.UpdateFranchiseNameUseCase;
import com.darguin.prueba_back_end.application.usecase.product.AddProductUseCase;
import com.darguin.prueba_back_end.application.usecase.product.GetTopStockProductsUseCase;
import com.darguin.prueba_back_end.application.usecase.product.RemoveProductUseCase;
import com.darguin.prueba_back_end.application.usecase.product.UpdateProductNameUseCase;
import com.darguin.prueba_back_end.application.usecase.product.UpdateProductStockUseCase;
import com.darguin.prueba_back_end.domain.port.out.BranchRepositoryPort;
import com.darguin.prueba_back_end.domain.port.out.FranchiseRepositoryPort;
import com.darguin.prueba_back_end.domain.port.out.ProductRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public CreateFranchiseUseCase createFranchiseUseCase(FranchiseRepositoryPort franchiseRepositoryPort) {
        return new CreateFranchiseUseCase(franchiseRepositoryPort);
    }

    @Bean
    public UpdateFranchiseNameUseCase updateFranchiseNameUseCase(FranchiseRepositoryPort franchiseRepositoryPort) {
        return new UpdateFranchiseNameUseCase(franchiseRepositoryPort);
    }

    @Bean
    public AddBranchUseCase addBranchUseCase(FranchiseRepositoryPort franchiseRepositoryPort,
                                              BranchRepositoryPort branchRepositoryPort) {
        return new AddBranchUseCase(franchiseRepositoryPort, branchRepositoryPort);
    }

    @Bean
    public UpdateBranchNameUseCase updateBranchNameUseCase(BranchRepositoryPort branchRepositoryPort) {
        return new UpdateBranchNameUseCase(branchRepositoryPort);
    }

    @Bean
    public AddProductUseCase addProductUseCase(BranchRepositoryPort branchRepositoryPort,
                                               ProductRepositoryPort productRepositoryPort) {
        return new AddProductUseCase(branchRepositoryPort, productRepositoryPort);
    }

    @Bean
    public RemoveProductUseCase removeProductUseCase(ProductRepositoryPort productRepositoryPort) {
        return new RemoveProductUseCase(productRepositoryPort);
    }

    @Bean
    public UpdateProductStockUseCase updateProductStockUseCase(ProductRepositoryPort productRepositoryPort) {
        return new UpdateProductStockUseCase(productRepositoryPort);
    }

    @Bean
    public UpdateProductNameUseCase updateProductNameUseCase(ProductRepositoryPort productRepositoryPort) {
        return new UpdateProductNameUseCase(productRepositoryPort);
    }

    @Bean
    public GetTopStockProductsUseCase getTopStockProductsUseCase(FranchiseRepositoryPort franchiseRepositoryPort,
                                                                  ProductRepositoryPort productRepositoryPort) {
        return new GetTopStockProductsUseCase(franchiseRepositoryPort, productRepositoryPort);
    }
}
