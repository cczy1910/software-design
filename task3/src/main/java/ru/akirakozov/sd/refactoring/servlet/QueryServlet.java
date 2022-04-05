package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.ProductDao;
import ru.akirakozov.sd.refactoring.domain.Product;
import ru.akirakozov.sd.refactoring.response.ProductHtmlResponseBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {

    private final ProductDao productDao;
    private final ProductHtmlResponseBuilder htmlResponseBuilder;

    public QueryServlet(
            ProductDao productDao,
            ProductHtmlResponseBuilder htmlResponseBuilder) {
        this.productDao = productDao;
        this.htmlResponseBuilder = htmlResponseBuilder;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        Product product;
        switch (command) {
            case "max":
                product = productDao.getProductWithMaxPrice();
                response.getWriter().println(htmlResponseBuilder.buildProductWithMaxPriceResponse(product));
                break;
            case "min": {
                product = productDao.getProductWithMinPrice();
                response.getWriter().println(htmlResponseBuilder.buildProductWithMinPriceResponse(product));
                break;
            }
            case "sum":
                long sumPrice = productDao.getSumPrice();
                response.getWriter().println(htmlResponseBuilder.buildTotalPriceResponse(sumPrice));
                break;
            case "count":
                int count = productDao.getProductsCount();
                response.getWriter().println(htmlResponseBuilder.buildTotalCountResponse(count));
                break;
            default:
                response.getWriter().println(htmlResponseBuilder.buildUnknownCommandResponse(command));
                break;
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
