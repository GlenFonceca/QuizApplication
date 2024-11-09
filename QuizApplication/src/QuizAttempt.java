import java.util.Date;

public class QuizAttempt {
    private String quizTitle;
    private int score;
    private Date attemptDate;

    public QuizAttempt(String quizTitle, int score, Date attemptDate) {
        this.quizTitle = quizTitle;
        this.score = score;
        this.attemptDate = attemptDate;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public int getScore() {
        return score;
    }

    public Date getAttemptDate() {
        return attemptDate;
    }

    @Override
    public String toString() {
        return "Quiz: " + quizTitle + " - Score: " + score + " - Date: " + attemptDate; // Display format in the list
    }
}
