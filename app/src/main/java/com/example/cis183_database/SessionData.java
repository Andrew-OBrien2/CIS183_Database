package com.example.cis183_database;

public class SessionData
{
    //These need to be static becuase I want to know who is logged in on all of the different
    //intents that I have.  I do not want to worry about passing this information from intent to intent
    //static ensures that once this is set once it will not be overwriten
    //to access a static class function you use the following syntax:
    //SessionData.getLoggedInUserId()
    private static User loggedInUser;

    public static User getLoggedInUser()
    {
        return loggedInUser;
    }

    public static void setLoggedInUser(User u)
    {
        loggedInUser = u;
    }
}
