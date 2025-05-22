package javaCoursework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Collections;

@SuppressWarnings("unused")
public class QuizPanel extends JFrame {
    private static final long serialVersionUID = 1L;

    // A list of questions for the quiz.
    private List<Question> questions; 

    // The index of the current question in the quiz.
    private int currentQuestionIndex = 0;

    // The player's current score.
    private int score = 0;

    // The current round number.
    private int currentRound = 1;

    // An array of round scores. Stores scores for each round
    private int[] roundScores = new int[5];

    // The player's name. Store the player name
    private String playerName;

    // The player's level. Store the player level
    private String playerLevel;

    // The label displaying the current question.
    private JLabel questionLabel;

    // The label displaying the round number.
    private JLabel roundLabel;

    // The label displaying the current question number.
    private JLabel questionNumberLabel;

    // The radio button for the first answer option.
    private JRadioButton option1;

    // The radio button for the second answer option.
    private JRadioButton option2;

    // The radio button for the third answer option.
    private JRadioButton option3;

    // The radio button for the fourth answer option.
    private JRadioButton option4;

    // A button group that holds the radio buttons for the answer options.
    private ButtonGroup optionsGroup;

    // The button that the player clicks to move to the next question.
    private JButton nextButton;

    public QuizPanel(List<Question> questions, String playerName, String playerLevel) {
        this.questions = questions;
        this.playerName = playerName;
        this.playerLevel = playerLevel;

        setTitle("Quiz");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        roundLabel = new JLabel("Round: " + currentRound);
        roundLabel.setFont(new Font("Arial", Font.BOLD, 16));
        roundLabel.setBounds(250, 10, 100, 30);
        add(roundLabel);

        questionNumberLabel = new JLabel("Question 1");
        questionNumberLabel.setFont(new Font("Arial", Font.BOLD, 14));
        questionNumberLabel.setBounds(20, 50, 150, 30);
        add(questionNumberLabel);

        questionLabel = new JLabel("", SwingConstants.LEFT);
        questionLabel.setBounds(20, 80, 550, 30);
        add(questionLabel);

        option1 = new JRadioButton();
        option2 = new JRadioButton();
        option3 = new JRadioButton();
        option4 = new JRadioButton();

        optionsGroup = new ButtonGroup();
        optionsGroup.add(option1);
        optionsGroup.add(option2);
        optionsGroup.add(option3);
        optionsGroup.add(option4);

        option1.setBounds(30, 130, 500, 25);
        option2.setBounds(30, 160, 500, 25);
        option3.setBounds(30, 190, 500, 25);
        option4.setBounds(30, 220, 500, 25);

        add(option1);
        add(option2);
        add(option3);
        add(option4);

        nextButton = new JButton("Next");
        nextButton.setBounds(450, 300, 100, 40);
        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
                nextQuestion();
            }
        });
        add(nextButton);

        displayQuestion();
    }

    // Displays the current question and its options on the screen.
    private void displayQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question q = questions.get(currentQuestionIndex);
            questionLabel.setText(q.getQuestionText());
            questionNumberLabel.setText("Question " + (currentQuestionIndex + 1));

            option1.setText(q.getOption1());
            option2.setText(q.getOption2());
            option3.setText(q.getOption3());
            option4.setText(q.getOption4());
        } else {
            showResult();
        }
    }

    // Checks if the selected answer is correct and updates the score.
    private void checkAnswer() {
        Question q = questions.get(currentQuestionIndex);
        int correctAnswerIndex = Integer.parseInt(q.getCorrectAnswer());

        // Find which option is selected
        int selectedOptionIndex = -1;
        if (option1.isSelected()) selectedOptionIndex = 1;
        if (option2.isSelected()) selectedOptionIndex = 2;
        if (option3.isSelected()) selectedOptionIndex = 3;
        if (option4.isSelected()) selectedOptionIndex = 4;

        // Compare selected option index with the correct answer index
        if (selectedOptionIndex == correctAnswerIndex) {
            score++; // Total score
            roundScores[currentRound - 1]++;
        }
    }

    // Proceeds to the next question or the next round if all questions of the current round are answered.
    private void nextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex % 5 == 0) {
            currentRound++;
            
            if (currentRound > 5) {
                showResult();
            } else {
                Collections.shuffle(questions);
                questions = questions.subList(0, 5);
                currentQuestionIndex = 0;
                roundLabel.setText("Round: " + currentRound);
                JOptionPane.showMessageDialog(this, "Starting Round " + currentRound);
            }
        }

        optionsGroup.clearSelection();
        displayQuestion();
    }

    // Displays the final results after all rounds are completed.
    private void showResult() {
        JOptionPane.showMessageDialog(this, "Quiz Finished!");
        StringBuilder resultMessage = new StringBuilder("Quiz Over!\n");

        int totalRoundsPlayed = 0;
        int totalScore = 0;

        for (int i = 0; i < roundScores.length; i++) {
            resultMessage.append("Round ").append(i + 1).append(": ").append(roundScores[i]).append(" points\n");
            totalScore += roundScores[i];
            if (roundScores[i] > 0) {
                totalRoundsPlayed++;
            }
        }

        double averageScore = (totalRoundsPlayed > 0) ? (double) totalScore / totalRoundsPlayed : 0;
        resultMessage.append("Average Score: ").append(String.format("%.2f", averageScore));

        // Saving competitors details and scores to database
        Competitor competitor = new Competitor(playerName, playerLevel, roundScores);
        competitor.saveToDatabase();
        dispose();
    }
}
