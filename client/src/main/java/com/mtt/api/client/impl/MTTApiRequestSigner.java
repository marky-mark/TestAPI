package com.mtt.api.client.impl;

import com.mtt.api.client.util.security.RequestSigner;
import com.mtt.api.client.util.security.SignableRequest;
import com.mtt.api.model.MTTApiKey;
import org.apache.http.NameValuePair;
import org.apache.shiro.codec.Hex;
import org.springframework.util.StringUtils;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;

/**
 * <p/>
 * TODO: Possible issue that existing query param values are likely to have been encoded - may need to decode
 */
public final class MTTApiRequestSigner implements RequestSigner {

    public static final String API_KEY_NAME =  "apiKey";
    public static final String TIMESTAMP_NAME = "timestamp";
    public static final String SIGNATURE_NAME = "signature";

    private static NameValuePairSorter nameValuePairSorter = new NameValuePairSorter();

    private MTTApiKey apiKey;

    /**
     * Constructor.
     *
     * @param apiKey
     */
    public MTTApiRequestSigner(MTTApiKey apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public void signRequest(SignableRequest request) {
        if (apiKey != null) {
            try {
                request.addQueryParameter(API_KEY_NAME, apiKey.getAccessKey());
                request.addQueryParameter(TIMESTAMP_NAME, String.valueOf(System.currentTimeMillis()));
                request.addQueryParameter(SIGNATURE_NAME, sign(getSignablePart(request), apiKey.getPrivateKey()));
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public MTTApiKey getApiKey() {
        return apiKey;
    }

    private String sign(String url, String privateKey) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKey key = new SecretKeySpec(privateKey.getBytes(), "hmacSHA256");
        Mac m = Mac.getInstance("hmacSHA256");
        m.init(key);
        m.update(url.getBytes());
        return new String(Hex.encode(m.doFinal()));
    }

    private String getSignablePart(SignableRequest request) {
        StringBuilder builder = new StringBuilder(request.getRequestPath());
        String queryString = request.getQueryString(nameValuePairSorter);
        if (StringUtils.hasLength(queryString)) {
            builder.append("?").append(queryString);
        }
        return builder.toString().toLowerCase();
    }

    private static class NameValuePairSorter implements Comparator<NameValuePair> {
        @Override
        public int compare(NameValuePair o1, NameValuePair o2) {
            int result = o1.getName().compareTo(o2.getName());
            if(result==0) {
                result = o1.getValue().compareTo(o2.getValue());
            }
            return result;
        }
    }
}
