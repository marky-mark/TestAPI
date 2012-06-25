package com.mtt.api.client.util.security;

import org.apache.http.NameValuePair;

import java.util.Comparator;

/**
 * Specifies a request that can be signed
 */
public interface SignableRequest {

    /**
     * @return the request path (excluding protocol, host, port) without query string.
     */
    String getRequestPath();

    /**
     * @param comparator for sorting
     * @return the sorted query string
     */
    String getQueryString(Comparator<NameValuePair> comparator);

    /**
     * Add a new query parameter to this request.
     *
     * @param name  the name of query parameter
     * @param value the value of the query parameter
     */
    void addQueryParameter(String name, String value);
}
