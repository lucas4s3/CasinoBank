import javax.swing.*;
import java.awt.*;
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
    private JButton hitButton, standButton, restartButton,betButton;
    private JTextField betField;
    private boolean hasPlacedBet = false;


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
        System.out.println(player);
        dealerHand = new ArrayList<>();
        createDeck();
        shuffleDeck();
        dealerSum = 0;
        dealerAceCount = 0;
        hiddenCard = deck.remove(deck.size() - 1);
        dealerSum += hiddenCard.getValue();
        dealerAceCount += hiddenCard.isAce() ? 1 : 0;

        Card card = deck.remove(deck.size() - 1);
        dealerSum += card.getValue();
        dealerAceCount += card.isAce() ? 1 : 0;
        dealerHand.add(card);

        //player
        playerHand = new ArrayList<>();
        playerSum = 0;
        playerAceCount = 0;

        for (int i = 0; i < 2; i++) {
            card = deck.remove(deck.size() - 1);
            playerSum += card.getValue();
            playerAceCount += card.isAce() ? 1 : 0;
            playerHand.add(card);
        }

    }

    @Override
    public boolean validateBet(double bet) {
        return false;
    }

    @Override
    public double calculateWinnings() {
        return 0;
    }

    private void initalizeUI(){
        frame = new JFrame("Blackjack");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,400);
        frame.setLocationRelativeTo(null);

        gamePanel = new JPanel(new GridLayout(2,1));
        buttonpanel = new JPanel(new FlowLayout());
        playerBalance = new JLabel("Balance: " + player.getBalance());
        bettingPanel = new JPanel(new GridLayout(1,1));
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
        restartButton = new JButton("Restart");
        restartButton.setForeground(Color.RED);
        betField = new JTextField();
        betField.setSize(10,500);

        frame.add(bettingPanel, BorderLayout.NORTH);
        frame.add(buttonpanel, BorderLayout.SOUTH);
        frame.add(gamePanel, BorderLayout.CENTER);
        buttonpanel.add(hitButton);
        buttonpanel.add(standButton);
        buttonpanel.add(restartButton);

        bettingPanel.add(betField);
        bettingPanel.add(betButton);
        bettingPanel.add(playerBalance);


        gamePanel.add(playerPanel);
        gamePanel.add(dealerPanel);

        gamePanel.setBackground(new Color(53, 101, 77));
        dealerPanel.setBackground(new Color(53, 101, 77));
        playerPanel.setBackground(new Color(53, 101, 77));
        buttonpanel.setBackground(new Color(53, 101, 77));
        bettingPanel.setBackground(new Color(53, 101, 77));

        //
        hitButton.setEnabled(false);
        standButton.setEnabled(false);


        updateHands();
        hitButton.addActionListener(e -> playerHit());
        restartButton.addActionListener(e -> restartGame());
        standButton.addActionListener(e -> stand());
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
                playerBalance.setText("Balance: " + player.getBalance());
                JOptionPane.showMessageDialog(frame, "Du satsade " + betAmount + "!");
                hasPlacedBet = true;
                hitButton.setEnabled(true);
                standButton.setEnabled(true);

                updateHands();
                playerPanel.setBorder(BorderFactory.createTitledBorder("Player hand: " + playerSum));



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
        updateHands();
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        updateHands();
        resetScores();

    }

    private void playerHit() {
        if (gameOver) {
            return;
        }
        Card card = deck.remove(deck.size() - 1);
        playerSum += card.getValue();
        playerAceCount += card.isAce() ? 1 : 0;
        playerHand.add(card);
        removePlayerAce();
        updateHands();
        playerPanel.setBorder(BorderFactory.createTitledBorder("Player hand : " + playerSum));



        if (playerSum > 21){
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
            Card card = deck.remove(deck.size() - 1);
            dealerSum += card.getValue();
            dealerAceCount += card.isAce() ? 1 : 0;
            dealerHand.add(card);
            removeDealerAce();
        }

        updateHands();
        playerPanel.setBorder(BorderFactory.createTitledBorder("Player hand: " + playerSum));
        dealerPanel.setBorder(BorderFactory.createTitledBorder("Dealer hand: " + dealerSum));


        if (dealerSum > 21 || playerSum > dealerSum) {
            try{
                betAmount = Integer.parseInt(betField.getText());
                int winnings = betAmount * 2;
                player.setBalance(player.getBalance() + winnings);
                playerBalance.setText("Balance: " + player.getBalance());
                playerPanel.setBorder(BorderFactory.createTitledBorder("You won! " + winnings));
                //JOptionPane.showMessageDialog(frame, "Du vann " + winnings + "!");
                endGame();

            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        } else if (dealerSum == playerSum) {
            betAmount = Integer.parseInt(betField.getText());
            player.setBalance(player.getBalance() + betAmount);
            playerPanel.setBorder(BorderFactory.createTitledBorder("its a tie! "));
          //  JOptionPane.showMessageDialog(frame, "Det vart lika");
            endGame();
        } else
            playerPanel.setBorder(BorderFactory.createTitledBorder("You lose! "));
         //   JOptionPane.showMessageDialog(frame,"Dealern vann");
        gameOver = true;
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        endGame();
    }

    private void updateHands() {
        dealerPanel.removeAll();
        playerPanel.removeAll();

        if (hasPlacedBet){
            for (Card card : dealerHand) {
                addCardToPanel(card, dealerPanel);
            }
            for (Card card : playerHand) {
                addCardToPanel(card, playerPanel);
            }
        }

        if (!gameOver) {
            if (!isHiddenCardRevealed) {
                addCardToPanel(new Card("Hidden", ""), dealerPanel); // Visa ett dolt kort
            } else {
                addCardToPanel(hiddenCard, dealerPanel); // Visa det faktiska kortet
            }
        }



        dealerPanel.revalidate();
        dealerPanel.repaint();
        playerPanel.revalidate();
        playerPanel.repaint();
    }


    private void resetScores(){
        playerPanel.setBorder(BorderFactory.createTitledBorder("Player hand: " + playerSum));
        dealerPanel.setBorder(BorderFactory.createTitledBorder("Dealer hand: " + (dealerSum - hiddenCard.getValue())));
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
                if (value.equalsIgnoreCase("A")) {
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

