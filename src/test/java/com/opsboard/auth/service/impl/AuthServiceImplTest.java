package com.opsboard.auth.service.impl;

import com.opsboard.auth.model.UserAccount;
import com.opsboard.auth.service.AuthService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class AuthServiceImplTest {

    private final AuthService authService = new AuthServiceImpl();

    @Test
    void authenticate_returnsUser_whenValidCredentials() {
        UserAccount account = authService.authenticate("user", "user1234!");

        assertNotNull(account);
        assertEquals("USER", account.getRole());
    }

    @Test
    void authenticate_returnsNull_whenInvalidCredentials() {
        assertNull(authService.authenticate("user", "bad"));
    }
}
