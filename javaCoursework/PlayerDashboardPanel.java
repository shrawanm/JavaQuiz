package javaCoursework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PlayerDashboardPanel extends JFrame {

    private static final long serialVersionUID = 1L;

    // The name of the player.
    @SuppressWarnings("unused")
    private String playerName;

    // The level of the player.
    @SuppressWarnings("unused")
    private String playerLevel;

    public PlayerDashboardPanel(String playerName, String playerLevel) {
        this.playerName = playerName;
        this.playerLevel = playerLevel;

        setTitle("Player Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(33, 33, 33));
        JLabel titleLabel = new JLabel("Player Hub", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
        titleLabel.setBounds(200, 20, 400, 50);
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel);

        // Start Quiz Button
        JButton startQuizButton = new JButton("Start Quiz");
        startQuizButton.setFont(new Font("SansSerif ", Font.BOLD, 25));
        startQuizButton.setBounds(150, 100, 500, 70);
        startQuizButton.setBackground(new Color(44, 44, 44));
        startQuizButton.setForeground(Color.WHITE);
        add(startQuizButton);

        // High Score Button
        JButton highScoreButton = new JButton("High Score");
        highScoreButton.setFont(new Font("SansSerif", Font.BOLD, 25));
        highScoreButton.setBounds(150, 180, 500, 70);
        highScoreButton.setBackground(new Color(44, 44, 44));
        highScoreButton.setForeground(Color.WHITE);
        add(highScoreButton);

        // Player Detail Button
        JButton playerDetailButton = new JButton("Player Detail");
        playerDetailButton.setFont(new Font("SansSerif", Font.BOLD, 25));
        playerDetailButton.setBounds(150, 260, 500, 70);
        playerDetailButton.setBackground(new Color(44, 44, 44));
        playerDetailButton.setForeground(Color.WHITE);
        add(playerDetailButton);

        // Statistical Summary Button
        JButton statisticalSummaryButton = new JButton("Statistical Summary");
        statisticalSummaryButton.setFont(new Font("SansSerif", Font.BOLD, 25));
        statisticalSummaryButton.setBounds(150, 340, 500, 70);
        statisticalSummaryButton.setBackground(new Color(44, 44, 44));
        statisticalSummaryButton.setForeground(Color.WHITE);
        add(statisticalSummaryButton);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 25));
        backButton.setBounds(150, 420, 500, 70);
        backButton.setBackground(new Color(44, 44, 44));
        backButton.setForeground(Color.WHITE);
        add(backButton);

        // Exit Button
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("SansSerif", Font.BOLD, 25));
        exitButton.setBounds(150, 500, 500, 70);
        exitButton.setBackground(new Color(44, 44, 44));
        exitButton.setForeground(Color.WHITE);
        add(exitButton);
    
        // Load competitors from the database
        CompetitorList competitorList = new CompetitorList();
        competitorList.loadFromDatabase();

        // Action Listeners
        startQuizButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                List<Question> questions = QuestionList.loadQuestionsFromDatabase(playerLevel);
                if (questions.size() < 5) {
                    JOptionPane.showMessageDialog(null, "No questions available in the database", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    QuizPanel quizPanel = new QuizPanel(questions, playerName, playerLevel);
                    quizPanel.setVisible(true);
                    dispose();
                }
            }
        });

        highScoreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Competitor topPerformer = competitorList.getTopPerformer();
                if (topPerformer != null) {
                    JOptionPane.showMessageDialog(null, "Top Performer: \n" + topPerformer.getFullDetails(), "High Score", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No competitors found.", "High Score", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        playerDetailButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String playerIdInput = JOptionPane.showInputDialog(null, "Enter competitor id:", "Search Player", JOptionPane.PLAIN_MESSAGE);
                if (playerIdInput != null && !playerIdInput.isEmpty()) {
                    try {
                        int playerId = Integer.parseInt(playerIdInput);
                        Competitor competitor = competitorList.getCompetitorById(playerId);
                        if (competitor != null) {
                            JOptionPane.showMessageDialog(null, competitor.getFullDetails(), "Player Details", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Competitor not found: " + playerId, "Player Details", JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid id", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Competitor id required", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        statisticalSummaryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String summary = competitorList.generateSummaryStatistics();
                JOptionPane.showMessageDialog(null, summary, "Statistical Summary", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new UserLoginPanel().setVisible(true);
                dispose();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
