package com.mtt.api.security;

import com.mtt.domain.entity.ApiKey;
import com.mtt.service.SecurityService;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class ApiKeyAuthenticationFilter extends AuthenticatingFilter {

    @Autowired
    private SecurityService securityService;

    /**
     * Get the Access key from the request query ?apiKey=XXXX, retrieve the key from the Database
     *
     * This is used by the platformRealm
     * @param request
     * @param servletResponse
     * @return  the Token with the username - relating to the apiKey
     * @throws Exception
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse servletResponse) throws Exception {

        String accessKey = request.getParameter(SecurityUtils.ACCESS_KEY);

        if (accessKey != null) {

            ApiKey apiKey = securityService.getApiKey(accessKey);

            if (apiKey != null) {
                return new UsernamePasswordToken(apiKey.getUser().getUsername(), "", false, getHost(request));
            }
        }

        //give the username no length if NOT found
        return new UsernamePasswordToken("", "", false, getHost(request));
    }

    /**
     * When a url is protected - the request is denied
     *
     * Here is where all the query strings are checked and make sure that the
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        //authenticate the public key and signature
        ApiAuthenticationResult authenticationResult = authenticate(request);
        if (authenticationResult.isAuthenticated()) {

            //Login - This then uses the PlatformRealm to login
            if (executeLogin(request, response)) {

                //this makes the request go to onLoginSuccess - can be overridden in the class
                return true;
            }
        }

        //Failed public and signature authentication!!
        WebUtils.toHttp(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        //this makes the request go to onLoginFailure - can be overridden in this class
        return false;
    }

    private ApiAuthenticationResult authenticate(ServletRequest request) throws Exception {
        ApiAuthenticationResult result = new ApiAuthenticationResult();
        String accessKey = request.getParameter(SecurityUtils.ACCESS_KEY);
        String timestamp = request.getParameter(SecurityUtils.TIMESTAMP);
        String signature = request.getParameter(SecurityUtils.SIGNATURE);

        result.setAuthenticated(false);
        result.setReceivedSignature(signature);
        result.setAccessKey(accessKey);
        result.setTimestamp(timestamp);

        // TODO: Temporary authentication disabled "hack" - MUST REMOVE
        if (StringUtils.hasLength(accessKey)
                && authenticationIsDisabled(request)) {
            result.setAuthenticated(true);
        } else if (StringUtils.hasLength(accessKey)
                && StringUtils.hasLength(timestamp)
                && StringUtils.hasLength(signature)) {
            ApiKey apiKey = securityService.getApiKey(accessKey);
            if (apiKey != null) {
                String expectedSignature = SecurityUtils.createApiRequestSignature(request, apiKey.getPrivateKey());
                result.setExpectedSignature(expectedSignature);
                result.setAuthenticated(signature.equals(expectedSignature));
            }
        }
        return result;
    }

    private boolean authenticationIsDisabled(ServletRequest request) {
        return "false".equals(request.getParameter(SecurityUtils.AUTHENTICATION_ENABLED));
    }
}
