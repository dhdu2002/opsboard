package com.opsboard.auth.store;

import com.opsboard.auth.model.UserAccount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserStoreTest {

    @Test
    void authenticate_returnsAdminUser_whenCredentialsMatch() {
        UserAccount user = UserStore.authenticate("admin", "admin1234!");

        assertNotNull(user);
        assertEquals("ADMIN", user.getRole());
    }

    @Test
    void authenticate_returnsNull_whenWrongPassword() {
        assertNull(UserStore.authenticate("admin", "wrong"));
    }

    @Test
    void authenticate_returnsNull_whenUnknownUser() {
        assertNull(UserStore.authenticate("nobody", "1234"));
    }
}
