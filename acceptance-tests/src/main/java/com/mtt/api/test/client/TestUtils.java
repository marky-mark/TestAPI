package com.mtt.api.test.client;

import org.apache.shiro.codec.Hex;
import org.springframework.util.Assert;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;

/**
 */
public class TestUtils {

    public static String createRequestPathWithSecurityParameters(
            String baseUri, String accessKey, String timestamp, String signature) throws Exception {
        StringBuilder queryStringBuilder = new StringBuilder();

        if (accessKey != null) {
            queryStringBuilder.append("apiKey=").append(URLEncoder.encode(accessKey, "UTF-8"));
        }

        if (timestamp != null) {
            if (queryStringBuilder.length() > 0) {
                queryStringBuilder.append("&");
            }
            queryStringBuilder.append("timestamp=").append(URLEncoder.encode(timestamp, "UTF-8"));
        }

        if (signature != null) {
            if (queryStringBuilder.length() > 0) {
                queryStringBuilder.append("&");
            }
            queryStringBuilder.append("signature=").append(URLEncoder.encode(signature, "UTF-8"));
        }

        StringBuilder requestPathBuilder = new StringBuilder(baseUri);

        if (queryStringBuilder.length() > 0) {
            requestPathBuilder.append("?").append(queryStringBuilder.toString());
        }

        return requestPathBuilder.toString();
    }

    public static String generateAuthenticationSignature(
            String url,
            String accessKey,
            String privateKey,
            String timestamp) throws Exception {
        Assert.notNull(accessKey, "Access key must be set for auto signature generation");
        Assert.notNull(privateKey, "Private key must be set for auto signature generation");
        Assert.notNull(timestamp, "Timestamp must be set for auto signature generation");
        StringBuilder urlToSign = new StringBuilder(url.toLowerCase());
        urlToSign.append("?apikey=").append(accessKey.toLowerCase());
        urlToSign.append("&").append("timestamp=").append(timestamp.toLowerCase());
        SecretKey key = new SecretKeySpec(privateKey.getBytes(), "hmacSHA256");
        Mac m = Mac.getInstance("hmacSHA256");
        m.init(key);
        m.update(urlToSign.toString().getBytes());
        return new String(Hex.encode(m.doFinal()));
    }
}
