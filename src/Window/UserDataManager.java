package Window;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserDataManager {
    private static final String FILE_PATH = "users.txt";
    private Map<String, Customer> users = new HashMap<>();

    public UserDataManager() {
        loadUsers();
    }

    // Ladda användardata från fil
    private void loadUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 3) {
                    String username = parts[0];
                    String password = parts[1];
                    int currentBalance = Integer.parseInt(parts[2]);
                    users.put(username, new Customer(username, password, currentBalance));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ingen användardata hittades. En ny fil skapas vid sparning.");
        } catch (IOException e) {
            System.out.println("Kunde inte läsa användardata: " + e.getMessage());
        }
    }

    // Hämta användare genom användarnamn
    public Customer getUserByUsername(String username) {
        return users.get(username);
    }

    // Registrera ny användare
    public boolean registerUser(String username, String password) {
        if (users.containsKey(username)) {
            return false;
        }
        users.put(username, new Customer(username, password, 0));
        saveUsers();
        return true;
    }


    public boolean loginUser(String username, String password) {
        Customer customer = users.get(username);
        return customer != null && customer.getPassword().equals(password);
    }

    private void saveUsers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Customer customer : users.values()) {
                writer.write(customer.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Kunde inte spara användardata: " + e.getMessage());
        }
    }
}
