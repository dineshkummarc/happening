/* 
 * Copyright 2012 Vaadin Ltd.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.vaadin.training.fundamentals.happening.domain.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class DomainUser extends AbstractEntity {
    private String hash;
    private String salt;
    private String name;
    private String picture;

    @NotNull
    @Column(unique = true)
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
