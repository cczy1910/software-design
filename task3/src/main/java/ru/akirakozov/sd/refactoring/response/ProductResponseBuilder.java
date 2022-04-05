package ru.akirakozov.sd.refactoring.response;

import ru.akirakozov.sd.refactoring.domain.Product;

import java.util.List;

public interface ProductResponseBuilder<T> {
    T buildProductsListResponse(List<Product> products);

    T buildAddProductResponse();

    T buildProductWithMinPriceResponse(Product product);

    T buildProductWithMaxPriceResponse(Product product);

    T buildTotalPriceResponse(long price);

    T buildTotalCountResponse(int count);

    T buildUnknownCommandResponse(String command);
}
