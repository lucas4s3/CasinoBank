import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TopListUI extends JPanel implements ListCellRenderer<User> {
    private JLabel nameLabel;
    private JLabel statsLabel;
    private static final Color panelColor = new Color(53, 101, 77);

    public TopListUI() {
        setLayout(new BorderLayout());
        nameLabel = new JLabel();
        statsLabel = new JLabel();

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.add(nameLabel);
        infoPanel.add(statsLabel);
        infoPanel.setOpaque(false);

        add(infoPanel, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setOpaque(true);
    }

    public void showTopListWindow(ArrayList<User> userList) {
        JList<User> playerJList = new JList<>(userList.toArray(new User[0]));
        playerJList.setCellRenderer(new TopListUI());
        playerJList.setBackground(panelColor);

        JScrollPane scrollPane = new JScrollPane(playerJList);
        scrollPane.getViewport().setBackground(panelColor);

        JButton closeButton = new JButton("Stäng");
        closeButton.addActionListener(e -> SwingUtilities.getWindowAncestor(closeButton).dispose());

        JFrame rankingsFrame = new JFrame("Topplista");
        rankingsFrame.setSize(300,550 );
        rankingsFrame.setLayout(new BorderLayout());
        rankingsFrame.add(scrollPane, BorderLayout.CENTER);
        rankingsFrame.add(closeButton, BorderLayout.SOUTH);
        rankingsFrame.getContentPane().setBackground(panelColor);
        rankingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        rankingsFrame.setVisible(true);
        rankingsFrame.setLocationRelativeTo(null);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends User> list, User user, int index, boolean isSelected, boolean cellHasFocus) {
        nameLabel.setText((index + 1) + ". " + user.getUsername());
        statsLabel.setText("Saldo: " + user.getBalance() + " SEK");

        setBackground(panelColor);
        setForeground(list.getForeground());

        setOpaque(true);
        return this;
    }

}
