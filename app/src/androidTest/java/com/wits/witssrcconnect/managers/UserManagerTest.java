package com.wits.witssrcconnect.managers;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import com.wits.witssrcconnect.R;
import com.wits.witssrcconnect.utils.UserUtils;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(AndroidJUnit4.class)
public class UserManagerTest {


    private static Context c = InstrumentationRegistry.getTargetContext();

    @BeforeClass
    public static void initUserManagerVar(){
        UserManager.initUserManager(c);
    }

    @Test
    public void initUserManager() {
        UserManager.initUserManager(c);
        assertNotNull(UserManager.SHARED_PREFERENCES);
    }

    @Test
    public void setUserLoggedIn() {
        int i = anyInt();
        UserManager.setUserLoggedIn(i, anyString());
        assertEquals(UserManager.getLoggedInUserType(), i);
    }

    @Test
    public void getCurrentlyLoggedInUsername() {
        String userName = anyString();
        UserManager.setUserLoggedIn(anyInt(), userName);
        assertEquals(userName, UserManager.getCurrentlyLoggedInUsername());
    }

    @Test
    public void getLoggedInUserTypeName() {
        UserManager.setUserLoggedIn(UserUtils.STUDENT, anyString());
        assertEquals(UserManager.getLoggedInUserTypeName(c),
                c.getResources().getString(R.string.student));
    }

    @Test
    public void getCurrentlyLoggedInStatus() {
        UserManager.setUserLoggedIn(anyInt(), anyString());
        assertTrue(UserManager.getCurrentlyLoggedInStatus());
    }

    @Test
    public void getLoggedInUserType() {
        int userType = anyInt();
        UserManager.setUserLoggedIn(userType, anyString());
        assertEquals(UserManager.getLoggedInUserType(), userType);
    }

    @Test
    public void getUserNameSurname() {
        String name = anyString();
        String surname = anyString();
        String nameSurname = String.format("%s %s", name, surname);
        UserManager.setUserNameSurname(name, surname);
        assertEquals(nameSurname, UserManager.getUserNameSurname());
    }

    @Test
    public void userLoggedOut() {
        UserManager.userLoggedOut();
        String currentlyLoggedInUsername = UserManager.getCurrentlyLoggedInUsername();
        String userTypeName = UserManager.getLoggedInUserTypeName(c);
        String userNameSurname = UserManager.getUserNameSurname();
        boolean currentlyLoggedInStatus = UserManager.getCurrentlyLoggedInStatus();
        int loggedInUserType = UserManager.getLoggedInUserType();

        assertTrue(
                currentlyLoggedInUsername.equals("") &&
                        userTypeName.equals("") &&
                        userNameSurname.equals("") &&
                        !currentlyLoggedInStatus &&
                        loggedInUserType == UserUtils.DEFAULT_USER
        );
    }
}