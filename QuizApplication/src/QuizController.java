import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizController {
    private Connection conn;

    public QuizController(Connection conn) {
        this.conn = conn;
    }

    public List<Question> getQuizQuestions(int quizId) {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT question_id, question_text, correct_answer FROM questions WHERE quiz_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, quizId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int questionId = rs.getInt("question_id");
                String questionText = rs.getString("question_text");
                String correctAnswer = rs.getString("correct_answer");
                questions.add(new Question(questionId, quizId, questionText, correctAnswer));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

}
