import javax.swing.text.html.Option;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class QuizService {
    private Connection conn;

    public QuizService(Connection conn) {
        this.conn = conn;
    }

    //This is A function to create a new Quiz Topic. Which can be used for future implementations.
    public boolean createQuiz(String title) {
        String sql = "INSERT INTO quizzes (title) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addQuestion(int quizId, String questionText, String correctAnswer, String op1, String op2, String op3, String op4) {
        String insertQuestionSQL = "INSERT INTO questions (quiz_id, question_text, correct_answer) VALUES (?, ?, ?)";
        String insertOptionSQL = "INSERT INTO options (question_id, option_text) VALUES (?, ?)";

        try (PreparedStatement stmtQuestion = conn.prepareStatement(insertQuestionSQL, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmtOption = conn.prepareStatement(insertOptionSQL)) {

            // Insert the question into the questions table
            stmtQuestion.setInt(1, quizId);
            stmtQuestion.setString(2, questionText);
            stmtQuestion.setString(3, correctAnswer);
            stmtQuestion.executeUpdate();

            // Retrieve the generated question_id
            ResultSet generatedKeys = stmtQuestion.getGeneratedKeys();
            if (generatedKeys.next()) {
                int questionId = generatedKeys.getInt(1);

                // Insert each option into the options table
                stmtOption.setInt(1, questionId);
                stmtOption.setString(2, op1);
                stmtOption.executeUpdate();

                stmtOption.setString(2, op2);
                stmtOption.executeUpdate();

                stmtOption.setString(2, op3);
                stmtOption.executeUpdate();

                stmtOption.setString(2, op4);
                stmtOption.executeUpdate();

                return true; // Successfully added question and options
            } else {
                throw new SQLException("Failed to retrieve question_id for the new question.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Quiz> listQuizzes() {
        List<Quiz> quizzes = new ArrayList<>();
        String query = "SELECT quiz_id, title FROM quizzes";

        try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int quizId = rs.getInt("quiz_id");
                String title = rs.getString("title");
                quizzes.add(new Quiz(quizId, title));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quizzes;
    }

    public List<QuizOption> getOptionsForQuestion(int questionId) {
        List<QuizOption> options = new ArrayList<>();
        String query = "SELECT option_id, option_text FROM options WHERE question_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, questionId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int optionId = rs.getInt("option_id");
                String optionText = rs.getString("option_text");
                options.add(new QuizOption(optionId, questionId, optionText)); // Updated to QuizOption
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return options;
    }

    public void saveQuizAttempt(String username, int quizId, int score) {
        String queryUserId = "SELECT user_id FROM users WHERE username = ?";
        String insertAttempt = "INSERT INTO quiz_attempts (user_id, quiz_id, score) VALUES (?, ?, ?)";

        try (PreparedStatement stmtUserId = conn.prepareStatement(queryUserId)) {
            stmtUserId.setString(1, username);
            ResultSet rs = stmtUserId.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("user_id");

                try (PreparedStatement stmtInsert = conn.prepareStatement(insertAttempt)) {
                    stmtInsert.setInt(1, userId);
                    stmtInsert.setInt(2, quizId);
                    stmtInsert.setInt(3, score);
                    stmtInsert.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
