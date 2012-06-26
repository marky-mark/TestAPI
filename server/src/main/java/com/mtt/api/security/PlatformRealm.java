package com.mtt.api.security;


import com.mtt.service.SecurityService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class PlatformRealm extends AuthorizingRealm {

    private SecurityService securityService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public final void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }
}
