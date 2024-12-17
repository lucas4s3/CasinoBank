import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static UserManager instance;
    private final String FILE_PATH = "users.txt";
    private Map<String, User> users = new HashMap<>();

    private UserManager() {
        loadUsers();
    }

    //Singleton
    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    private void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String username = parts[1].trim();
                    String password = parts[2].trim();
                    double balance = Double.parseDouble(parts[3].trim());
                    double depositLimit = Double.parseDouble(parts[4].trim());
                    users.put(username, new User(username, password, balance, depositLimit));
                }
            }
        } catch (FileNotFoundException e) {
            // Ingen fil än, skapas vid första saveUsers()
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        users.put(user.getUsername(), user);
        saveUsers();
    }

    public void removeUser(String username) {
        users.remove(username);
        saveUsers();
    }

    public User findUser(String username) {
        return users.get(username);
    }

    public void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (User user : users.values()) {
                writer.write(user.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Kunde inte spara användare till fil.");
        }
    }
}
