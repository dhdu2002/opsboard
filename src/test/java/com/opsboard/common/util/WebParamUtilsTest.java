package com.opsboard.common.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class WebParamUtilsTest {

    @Test
    void parseLong_returnsDefault_whenBlankOrInvalid() {
        assertEquals(7L, WebParamUtils.parseLong("", 7L));
        assertEquals(7L, WebParamUtils.parseLong("abc", 7L));
    }

    @Test
    void parseLong_returnsParsedValue_whenValidNumber() {
        assertEquals(123L, WebParamUtils.parseLong("123", 7L));
    }

    @Test
    void parseNullableLong_returnsNull_whenBlankOrInvalid() {
        assertNull(WebParamUtils.parseNullableLong(""));
        assertNull(WebParamUtils.parseNullableLong("x1"));
    }

    @Test
    void text_trimsAndNullSafe() {
        assertEquals("hello", WebParamUtils.text("  hello "));
        assertEquals("", WebParamUtils.text(null));
    }
}
