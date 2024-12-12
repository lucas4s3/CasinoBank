package Window;

import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JFrame {
    private UserDataManager userDataManager;

    public LoginWindow() {
        this.userDataManager = new UserDataManager();
        setTitle("Login");
        setSize(400, 400);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JLabel backgroundLabel = new JLabel(new ImageIcon("src/Window/pictures/Designer.jpeg"));
        backgroundLabel.setLayout(new BorderLayout());
        setContentPane(backgroundLabel);

        JPanel fieldPanel = new JPanel();
        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.Y_AXIS));
        fieldPanel.setOpaque(false);
        fieldPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));

        fieldPanel.add(usernameLabel);
        fieldPanel.add(usernameField);
        fieldPanel.add(Box.createVerticalStrut(10));
        fieldPanel.add(passwordLabel);
        fieldPanel.add(passwordField);
        fieldPanel.add(Box.createVerticalStrut(10));

        backgroundLabel.add(fieldPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        JButton loginButton = new JButton("Logga in");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(Color.WHITE);
        loginButton.setForeground(Color.BLACK);
        loginButton.setPreferredSize(new Dimension(150, 30));

        JButton backButton = new JButton("Tillbaka");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        backButton.setPreferredSize(new Dimension(150, 30));

        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Alla fält måste fyllas i.", "Felmeddelande", JOptionPane.ERROR_MESSAGE);
                return;
            }


            Customer customer = userDataManager.getUserByUsername(username);
            if (customer != null && customer.getPassword().equals(password)) {

                SessionManager.setLoggedInUser(customer);

                JOptionPane.showMessageDialog(this, "Inloggning lyckades!");

                setVisible(false);
                new MenuWindow();
            } else {
                JOptionPane.showMessageDialog(this, "Felaktigt användarnamn eller lösenord.", "Felmeddelande", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public static void main(String[] args) {
        LoginWindow loginWindow = new LoginWindow();
    }

}
