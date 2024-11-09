import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserAttemptsFrame extends JFrame {
    private JPanel contentPanel; // Panel to hold all components
    private JPanel attemptsPanel; // Panel to hold attempt details
    private Connection conn; // Database connection
    private JButton goHomebtn;
    Color Mainpurple = new Color(167, 41, 245);
    Color textColor = new Color(0, 14, 81);
    public UserAttemptsFrame(String username, Connection connection) {
        this.conn = connection; // Set the database connection
        setTitle("QuizCentral");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Create the content panel and set its layout to null for absolute positioning
        contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setBackground(Color.white);
        setContentPane(contentPanel);

        // Fetch attempts from the database
        List<QuizAttempt> attempts = getUserAttempts(username);

        goHomebtn = new JButton("Home");
        goHomebtn.setBounds(10,20,150,35);
        goHomebtn.setFont(new Font("Arial", Font.PLAIN, 22));
        goHomebtn.setBackground(Mainpurple);
        goHomebtn.setOpaque(true);
        goHomebtn.setForeground(Color.white);
        goHomebtn.setFocusPainted(false);
        goHomebtn.setBorder(new LineBorder(Mainpurple, 4));
        goHomebtn.addActionListener(e -> {
            this.setVisible(false);
            new HomePage(conn,HomePage.CurrentUser);
        });
        contentPanel.add(goHomebtn);

        // Add heading at the top
        JLabel heading = new JLabel("Previous Attempts", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        heading.setForeground(new Color(167, 41, 245));  // Purple color for heading
        heading.setBounds(0, 20, 1000, 50);  // Position heading at the top center
        heading.setOpaque(true);
        heading.setBackground(Color.WHITE);
        heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        contentPanel.add(heading);

        // Create the panel to hold the attempt details
        attemptsPanel = new JPanel();
        attemptsPanel.setLayout(new BoxLayout(attemptsPanel, BoxLayout.Y_AXIS));  // Stack attempts vertically
        attemptsPanel.setBackground(Color.WHITE);

        // Add each attempt as a JLabel inside a panel
        for (QuizAttempt attempt : attempts) {
            JPanel attemptPanel = createAttemptPanel(attempt);
            attemptsPanel.add(attemptPanel);
        }

        // Create a scroll pane for the attempts
        JScrollPane scrollPane = new JScrollPane(attemptsPanel);
        scrollPane.setBackground(Color.LIGHT_GRAY); // Set scroll pane background
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBounds(25, 80, 950, 500); // Set position and size of the scroll pane
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0)); // Set width of vertical scroll bar
        scrollPane.getVerticalScrollBar().setBackground(Color.GRAY); // Set scroll bar color
        contentPanel.add(scrollPane);

        setVisible(true);
    }

    // Create a panel for each attempt with styled JLabels
    private JPanel createAttemptPanel(QuizAttempt attempt) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));  // Align components in a single row with space between them
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(950, 60)); // Make sure each row occupies the same width as the scroll pane
        panel.setBorder(BorderFactory.createLineBorder(Mainpurple, 2)); // Border around each attempt

        // Create labels for attempt number, quiz title, score, and attempt date
        JLabel attemptNoLabel = new JLabel("Attempt #" + (attemptsPanel.getComponentCount() + 1)); // Display attempt number
        attemptNoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        attemptNoLabel.setForeground(Mainpurple);  // Purple color for attempt number

        JLabel titleLabel = new JLabel("Quiz: " + attempt.getQuizTitle());
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        titleLabel.setForeground(Color.BLACK);

        JLabel scoreLabel = new JLabel("Score: " + attempt.getScore());
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        scoreLabel.setForeground(Color.BLACK);

        JLabel dateLabel = new JLabel("Date: " + attempt.getAttemptDate().toString());
        dateLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        dateLabel.setForeground(Color.GRAY);

        // Add the labels to the panel
        panel.add(attemptNoLabel);
        panel.add(titleLabel);
        panel.add(scoreLabel);
        panel.add(dateLabel);

        return panel;

    }

    // Fetch user attempts from the database
    public List<QuizAttempt> getUserAttempts(String username) {
        List<QuizAttempt> attempts = new ArrayList<>();
        String query = "SELECT q.title, qa.score, qa.attempt_date " +
                "FROM quiz_attempts qa " +
                "JOIN users u ON qa.user_id = u.user_id " +
                "JOIN quizzes q ON qa.quiz_id = q.quiz_id " +
                "WHERE u.username = ? " +
                "ORDER BY qa.attempt_date DESC"; // Order by attempt_date descending

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username); // Set the username in the query
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String quizTitle = rs.getString("title");
                int score = rs.getInt("score");
                Date attemptDate = rs.getTimestamp("attempt_date"); // Retrieve the attempt date
                attempts.add(new QuizAttempt(quizTitle, score, attemptDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attempts;
    }
}

