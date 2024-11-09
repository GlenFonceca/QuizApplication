import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DashboardPanel extends JPanel {
    private Connection con;  // Database connection

    // Labels to display the statistics
    private JLabel usersCountLabel;
    private JLabel questionsCountLabel;
    private JLabel quizzesCountLabel;
    private JLabel attemptsCountLabel;

    public DashboardPanel(Connection con) {
        this.con = con;

        // Set panel properties
        //setLayout(new GridLayout(5, 1, 10, 20));  // 5 rows, 1 column, with padding
        setLayout(null);
        setBackground(Color.WHITE);
        setBounds(200, 0, 800, 700);  // Set the size of the panel

        JLabel dashboardImg = new JLabel();
        dashboardImg.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("images/dashboard.png")).getImage().getScaledInstance(800, 700, Image.SCALE_SMOOTH))); // Set your top icon
        dashboardImg.setBounds(0, 0, 800, 700);
        add(dashboardImg);


//        // Initialize labels for displaying statistics
//        usersCountLabel = createLabel("Number of Users: ");
//        questionsCountLabel = createLabel("Number of Questions: ");
//        quizzesCountLabel = createLabel("Number of Quiz Topics: ");
//        attemptsCountLabel = createLabel("Number of Quiz Attempts: ");
//
//        // Add labels to the panel
//        add(usersCountLabel);
//        add(questionsCountLabel);
//        add(quizzesCountLabel);
//        add(attemptsCountLabel);

        // Fetch data from database and update labels
        //updateStatistics();
    }

//    // Helper method to create a JLabel with custom properties
//    private JLabel createLabel(String text) {
//        JLabel label = new JLabel(text);
//        label.setFont(new Font("Arial", Font.PLAIN, 18));
//        label.setForeground(Color.BLACK);
//        return label;
//    }

//    // Method to update the statistics by querying the database
//    private void updateStatistics() {
//        try {
//            // Query to get the number of users
//            String usersQuery = "SELECT COUNT(*) FROM users";
//            Statement stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery(usersQuery);
//            if (rs.next()) {
//                int userCount = rs.getInt(1);
//                usersCountLabel.setText("Number of Users: " + userCount);
//            }
//
//            // Query to get the number of questions
//            String questionsQuery = "SELECT COUNT(*) FROM questions";
//            rs = stmt.executeQuery(questionsQuery);
//            if (rs.next()) {
//                int questionCount = rs.getInt(1);
//                questionsCountLabel.setText("Number of Questions: " + questionCount);
//            }
//
//            // Query to get the number of quizzes (topics)
//            String quizzesQuery = "SELECT COUNT(*) FROM quizzes";
//            rs = stmt.executeQuery(quizzesQuery);
//            if (rs.next()) {
//                int quizCount = rs.getInt(1);
//                quizzesCountLabel.setText("Number of Quiz Topics: " + quizCount);
//            }
//
//            // Query to get the number of quiz attempts
//            String attemptsQuery = "SELECT COUNT(*) FROM quiz_attempts";
//            rs = stmt.executeQuery(attemptsQuery);
//            if (rs.next()) {
//                int attemptCount = rs.getInt(1);
//                attemptsCountLabel.setText("Number of Quiz Attempts: " + attemptCount);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}




