package Window;

import javax.swing.*;
import java.awt.*;

public class RegisterWindow extends JFrame {
    private UserDataManager userDataManager;

    public RegisterWindow() {
        this.userDataManager = new UserDataManager();
        setTitle("Register");
        setSize(400, 400);
        setResizable(false);
        setFocusable(false);
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

        JLabel passwordLabel = new JLabel("Lösenord:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));

        JCheckBox showPasswordCheckBox = new JCheckBox("Visa lösenord");
        showPasswordCheckBox.setForeground(Color.WHITE);
        showPasswordCheckBox.setOpaque(false);
        showPasswordCheckBox.setFont(new Font("Arial", Font.PLAIN, 14));
        showPasswordCheckBox.addActionListener(e -> {
            if (showPasswordCheckBox.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        });

        fieldPanel.add(usernameLabel);
        fieldPanel.add(usernameField);
        fieldPanel.add(Box.createVerticalStrut(10));
        fieldPanel.add(passwordLabel);
        fieldPanel.add(passwordField);
        fieldPanel.add(Box.createVerticalStrut(10));
        fieldPanel.add(showPasswordCheckBox);
        fieldPanel.add(Box.createVerticalStrut(10));

        backgroundLabel.add(fieldPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        JButton registerButton = new JButton("Registrera dig");
        registerButton.setFocusable(false);
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setPreferredSize(new Dimension(200, 30));
        registerButton.setBackground(Color.WHITE);
        registerButton.setForeground(Color.BLACK);

        JButton loginButton = new JButton("Logga in");
        loginButton.setFocusable(false);
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setPreferredSize(new Dimension(200, 30));
        loginButton.setBackground(Color.WHITE);
        loginButton.setForeground(Color.BLACK);

        JButton backButton = new JButton("Tillbaka");
        backButton.setFocusable(false);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);

        registerButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Alla fält måste fyllas i.", "Felmeddelande", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (userDataManager.registerUser(username, password)) {
                JOptionPane.showMessageDialog(this, "Registrering lyckades!");
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "Användarnamnet är upptaget.", "Felmeddelande", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> setVisible(false));

        buttonPanel.add(registerButton);
        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    public static void main(String[] args) {
        new RegisterWindow();
    }
}
