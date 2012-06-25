package com.mtt.domain.entity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Represents an API key.
 */
@Entity
@Table(name = "api_key")
@Cacheable
public class ApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usr_id")
    private User user;

    @Column(name = "access_key", nullable = false)
    private String accessKey;

    @Column(name = "private_key", nullable = false)
    private String privateKey;

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiKey)) {
            return false;
        }

        ApiKey apiKey = (ApiKey) o;

        return id != null && id.equals(apiKey.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : super.hashCode();
    }

    @Override
    public String toString() {
        return "ApiKey{"
                + "user=" + user
                + ", accessKey='" + accessKey + '\''
                + ", privateKey='" + privateKey + '\''
                + '}';
    }
}
