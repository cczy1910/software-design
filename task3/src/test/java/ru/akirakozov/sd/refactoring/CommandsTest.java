package ru.akirakozov.sd.refactoring;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import static ru.akirakozov.sd.refactoring.TestUtil.*;
import static ru.akirakozov.sd.refactoring.util.HtmlUtils.wrapHtml;

public class CommandsTest extends BaseTest {

    @Test
    public void sumCommandTest() throws URISyntaxException, IOException {
        addSampleProducts();
        var expected = wrapHtml(
                new StringBuilder()
                        .append("Summary price:")
                        .append("600")
                        .toString()
        );
        var actual = sendQueryReq("sum");
        assertEqualsIgnoreWhitespace(expected, actual);
    }

    @Test
    public void countCommandTest() throws URISyntaxException, IOException {
        addSampleProducts();
        var expected = wrapHtml(
                new StringBuilder()
                        .append("Number of products:")
                        .append("3")
                        .toString()
        );
        var actual = sendQueryReq("count");
        assertEqualsIgnoreWhitespace(expected, actual);
    }

    @Test
    public void maxCommandTest() throws URISyntaxException, IOException {
        addSampleProducts();
        var expected = wrapHtml(
                new StringBuilder()
                        .append("<h1>Product with max price:</h1>")
                        .append("imac 300</br>")
                        .toString()
        );
        var actual = sendQueryReq("max");
        assertEqualsIgnoreWhitespace(expected, actual);
    }

    @Test
    public void minCommandTest() throws URISyntaxException, IOException {
        addSampleProducts();
        var expected = wrapHtml(
                new StringBuilder()
                        .append("<h1>Product with min price:</h1>")
                        .append("iphone 100</br>")
                        .toString()
        );
        var actual = sendQueryReq("min");
        assertEqualsIgnoreWhitespace(expected, actual);
    }


    private void addSampleProducts() throws URISyntaxException, IOException {
        sendAddProductReq("iphone", 100);
        sendAddProductReq("ipad", 200);
        sendAddProductReq("imac", 300);
    }

    private String sendQueryReq(String command) throws URISyntaxException, IOException {
        return sendGetRequest("query", Map.of("command", command));
    }
}
