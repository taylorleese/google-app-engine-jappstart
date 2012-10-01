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
package com.jappstart.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.LocaleResolver;

import com.jappstart.exception.DuplicateUserException;
import com.jappstart.form.Register;
import com.jappstart.model.auth.UserAccount;
import com.jappstart.service.auth.EnhancedUserDetailsService;
import com.jappstart.service.mail.MailService;

/**
 * The registration controller.
 */
@Controller
@RequestMapping("/register")
@SessionAttributes(RegisterController.REGISTER)
public class RegisterController {

    /**
     * The register form attribute name.
     */
    protected static final String REGISTER = "register";

    /**
     * The password encoder.
     */
    private PasswordEncoder passwordEncoder;

    /**
     * The user details service.
     */
    private EnhancedUserDetailsService userDetailsService;

    /**
     * The mail service.
     */
    private MailService mailService;

    /**
     * The locale resolver.
     */
    private LocaleResolver localeResolver;

    /**
     * The message source.
     */
    private MessageSource messageSource;

    /**
     * Returns the password encoder.
     *
     * @return the password encoder
     */
    public final PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    /**
     * Sets the password encoder.
     *
     * @param passwordEncoder the password encoder
     */
    @Autowired
    public final void setPasswordEncoder(
        final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
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
     * Gets the locale resolver.
     *
     * @return the locale resolver
     */
    public final LocaleResolver getLocaleResolver() {
        return localeResolver;
    }

    /**
     * Sets the locale resolver.
     *
     * @param localeResolver the locale resolver
     */
    @Autowired
    public final void setLocaleResolver(final LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }

    /**
     * Gets the message source.
     *
     * @return the message source
     */
    public final MessageSource getMessageSource() {
        return messageSource;
    }

    /**
     * Sets the message source.
     *
     * @param messageSource the message source
     */
    @Autowired
    public final void setMessageSource(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Display the create account form.
     *
     * @param model the model map
     * @return the view name
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public final String create(final ModelMap model) {
        model.addAttribute(REGISTER, new Register());
        return "create";
    }

    /**
     * Activate the account with the given activation key.
     *
     * @param key the activation key
     * @param modelMap the model map
     * @return the view name
     */
    @RequestMapping(value = "/activate/{key}", method = RequestMethod.GET)
    public final String activate(@PathVariable("key") final String key,
        final ModelMap modelMap) {

        final boolean status = userDetailsService.activateUser(key);

        if (!status) {
            modelMap.put("error", true);
        }

        return "activate";
    }

    /**
     * Handle the create account form submission.
     *
     * @param register the register form bean
     * @param binding the binding result
     * @param request the HTTP servlet request
     * @return the path
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public final String submit(
        @ModelAttribute(REGISTER) @Valid final Register register,
        final BindingResult binding, final HttpServletRequest request) {
        final Locale locale = localeResolver.resolveLocale(request);

        if (binding.hasErrors()) {
            return "create";
        }

        final UserAccount user = new UserAccount(register.getUsername());
        user.setDisplayName(register.getDisplayName());
        user.setEmail(register.getEmail());
        user.setPassword(passwordEncoder.encodePassword(
            register.getPassword(), user.getSalt()));

        try {
            userDetailsService.addUser(user, locale);
        } catch (DuplicateUserException e) {
            binding.addError(new FieldError(REGISTER, "username",
                messageSource.getMessage("create.error.username", null,
                    locale)));
            return "create";
        }

        return "redirect:/register/success";
    }

}
