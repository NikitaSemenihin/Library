package com.semenihin.converter.impl;

import com.semenihin.entity.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LBUserConverterTest {
    private static final String EXPECTED_CONVERTED_RESULT = "101 \"user\" test@email.com +3242543224";
    private static final Long TEST_USER_ID = 101L;
    private static final String TEST_USER_FULL_NAME = "user";
    private static final String TEST_USER_EMAIL = "test@email.com";
    private static final String TEST_USER_PHONE_NUMBER = "+3242543224";

    private LBUserConverter userConverter = new LBUserConverter();

    User testUser;

    @Before
    public void setUp() {
        userConverter = new LBUserConverter();
        testUser = new User(TEST_USER_ID, TEST_USER_FULL_NAME, TEST_USER_EMAIL, TEST_USER_PHONE_NUMBER);
    }

    @Test
    public void convertTest() {
        String result = userConverter.convert(testUser);
        assertEquals(EXPECTED_CONVERTED_RESULT, result);
    }
}