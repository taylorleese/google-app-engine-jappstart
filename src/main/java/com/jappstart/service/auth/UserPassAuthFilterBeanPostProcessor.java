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

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.security.web.authentication.
    UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

/**
 * The username password authentication filter bean post processor
 * implementation.
 */
@Service
public class UserPassAuthFilterBeanPostProcessor implements BeanPostProcessor {

    /**
     * The username parameter.
     */
    private String usernameParameter;

    /**
     * The password parameter.
     */
    private String passwordParameter;

    @Override
    public final Object postProcessAfterInitialization(final Object bean,
        final String beanName) {
        return bean;
    }

    @Override
    public final Object postProcessBeforeInitialization(final Object bean,
        final String beanName) {
        if (bean instanceof UsernamePasswordAuthenticationFilter) {
            final UsernamePasswordAuthenticationFilter filter =
                (UsernamePasswordAuthenticationFilter) bean;
            filter.setUsernameParameter(getUsernameParameter());
            filter.setPasswordParameter(getPasswordParameter());
        }

        return bean;
    }

    /**
     * Sets the username parameter.
     *
     * @param usernameParameter the username parameter
     */
    public final void setUsernameParameter(final String usernameParameter) {
        this.usernameParameter = usernameParameter;
    }

    /**
     * Gets the username parameter.
     *
     * @return the username parameter
     */
    public final String getUsernameParameter() {
        return usernameParameter;
    }

    /**
     * Sets the password parameter.
     *
     * @param passwordParameter the password parameter
     */
    public final void setPasswordParameter(final String passwordParameter) {
        this.passwordParameter = passwordParameter;
    }

    /**
     * Gets the password parameter.
     *
     * @return the password parameter
     */
    public final String getPasswordParameter() {
        return passwordParameter;
    }

}
