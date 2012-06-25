package com.mtt.api.client.util;

import com.mtt.api.client.util.security.RequestSigner;
import com.mtt.api.client.util.security.SignableURI;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.core.BaseClientResponse;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;

import java.io.IOException;

/**
 * Custom {@link org.jboss.resteasy.client.ClientExecutor} that extends Http Client executor and
 * adds in request signing functionality.
 * <p/>
 * TODO: Other than the request signing, this code was copied from RESTEasy due to issues in signing.
 * TODO: Not currently unit tested as was pasted in from elsewhere.
 */
public class RequestSigningClientExecutor extends ApacheHttpClient4Executor {

    private RequestSigner requestSigner;

    /**
     * Constructor.
     *
     * @param httpClient    the {@link org.apache.http.client.HttpClient} to use
     * @param requestSigner for signing outgoing requests
     */
    public RequestSigningClientExecutor(
            HttpClient httpClient,
            RequestSigner requestSigner) {

        super(httpClient);
        this.requestSigner = requestSigner;
    }

    @Override
    public ClientResponse execute(ClientRequest request) throws Exception {

        // We sign the request here.
        SignableURI uri = signRequest(request);

        final HttpRequestBase httpMethod = createHttpMethod(uri.getSignedURI(), request.getHttpMethod());
        loadHttpMethod(request, httpMethod);

        return execute(request, httpMethod);
    }

    private ClientResponse execute(ClientRequest request, HttpRequestBase httpMethod) throws IOException {

        final HttpResponse res = httpClient.execute(httpMethod, httpContext);

        BaseClientResponse response = new BaseClientResponse(new DefaultClientResponseStreamFactory(res), this);
        response.setStatus(res.getStatusLine().getStatusCode());
        response.setHeaders(extractHeaders(res));
        response.setProviderFactory(request.getProviderFactory());
        return response;
    }

    private SignableURI signRequest(ClientRequest request) throws Exception {
        SignableURI uri = new SignableURI(request.getUri());
        requestSigner.signRequest(uri);
        return uri;
    }

    private HttpRequestBase createHttpMethod(String url, String restVerb) {
        if ("GET".equals(restVerb)) {
            return new HttpGet(url);
        } else if ("POST".equals(restVerb)) {
            return new HttpPost(url);
        } else {
            final String verb = restVerb;
            return new HttpPost(url) {
                @Override
                public String getMethod() {
                    return verb;
                }
            };
        }
    }
}
