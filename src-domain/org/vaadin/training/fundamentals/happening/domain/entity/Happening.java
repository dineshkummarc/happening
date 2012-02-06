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

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Happening extends AbstractEntity {

    @ManyToOne
    private DomainUser owner;
    private String title;
    @Temporal(TemporalType.DATE)
    private Date starts;
    @Temporal(TemporalType.DATE)
    private Date ends;
    private long latitude;
    private long longitude;
    private String venue;
    private boolean live;
    
    public Happening() {
        
    }
    
    public void setOwner(DomainUser owner) {
        this.owner = owner;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStarts(Date starts) {
        this.starts = starts;
    }

    public void setEnds(Date ends) {
        this.ends = ends;
    }

    public void setLatitude(long latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(long longitude) {
        this.longitude = longitude;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public DomainUser getOwner() {
        return owner;
    }
    
    public String getTitle() {
        return title;
    }

    public Date getStarts() {
        return starts;
    }

    public Date getEnds() {
        return ends;
    }

    public long getLatitude() {
        return latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public String getVenue() {
        return venue;
    }

    public boolean isLive() {
        return live;
    }
 }
