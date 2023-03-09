package com.guluev.cloudstorage.auth;

import com.guluev.cloudstorage.entity.User;

public interface IAuthorizationFacade {
    User getAuthenticationUser();

    long getCurrentUserId();
}
