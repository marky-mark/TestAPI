package com.mtt.domain.entity;

import com.mtt.service.KeyGeneratorService;
import org.joda.time.DateTime;
import org.joda.time.Days;

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
import java.util.Date;

/**
 * Entity representing a key generated for user activation.
 */
@Entity
@Table(name = "usr_activation_key")
@Cacheable
public class UserActivationKey {

    private static final int ACTIVE_DAYS = 14;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "activation_key", nullable = false, unique = true)
    private String key;

    @Column(name = "expiry_date", nullable = false)
    private Date expiryDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usr_id", nullable = false)
    private User user;

    public Long getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public DateTime getExpiryDate() {
        return new DateTime(expiryDate);
    }

    public boolean isExpired() {
       return new DateTime().isAfter(getExpiryDate());
    }

    public User getUser() {
        return user;
    }

    /**
     * Initialise the activation key.
     *
     * @param user                the user who "owns" the key.
     * @param keyGeneratorService service for generating key
     */
    public void initialise(User user, KeyGeneratorService keyGeneratorService) {
        if (id == null) {
            this.user = user;
            expiryDate = new DateTime(new Date()).plus(Days.days(ACTIVE_DAYS)).toDate();
            key = keyGeneratorService.generateKey(user.getUsername());
        }
    }
}

