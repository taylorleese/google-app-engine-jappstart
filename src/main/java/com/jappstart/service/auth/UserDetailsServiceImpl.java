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
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.jappstart.exception.DuplicateUserException;
import com.jappstart.model.auth.UserAccount;

/**
 * The user details service implementation.
 */
@Service
public class UserDetailsServiceImpl implements EnhancedUserDetailsService {

    /**
     * The default cache expiration in seconds.
     */
    private static final int DEFAULT_EXPIRATION = 3600;

    /**
     * The username field name.
     */
    private static final String USERNAME = "username";

    /**
     * The select user query.
     */
    private static final String SELECT_USER =
        "SELECT u FROM UserAccount u WHERE username = :username";

    /**
     * The entity manager.
     */
    @PersistenceContext
    private transient EntityManager entityManager;

    /**
     * The mail task name.
     */
    private String mailTaskName;

    /**
     * The mail task URL.
     */
    private String mailTaskUrl;

    /**
     * The datastore service.
     */
    private DatastoreService datastoreService;

    /**
     * The memcache service.
     */
    private MemcacheService memcacheService;

    /**
     * Returns the mail task name.
     *
     * @return the mail task name
     */
    public final String getMailTaskName() {
        return mailTaskName;
    }

    /**
     * Sets the mail task name.
     *
     * @param mailTaskName the mail task name
     */
    public final void setMailTaskName(final String mailTaskName) {
        this.mailTaskName = mailTaskName;
    }

    /**
     * Returns the mail task URL.
     *
     * @return the mail task URL
     */
    public final String getMailTaskUrl() {
        return mailTaskUrl;
    }

    /**
     * Sets the mail task URL.
     *
     * @param mailTaskUrl the mail task URL
     */
    public final void setMailTaskUrl(final String mailTaskUrl) {
        this.mailTaskUrl = mailTaskUrl;
    }

    /**
     * Returns the datastore service.
     *
     * @return the datastore service
     */
    public final DatastoreService getDatastoreService() {
        return datastoreService;
    }

    /**
     * Sets the datastore service.
     *
     * @param datastoreService the datastore service
     */
    public final void setDatastoreService(
        final DatastoreService datastoreService) {
        this.datastoreService = datastoreService;
    }

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
     * Locates the user based on the username.
     *
     * @param username string the username
     * @return the user details
     */
    @Override
    public final UserDetails loadUserByUsername(final String username) {
        final List<GrantedAuthority> authorities =
            new ArrayList<GrantedAuthority>();
        UserAccount user = (UserAccount) memcacheService.get(username);

        if (user == null) {
            final Query query = entityManager.createQuery(
                "SELECT u FROM UserAccount u WHERE username = :username");
            query.setParameter(USERNAME, username);

            try {
                user = (UserAccount) query.getSingleResult();

                memcacheService.put(username, user,
                    Expiration.byDeltaSeconds(DEFAULT_EXPIRATION));
            } catch (NoResultException e) {
                throw new UsernameNotFoundException("Username not found.", e);
            }
        }

        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        return new EnhancedUser(user.getUsername(), user.getEmail(),
            user.getDisplayName(), user.getPassword(), user.getSalt(),
            user.isEnabled(), user.isAccountNonExpired(),
            user.isCredentialsNonExpired(), user.isAccountNonLocked(),
            authorities);
    }

    /**
     * Returns the user account for the given username.
     *
     * @param username the username
     * @return the user account
     */
    @Override
    public final UserAccount getUser(final String username) {
        UserAccount user = (UserAccount) memcacheService.get(username);

        if (user == null) {
            final Query query = entityManager.createQuery(
                "SELECT u FROM UserAccount u WHERE username = :username");
            query.setParameter(USERNAME, username);

            try {
                user = (UserAccount) query.getSingleResult();

                memcacheService.put(username, user,
                    Expiration.byDeltaSeconds(DEFAULT_EXPIRATION));
            } catch (NoResultException e) {
                return null;
            }
        }

        return user;
    }

    /**
     * Adds a user.
     *
     * @param user the user
     * @param locale the locale
     */
    @Override
    @Transactional
    public final void addUser(final UserAccount user, final Locale locale) {
        final UserAccount cachedUser = (UserAccount) memcacheService.get(
            user.getUsername());

        if (cachedUser != null) {
            throw new DuplicateUserException();
        }

        final Query query = entityManager.createQuery(
            "SELECT u FROM UserAccount u WHERE username = :username");
        query.setParameter(USERNAME, user.getUsername());

        @SuppressWarnings("unchecked")
        final List results = query.getResultList();
        if (results != null && !results.isEmpty()) {
            throw new DuplicateUserException();
        }

        entityManager.persist(user);

        memcacheService.put(user.getUsername(), user,
            Expiration.byDeltaSeconds(DEFAULT_EXPIRATION));

        final TaskOptions taskOptions =
            TaskOptions.Builder.withUrl(mailTaskUrl)
            .param("username", user.getUsername())
            .param("locale", locale.toString());

        final Queue queue = QueueFactory.getQueue(mailTaskName);
        queue.add(datastoreService.getCurrentTransaction(), taskOptions);
    }

    /**
     * Activates the user with the given activation key.
     *
     * @param key the activation key
     * @return true if successful; false otherwise
     */
    @Override
    @Transactional
    public final boolean activateUser(final String key) {
        final Query query = entityManager.createQuery(
            "SELECT u FROM UserAccount u WHERE activationKey = :key");
        query.setParameter("key", key);

        try {
            final UserAccount user = (UserAccount) query.getSingleResult();
            user.setEnabled(true);

            entityManager.persist(user);

            memcacheService.put(user.getUsername(), user,
                Expiration.byDeltaSeconds(DEFAULT_EXPIRATION));

            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    /**
     * Indicates if the activation e-mail has been sent.
     *
     * @param username the username
     * @return true if sent; false otherwise
     */
    @Override
    public final boolean isActivationEmailSent(final String username) {
        UserAccount user = (UserAccount) memcacheService.get(username);

        if (user == null) {
            final Query query = entityManager.createQuery(SELECT_USER);
            query.setParameter(USERNAME, username);

            try {
                user = (UserAccount) query.getSingleResult();

                memcacheService.put(username, user,
                    Expiration.byDeltaSeconds(DEFAULT_EXPIRATION));
            } catch (NoResultException e) {
                throw new UsernameNotFoundException("Username not found.", e);
            }
        }

        return user.isActivationEmailSent();
    }

    /**
     * Updates the activation e-mail sent status.
     *
     * @param username the username
     */
    @Override
    @Transactional
    public final void activationEmailSent(final String username) {
        final Query query = entityManager.createQuery(SELECT_USER);
        query.setParameter(USERNAME, username);

        try {
            final UserAccount user = (UserAccount) query.getSingleResult();
            user.setActivationEmailSent(true);

            entityManager.persist(user);

            memcacheService.put(user.getUsername(), user,
                Expiration.byDeltaSeconds(DEFAULT_EXPIRATION));
        } catch (NoResultException e) {
            throw new UsernameNotFoundException("Username not found.", e);
        }
    }

}
