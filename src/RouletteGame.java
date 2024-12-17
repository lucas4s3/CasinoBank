import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;



public class RouletteGame extends JFrame implements Game {
    private User user;
    private JLabel balanceLabel;
    private JLabel resultLabel;
    private JTextField betField;
    private JComboBox<String> betTypeComboBox;
    private JButton spinButton;
    private RouletteWheelPanel wheelPanel;
    private Timer spinTimer;

    public RouletteGame(User user) {
        this.user = user;
        // Byt ut den lokala balance mot user.getBalance() nedan.

        setTitle("Roulette Game");
        setSize(500, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // DISPOSE_ON_CLOSE så du kan stänga fönstret utan att avsluta hela programmet
        setLayout(new BorderLayout());

        // Toppanel
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        balanceLabel = new JLabel("Balance: $" + user.getBalance());
        resultLabel = new JLabel("Place your bet and spin the wheel!");
        JButton instructions = new JButton("Instructions");
        topPanel.add(balanceLabel);
        topPanel.add(instructions);
        topPanel.add(resultLabel);
        add(topPanel, BorderLayout.NORTH);

        // Roulettehjulet
        wheelPanel = new RouletteWheelPanel();
        add(wheelPanel, BorderLayout.CENTER);

        // Bottenpanel för satsning
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(new JLabel("Bet Amount:"));
        betField = new JTextField(5);
        bottomPanel.add(betField);

        String[] betTypes = {"Red", "Black", "Green"};
        betTypeComboBox = new JComboBox<>(betTypes);
        bottomPanel.add(new JLabel("Bet Type:"));
        bottomPanel.add(betTypeComboBox);

        spinButton = new JButton("Spin the Wheel");

        instructions.addActionListener(e -> displayInstructions());
        spinButton.addActionListener(new SpinButtonListener());
        bottomPanel.add(spinButton);
        add(bottomPanel, BorderLayout.SOUTH);

        this.setLocationRelativeTo(null);

        // Notera att vi inte anropar setVisible(true) här. Det gör vi i startGame().
    }

    @Override
    public void startGame() {
        // När spelet ska startas, visar vi fönstret:
        setVisible(true);
    }

    @Override
    public void displayInstructions() {
        String instructions = "Roulette-instruktioner:\n" +
                "- Välj vas du vill satsa på. (Röd, Svart, grön).\n" +
                "- Ange hur mycket du vill satsa och klicka på 'Spin the Wheel'.\n" +
                "- Om du vinner läggs vinsten till ditt saldo.";
        JOptionPane.showMessageDialog(this, instructions, "Instruktioner", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void closeGame() {
        dispose();
    }

    private class SpinButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String betType = (String) betTypeComboBox.getSelectedItem();
            int betAmount;

            try {
                betAmount = Integer.parseInt(betField.getText());
                if (betAmount <= 0 || betAmount > user.getBalance()) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid bet amount!");
                return;
            }

            spinButton.setEnabled(false);
            Random random = new Random();
            int resultNumber = random.nextInt(36);

            spinTimer = new Timer(50, new ActionListener() {
                private int rotations = 0;

                @Override
                public void actionPerformed(ActionEvent e) {
                    wheelPanel.rotateWheel();
                    rotations++;

                    if (rotations > 50) {
                        spinTimer.stop();
                        spinButton.setEnabled(true);

                        String color = wheelPanel.getColorForNumber(resultNumber);
                        checkBet(betType, betAmount, resultNumber, color);
                    }
                }
            });

            spinTimer.start();
        }

        private void checkBet(String betType, int betAmount, int resultNumber, String color) {
            boolean win = false;
            if ((betType.equals("Red") && color.equals("Red")) ||
                    (betType.equals("Black") && color.equals("Black")) ||
                    (betType.equals("Odd") && resultNumber % 2 != 0 && resultNumber != 0) ||
                    (betType.equals("Even") && resultNumber % 2 == 0 && resultNumber != 0) ||
                    (betType.equals("Number") && betField.getText().equals(String.valueOf(resultNumber)))) {
                win = true;
            }

            double currentBalance = user.getBalance();

            if (win) {
                int payout = betType.equals("Number") ? betAmount * 35 : betAmount * 2;
                user.setBalance(currentBalance + payout);
                resultLabel.setText("You win! Number: " + resultNumber + " (" + color + ")");
            } else {
                user.setBalance(currentBalance - betAmount);
                resultLabel.setText("You lose! Number: " + resultNumber + " (" + color + ")");
            }

            balanceLabel.setText("Balance: $" + user.getBalance());

            if (user.getBalance() <= 0) {
                JOptionPane.showMessageDialog(RouletteGame.this, "Game Over! You're out of money.");
                dispose(); // Stänger spelet om pengarna tar slut
            }
        }
    }

    private class RouletteWheelPanel extends JPanel {
        private int currentAngle = 0;

        public RouletteWheelPanel() {
            setPreferredSize(new Dimension(400, 400));
        }

        public void rotateWheel() {
            currentAngle = (currentAngle + 10) % 360;
            repaint();
        }

        public String getColorForNumber(int number) {
            if (number == 0) return "Green";
            return (number % 2 == 0) ? "Black" : "Red";
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;
            int radius = Math.min(getWidth(), getHeight()) / 2 - 10;

            for (int i = 0; i < 36; i++) {
                g2d.setColor((i == 0) ? Color.GREEN : (i % 2 == 0) ? Color.BLACK : Color.RED);
                g2d.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2,
                        currentAngle + i * (360 / 36), 360 / 36);
            }

            g2d.setColor(Color.WHITE);
            g2d.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);

            g2d.setColor(Color.YELLOW);
            g2d.fillPolygon(new int[]{centerX - 10, centerX + 10, centerX}, new int[]{10, 10, 30}, 3);
        }
    }
}
