package javaCoursework;

import java.sql.*;
import java.util.*;

/**
 * The CompetitorList class manages a list of competitors loaded from a database.
 * It provides methods for loading, retrieving, displaying, and analyzing competitor data.
 */
public class CompetitorList {
	/**
	 * Constructs a new CompetitorList object.
	 * Initializes the list of competitors for the game or quiz.
	 */
	 CompetitorList() {
	    // Constructor implementation
	}
    private List<Competitor> competitors = new ArrayList<>();

    /**
     * Loads competitors from the database and populates the internal list.
     * Each competitor's details are fetched and stored as Competitor objects.
     */
    public void loadFromDatabase() {
        String url = "jdbc:mysql://localhost:3306/coursework";
        String user = "root";
        String password = "";
        String query = "SELECT * FROM Competitors";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("Fetching competitors from database.");
            while (rs.next()) {
                int id = rs.getInt("Competitor_id");
                String name = rs.getString("Name");
                String level = rs.getString("Level");
                int[] scores = {
                    rs.getInt("Score1"),
                    rs.getInt("Score2"),
                    rs.getInt("Score3"),
                    rs.getInt("Score4"),
                    rs.getInt("Score5")
                };
                // Create Competitor object
                Competitor competitor = new Competitor(name, level, scores);
                competitor.setCompetitorID(id); // Set ID from database
                competitors.add(competitor);
                System.out.println("Loaded competitor : " + competitor.getFullDetails());
            }
            System.out.println("Total competitors : " + competitors.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a competitor by their unique ID.
     * @param playerId The ID of the competitor to retrieve.
     * @return The Competitor object if found, or null if no competitor matches the ID.
     */
    public Competitor getCompetitorById(int playerId) {
        for (Competitor competitor : competitors) {
            if (competitor.getCompetitorID() == playerId) {
                return competitor;
            }
        }
        return null; 
    }

    /**
     * Returns a string containing the full details of all competitors.
     *
     * @return A formatted string with details of all competitors.
     */
    public String getAllCompetitorsDetails() {
        StringBuilder details = new StringBuilder();
        for (Competitor competitor : competitors) {
            details.append(competitor.getFullDetails()).append("\n\n");
        }
        return details.toString();
    }

    /**
     * Displays the full details of all competitors in the console.
     */
    public void displayAllCompetitors() {
        System.out.println("Competitors List:");
        for (Competitor competitor : competitors) {
            System.out.println(competitor.getFullDetails());
        }
    }

    /**
     * Finds the top performer based on the overall score.
     *
     * @return The Competitor object with the highest overall score, or null if the list is empty.
     */
    public Competitor getTopPerformer() {
        if (competitors.isEmpty()) return null;
        Competitor topPerformer = competitors.get(0);
        for (Competitor competitor : competitors) {
            if (competitor.getOverallScore() > topPerformer.getOverallScore()) {
                topPerformer = competitor;
            }
        }
        return topPerformer;
    }

    /**
     * Generates summary statistics for all competitors, including total competitors,
     * average score, and score frequencies.
     *
     * @return A formatted string containing the summary statistics.
     */
    public String generateSummaryStatistics() {
        if (competitors.isEmpty()) {
            return "No competitors available.";
        }
        Map<Integer, Integer> scoreFrequencyMap = new HashMap<>();
        int totalCompetitors = competitors.size();
        int totalScores = 0;
        int totalScoreCount = 0;
        // Data collection for statistical summary
        for (Competitor competitor : competitors) {
            for (int score : competitor.getScores()) {
                scoreFrequencyMap.put(score, scoreFrequencyMap.getOrDefault(score, 0) + 1);
                totalScores += score;
                totalScoreCount++;
            }
        }
        double averageScore = (totalScoreCount > 0) ? (double) totalScores / totalScoreCount : 0;
        StringBuilder summaryMessage = new StringBuilder();
        summaryMessage.append("Summary Statistics:\n");
        summaryMessage.append("Total Competitors: ").append(totalCompetitors).append("\n");
        summaryMessage.append("Average Score: ").append(String.format("%.2f", averageScore)).append("\n");
        summaryMessage.append("Score Frequencies:\n");
        for (Map.Entry<Integer, Integer> entry : scoreFrequencyMap.entrySet()) {
            summaryMessage.append("Score " + entry.getKey() + " appears " + entry.getValue() + " times.\n");
        }
        return summaryMessage.toString();
    }

    /**
     * Finds a competitor by their unique ID.
     * @param id The ID of the competitor to find.
     * @return The Competitor object if found, or null if no competitor matches the ID.
     */
    public Competitor findCompetitorByID(int id) {
        for (Competitor competitor : competitors) {
            if (competitor.getCompetitorID() == id) {
                return competitor;
            }
        }
        return null;
    }
}