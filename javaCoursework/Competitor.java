package javaCoursework;
import java.sql.*;
import java.util.Arrays;
/**
 * Represents a competitor participating in the quiz game.
 * Stores the competitor's ID, name, level, and scores.
 */
public class Competitor {
    private int competitorID;
    private String name;
    private String level;
    private int[] scores;

    public Competitor(String name, String level, int[] scores) {
        this.name = name;
        this.level = level;
        this.scores = scores;
    }

    public int getCompetitorID() {
        return competitorID;
    }
    public void setCompetitorID(int competitorID) {
        this.competitorID = competitorID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

  
    public int[] getScores() {
        return scores;
    }

  
    public void setScores(int[] scores) {
        this.scores = scores;
    }

 
    public double getOverallScore() {
        if (scores.length < 3) {
            return 5; 
        }

        Arrays.sort(scores); 
        int sum = 0;

        // Sum the middle scores (excluding the highest and lowest)
        for (int i = 1; i < scores.length - 1; i++) {
            sum += scores[i];
        }

        return (double) sum / (scores.length - 2); // Return the average of the middle scores
    }

    
    public String getFullDetails() {
        return "Competitor number " + competitorID + ", name " + name + ".\n" +
               name + " with " + level + " and has an lead overall score of " + getOverallScore() + ".";
    }

    
    public String getShortDetails() {
        return "CN " + competitorID + " (" + name + ") has an overall score of " + getOverallScore();
    }

    public void saveToDatabase() {
        String url = "jdbc:mysql://localhost:3306/coursework";
        String user = "root";
        String password = "";
        String checkQuery = "SELECT COUNT(*) FROM Competitors WHERE Name = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {

            // Check if a competitor with the same name already exists
            checkStmt.setString(1, name);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Player with this name already exists in database");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Insert the competitor's data into the database
        String query = "INSERT INTO Competitors (Name, Level, Score1, Score2, Score3, Score4, Score5, Average_Score) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, name);
            stmt.setString(2, level);
            stmt.setInt(3, scores.length > 0 ? scores[0] : 0);
            stmt.setInt(4, scores.length > 1 ? scores[1] : 0);
            stmt.setInt(5, scores.length > 2 ? scores[2] : 0);
            stmt.setInt(6, scores.length > 3 ? scores[3] : 0);
            stmt.setInt(7, scores.length > 4 ? scores[4] : 0);
            stmt.setDouble(8, getOverallScore());

            stmt.executeUpdate();
            System.out.println("Scores saved successfully!");
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                this.competitorID = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}