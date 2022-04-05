package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.domain.Product;
import ru.akirakozov.sd.refactoring.response.ProductHtmlResponseBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {

    private final ProductDao productDao;
    private final ProductHtmlResponseBuilder htmlResponseBuilder;

    public GetProductsServlet(
            ProductDao productDao,
            ProductHtmlResponseBuilder htmlResponseBuilder
    ) {
        this.productDao = productDao;
        this.htmlResponseBuilder = htmlResponseBuilder;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<Product> products = productDao.getAll();
            htmlResponseBuilder.buildProductsListResponse(products);
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
