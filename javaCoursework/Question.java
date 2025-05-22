package javaCoursework;
public class Question {
    private int questionID;
    private String questionText;
    private String option1, option2, option3, option4;
    private String correctAnswer; 
    private String difficultyLevel; 
    private String category; 
    public Question(int questionID, String questionText, String option1, String option2, String option3, 
                    String option4, String correctAnswer, String difficultyLevel, String category) {
        this.questionID = questionID;
        this.questionText = questionText;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctAnswer = correctAnswer; 
        this.difficultyLevel = difficultyLevel; 
        this.category = category; 
    }

    public int getQuestionID() {
        return questionID;
    }
    public String getQuestionText() {
        return questionText;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public String getCorrectAnswer() {
        return correctAnswer; 
    }
    public String getDifficulty() {
        return difficultyLevel;
    }

    public String getCategory() {
        return category; 
    }
}
