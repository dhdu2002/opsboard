package com.opsboard.auth.service.impl;

import com.opsboard.auth.model.UserAccount;
import com.opsboard.auth.service.AuthService;
import com.opsboard.auth.store.UserStore;

public class AuthServiceImpl implements AuthService {
    @Override
    public UserAccount authenticate(String username, String password) {
        return UserStore.authenticate(username, password);
    }
}
