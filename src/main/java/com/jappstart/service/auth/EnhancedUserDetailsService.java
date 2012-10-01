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

import java.util.Locale;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.jappstart.model.auth.UserAccount;

/**
 * The enhanced user details service interface.
 */
public interface EnhancedUserDetailsService extends UserDetailsService {

    /**
     * Adds a user.
     *
     * @param user the user
     * @param locale the locale
     */
    void addUser(final UserAccount user, final Locale locale);

    /**
     * Returns the user account for the given username.
     *
     * @param username the username
     * @return the user account
     */
    UserAccount getUser(final String username);

    /**
     * Activates the user with the given activation key.
     *
     * @param key the activation key
     * @return true if successful; false otherwise
     */
    boolean activateUser(final String key);

    /**
     * Indicates if the activation e-mail has been sent.
     *
     * @param username the username
     * @return true if sent; false otherwise
     */
    boolean isActivationEmailSent(final String username);

    /**
     * Updates the activation e-mail sent status.
     *
     * @param username the username
     */
    void activationEmailSent(final String username);

}
