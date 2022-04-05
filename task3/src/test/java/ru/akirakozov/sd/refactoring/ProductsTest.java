package ru.akirakozov.sd.refactoring;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static ru.akirakozov.sd.refactoring.TestUtil.*;
import static ru.akirakozov.sd.refactoring.util.HtmlUtils.wrapHtml;

public class ProductsTest extends BaseTest {

    @Test
    public void addProductOkTest() throws URISyntaxException, IOException {
        var expected = "OK";
        var actual = sendAddProductReq("iphone", 100);
        assertEqualsIgnoreWhitespace(expected, actual);
    }

    @Test
    public void getEmptyProductsTest() throws URISyntaxException, IOException {
        var expected = wrapHtml("");
        var actual = sendGetProductReq();
        assertEqualsIgnoreWhitespace(expected, actual);
    }

    @Test
    public void addAndGetProductsTest() throws URISyntaxException, IOException {
        sendAddProductReq("iphone", 100);
        sendAddProductReq("ipad", 200);
        sendAddProductReq("imac", 300);
        var expected = wrapHtml(
                new StringBuilder()
                        .append("iphone 100</br>")
                        .append("ipad 200</br>")
                        .append("imac 300</br>")
                        .toString()
        );
        var actual = sendGetProductReq();
        assertEqualsIgnoreWhitespace(expected, actual);
    }

}
