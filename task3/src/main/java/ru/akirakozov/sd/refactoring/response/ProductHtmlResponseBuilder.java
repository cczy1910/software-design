package ru.akirakozov.sd.refactoring.response;

import ru.akirakozov.sd.refactoring.domain.Product;

import java.util.List;
import java.util.stream.Collectors;

import static ru.akirakozov.sd.refactoring.util.HtmlUtils.wrapHtml;

public class ProductHtmlResponseBuilder implements ProductResponseBuilder<String> {

    @Override
    public String buildProductsListResponse(List<Product> products) {
        return wrapHtml(
                products.stream()
                        .map(p -> p.getName() + "\t" + p.getPrice() + "</br>")
                        .collect(Collectors.joining("\n"))
        );
    }

    @Override
    public String buildAddProductResponse() {
        return "OK";
    }

    @Override
    public String buildProductWithMinPriceResponse(Product product) {
        return wrapHtml(
                new StringBuilder()
                        .append("<h1>Product with min price: </h1>")
                        .append(product.getName() + "\t" + product.getPrice() + "</br>")
                        .toString()
        );
    }

    @Override
    public String buildProductWithMaxPriceResponse(Product product) {
        return wrapHtml(
                new StringBuilder()
                        .append("<h1>Product with max price: </h1>")
                        .append(product.getName() + "\t" + product.getPrice() + "</br>")
                        .toString()
        );
    }

    @Override
    public String buildTotalPriceResponse(long price) {
        return wrapHtml(
                new StringBuilder()
                        .append("Summary price: ")
                        .append(price)
                        .toString()
        );
    }

    @Override
    public String buildTotalCountResponse(int count) {
        return wrapHtml(
                new StringBuilder()
                        .append("Number of products: ")
                        .append(count)
                        .toString()
        );
    }

    @Override
    public String buildUnknownCommandResponse(String command) {
        return wrapHtml("Unknown command: " + command);
    }

}
