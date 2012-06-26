package com.mtt.api.security;


import com.mtt.api.exception.UserAccountNotActiveException;
import com.mtt.domain.entity.User;
import com.mtt.domain.entity.UserStatus;
import com.mtt.service.SecurityService;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

public class PlatformRealm extends AuthorizingRealm {

    private SecurityService securityService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PlatformRealm.class);

    @Override
    public final String getName() {
        return "Test_API_Realm";
    }

    public final void securityInfoChanged(String username) {
        clearCachedAuthorizationInfo(createPrincipalCollection(username));
    }

    /**
     * This makes sure that the roles of the user is correct - Not in the scope of this project
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthorizationException("Principal collection must not be null");
        }

        String username = (String) getAvailablePrincipal(principals);

        if (username == null) {
            throw new AuthorizationException("No suitable principals found in principal collection");
        }

        try {
            User user = securityService.getUser(username);

            if (user == null) {
                throw new AuthorizationException("No account found for user [" + username + "]");
            }

            if (!user.getStatus().equals(UserStatus.ACTIVE)) {
                throw new UserAccountNotActiveException();
            }

            //add the roles asscoaited with the user - outside the scope of this project
            Set<String> roleNames = new HashSet<String>();
//            for (Role role : user.getRoles()) {
//                roleNames.add(role.getName().toString());
//            }

            roleNames.add("STANDARD_API_USER");
            SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo(roleNames);
//            authorizationInfo.setStringPermissions(user.getPermissions());
            Set<String> permissions = new HashSet<String>();
            permissions.add("domain:action:" + user.getId());//the target can also be *
              authorizationInfo.setStringPermissions(permissions);
            return authorizationInfo;

        } catch (DataAccessException ex) {
            final String message = "There was an error while authorizing user [" + username + "]";
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(message, ex);
            }
            throw new AuthorizationException(message, ex);
        }
    }

    /**
     * this takes the principals received in the AuthenticationFilter..gets the user from the Database
     * Make sure that the passwords of the user is correct
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        Assert.isInstanceOf(UsernamePasswordToken.class, token);
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;

        String username = upToken.getUsername();

        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm");
        }

        try {
            User user = securityService.getUser(username);

            if (user == null) {
                throw new UnknownAccountException("No account found for user [" + username + "]");
            }

            return new SimpleAuthenticationInfo(
                    createPrincipalCollection(user.getUsername()),
                    user.getPassword(),
                    //NOTE:: The salt is the same as HashedAndSaltedPassword
                    new SimpleByteSource(Base64.decode("abyssus")));

        } catch (DataAccessException ex) {
            final String message = "There was an error while authenticating user [" + username + "]";
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error(message, ex);
            }
            throw new AuthenticationException(message, ex);
        }
    }

    public final void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    private PrincipalCollection createPrincipalCollection(String username) {
        return new SimplePrincipalCollection(username, getName());
    }
}
