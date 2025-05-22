package javaCoursework;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AdminPanel extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPanel adminButtonsPanel;

    // Database connection details
    private String url = "jdbc:mysql://localhost:3306/coursework";
    private String dbUser = "root";
    private String dbPassword = "";

    // Constructor to setup the AdminPanel UI
    public AdminPanel() {
        // Set up the main frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 809, 718);
        contentPane = new JPanel();
        contentPane.setBorder(null);
        setContentPane(contentPane);
        contentPane.setLayout(null);
        getContentPane().setBackground(new Color(18, 18, 18));

        // --- Login Panel Components ---

        // Username label and text field
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(new Font("SansSerif", Font.BOLD, 30));
        lblUsername.setForeground(Color.WHITE);
        lblUsername.setBounds(140, 50, 200, 50);
        contentPane.add(lblUsername);

        usernameField = new JTextField();
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 25));
        usernameField.setBounds(350, 50, 300, 50);
        contentPane.add(usernameField);
        usernameField.setColumns(10);

        // Password label and password field
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(new Font("SansSerif", Font.BOLD, 30));
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setBounds(140, 150, 200, 50);
        contentPane.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 25));
        passwordField.setBounds(350, 150, 300, 50);
        contentPane.add(passwordField);

        // Login button to validate admin credentials
        JButton loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 30));
        loginButton.setBackground(new Color(33, 33, 33));
        loginButton.setForeground(Color.WHITE);
        loginButton.setOpaque(true);
        loginButton.setContentAreaFilled(true);
        loginButton.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 2, true));
        loginButton.setBounds(140, 250, 500, 100);
        contentPane.add(loginButton);

        // Login button action: validate credentials and show admin options if valid
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (validateAdminLogin(username, password)) {
                    System.out.println("Admin logged in: " + username);
                    showAdminButtons();
                } else {
                    System.out.println("Invalid username or password.");
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Back button to return to the previous GUI
        JButton backButton = new JButton("BACK");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 30));
        backButton.setBackground(new Color(33, 33, 33));
        backButton.setForeground(Color.WHITE);
        backButton.setOpaque(true);
        backButton.setContentAreaFilled(true);
        backButton.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 2, true));
        backButton.setBounds(140, 380, 500, 100);
        contentPane.add(backButton);

        // Back button action: Open the previous GUI and dispose the current frame
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GUI gui = new GUI();
                gui.setVisible(true);
                dispose();
            }
        });

        // --- Admin Buttons Panel (visible after login) ---

        adminButtonsPanel = new JPanel();
        adminButtonsPanel.setBounds(0, 0, 791, 539);
        adminButtonsPanel.setBackground(new Color(18, 18, 18));
        adminButtonsPanel.setLayout(null);
        adminButtonsPanel.setVisible(false);
        contentPane.add(adminButtonsPanel);

        // Button to view questions
        JButton viewQuestionButton = new JButton("View Question");
        viewQuestionButton.setFont(new Font("SansSerif", Font.BOLD, 30));
        viewQuestionButton.setBackground(new Color(33, 33, 33));
        viewQuestionButton.setForeground(Color.WHITE);
        viewQuestionButton.setOpaque(true);
        viewQuestionButton.setContentAreaFilled(true);
        viewQuestionButton.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 2));
        viewQuestionButton.setBounds(54, 50, 689, 100);
        adminButtonsPanel.add(viewQuestionButton);
        viewQuestionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewQuestions();
            }
        });

        // Button to add a new question
        JButton addQuestionButton = new JButton("Add Question");
        addQuestionButton.setFont(new Font("SansSerif", Font.BOLD, 30));
        addQuestionButton.setBackground(new Color(33, 33, 33));
        addQuestionButton.setForeground(Color.WHITE);
        addQuestionButton.setOpaque(true);
        addQuestionButton.setContentAreaFilled(true);
        addQuestionButton.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 2));
        addQuestionButton.setBounds(54, 180, 689, 100);
        adminButtonsPanel.add(addQuestionButton);
        addQuestionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addQuestion();
            }
        });

        // Button to update an existing question
        JButton updateQuestionButton = new JButton("Update Question");
        updateQuestionButton.setFont(new Font("SansSerif", Font.BOLD, 30));
        updateQuestionButton.setBackground(new Color(33, 33, 33));
        updateQuestionButton.setForeground(Color.WHITE);
        updateQuestionButton.setOpaque(true);
        updateQuestionButton.setContentAreaFilled(true);
        updateQuestionButton.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 2));
        updateQuestionButton.setBounds(54, 310, 689, 100);
        adminButtonsPanel.add(updateQuestionButton);
        updateQuestionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateQuestion();
            }
        });

        // Button to delete a question
        JButton deleteQuestionButton = new JButton("Delete Question");
        deleteQuestionButton.setFont(new Font("SansSerif", Font.BOLD, 30));
        deleteQuestionButton.setBackground(new Color(33, 33, 33));
        deleteQuestionButton.setForeground(Color.WHITE);
        deleteQuestionButton.setOpaque(true);
        deleteQuestionButton.setContentAreaFilled(true);
        deleteQuestionButton.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 2));
        deleteQuestionButton.setBounds(140, 440, 500, 100);
        adminButtonsPanel.add(deleteQuestionButton);
        deleteQuestionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteQuestion();
            }
        });

        // Logout button to return to the login screen
        JButton logoutButton = new JButton("LogOut");
        logoutButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        logoutButton.setBackground(new Color(255, 0, 0));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setOpaque(true);
        logoutButton.setContentAreaFilled(true);
        logoutButton.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 2));
        logoutButton.setBounds(664, 494, 117, 44);
        adminButtonsPanel.add(logoutButton);
        
                // Button to view competitors' results
                JButton resultsButton = new JButton("Results");
                resultsButton.setBounds(10, 497, 120, 38);
                adminButtonsPanel.add(resultsButton);
                resultsButton.setFont(new Font("SansSerif", Font.BOLD, 12));
                resultsButton.setBackground(new Color(0, 255, 0));
                resultsButton.setForeground(Color.WHITE);
                resultsButton.setOpaque(true);
                resultsButton.setContentAreaFilled(true);
                resultsButton.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 2));
                resultsButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Create a new frame to display competitor results
                        JFrame resultFrame = new JFrame("Competitors results");
                        resultFrame.setSize(900, 600);
                        resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        resultFrame.getContentPane().setBackground(new Color(33, 33, 33));

                        String[] columnNames = {
                            "Competitor ID", "Name", "Level",
                            "Score1", "Score2", "Score3", "Score4", "Score5", "Average Score"
                        };
                        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
                        JTable resultTable = new JTable(tableModel);
                        JScrollPane scrollPane = new JScrollPane(resultTable);
                        resultFrame.getContentPane().add(scrollPane);

                        // Style the table
                        resultTable.setBackground(new Color(40, 40, 40));
                        resultTable.setForeground(Color.WHITE);
                        resultTable.setGridColor(new Color(60, 60, 60));
                        resultTable.setSelectionBackground(new Color(80, 80, 80));
                        resultTable.setSelectionForeground(Color.WHITE);
                        resultTable.getTableHeader().setForeground(Color.WHITE);
                        resultTable.getTableHeader().setBackground(new Color(50, 50, 50));

                        // Fetch competitor results from the database
                        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword);
                             Statement stmt = conn.createStatement();
                             ResultSet rs = stmt.executeQuery("SELECT * FROM competitors")) {
                            while (rs.next()) {
                                int competitorID = rs.getInt("Competitor_id");
                                String name = rs.getString("Name");
                                String level = rs.getString("Level");
                                int score1 = rs.getInt("Score1");
                                int score2 = rs.getInt("Score2");
                                int score3 = rs.getInt("Score3");
                                int score4 = rs.getInt("Score4");
                                int score5 = rs.getInt("Score5");
                                double averageScore = rs.getDouble("Average_Score");
                                tableModel.addRow(new Object[]{
                                    competitorID, name, level,
                                    score1, score2, score3, score4, score5, averageScore
                                });
                            }
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Error fetching results: " + ex.getMessage(),
                                    "Database Error", JOptionPane.ERROR_MESSAGE);
                            ex.printStackTrace();
                        }
                        resultFrame.setVisible(true);
                    }
                });
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to logout?",
                        "Exit Confirmation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );
                if (choice == JOptionPane.YES_OPTION) {
                    // Remove admin buttons and revert back to the login panel
                    contentPane.removeAll();
                    JLabel lblUsername = new JLabel("Username:");
                    lblUsername.setFont(new Font("SansSerif", Font.BOLD, 30));
                    lblUsername.setBounds(140, 50, 200, 50);
                    contentPane.add(lblUsername);
                    JLabel lblPassword = new JLabel("Password:");
                    lblPassword.setFont(new Font("SansSerif", Font.BOLD, 30));
                    lblPassword.setBounds(140, 150, 200, 50);
                    contentPane.add(lblPassword);
                    contentPane.add(usernameField);
                    contentPane.add(passwordField);
                    contentPane.add(loginButton);
                    contentPane.add(backButton);
                    contentPane.revalidate();
                    contentPane.repaint();
                }
            }
        });
    }

    /**
     * Replaces the login components with the admin buttons panel.
     */
    private void showAdminButtons() {
        contentPane.removeAll();
        contentPane.add(adminButtonsPanel);
        adminButtonsPanel.setVisible(true);
        revalidate();
        repaint();
    }

    /**
     * Validates the admin login credentials against the database.
     *
     * @param username the entered username
     * @param password the entered password
     * @return true if valid, false otherwise
     */
    private boolean validateAdminLogin(String username, String password) {
        boolean isValid = false;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, dbUser, dbPassword);
            // Check if the 'admin' table exists
            String checkTableQuery = "SHOW TABLES LIKE 'admin'";
            Statement checkStmt = conn.createStatement();
            ResultSet rs = checkStmt.executeQuery(checkTableQuery);
            if (rs.next()) {
                String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setString(2, password);
                rs = stmt.executeQuery();
                if (rs.next()) {
                    isValid = true;
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "Admin info does not exist, enter correct username and password",
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error connecting to database: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isValid;
    }

    /**
     * Opens a new frame to view all questions from the database.
     */
    private void viewQuestions() {
        JFrame viewFrame = new JFrame("View Questions");
        viewFrame.setSize(800, 600);
        viewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewFrame.getContentPane().setBackground(new Color(33, 33, 33));

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        textArea.setBackground(new Color(50, 50, 50));
        textArea.setForeground(Color.WHITE);
        textArea.setCaretColor(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 2));
        viewFrame.getContentPane().add(scrollPane);

        // Fetch questions from the database and display them
        try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword)) {
            String query = "SELECT * FROM questions";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int questionID = rs.getInt("QuestionID");
                String questionText = rs.getString("QuestionText");
                String options = rs.getString("Option1") + ", " +
                        rs.getString("Option2") + ", " +
                        rs.getString("Option3") + ", " +
                        rs.getString("Option4");
                String correctAnswer = rs.getString("CorrectAnswer");
                String difficultyLevel = rs.getString("DifficultyLevel");
                String category = rs.getString("Category");
                textArea.append("Question ID: " + questionID + "\n");
                textArea.append("Question: " + questionText + "\n");
                textArea.append("Options: " + options + "\n");
                textArea.append("Correct Answer: " + correctAnswer + "\n");
                textArea.append("Difficulty Level: " + difficultyLevel + "\n");
                textArea.append("Category: " + category + "\n\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        viewFrame.setVisible(true);
    }

    /**
     * Opens a new frame to add a new question to the database.
     */
    private void addQuestion() {
        JFrame addFrame = new JFrame("Add New Question");
        addFrame.setSize(500, 600);
        addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addFrame.getContentPane().setBackground(new Color(33, 33, 33));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2));
        panel.setBackground(new Color(33, 33, 33));

        // Add form components
        panel.add(createStyledLabel("Question:"));
        JTextField questionField = createStyledTextField();
        panel.add(questionField);

        panel.add(createStyledLabel("Option 1:"));
        JTextField option1Field = createStyledTextField();
        panel.add(option1Field);

        panel.add(createStyledLabel("Option 2:"));
        JTextField option2Field = createStyledTextField();
        panel.add(option2Field);

        panel.add(createStyledLabel("Option 3:"));
        JTextField option3Field = createStyledTextField();
        panel.add(option3Field);

        panel.add(createStyledLabel("Option 4:"));
        JTextField option4Field = createStyledTextField();
        panel.add(option4Field);

        panel.add(createStyledLabel("Correct Answer:"));
        JTextField correctAnswerField = createStyledTextField();
        panel.add(correctAnswerField);

        panel.add(createStyledLabel("Difficulty Level:"));
        String[] difficultyLevels = {"Beginner", "Intermediate", "Advanced"};
        @SuppressWarnings("rawtypes")
        JComboBox difficultyLevelComboBox = createStyledComboBox(difficultyLevels);
        panel.add(difficultyLevelComboBox);

        panel.add(createStyledLabel("Category:"));
        JTextField categoryField = createStyledTextField();
        panel.add(categoryField);

        // Button to submit the new question
        JButton submitButton = createStyledButton("Add Question");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String questionText = questionField.getText();
                String option1 = option1Field.getText();
                String option2 = option2Field.getText();
                String option3 = option3Field.getText();
                String option4 = option4Field.getText();
                String correctAnswer = correctAnswerField.getText();
                String difficultyLevel = (String) difficultyLevelComboBox.getSelectedItem();
                String category = categoryField.getText();

                // Validate that all fields are filled
                if (questionText.isEmpty() || option1.isEmpty() || option2.isEmpty() ||
                        option3.isEmpty() || option4.isEmpty() || correctAnswer.isEmpty() ||
                        difficultyLevel.isEmpty() || category.isEmpty()) {
                    JOptionPane.showMessageDialog(addFrame, "Please fill in all fields",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Insert the new question into the database
                    try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword)) {
                        String query = "INSERT INTO questions (QuestionText, Option1, Option2, Option3, Option4, CorrectAnswer, DifficultyLevel, Category) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                        try (PreparedStatement stmt = conn.prepareStatement(query)) {
                            stmt.setString(1, questionText);
                            stmt.setString(2, option1);
                            stmt.setString(3, option2);
                            stmt.setString(4, option3);
                            stmt.setString(5, option4);
                            stmt.setString(6, correctAnswer);
                            stmt.setString(7, difficultyLevel);
                            stmt.setString(8, category);
                            stmt.executeUpdate();
                            JOptionPane.showMessageDialog(addFrame, "Question added successfully!");
                            addFrame.dispose();
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(addFrame, "Error adding question: " + ex.getMessage(),
                                "Database Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            }
        });
        panel.add(submitButton);

        addFrame.getContentPane().add(panel);
        addFrame.setVisible(true);
    }

    /**
     * Helper method to create a styled JLabel.
     */
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        return label;
    }

    /**
     * Helper method to create a styled JTextField.
     */
    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setBackground(new Color(50, 50, 50));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80)));
        return field;
    }

    /**
     * Helper method to create a styled JComboBox.
     */
    @SuppressWarnings("rawtypes")
    private JComboBox createStyledComboBox(String[] items) {
        JComboBox comboBox = new JComboBox<>(items);
        comboBox.setBackground(new Color(50, 50, 50));
        comboBox.setForeground(Color.WHITE);
        return comboBox;
    }

    /**
     * Helper method to create a styled JButton.
     */
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setBackground(new Color(33, 33, 33));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 2));
        return button;
    }

    /**
     * Opens a new frame to update an existing question.
     */
    private void updateQuestion() {
        JFrame updateFrame = new JFrame("Update Question");
        updateFrame.setSize(500, 600);
        updateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updateFrame.getContentPane().setBackground(new Color(44, 44, 44));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 2));
        panel.setBackground(new Color(44, 44, 44));

        // Input field for the question ID to update
        JLabel questionIDLabel = new JLabel("Enter Question ID to Update:");
        questionIDLabel.setForeground(Color.WHITE);
        panel.add(questionIDLabel);
        JTextField questionIDField = new JTextField();
        panel.add(questionIDField);

        // New question details fields
        JLabel questionLabel = new JLabel("New Question:");
        questionLabel.setForeground(Color.WHITE);
        panel.add(questionLabel);
        JTextField questionField = new JTextField();
        panel.add(questionField);

        JLabel option1Label = new JLabel("Option 1:");
        option1Label.setForeground(Color.WHITE);
        panel.add(option1Label);
        JTextField option1Field = new JTextField();
        panel.add(option1Field);

        JLabel option2Label = new JLabel("Option 2:");
        option2Label.setForeground(Color.WHITE);
        panel.add(option2Label);
        JTextField option2Field = new JTextField();
        panel.add(option2Field);

        JLabel option3Label = new JLabel("Option 3:");
        option3Label.setForeground(Color.WHITE);
        panel.add(option3Label);
        JTextField option3Field = new JTextField();
        panel.add(option3Field);

        JLabel option4Label = new JLabel("Option 4:");
        option4Label.setForeground(Color.WHITE);
        panel.add(option4Label);
        JTextField option4Field = new JTextField();
        panel.add(option4Field);

        JLabel correctAnswerLabel = new JLabel("Correct Answer:");
        correctAnswerLabel.setForeground(Color.WHITE);
        panel.add(correctAnswerLabel);
        JTextField correctAnswerField = new JTextField();
        panel.add(correctAnswerField);

        JLabel difficultyLabel = new JLabel("Difficulty Level:");
        difficultyLabel.setForeground(Color.WHITE);
        panel.add(difficultyLabel);
        String[] difficultyLevels = {"Beginner", "Intermediate", "Advanced"};
        @SuppressWarnings("rawtypes")
        JComboBox difficultyLevelComboBox = new JComboBox<>(difficultyLevels);
        panel.add(difficultyLevelComboBox);

        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setForeground(Color.WHITE);
        panel.add(categoryLabel);
        JTextField categoryField = new JTextField();
        panel.add(categoryField);

        // Button to submit the update
        JButton submitButton = new JButton("Update Question");
        submitButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        submitButton.setBackground(new Color(60, 60, 60));
        submitButton.setForeground(Color.WHITE);
        submitButton.setOpaque(true);
        submitButton.setContentAreaFilled(true);
        submitButton.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 2));
        panel.add(submitButton);

        updateFrame.getContentPane().add(panel);
        updateFrame.setVisible(true);

        // Action to perform when updating the question
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String questionID = questionIDField.getText();
                String questionText = questionField.getText();
                String option1 = option1Field.getText();
                String option2 = option2Field.getText();
                String option3 = option3Field.getText();
                String option4 = option4Field.getText();
                String correctAnswer = correctAnswerField.getText();
                String difficultyLevel = (String) difficultyLevelComboBox.getSelectedItem();
                String category = categoryField.getText();

                // Validate that all fields are filled
                if (questionID.isEmpty() || questionText.isEmpty() || option1.isEmpty() ||
                        option2.isEmpty() || option3.isEmpty() || option4.isEmpty() ||
                        correctAnswer.isEmpty() || difficultyLevel.isEmpty() || category.isEmpty()) {
                    JOptionPane.showMessageDialog(updateFrame, "Please fill in all fields",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Update the question in the database
                    try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword)) {
                        String checkQuery = "SELECT * FROM questions WHERE QuestionID = ?";
                        try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                            checkStmt.setInt(1, Integer.parseInt(questionID));
                            ResultSet rs = checkStmt.executeQuery();
                            if (!rs.next()) {
                                JOptionPane.showMessageDialog(updateFrame, "Question ID not found!",
                                        "Update Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                String query = "UPDATE questions SET QuestionText = ?, Option1 = ?, Option2 = ?, Option3 = ?, Option4 = ?, CorrectAnswer = ?, DifficultyLevel = ?, Category = ? WHERE QuestionID = ?";
                                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                                    stmt.setString(1, questionText);
                                    stmt.setString(2, option1);
                                    stmt.setString(3, option2);
                                    stmt.setString(4, option3);
                                    stmt.setString(5, option4);
                                    stmt.setString(6, correctAnswer);
                                    stmt.setString(7, difficultyLevel);
                                    stmt.setString(8, category);
                                    stmt.setInt(9, Integer.parseInt(questionID));
                                    stmt.executeUpdate();
                                    JOptionPane.showMessageDialog(updateFrame, "Question updated successfully!");
                                    updateFrame.dispose();
                                }
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(updateFrame, "Error updating question: " + ex.getMessage(),
                                "Database Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Opens a new frame to delete a question from the database.
     */
    private void deleteQuestion() {
        JFrame deleteFrame = new JFrame("Delete Question");
        deleteFrame.setSize(400, 200);
        deleteFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        deleteFrame.getContentPane().setBackground(new Color(33, 33, 33));

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.setBackground(new Color(33, 33, 33));

        // Input field for the question ID to delete
        JLabel idLabel = new JLabel("Enter Question ID to Delete:");
        idLabel.setForeground(Color.WHITE);
        panel.add(idLabel);

        JTextField questionIDField = new JTextField();
        panel.add(questionIDField);

        // Button to perform deletion
        JButton deleteButton = new JButton("Delete Question");
        deleteButton.setBackground(new Color(255, 69, 58));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String questionID = questionIDField.getText();
                if (questionID.isEmpty()) {
                    JOptionPane.showMessageDialog(deleteFrame, "Please enter a Question ID",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Delete the question from the database
                    try (Connection conn = DriverManager.getConnection(url, dbUser, dbPassword)) {
                        String checkQuery = "SELECT * FROM questions WHERE QuestionID = ?";
                        try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                            checkStmt.setInt(1, Integer.parseInt(questionID));
                            ResultSet rs = checkStmt.executeQuery();
                            if (!rs.next()) {
                                JOptionPane.showMessageDialog(deleteFrame, "Question ID not found!",
                                        "Delete Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                String deleteQuery = "DELETE FROM questions WHERE QuestionID = ?";
                                try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                                    deleteStmt.setInt(1, Integer.parseInt(questionID));
                                    deleteStmt.executeUpdate();
                                    JOptionPane.showMessageDialog(deleteFrame, "Question deleted successfully!");
                                    deleteFrame.dispose();
                                }
                            }
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(deleteFrame, "Error deleting question: " + ex.getMessage(),
                                "Database Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            }
        });
        panel.add(deleteButton);

        deleteFrame.getContentPane().add(panel);
        deleteFrame.setVisible(true);
    }
}
