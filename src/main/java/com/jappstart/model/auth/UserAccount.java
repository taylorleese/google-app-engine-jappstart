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
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.security.crypto.codec.Hex;
import org.springframework.stereotype.Repository;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * The user account entity class.
 */
@SuppressWarnings("serial")
@Repository
@Entity
public class UserAccount implements Serializable {

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
     * The e-mail address.
     */
    private String email;

    /**
     * The display name.
     */
    private String displayName;

    /**
     * The password.
     */
    private String password;

    /**
     * The salt.
     */
    private String salt;

    /**
     * The role.
     */
    private String role;

    /**
     * The activation key.
     */
    private String activationKey;

    /**
     * The activation e-mail status.
     */
    private boolean activationEmailSent;

    /**
     * The enabled status.
     */
    private boolean enabled;

    /**
     * The account non-expired status.
     */
    private boolean accountNonExpired;

    /**
     * The account non-locked status.
     */
    private boolean accountNonLocked;

    /**
     * The credentials non-expired status.
     */
    private boolean credentialsNonExpired;

    /**
     * The persistent user.
     */
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PersistentUser persistentUser;

    /**
     * Create a user account with a unique username.
     *
     * @param username the username
     */
    public UserAccount(final String username) {
        MessageDigest sDigest = null;
        MessageDigest aDigest = null;

        this.key = KeyFactory.createKey(getClass().getSimpleName(), username);
        this.username = username;
        this.enabled = false;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.role = "ROLE_USER";

        try {
            sDigest = MessageDigest.getInstance("SHA-256");
            aDigest = MessageDigest.getInstance("SHA-256");
            sDigest.update(UUID.randomUUID().toString().getBytes("UTF-8"));
            aDigest.update(UUID.randomUUID().toString().getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }

        this.salt = String.valueOf(Hex.encode(sDigest.digest()));
        this.activationKey = String.valueOf(Hex.encode(aDigest.digest()));
        this.activationEmailSent = false;
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
     * Returns the email.
     *
     * @return the email
     */
    public final String getEmail() {
        return email;
    }

    /**
     * Sets the email.
     *
     * @param email the email
     */
    public final void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Gets the display name.
     *
     * @return the display name
     */
    public final String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the display name.
     *
     * @param displayName the display name
     */
    public final void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the password.
     *
     * @return the password
     */
    public final String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password
     */
    public final void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Returns the salt.
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

    /**
     * Returns the role.
     *
     * @return the role
     */
    public final String getRole() {
        return role;
    }

    /**
     * Sets the role.
     *
     * @param role the role
     */
    public final void setRole(final String role) {
        this.role = role;
    }

    /**
     * Gets the activation key.
     *
     * @return the activation key
     */
    public final String getActivationKey() {
        return activationKey;
    }

    /**
     * Sets the activation key.
     *
     * @param activationKey the activation key
     */
    public final void setActivationKey(final String activationKey) {
        this.activationKey = activationKey;
    }

    /**
     * Indicates if the activation e-mail has been sent.
     *
     * @return true if sent; false otherwise
     */
    public final boolean isActivationEmailSent() {
        return activationEmailSent;
    }

    /**
     * Sets the activation e-mail sent status.
     *
     * @param activationEmailSent true if sent; false otherwise
     */
    public final void setActivationEmailSent(
        final boolean activationEmailSent) {
        this.activationEmailSent = activationEmailSent;
    }

    /**
     * Indicates if the account is enabled.
     *
     * @return true if enabled; false otherwise
     */
    public final boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the enabled status.
     *
     * @param enabled true if enabled; false otherwise
     */
    public final void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Indicates if the account is non-expired.
     *
     * @return true if non-expired; false otherwise
     */
    public final boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    /**
     * Sets the account non-expired status.
     *
     * @param accountNonExpired true if non-expired; false otherwise
     */
    public final void setAccountNonExpired(final boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    /**
     * Indicates if the account is non-locked.
     *
     * @return true if non-locked; false otherwise
     */
    public final boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    /**
     * Sets the account non-locked status.
     *
     * @param accountNonLocked true if non-locked; false otherwise
     */
    public final void setAccountNonLocked(final boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    /**
     * Indicates if the account credentials are non-expired.
     *
     * @return true if non-expired; false otherwise
     */
    public final boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    /**
     * Sets the account credentials non-expired status.
     *
     * @param credentialsNonExpired true if non-expired; false otherwise
     */
    public final void setCredentialsNonExpired(
        final boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
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
