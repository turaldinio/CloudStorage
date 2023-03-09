package com.guluev.cloudstorage.auth;

import com.guluev.cloudstorage.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class IAuthenticationImpl implements IAuthorizationFacade {
    @Override
    public User getAuthenticationUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public long getCurrentUserId() {
        return getAuthenticationUser().getId();
    }
}
