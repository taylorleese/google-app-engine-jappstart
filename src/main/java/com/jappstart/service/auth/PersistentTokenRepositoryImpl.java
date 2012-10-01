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
package com.jappstart.service.auth;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.security.web.authentication.rememberme.
    PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.
    PersistentTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.jappstart.model.auth.PersistentLogin;
import com.jappstart.model.auth.PersistentUser;
import com.jappstart.model.auth.UserAccount;

/**
 * The persistent token repository implementation.
 */
@Service
public class PersistentTokenRepositoryImpl
    implements PersistentTokenRepository {

    /**
     * The default cache expiration in seconds.
     */
    private static final int DEFAULT_EXPIRATION = 3600;

    /**
     * The entity manager.
     */
    @PersistenceContext
    private transient EntityManager entityManager;

    /**
     * The memcache service.
     */
    private MemcacheService memcacheService;

    /**
     * Returns the memcache service.
     *
     * @return the memcache service
     */
    public final MemcacheService getMemcacheService() {
        return memcacheService;
    }

    /**
     * Sets the memcache service.
     *
     * @param memcacheService the memcache service
     */
    public final void setMemcacheService(
        final MemcacheService memcacheService) {
        this.memcacheService = memcacheService;
    }

    /**
     * Creates a new remember me token.
     *
     * @param token the remember me token
     */
    @Override
    @Transactional
    public final void createNewToken(final PersistentRememberMeToken token) {
        final Query query = entityManager.createQuery(
            "SELECT u FROM UserAccount u WHERE username = :username");
        query.setParameter("username", token.getUsername());
        final UserAccount user = (UserAccount) query.getSingleResult();

        if (user.getPersistentUser() == null) {
            user.setPersistentUser(
                new PersistentUser(user.getKey(), token.getUsername()));
        }

        if (user.getPersistentUser().getPersistentLogins() == null) {
            user.getPersistentUser().setPersistentLogins(
                new ArrayList<PersistentLogin>());
        }

        user.getPersistentUser().getPersistentLogins().add(
            createPersistentLogin(user.getPersistentUser().getKey(), token));

        entityManager.persist(user);

        memcacheService.put(user.getUsername(), user,
            Expiration.byDeltaSeconds(DEFAULT_EXPIRATION));
    }

    /**
     * Gets the token for the given series.
     *
     * @param series the series
     * @return the remember me token
     */
    @Override
    public final PersistentRememberMeToken getTokenForSeries(
        final String series) {
        PersistentLogin persistentLogin = null;
        final Query query = entityManager.createQuery(
            "SELECT p FROM PersistentLogin p WHERE series = :series");
        query.setParameter("series", series);

        try {
            persistentLogin = (PersistentLogin) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

        return new PersistentRememberMeToken(
            persistentLogin.getUsername(),
            persistentLogin.getSeries(),
            persistentLogin.getToken(),
            persistentLogin.getLastUsed());
    }

    /**
     * Removes the tokens for the given username.
     *
     * @param username the username
     */
    @Override
    @Transactional
    public final void removeUserTokens(final String username) {
        final Query query = entityManager.createQuery(
            "SELECT p FROM PersistentUser p WHERE username = :username");
        query.setParameter("username", username);

        if (query.getResultList().size() > 0) {
            final PersistentUser persistentUser =
                (PersistentUser) query.getSingleResult();
            entityManager.remove(persistentUser);
        }
    }

    /**
     * Updates the token given the series, token value, and last used date.
     *
     * @param series the series
     * @param tokenValue the token value
     * @param lastUsed the last used date
     */
    @Override
    @Transactional
    public final void updateToken(final String series, final String tokenValue,
        final Date lastUsed) {
        final Query query = entityManager.createQuery(
            "SELECT p FROM PersistentLogin p WHERE series = :series");
        query.setParameter("series", series);

        final PersistentLogin persistentLogin =
             (PersistentLogin) query.getSingleResult();

        persistentLogin.setToken(tokenValue);
        persistentLogin.setLastUsed(lastUsed);

        entityManager.persist(persistentLogin);
    }

    /**
     * Creates a persistent login given a remember me token.
     *
     * @param key the parent key
     * @param token the remember me token
     * @return the persistent login
     */
    private PersistentLogin createPersistentLogin(
        final Key key, final PersistentRememberMeToken token) {
        final PersistentLogin persistentLogin =
            new PersistentLogin(key, token.getUsername());

        persistentLogin.setSeries(token.getSeries());
        persistentLogin.setToken(token.getTokenValue());
        persistentLogin.setLastUsed(token.getDate());

        return persistentLogin;
    }

}
