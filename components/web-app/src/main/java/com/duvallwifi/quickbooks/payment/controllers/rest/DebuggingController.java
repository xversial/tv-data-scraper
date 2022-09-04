package com.duvallwifi.quickbooks.payment.controllers.rest;

import com.duvallwifi.quickbooks.payment.accounting.util.AuthorityHelper;
import com.duvallwifi.quickbooks.payment.dao.PrivilegeRepository;
import com.duvallwifi.quickbooks.payment.dao.RoleRepository;
import com.duvallwifi.quickbooks.payment.dao.UserRepository;
import com.duvallwifi.quickbooks.payment.model.user.UserEntity;
import com.duvallwifi.quickbooks.payment.model.user.Privilege;
import com.duvallwifi.quickbooks.payment.model.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.*;

@ConditionalOnExpression("${debug.controllers.enabled:true}")
@RestController
@RequestMapping("debugging")
@Transactional
public class DebuggingController
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserName(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        final SecurityContextImpl spring_security_context;
        final Authentication auth;
        spring_security_context = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        if(spring_security_context != null)
        {
            auth = spring_security_context.getAuthentication();
            if(auth != null)
            {
                return auth.getName();
            }
        }
        return "";
    }

    @GetMapping("/unactivated-error")
    public ModelAndView unactivatedMakeError() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user;
        user = userRepository.findByEmail(auth.getName());
        ModelAndView modelAndView = new ModelAndView("unactivated");
        modelAndView.addObject("message", "test");
        modelAndView.addObject("user", user);
        return modelAndView;
    }
    @GetMapping("/unactivated")
    public ModelAndView unactivatedWithModelAndView() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserEntity user;
        if (AuthorityHelper.isAnonymous(auth)) {
            user = new UserEntity(0L, "Anonymous Debugging Test User",
                    "Anonymous Debugging Test User",
                    "Anonymous", "", "-1",
                    true, false, true, null
            );
        } else {
            user = userRepository.findByEmail(auth.getName());
        }
        ModelAndView modelAndView = new ModelAndView("unactivated");
        modelAndView.addObject("message", "test");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/authorities", method = RequestMethod.GET)
    @ResponseBody
    public Object authorities(HttpServletRequest request)
    {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities();
    }

    @RequestMapping(value = "/ip", method = RequestMethod.GET)
    @ResponseBody
    public Object ip(HttpServletRequest request)
    {
        return request.getRemoteAddr();
    }

    @RequestMapping(value = "/locale", method = RequestMethod.GET)
    @ResponseBody
    public Object locale(HttpServletRequest request)
    {
        return LocaleContextHolder.getLocale();
    }

    @RequestMapping(value = "/authorities/{authority}", method = RequestMethod.GET)
    @ResponseBody
    public Object authoritiesMatch(HttpServletRequest request, @PathVariable(value="authority") String authority)
    {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        final boolean role = authorities.stream().anyMatch(a -> a.getAuthority().equals(authority));
        return role;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public Object currentUser(HttpServletRequest request) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

//        HttpSession session = request.getSession(true);
//        final SecurityContextImpl spring_security_context = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
//        final Authentication auth = spring_security_context.getAuthentication();
//        UserEntity user = (UserEntity) auth.getPrincipal();

        return auth.getPrincipal();
    }

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    @ResponseBody
    public Object currentUserRoles(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        final SecurityContextImpl spring_security_context = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        final Authentication auth = spring_security_context.getAuthentication();
        Object user = auth.getPrincipal();
        Collection<Role> roles = null;
        if(user instanceof org.springframework.security.core.userdetails.User){
            user = (org.springframework.security.core.userdetails.User) user;
//            roles = ((org.springframework.security.core.userdetails.User) user).get();
        } else if (user instanceof UserEntity){
            user = (UserEntity) user;
            roles = ((UserEntity) user).getRoles();
        } else {
            return null;
        }
//        UserEntity user = (UserEntity) auth.getPrincipal();
        Map<String, Object> map = new HashMap<String, Object>();
        if(roles == null){
            return null;
        }
        for (Role role:roles)
        {
            final Collection<Privilege> privileges = role.getPrivileges();
            final ArrayList map2 = new ArrayList();
            for(Privilege priv: privileges){
                map2.add(priv.getName());
            }
            map.put(role.getName(), map2);
        }

        return map;
    }

    @RequestMapping(value = "/session", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> session(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Map<String, Object> map = new HashMap<String, Object>();
//        ObjectMapper mapper = new ObjectMapper();
//        ObjectNode rootNode = mapper.createObjectNode();
        final Enumeration<String> e = session.getAttributeNames();
        while (e.hasMoreElements()){
            String key = e.nextElement();
            map.put(key, session.getAttribute(key));
        }
        return new ResponseEntity<Object>(map, HttpStatus.OK);
    }
/*
    @GetMapping("/user/current")
    UserEntity userRoles(Authentication authentication) {

        return userRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }
    @GetMapping("/user/roles")
    UserEntity userRoles(Authentication authentication) {

        return repository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }*/
}
