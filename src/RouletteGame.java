/*public class RouletteGame implements Game {
    public static final int MIN_BET = 5;
    public static final int MAX_BET = 1000;

    @Override
    public void startGame() {
        System.out.println("Starting Roulette game...");
    }

    @Override
    public boolean validateBet(double bet) {
        return bet >= MIN_BET && bet <= MAX_BET;
    }

    @Override
    public double calculateWinnings() {
        // Roulette-specifik logik
        return Math.random() * MAX_BET;
    }

    public int spinWheel() {
        return (int) (Math.random() * 36); // 0-35, roulette-hjul
    }
}

 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class RouletteGame extends JFrame {
    private JLabel balanceLabel; // Visar spelarens nuvarande balans
    private JLabel resultLabel; // Visar resultatet av den senaste spinnen
    private JTextField betField; // Fält för att mata in insatsen
    private JComboBox<String> betTypeComboBox; // Rullgardinsmeny för att välja satsningstyp
    private JButton spinButton; // Knapp för att snurra hjulet
    private int balance;  // Startbalansen för spelaren
    private RouletteWheelPanel wheelPanel; // Panel som visar roulettehjulet
    private Timer spinTimer; // Timer för att animera hjulets rotation
    private User player;

    public RouletteGame(User currentuser) {
        // Konfigurerar fönstret
        this.player = currentuser;
        this.balance = player.getBalance();
        setTitle("Roulette Game");
        setSize(500, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Skapar topp panelen
        JPanel topPanel = new JPanel(new GridLayout(2, 1));
        balanceLabel = new JLabel("Balance: $" + balance);
        resultLabel = new JLabel("Place your bet and spin the wheel!");
        topPanel.add(balanceLabel);
        topPanel.add(resultLabel);
        add(topPanel, BorderLayout.NORTH);

        // Skapar panelen med roulettehjulet
        wheelPanel = new RouletteWheelPanel();
        add(wheelPanel, BorderLayout.CENTER);

        // Skapar botten panelen för insats och kontroller
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(new JLabel("Bet Amount:"));
        betField = new JTextField(5);
        bottomPanel.add(betField);

        String[] betTypes = {"Red", "Black", "Odd", "Even", "Number"};
        betTypeComboBox = new JComboBox<>(betTypes);
        bottomPanel.add(new JLabel("Bet Type:"));
        bottomPanel.add(betTypeComboBox);

        spinButton = new JButton("Spin the Wheel");
        spinButton.addActionListener(new SpinButtonListener());
        bottomPanel.add(spinButton);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true); // Visar fönstret
    }

    private class SpinButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String betType = (String) betTypeComboBox.getSelectedItem();
            int betAmount;

            try {
                betAmount = Integer.parseInt(betField.getText());
                if (betAmount <= 0 || betAmount > balance) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid bet amount!");
                return;
            }

            spinButton.setEnabled(false); // Inaktiverar knappen under snurr

            Random random = new Random();
            int resultNumber = random.nextInt(37); // Random nummer mellan 0 och 36

            spinTimer = new Timer(50, new ActionListener() {
                private int rotations = 0;

                @Override
                public void actionPerformed(ActionEvent e) {
                    wheelPanel.rotateWheel(); // Roterar hjulet grafiskt
                    rotations++;

                    if (rotations > 50) { // Stoppar snurr efter ett antal rotationer
                        spinTimer.stop();
                        spinButton.setEnabled(true); // Aktiverar knappen igen

                        String color = wheelPanel.getColorForNumber(resultNumber);
                        checkBet(betType, betAmount, resultNumber, color);
                    }
                }
            });

            spinTimer.start(); // Startar snurr animationen
        }

        private void checkBet(String betType, int betAmount, int resultNumber, String color) {
            boolean win = false;
            if (betType.equals("Red") && color.equals("Red") ||
                    betType.equals("Black") && color.equals("Black") ||
                    betType.equals("Odd") && resultNumber % 2 != 0 && resultNumber != 0 ||
                    betType.equals("Even") && resultNumber % 2 == 0 && resultNumber != 0 ||
                    (betType.equals("Number") && betField.getText().equals(String.valueOf(resultNumber)))) {
                win = true;
            }

            if (win) {
                int payout = betType.equals("Number") ? betAmount * 35 : betAmount * 2;
                balance += payout;
                resultLabel.setText("You win! Number: " + resultNumber + " (" + color + ")");
            } else {
                balance -= betAmount;
                resultLabel.setText("You lose! Number: " + resultNumber + " (" + color + ")");
            }

            balanceLabel.setText("Balance: $" + balance);
            player.setBalance(balance);

            if (balance <= 0) {
                JOptionPane.showMessageDialog(RouletteGame.this, "Game Over! You're out of money.");
                System.exit(0);
            }
        }
    }

    private class RouletteWheelPanel extends JPanel {
        private int currentAngle = 0; // Håller reda på den nuvarande vinkeln för hjulet

        public RouletteWheelPanel() {
            setPreferredSize(new Dimension(400, 400)); // Sätter panelstorleken
        }

        public void rotateWheel() {
            currentAngle = (currentAngle + 15) % 360; // Ökar vinkeln för att simulera rotation
            repaint(); // Uppdaterar panelen grafiskt
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

            for (int i = 0; i < 37; i++) {
                g2d.setColor((i == 0) ? Color.GREEN : (i % 2 == 0) ? Color.BLACK : Color.RED);
                g2d.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2,
                        currentAngle + i * (360 / 37), 360 / 37);
            }

            g2d.setColor(Color.WHITE);
            g2d.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);

            g2d.setColor(Color.BLACK);
            g2d.fillPolygon(new int[]{centerX - 10, centerX + 10, centerX}, new int[]{10, 10, 30}, 3);
        }
    }

}


