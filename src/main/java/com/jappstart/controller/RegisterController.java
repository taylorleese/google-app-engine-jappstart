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
package com.jappstart.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.jappstart.exception.DuplicateUserException;
import com.jappstart.form.Register;
import com.jappstart.model.auth.UserAccount;
import com.jappstart.service.auth.EnhancedUserDetailsService;

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
     * Handle the create account form submission.
     *
     * @param register the register form bean
     * @param binding the binding result
     * @return the path
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public final String submit(
        @ModelAttribute(REGISTER) @Valid final Register register,
        final BindingResult binding) {

        if (binding.hasErrors()) {
            return "create";
        }

        final UserAccount user = new UserAccount(register.getEmail());
        user.setFirstName(register.getFirstName());
        user.setLastName(register.getLastName());
        user.setPassword(passwordEncoder.encodePassword(
            register.getPassword(), user.getSalt()));

        try {
            userDetailsService.addUser(user);
        } catch (DuplicateUserException e) {
            binding.addError(new FieldError(REGISTER, "email",
                "An account already exists with this e-mail."));
            return "create";
        }

        return "redirect:/register/success";
    }

}
