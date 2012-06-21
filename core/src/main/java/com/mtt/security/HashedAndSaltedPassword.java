package com.mtt.security;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for assisting in creating a hashed and salted password.
 */
public final class HashedAndSaltedPassword implements SaltedPassword {

    private static final Logger LOGGER = LoggerFactory.getLogger(HashedAndSaltedPassword.class);

    private static final String salt = "abyssus";

    private String hashedPassword;

    /**
     * Constructor.
     *
     * @param plainTextPassword the password to salt and hash
     */
    public HashedAndSaltedPassword(String plainTextPassword) {
        ByteSource saltSource = new SimpleByteSource(Base64.decode(salt));
        this.hashedPassword = new Sha256Hash(plainTextPassword, saltSource, 1024).toBase64();
    }

    public String getSalt() {
        return salt;
    }

    public String getPassword() {
        return hashedPassword;
    }

    /**
     * Compare a plaintext password with an encrypted version
     *
     * @param plainTextPassword - the plaintext password
     * @param encryptedPassword - encrypted password
     * @return true on match, false otherwise
     */

    public static Boolean comparePassword(String plainTextPassword, String encryptedPassword) {
        ByteSource saltSource = new SimpleByteSource(Base64.decode(salt));
        return new Sha256Hash(plainTextPassword, saltSource, 1024).toBase64().equals(encryptedPassword);
    }

    /**
     * Runnable - allows for on the fly generation of hashed and salted password.
     *
     * @param args app args
     */
    public static void main(String[] args) {
        HashedAndSaltedPassword password = new HashedAndSaltedPassword(args[0]);
        LOGGER.info("to hash: " + args[0]);
        LOGGER.info("Hashed Password: " + password.getPassword());
        LOGGER.info("Salt used: " + password.getSalt());
    }
}
