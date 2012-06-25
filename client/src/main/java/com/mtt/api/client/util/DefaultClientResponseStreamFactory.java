package com.mtt.api.client.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.jboss.resteasy.client.core.BaseClientResponse;
import org.jboss.resteasy.client.core.SelfExpandingBufferredInputStream;

import java.io.IOException;
import java.io.InputStream;

/**
 * Stream factory for API response handling - this code was stripped as is  from the
 * RESTEasy {@link org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor} as we needed to
 * customise behaviour when executing client API requests.
 *
 * TODO: This is not unit tested - I didn't write it (it probably should be...)
 */
public final class DefaultClientResponseStreamFactory implements BaseClientResponse.BaseClientResponseStreamFactory {

    private InputStream stream;

    private HttpResponse res;

    /**
     * Constructor.
     *
     * @param response the response.
     */
    public DefaultClientResponseStreamFactory(HttpResponse response) {
        this.res = response;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        if (stream == null) {
            HttpEntity entity = res.getEntity();
            if (entity == null) {
                return null;
            }
            stream = new SelfExpandingBufferredInputStream(entity.getContent());
        }
        return stream;
    }

    @Override
    public void performReleaseConnection() {
        // Apache Client 4 is stupid,  You have to get the InputStream and close it if there is an entity
        // otherwise the connection is never released.  There is, of course, no close() method on response
        // to make this easier.
        try {
            if (stream != null) {
                stream.close();
            } else {
                InputStream is = getInputStream();
                if (is != null) {
                    is.close();
                }
            }
        } catch (Exception ignore) {
        }
    }
}
