package com.duvallwifi.quickbooks.payment.accounting.util;

import com.duvallwifi.quickbooks.payment.model.user.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthorityHelper
{
    private static final Logger LOG = LoggerFactory.getLogger(AuthorityHelper.class);

    public static boolean isAnonymous()
    {
        return isAnonymous(getAuth());
    }
    public static boolean isAnonymous(Authentication auth)
    {
        return hasAuthority(auth, "ROLE_ANONYMOUS");
    }

    public static boolean hasAuthority(String authority)
    {
        return hasAuthority(getAuth(), authority);
    }
    public static boolean hasAuthority(Authentication auth, String authority)
    {
        return auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(authority));
    }
    public static boolean hasAuthority(UserEntity auth, String authority)
    {
        return auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(authority));
    }

    public static Authentication getAuth()
    {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
