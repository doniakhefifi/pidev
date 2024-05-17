package com.example.fpidevCode;

import com.example.fpidevCode.entities.User;
import com.example.fpidevCode.interfaces.ServiceUser;

public class DashboardController {
    ServiceUser serviceUser;
    private int index = -1;
    private static User loggedInUser;
    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }
}
