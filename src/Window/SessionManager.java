package Window;

public class SessionManager {
    private static Customer loggedInUser;

    public static Customer getLoggedInUser() {
        return loggedInUser;
    }

    public static void setLoggedInUser(Customer user) {
        loggedInUser = user;
    }

}
