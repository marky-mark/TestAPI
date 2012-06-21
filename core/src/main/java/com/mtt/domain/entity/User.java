package com.mtt.domain.entity;


import com.mtt.security.HashedAndSaltedPassword;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usr")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "usrname", nullable = false, unique = true)
    private String username;

//    @Column(name = "salt", nullable = false)
//    private String salt;

    @Column(name = "passwrd", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "contact_phone", nullable = true)
    private String telephoneNumber;

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserStatus status;

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    //    public String getSalt() {
//        return salt;
//    }

    public void setPassword(String plainTextPassword) {
        Assert.notNull(plainTextPassword);
        HashedAndSaltedPassword password = new HashedAndSaltedPassword(plainTextPassword);
        this.password = password.getPassword();
//        this.salt = password.getSalt();
    }

    /**
     * Verify the password matches the user's password
     *
     * @param plaintextPassword an unencrypted password
     * @return true if matched, false otherwise
     */
    public Boolean verifyPassword(String plaintextPassword) {
        return HashedAndSaltedPassword.comparePassword(plaintextPassword, this.password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }

        User user = (User) o;

        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : super.hashCode();
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", username='" + username + '\''
                + '}';
    }
}
