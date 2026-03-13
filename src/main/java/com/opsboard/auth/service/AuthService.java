package com.opsboard.auth.service;

import com.opsboard.auth.model.UserAccount;

public interface AuthService {
    UserAccount authenticate(String username, String password);
}
