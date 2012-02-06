package org.vaadin.training.fundamentals.happening.domain.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class DomainUser extends AbstractEntity {
    private String hash;
    private String salt;
    private String name;
    private String picture;
    private String accountId;
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<Happening> happenings = new HashSet<Happening>();

    public Set<Happening> getHappenings() {
        return happenings;
    }

    public void setHappenings(Set<Happening> happenings) {
        this.happenings = happenings;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    
}    
