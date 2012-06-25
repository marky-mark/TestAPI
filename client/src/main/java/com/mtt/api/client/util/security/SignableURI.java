package com.mtt.api.client.util.security;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.jboss.resteasy.specimpl.UriBuilderImpl;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Allow a URI to be modified.
 */
public final class SignableURI implements SignableRequest {

    private URI uri;

    private List<NameValuePair> queryParameters;

    public SignableURI(String uri) throws Exception {
        this.uri = new URI(uri);
        queryParameters = new ArrayList<NameValuePair>(URLEncodedUtils.parse(this.uri, "UTF-8"));
    }

    @Override
    public String getRequestPath() {
        return uri.getPath();
    }

    @Override
    public String getQueryString(Comparator<NameValuePair> comparator) {
        return createQueryString(getQueryParameters(comparator));
    }

    @Override
    public void addQueryParameter(String name, String value) {
        queryParameters.add(new BasicNameValuePair(name, value));
    }

    public String getSignedURI() {
        UriBuilder uriBuilder = new UriBuilderImpl().uri(uri);
        if (queryParameters.size() > 0) {
            uriBuilder.replaceQuery(URLEncodedUtils.format(queryParameters, "UTF-8"));
        }
        return uriBuilder.build().toString();
    }

    private List<NameValuePair> getQueryParameters(Comparator<NameValuePair> comparator) {
        List<NameValuePair> queryParams = new ArrayList<NameValuePair>(queryParameters);
        Collections.sort(queryParams, comparator);
        return queryParams;
    }

    private String createQueryString(List<NameValuePair> params) {
        int index = 0;
        StringBuilder queryStringBuilder = new StringBuilder();
        for (NameValuePair nvp : params) {
            if (index > 0) {
                queryStringBuilder.append("&");
            }
            queryStringBuilder.append(nvp.getName()).append("=").append(nvp.getValue());
            index++;
        }
        return queryStringBuilder.toString();
    }
}
