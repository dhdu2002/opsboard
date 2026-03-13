package com.opsboard.auth.store;

import com.opsboard.auth.model.UserAccount;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class UserStore {
    private static final Map<String, UserAccount> USERS = new ConcurrentHashMap<>();

    static {
        USERS.put("admin", new UserAccount("admin", "admin1234!", "ADMIN"));
        USERS.put("user", new UserAccount("user", "user1234!", "USER"));
    }

    private UserStore() {
    }

    public static UserAccount authenticate(String username, String password) {
        UserAccount account = USERS.get(username);
        if (account == null) {
            return null;
        }
        if (!account.getPassword().equals(password)) {
            return null;
        }
        return account;
    }
}
