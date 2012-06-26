package com.mtt.api.security;


import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.Assert;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SecurityUtils {

    public static final String ACCESS_KEY = "apiKey";

    public static final String TIMESTAMP = "timestamp";

    public static final String SIGNATURE = "signature";

    public static final String AUTHENTICATION_ENABLED = "authentication";

    private static final String API_SIGNATURE_ALGORITHM = "hmacSHA256";

    /**
     * Private utility class constructor.
     */
    private SecurityUtils() {
    }

    /**
     * Create an api signature given a request and private key.
     *
     * @param request    the source request
     * @param privateKey the private key
     * @return an api signature given a request and private key.
     * @throws java.security.NoSuchAlgorithmException when algorithm can't be found
     * @throws java.security.InvalidKeyException      when the key is invalid
     */
    public static String createApiRequestSignature(ServletRequest request, String privateKey)
            throws NoSuchAlgorithmException, InvalidKeyException {
        Assert.notNull(request);
        Assert.notNull(privateKey);
        return signUrl(normaliseUrl(request), privateKey);
    }

    public static String signUrl(String url, String privateKey)
            throws NoSuchAlgorithmException, InvalidKeyException {
        Assert.notNull(url);
        Assert.notNull(privateKey);
        SecretKey key = new SecretKeySpec(privateKey.getBytes(), API_SIGNATURE_ALGORITHM);
        Mac m = Mac.getInstance(API_SIGNATURE_ALGORITHM);
        m.init(key);
        m.update(url.getBytes());
        return new String(Hex.encode(m.doFinal()));
    }

    /**
     * Temporary improvement t URL normalisation.
     * <ul>
     *     <li>lowercase parameters and values</li>
     *     <li>re-order parameters and values alphabetically</li>
     * </ul>
     *
     * TODO: this needs to be factored out along with the code in the client that generates signatures.
     *
     * @param request servlet request
     * @return normalised URL
     */
    public static String normaliseUrl(ServletRequest request) {
        HttpServletRequest httpRequest = WebUtils.toHttp(request);

        String uri = httpRequest.getRequestURI().toLowerCase();

        Map<String, String[]> parameters = Maps.newHashMap(request.getParameterMap());
        parameters.remove(SIGNATURE);

        if (parameters.size() > 0) {

            List<KeyValuePair<String, String>> parameterPairs = Lists.newArrayList();

            // lowercase params/values
            for (String name : parameters.keySet()) {
                for (String value : parameters.get(name)) {
                    parameterPairs.add(new KeyValuePair<String, String>(name.toLowerCase(), value.toLowerCase()));
                }
            }

            // sort params/values
            Collections.sort(parameterPairs, new KeyValuePairComparator());

            // join key/value pairs
            Function<KeyValuePair<String, String>, String> keyValuePairJoiner =
                    new Function<KeyValuePair<String, String>, String>() {
                        @Override
                        public String apply(KeyValuePair<String, String> pair) {
                            return Joiner.on("=").join(pair.getKey(), pair.getValue());
                        }
                    };

            // join entire query string
            String query = Joiner.on("&").join(Collections2.transform(parameterPairs, keyValuePairJoiner));

            uri = Joiner.on("?").join(uri, query);
        }
        return uri;
    }
}
