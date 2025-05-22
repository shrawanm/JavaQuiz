package javaCoursework;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class QuestionList {
	 QuestionList() {
	    // Constructor implementation
	}
    private static final String URL = "jdbc:mysql://localhost:3306/coursework";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    public static List<Question> loadQuestionsFromDatabase(String difficultyLevel) {
        List<Question> questions = new ArrayList<>();
        
        // SQL query to fetch questions based on difficulty level
        String query = "SELECT * FROM questions WHERE DifficultyLevel = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, difficultyLevel);  
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("QuestionID");
                String text = rs.getString("QuestionText");
                String option1 = rs.getString("Option1");
                String option2 = rs.getString("Option2");
                String option3 = rs.getString("Option3");
                String option4 = rs.getString("Option4");
                String correctAnswer = rs.getString("CorrectAnswer");
                String category = rs.getString("Category");
                
                // Create new question object and add it to the list
                Question question = new Question(id, text, option1, option2, option3, option4, correctAnswer, difficultyLevel, category);
                questions.add(question);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
                return getRandomQuestions(questions, 5);
    }
    
    private static List<Question> getRandomQuestions(List<Question> allQuestions, int count) {
        List<Question> selectedQuestions = new ArrayList<>();
        Random random = new Random();
        
        // Randomly select count questions from the list
        while (selectedQuestions.size() < count && !allQuestions.isEmpty()) {
            int index = random.nextInt(allQuestions.size());
            selectedQuestions.add(allQuestions.remove(index));
        }
        return selectedQuestions;
    }
}
