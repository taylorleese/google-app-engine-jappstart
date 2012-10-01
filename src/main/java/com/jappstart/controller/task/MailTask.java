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
package com.jappstart.controller.task;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jappstart.service.auth.EnhancedUserDetailsService;
import com.jappstart.service.mail.MailService;

/**
 * The mail task controller.
 */
@Controller
@RequestMapping("/task/mail")
public class MailTask {

    /**
     * The logger.
     */
    private static final Logger LOGGER =
        LoggerFactory.getLogger(MailTask.class);

    /**
     * The mail service.
     */
    private MailService mailService;

    /**
     * The user details service.
     */
    private EnhancedUserDetailsService userDetailsService;

    /**
     * Gets the mail service.
     *
     * @return the mail service
     */
    public final MailService getMailService() {
        return mailService;
    }

    /**
     * Sets the mail service.
     *
     * @param mailService the mail service
     */
    @Autowired
    public final void setMailService(final MailService mailService) {
        this.mailService = mailService;
    }

    /**
     * Gets the user details service.
     *
     * @return the user details service
     */
    public final EnhancedUserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    /**
     * Sets the user details service.
     *
     * @param userDetailsService the user details service
     */
    @Autowired
    public final void setUserDetailsService(
        final EnhancedUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Sends the activation e-mail.
     *
     * @param username the username
     * @param locale the locale
     * @param response the servlet response
     */
    @RequestMapping(value = "/activate", method = RequestMethod.POST)
    public final void sendActivationMail(@RequestParam final String username,
        @RequestParam final String locale, final HttpServletResponse response) {
        final String logMessage =
            String.format("Activation e-mail sent for user: %s.", username);

        try {
            if (!userDetailsService.isActivationEmailSent(username)) {
                mailService.sendActivationEmail(
                    userDetailsService.getUser(username), locale);
                userDetailsService.activationEmailSent(username);
                LOGGER.info(logMessage);
            }
        } catch (MessagingException e) {
            LOGGER.warn("Messaging exception in mail task.", e);
            response.setStatus(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
