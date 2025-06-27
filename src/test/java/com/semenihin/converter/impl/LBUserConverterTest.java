package com.semenihin.converter.impl;

import com.semenihin.entity.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LBUserConverterTest {
    public LBUserConverter userConverter = new LBUserConverter();

    User user = new User(101L, "user", "test@email.com", "+3242543224");

    @Test
    public void convertTest() {
        assertEquals(String.format("%d \"%s\" %s %s",
                user.getId(), user.getFullName(), user.getEmail(), user.getPhoneNumber()),userConverter.convert(user));
    }
}