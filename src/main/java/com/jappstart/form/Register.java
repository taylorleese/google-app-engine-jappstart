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
package com.jappstart.form;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

/**
 * The registration form bean.
 */
@SuppressWarnings("serial")
public class Register implements Serializable {

    /**
     * Display name.
     */
    @Pattern(regexp = ".+")
    private String displayName;

    /**
     * The username.
     */
    @Pattern(regexp = ".+")
    private String username;

    /**
     * The e-mail address.
     */
    @Pattern(regexp = ".+@.+\\.[a-z]+")
    private String email;

    /**
     * The password.
     */
    @Pattern(regexp = ".{5,}+")
    private String password;

    /**
     * Sets the display name.
     *
     * @param displayName the display name
     */
    /* CHECKSTYLE:OFF */
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }
    /* CHECKSTYLE:ON */

    /**
     * Gets the display name.
     *
     * @return the display name
     */
    /* CHECKSTYLE:OFF */
    public String getDisplayName() {
        return displayName;
    }
    /* CHECKSTYLE:ON */

    /**
     * Sets the username.
     *
     * @param username the username
     */
    /* CHECKSTYLE:OFF */
    public void setUsername(final String username) {
        this.username = username;
    }
    /* CHECKSTYLE:ON */

    /**
     * Gets the username.
     *
     * @return the username
     */
    /* CHECKSTYLE:OFF */
    public String getUsername() {
        return username;
    }
    /* CHECKSTYLE:ON */

    /**
     * Sets the e-mail address.
     *
     * @param email the e-mail address
     */
    /* CHECKSTYLE:OFF */
    public void setEmail(final String email) {
        this.email = email;
    }
    /* CHECKSTYLE:ON */

    /**
     * Gets the e-mail address.
     *
     * @return the e-mail address
     */
    /* CHECKSTYLE:OFF */
    public String getEmail() {
        return email;
    }
    /* CHECKSTYLE:ON */

    /**
     * Sets the password.
     *
     * @param password the password
     */
    /* CHECKSTYLE:OFF */
    public void setPassword(final String password) {
        this.password = password;
    }
    /* CHECKSTYLE:ON */

    /**
     * Gets the password.
     *
     * @return the password
     */
    /* CHECKSTYLE:OFF */
    public String getPassword() {
        return password;
    }
    /* CHECKSTYLE:ON */

}
