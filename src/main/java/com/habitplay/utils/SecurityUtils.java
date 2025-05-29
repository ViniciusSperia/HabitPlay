package com.habitplay.utils;

import com.habitplay.user.model.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    public static void validateOwnership(User owner) {
        User currentUser = getCurrentUser();
        if (!currentUser.getId().equals(owner.getId())) {
            throw new AccessDeniedException("You are not allowed to access this resource");
        }
    }
}
