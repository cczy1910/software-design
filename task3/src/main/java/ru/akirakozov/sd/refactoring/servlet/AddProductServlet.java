package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.domain.Product;
import ru.akirakozov.sd.refactoring.response.ProductHtmlResponseBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class AddProductServlet extends HttpServlet {
    private final ProductDao productDao;
    private final ProductHtmlResponseBuilder htmlResponseBuilder;

    public AddProductServlet(
            ProductDao productDao,
            ProductHtmlResponseBuilder htmlResponseBuilder
    ) {
        this.productDao = productDao;
        this.htmlResponseBuilder = htmlResponseBuilder;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));
        productDao.addProduct(new Product(name, price));

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(htmlResponseBuilder.buildAddProductResponse());
    }

}
