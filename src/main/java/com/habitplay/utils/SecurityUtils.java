package com.habitplay.utils;

import com.habitplay.user.model.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("No authenticated user found.");
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof User user)) {
            throw new AccessDeniedException("Invalid user principal.");
        }

        return user;
    }

    public static void validateOwnership(User owner) {
        User currentUser = getCurrentUser();
        if (!currentUser.getId().equals(owner.getId())) {
            throw new AccessDeniedException("Access denied: user does not own this resource.");
        }
    }
}
