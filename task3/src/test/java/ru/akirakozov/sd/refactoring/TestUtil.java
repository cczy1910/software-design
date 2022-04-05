package ru.akirakozov.sd.refactoring;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class TestUtil {
    public static String sendGetRequest(String method, Map<String, Object> params)
            throws URISyntaxException, IOException {


        try (var httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet("http://localhost:" + BaseTest.appConfig.getServerPort() + "/" + method);
            var uriBuilder = new URIBuilder(httpGet.getURI());

            params.forEach((k, v) -> uriBuilder.addParameter(k, v.toString()));
            var uri = uriBuilder.build();
            httpGet.setURI(uri);

            try (var response = httpClient.execute(httpGet)) {
                return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            }
        }
    }

    public static void assertEqualsIgnoreWhitespace(String expected, String actual) {
        String s1 = expected.replaceAll("\\s+", "");
        String s2 = actual.replaceAll("\\s+", "");
        Assert.assertEquals(s1, s2);
    }

    public static String sendGetProductReq() throws URISyntaxException, IOException {
        return sendGetRequest("get-products", Map.of());
    }

    public static String sendAddProductReq(String name, long price) throws URISyntaxException, IOException {
        return sendGetRequest("add-product", Map.of(
                "name", name,
                "price", price
        ));
    }
}
