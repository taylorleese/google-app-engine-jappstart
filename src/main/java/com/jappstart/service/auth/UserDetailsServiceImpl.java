/*
 *  Copyright (C) 2010 Taylor Leese (tleese22@gmail.com)
 *
 *  This file is part of jappstart.
 *
 *  jappstart is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  jappstart is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with jappstart.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jappstart.service.auth;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jappstart.exception.DuplicateUserException;
import com.jappstart.model.auth.UserAccount;

/**
 * The user details service implementation.
 */
@Repository
public class UserDetailsServiceImpl implements EnhancedUserDetailsService {

    /**
     * The entity manager.
     */
    @PersistenceContext
    private transient EntityManager entityManager;

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

        final Query query = entityManager.createQuery(
            "SELECT u FROM UserAccount u WHERE username = :username");
        query.setParameter("username", username);

        final UserAccount user = (UserAccount) query.getSingleResult();

        authorities.add(new GrantedAuthorityImpl(user.getRole()));

        final UserDetails userDetails = new EnhancedUser(user.getUsername(),
            user.getPassword(), user.getSalt(), user.isEnabled(),
            user.isAccountNonExpired(), user.isCredentialsNonExpired(),
            user.isAccountNonLocked(), authorities);

        return userDetails;
    }

    /**
     * Adds a user.
     *
     * @param user the user
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public final void addUser(final UserAccount user) {
        final Query query = entityManager.createQuery(
            "SELECT u FROM UserAccount u WHERE username = :username");
        query.setParameter("username", user.getUsername());

        final List results = query.getResultList();
        if (results != null && !results.isEmpty()) {
            throw new DuplicateUserException();
        }

        entityManager.persist(user);
    }

}
