package Window;

import javax.swing.*;
import java.awt.*;

public class MenuWindow extends JFrame {
    public MenuWindow() {

        setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        JPanel panel2 = new JPanel(new BorderLayout());

        panel.setLayout(null);

        Customer currentCustomer = SessionManager.getLoggedInUser();


        if (currentCustomer == null) {
            JOptionPane.showMessageDialog(this, "Ingen användare är inloggad.", "Fel", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }


        System.out.println("Inloggad användare: " + currentCustomer.getUsername());

        setTitle("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 550);
        setResizable(false);
        setFocusable(false);
        setLocationRelativeTo(null);


        JLabel welcomeLabel = new JLabel("Välkommen, " + currentCustomer.getUsername() + "!");
        welcomeLabel.setForeground(Color.BLACK); // Färg på texten
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setBounds(50, 150, 300, 40); // Sätt positionen så den syns ordentligt
        panel.add(welcomeLabel);

        JLabel balanceLabel = new JLabel("Ditt saldo: " + currentCustomer.getCurrentBalance() + " kr");
        balanceLabel.setForeground(Color.BLACK); // Sätt färgen för texten
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        balanceLabel.setBounds(50, 200, 300, 40);
        panel.add(balanceLabel);


        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MenuWindow();
    }
}
