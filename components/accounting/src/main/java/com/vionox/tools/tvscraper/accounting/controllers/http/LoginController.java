package com.vionox.tools.tvscraper.accounting.controllers.http;

import com.vionox.tools.tvscraper.accounting.service.contracts.IUserService;
import com.vionox.tools.tvscraper.dao.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

@Controller
public class LoginController
{
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    private static final String AUTHENTICATION_ERROR_SESSION_VAR = WebAttributes.AUTHENTICATION_EXCEPTION;
    @Autowired
    IUserService userService;
    @Autowired
    UserRepository userRepository;
    @InitBinder
    void initBinder(WebDataBinder binder) {
        binder.setAllowedFields("id", "firstName", "lastName", "email", "password", "matchingPassword", "lotNumber", "lot");
    }

    @RequestMapping(value = "/register")
    public String register() {
        return "register";
    }
    @RequestMapping(value = "/test/login")
    public String loginTest() {
        return "login";
    }

    @RequestMapping(value = "/login")
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        HttpSession session = request.getSession(false);
        Object errorMessage = null;
        if (session != null) {

            errorMessage = session.getAttribute(LoginController.AUTHENTICATION_ERROR_SESSION_VAR);
            if (errorMessage != null) {
//                errorMessage = ex.getMessage();
                modelAndView.addObject("errorMessage", errorMessage);
                session.removeAttribute(LoginController.AUTHENTICATION_ERROR_SESSION_VAR);
            }
        }
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout"; //You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }
/*

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView saveUser(@Valid @ModelAttribute UserDto userDto, BindingResult errors, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        if (!errors.hasErrors()) {
            UserEntity user = userService.registerNewUserAccount(userDto);
            model.addAttribute("user", user);
            modelAndView.setViewName("redirect:/login");
        } else {
            modelAndView.addObject("validationErrors", errors);
            modelAndView.addObject("userInput", userDto);
            modelAndView.setViewName("register.html");
        }
        return modelAndView;
    }
*/

}
