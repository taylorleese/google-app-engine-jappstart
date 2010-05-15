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

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * The enhanced user.
 */
@SuppressWarnings("serial")
public class EnhancedUser extends User {

    /**
     * The salt.
     */
    private String salt;

    /**
     * Creates an enhanced user.
     *
     * @param username the username
     * @param password the password
     * @param salt the salt
     * @param enabled enabled
     * @param accountNonExpired acount non-expired
     * @param credentialsNonExpired credentials non-expired
     * @param accountNonLocked account non-locked
     * @param authorities authorities
     */
    public EnhancedUser(final String username, final String password,
        final String salt, final boolean enabled,
        final boolean accountNonExpired, final boolean credentialsNonExpired,
        final boolean accountNonLocked,
        final Collection<GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired,
            credentialsNonExpired, accountNonLocked, authorities);
        this.salt = salt;
    }

    /**
     * Gets the salt.
     *
     * @return the salt
     */
    public final String getSalt() {
        return salt;
    }

    /**
     * Sets the salt.
     *
     * @param salt the salt
     */
    public final void setSalt(final String salt) {
        this.salt = salt;
    }

    @Override
    public final boolean equals(final Object o) {
        return super.equals(o);
    }

    @Override
    public final int hashCode() {
        return super.hashCode();
    }

}
