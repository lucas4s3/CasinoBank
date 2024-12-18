import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;

public class Blackjack implements Game {
    private User player;
    private JFrame frame;
    private int betAmount;
    private JPanel bettingPanel;
    private JLabel playerBalance;
    private JPanel gamePanel;
    private JPanel buttonpanel;
    private JPanel playerPanel, dealerPanel;
    private JButton hitButton, standButton, restartButton, betButton, instructionsButton;
    private JTextField betField;
    private boolean hasPlacedBet = false;
    private static final Color panelColor = new Color(53, 101, 77);


    ArrayList<Card> deck;
    private boolean isHiddenCardRevealed;
    private boolean gameOver = false;

    // dealer
    Card hiddenCard;
    ArrayList<Card> dealerHand;
    int dealerSum;
    int dealerAceCount;

    //player
    ArrayList<Card> playerHand;
    int playerSum;
    int playerAceCount;

    Blackjack(User user) {
        this.player = user;
        startGame();
        initalizeUI();

    }

    public void startGame() {
        dealerHand = new ArrayList<>();
        createDeck();
        shuffleDeck();
        dealerSum = 0;
        dealerAceCount = 0;
        hiddenCard = deck.removeLast();
        dealerSum += hiddenCard.getValue();
        dealerAceCount += hiddenCard.isAce() ? 1 : 0;

        Card card = deck.removeLast();
        dealerSum += card.getValue();
        dealerAceCount += card.isAce() ? 1 : 0;
        dealerHand.add(card);

        //player
        playerHand = new ArrayList<>();
        playerSum = 0;
        playerAceCount = 0;

        for (int i = 0; i < 2; i++) {
            card = deck.removeLast();
            playerSum += card.getValue();
            playerAceCount += card.isAce() ? 1 : 0;
            playerHand.add(card);

        }

    }


    @Override
    public void displayInstructions() {
        String instructions = "Blackjack-regler:\n" +
                "- Målet är att komma så nära 21 som möjligt utan att gå över.\n" +
                "- Ess räknas som 1 eller 11.\n" +
                "- Klädda kort (J, Q, K) är värda 10.\n" +
                "- Om du går över 21 förlorar du direkt.\n" +
                "- Sätt en insats, klicka 'Hit' för att dra kort, 'Stand' för att stanna.\n";
        JOptionPane.showMessageDialog(frame, instructions, "Blackjack Instructions", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void closeGame() {
        if (frame != null) {
            frame.dispose();
            frame = null;
        }
    }


    private void initalizeUI() {
        frame = new JFrame("Blackjack");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeGame();
                System.out.println("Spelet avslutas. Går tillbaka till huvudmenyn.");
                CasinoApp.getInstance(UserManager.getInstance()).quitGame();
            }
        });

        gamePanel = new JPanel(new GridLayout(2, 1));
        buttonpanel = new JPanel(new GridLayout(1, 4));
        playerBalance = new JLabel("Balance: " + player.getBalance() + "kr");
        bettingPanel = new JPanel(new GridLayout(1, 1));
        dealerPanel = new JPanel();
        playerPanel = new JPanel();

        dealerPanel.setBorder(BorderFactory.createTitledBorder("Dealer hand: " + (0)));
        playerPanel.setBorder(BorderFactory.createTitledBorder("Player hand : " + (0)));

        betButton = new JButton("Bet");
        betButton.setForeground(Color.RED);
        hitButton = new JButton("Hit");
        hitButton.setForeground(Color.RED);
        standButton = new JButton("Stand");
        standButton.setForeground(Color.RED);
        restartButton = new JButton("Spela igen");
        restartButton.setForeground(Color.RED);
        instructionsButton = new JButton("Instruktioner");
        instructionsButton.setForeground(Color.BLUE);
        betField = new JTextField();
        betField.setSize(10, 500);

        frame.add(bettingPanel, BorderLayout.NORTH);
        frame.add(buttonpanel, BorderLayout.SOUTH);
        frame.add(gamePanel, BorderLayout.CENTER);
        buttonpanel.add(hitButton);
        buttonpanel.add(standButton);
        buttonpanel.add(restartButton);
        buttonpanel.add(instructionsButton);


        bettingPanel.add(betField);
        bettingPanel.add(betButton);
        bettingPanel.add(playerBalance);


        gamePanel.add(playerPanel);
        gamePanel.add(dealerPanel);

        gamePanel.setBackground(panelColor);
        dealerPanel.setBackground(panelColor);
        playerPanel.setBackground(panelColor);
        buttonpanel.setBackground(panelColor);
        bettingPanel.setBackground(panelColor);

        //
        hitButton.setEnabled(false);
        standButton.setEnabled(false);


        updateHands();
        hitButton.addActionListener(e -> playerHit());
        restartButton.addActionListener(e -> restartGame());
        standButton.addActionListener(e -> stand());
        instructionsButton.addActionListener(e -> displayInstructions());
        betButton.addActionListener(e -> {
            try {
                betAmount = Integer.parseInt(betField.getText());

                if (betAmount < 5) {
                    JOptionPane.showMessageDialog(frame, "Insatsen måste vara mer än 5");
                    return;
                }

                if (betAmount > player.getBalance()) {
                    JOptionPane.showMessageDialog(frame, "Du har inte tillräckligt med pengar för att satsa så mycket.");
                    return;
                }

                player.setBalance(player.getBalance() - betAmount);
                playerBalance.setText("Saldo: " + player.getBalance());
                JOptionPane.showMessageDialog(frame, "Du satsade " + betAmount + "kr");
                hasPlacedBet = true;
                hitButton.setEnabled(true);
                standButton.setEnabled(true);

                updateHands();
                playerPanel.setBorder(BorderFactory.createTitledBorder("Spelarens hand: " + playerSum));
                dealerPanel.setBorder(BorderFactory.createTitledBorder("Dealer hand: " + (dealerSum - hiddenCard.getValue())));


            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Ange ett giltigt nummer.");
            }
        });


        frame.setVisible(true);
    }

    private void restartGame() {
        gameOver = false;
        hasPlacedBet = false;
        startGame();
        isHiddenCardRevealed = false;
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        updateHands();
        resetScores();

    }

    private void playerHit() {
        if (gameOver) {
            return;
        }
        Card card = deck.removeLast();
        playerSum += card.getValue();
        playerAceCount += card.isAce() ? 1 : 0;
        playerHand.add(card);
        removePlayerAce();
        updateHands();
        playerPanel.setBorder(BorderFactory.createTitledBorder("Player hand: " + playerSum));

        if (playerSum > 21) {
            JOptionPane.showMessageDialog(frame, "Du förlora, dealern vann.");
            gameOver = true;
            hitButton.setEnabled(false);
            standButton.setEnabled(false);
        }
    }

    private void stand() {
        if (gameOver) {
            return;
        }

        if (!isHiddenCardRevealed) {
            dealerHand.add(hiddenCard);
            removeDealerAce();
            isHiddenCardRevealed = true;
        }

        while (dealerSum < 17) {
            Card card = deck.removeLast();
            dealerSum += card.getValue();
            dealerAceCount += card.isAce() ? 1 : 0;
            dealerHand.add(card);
            removeDealerAce();
        }

        updateHands();
        playerPanel.setBorder(BorderFactory.createTitledBorder("Player hand: " + playerSum));
        dealerPanel.setBorder(BorderFactory.createTitledBorder("Dealer hand: " + dealerSum));


        if (dealerSum > 21 || playerSum > dealerSum) {
            int winnings = betAmount * 2;
            player.setBalance(player.getBalance() + winnings);
            playerBalance.setText("Balance: " + player.getBalance());
            playerPanel.setBorder(BorderFactory.createTitledBorder("Du vann! " + winnings + "kr"));
        } else if (dealerSum == playerSum) {
            betAmount = Integer.parseInt(betField.getText());
            player.setBalance(player.getBalance() + betAmount);
            playerPanel.setBorder(BorderFactory.createTitledBorder("Det blev lika!"));
        } else
            playerPanel.setBorder(BorderFactory.createTitledBorder("Du förlora!"));
        gameOver = true;
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        endGame();
    }

    private void updateHands() {
        dealerPanel.removeAll();
        playerPanel.removeAll();

        if (hasPlacedBet) {
            for (Card card : dealerHand) {
                addCardToPanel(card, dealerPanel);
            }
            for (Card card : playerHand) {
                addCardToPanel(card, playerPanel);
            }
        }

        if (!gameOver) {
            if (!isHiddenCardRevealed) {
                addCardToPanel(new Card("Dolt", ""), dealerPanel);
            } else {
                addCardToPanel(hiddenCard, dealerPanel);
            }
        }


        dealerPanel.revalidate();
        dealerPanel.repaint();
        playerPanel.revalidate();
        playerPanel.repaint();
    }


    private void resetScores() {
        playerPanel.setBorder(BorderFactory.createTitledBorder("Spelarens hand: 0"));
        dealerPanel.setBorder(BorderFactory.createTitledBorder("Dealer hand: 0"));
    }

    private void endGame() {
        gameOver = true;
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        updateHands();
    }


    private void addCardToPanel(Card card, JPanel panel) {
        JLabel cardLabel = new JLabel(card.toString());
        cardLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cardLabel.setOpaque(true);

        if (card.suit.equals("♦") || card.suit.equals("♥")) {
            cardLabel.setForeground(Color.RED);
        } else {
            cardLabel.setForeground(Color.BLACK);
        }
        cardLabel.setBackground(Color.WHITE);
        cardLabel.setPreferredSize(new Dimension(60, 90));
        cardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(cardLabel);
    }


    private class Card {
        private String value;
        private String suit;

        Card(String value, String suit) {
            this.value = value;
            this.suit = suit;
        }

        public String toString() {
            return value + "-" + suit;
        }

        public int getValue() {
            if ("AJQK".contains(value)) {
                if (value.equals("A")) {
                    return 11;
                }
                return 10;
            }

            return Integer.parseInt(value);
        }

        public boolean isAce() {
            return value.equalsIgnoreCase("A");
        }
    }


    private void createDeck() {
        deck = new ArrayList<>();
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] suits = {"♣", "♦", "♥", "♠"};
        for (int i = 0; i < suits.length; i++) {
            for (int j = 0; j < values.length; j++) {
                Card card = new Card(values[j], suits[i]);
                deck.add(card);
            }
        }
    }

    private void shuffleDeck() {
        Collections.shuffle(deck);
    }

    private int removePlayerAce() {
        while (playerSum > 21 && playerAceCount > 0) {
            playerSum -= 10;
            playerAceCount -= 1;
        }
        return playerSum;
    }

    private int removeDealerAce() {
        while (dealerSum > 21 && dealerAceCount > 0) {
            dealerSum -= 10;
            dealerAceCount -= 1;
        }
        return dealerSum;
    }


}
