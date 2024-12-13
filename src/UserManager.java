import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private final String FILE_PATH = "users.txt";
    private Map<String, User> users = new HashMap<>();


    public UserManager(){
        loadusers();
    }

    private void loadusers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String tempLine;
            while ((tempLine = reader.readLine())!= null){
                String[] parts = tempLine.split(",");
                if  (parts.length == 4 ){
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    int balance = Integer.parseInt(parts[2].trim());
                    int depositLimit = Integer.parseInt(parts[3].trim());
                    users.put(username, new User(username,password,balance,depositLimit));

                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public User getUser(String username){
        return users.get(username);
    }
    public boolean registerUser(String username, String password){
        if (users.containsKey(username)){
            return false;
        }
        users.put(username, new User(username,password,0,9999999));
        saveUsers();
        return true;
    }

    public User loginUser(String username, String password){
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)){
            System.out.println("Du är inloggad som: " + user.getUsername());
            return user;
        }
        System.out.println("Inloggning misslyckades");
        return null;
    }
    public void saveUsers(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH,true))){
            for (User user : users.values()) {
                writer.write(user.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Kunde inte spara användare till fil.");
        }
    }
}