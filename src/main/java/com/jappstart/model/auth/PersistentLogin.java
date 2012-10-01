/*
 *  Copyright (C) 2010-2012 Taylor Leese (tleese22@gmail.com)
 *
 *  This file is part of jappstart.
 *
 *  jappstart is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  jappstart is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with jappstart.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jappstart.model.auth;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * The persistent login entity class.
 */
@SuppressWarnings("serial")
@Repository
@Entity
public class PersistentLogin implements Serializable {

    /**
     * The key.
     */
    @Id
    private Key key;

    /**
     * The username.
     */
    private String username;

    /**
     * The series.
     */
    private String series;

    /**
     * The token.
     */
    private String token;

    /**
     * The last used date.
     */
    private Date lastUsed;

    /**
     * The persistent user.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private PersistentUser persistentUser;

    /**
     * Create persistent login with a unique username.
     *
     * @param key the parent key
     * @param username the username
     */
    public PersistentLogin(final Key key, final String username) {
        this.key = KeyFactory.createKey(key, getClass().getSimpleName(),
            username);
        this.username = username;
    }

    /**
     * Returns the key.
     *
     * @return the key
     */
    public final Key getKey() {
        return key;
    }

    /**
     * Sets the key.
     *
     * @param key the key
     */
    public final void setKey(final Key key) {
        this.key = key;
    }

    /**
     * Returns the username.
     *
     * @return the username
     */
    public final String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the username
     */
    public final void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Returns the series.
     *
     * @return the series.
     */
    public final String getSeries() {
        return series;
    }

    /**
     * Sets the series.
     *
     * @param series the series
     */
    public final void setSeries(final String series) {
        this.series = series;
    }

    /**
     * Returns the token.
     *
     * @return the token
     */
    public final String getToken() {
        return token;
    }

    /**
     * Sets the token.
     *
     * @param token the token
     */
    public final void setToken(final String token) {
        this.token = token;
    }

    /**
     * Returns the last used date.
     *
     * @return the last used date
     */
    public final Date getLastUsed() {
        return lastUsed;
    }

    /**
     * Sets the last used date.
     *
     * @param lastUsed the last used date
     */
    public final void setLastUsed(final Date lastUsed) {
        this.lastUsed = lastUsed;
    }

    /**
     * Gets the persistent user.
     *
     * @return the persistent user
     */
    public final PersistentUser getPersistentUser() {
        return persistentUser;
    }

    /**
     * Sets the persistent user.
     *
     * @param persistentUser the persistent user
     */
    public final void setPersistentUser(final PersistentUser persistentUser) {
        this.persistentUser = persistentUser;
    }

}
