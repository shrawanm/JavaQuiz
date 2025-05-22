package javaCoursework;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;

public class UserLoginPanel extends JFrame {

    private static final long serialVersionUID = 1L;
    // The main panel that holds the content of the User Login UI.
    private JPanel contentPane;

    // The text field for entering the username.
    private JTextField usernameField;

    // The combo box for selecting the user's level.
    private JComboBox<String> levelComboBox;

    public UserLoginPanel() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);
        contentPane.setBackground(new Color(33, 33, 33)); // Dark background color

        // title label
        JLabel lblHeading = new JLabel("Player Register");
        lblHeading.setFont(new Font("SansSerif ", Font.BOLD, 40));
        lblHeading.setBounds(250, 20, 400, 50);
        lblHeading.setForeground(Color.WHITE);
        contentPane.add(lblHeading);

        JLabel lblUsername = new JLabel("Name:");
        lblUsername.setFont(new Font("SansSerif ", Font.BOLD, 30));
        lblUsername.setBounds(140, 90, 200, 50);
        lblUsername.setForeground(Color.WHITE);
        contentPane.add(lblUsername);

        usernameField = new JTextField();
        usernameField.setFont(new Font("SansSerif ", Font.PLAIN, 25));
        usernameField.setBounds(350, 90, 300, 50);
        usernameField.setBackground(new Color(44, 44, 44));
        usernameField.setForeground(Color.WHITE);
        contentPane.add(usernameField);
        usernameField.setColumns(10);

        JLabel lblLevel = new JLabel("Level:");
        lblLevel.setFont(new Font("SansSerif ", Font.BOLD, 30));
        lblLevel.setBounds(140, 170, 200, 50);
        lblLevel.setForeground(Color.WHITE);
        contentPane.add(lblLevel);

        levelComboBox = new JComboBox<>(new String[]{"Beginner", "Intermediate", "Advanced"});
        levelComboBox.setFont(new Font("SansSerif ", Font.PLAIN, 25));
        levelComboBox.setBounds(350, 170, 300, 50);
        levelComboBox.setBackground(new Color(44, 44, 44));
        levelComboBox.setForeground(Color.WHITE);
        contentPane.add(levelComboBox);

        // register button
        JButton registerButton = new JButton("REGISTER");
        registerButton.setFont(new Font("SansSerif ", Font.BOLD, 30));
        registerButton.setBounds(140, 250, 500, 80);
        registerButton.setBackground(new Color(44, 44, 44));
        registerButton.setForeground(Color.WHITE);
        contentPane.add(registerButton);

        // clear button
        JButton clearButton = new JButton("CLEAR");
        clearButton.setFont(new Font("SansSerif ", Font.BOLD, 30));
        clearButton.setBounds(140, 350, 500, 80);
        clearButton.setBackground(new Color(44, 44, 44));
        clearButton.setForeground(Color.WHITE);
        contentPane.add(clearButton);

        // back button
        JButton backButton = new JButton("BACK");
        backButton.setFont(new Font("SansSerif ", Font.BOLD, 30));
        backButton.setBounds(140, 450, 500, 80);
        backButton.setBackground(new Color(44, 44, 44));
        backButton.setForeground(Color.WHITE);
        contentPane.add(backButton);

        // register button action listener
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String level = (String) levelComboBox.getSelectedItem();
                if (username.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter your name", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (doesUsernameExist(username)) {
                    JOptionPane.showMessageDialog(null, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                registerNewUser(username, level);
                openPlayerDashboard(username, level);
                dispose();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                usernameField.setText("");
                levelComboBox.setSelectedIndex(0);
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Navigate back to the main menu (GUI)
                new GUI().setVisible(true);
                dispose();
            }
        });
    }

    // Opens the player dashboard after successful registration.
    private void openPlayerDashboard(String playerName, String playerLevel) {
        PlayerDashboardPanel dashboard = new PlayerDashboardPanel(playerName, playerLevel);
        dashboard.setVisible(true);
    }

    // Checks if the entered username already exists in the database.
    private boolean doesUsernameExist(String username) {
        String url = "jdbc:mysql://localhost:3306/coursework";
        String user = "root";
        String pass = "";
        String query = "SELECT COUNT(*) FROM Player WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    // Registers a new user in the database with the provided username and level.
    private void registerNewUser(String username, String level) {
        String url = "jdbc:mysql://localhost:3306/coursework";
        String user = "root";
        String pass = "";
        String query = "INSERT INTO Player (name, difficultyLevel) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, level);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
