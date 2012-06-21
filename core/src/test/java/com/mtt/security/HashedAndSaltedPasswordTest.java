package com.mtt.security;

import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class HashedAndSaltedPasswordTest {

    @Test
    public void testHashedValue() {
        HashedAndSaltedPassword password = new HashedAndSaltedPassword("password");
        assertThat(password.getSalt(), equalTo("abyssus"));
        assertThat(password.getPassword(), equalTo("bTKa66S/DkJEWzy+TXNeH5BppPGgDRnwQtLR9x2cyao="));
    }

    @Test
    public void testPasswordVerifyTrue() {
        assertThat(HashedAndSaltedPassword.comparePassword("ThisIsNotThePassword",
                "vxfOxS7a2svQcAdv8kNE6lix0JAmPjyHbKPCZk2Ua08="), equalTo(false));
    }

    @Test
    public void testPasswordVerifyHashedFalse() {
        assertThat(HashedAndSaltedPassword.comparePassword("password", "NoMatch"), equalTo(false));
    }
}
