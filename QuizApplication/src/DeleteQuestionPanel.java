import com.sun.tools.javac.Main;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteQuestionPanel extends JPanel implements ActionListener {
    Color Mainpurple = new Color(167, 41, 245);
    Color textColor = new Color(0, 14, 81);
    private Connection con;
    private int quizId;  // Store the quiz ID passed from SelectQuizPanel
    private JTextField questionIdField;
    private JButton deleteButton;
    private JTextArea questionListArea;

    // Updated constructor to accept quizId
    DeleteQuestionPanel(Connection con, int quizId) {
        this.con = con;
        this.quizId = quizId;  // Set the quiz ID
        setLayout(null);
        setBackground(Color.white);
        setBounds(200, 0, 800, 700);

        JLabel titleLabel = new JLabel("Delete Questions");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titleLabel.setBounds(275, 20, 400, 30);
        add(titleLabel);

        // Text area to display questions in a scrollable panel
        questionListArea = new JTextArea();
        questionListArea.setEditable(false);
        questionListArea.setForeground(textColor);
        questionListArea.setFont(new Font("rubik",Font.PLAIN,18));
        JScrollPane scrollPane = new JScrollPane(questionListArea);
        scrollPane.setBounds(50, 75, 700, 400);
        scrollPane.setBorder(new LineBorder(Mainpurple,5));
        add(scrollPane);

        JLabel idLabel = new JLabel("Enter Question ID to delete:");
        idLabel.setBounds(50, 525, 350, 50);
        idLabel.setForeground(textColor);
        idLabel.setFont(new Font("rubik",Font.PLAIN,23));
        add(idLabel);

        questionIdField = new JTextField();
        questionIdField.setBounds(425, 525, 150, 40);
        questionIdField.setFont(new Font("rubik",Font.BOLD,25));
        questionIdField.setBorder(new LineBorder(Mainpurple, 3));
        add(questionIdField);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(600, 525, 150, 40);
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 23));
        deleteButton.setBackground(Mainpurple);
        deleteButton.setOpaque(true);
        deleteButton.setForeground(Color.white);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorder(new LineBorder(Mainpurple, 4));
        deleteButton.addActionListener(this);
        add(deleteButton);

        displayQuestions();  // Populate the questions list for the given quiz
    }

    // Modified displayQuestions to show only questions for the specified quiz_id
    private void displayQuestions() {
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT question_id, question_text FROM questions WHERE quiz_id = ?"
            );
            stmt.setInt(1, quizId);  // Use the quizId to filter questions
            ResultSet rs = stmt.executeQuery();
            questionListArea.setText("");  // Clear previous content

            while (rs.next()) {
                int id = rs.getInt("question_id");
                String text = rs.getString("question_text");
                questionListArea.append("   ID: " + id + " - " + text + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading questions.");
        }
    }

    // Method to delete a question by ID (and cascade deletes options due to foreign key constraint)
    private void deleteQuestion(int questionId) {
        try {
            // Prepare statement to delete the question, which will automatically delete associated options
            PreparedStatement stmt = con.prepareStatement("DELETE FROM questions WHERE question_id = ?");
            stmt.setInt(1, questionId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Question and associated options deleted successfully.");
                displayQuestions();  // Refresh the question list
            } else {
                JOptionPane.showMessageDialog(this, "Question ID not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting question.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == deleteButton) {
            try {
                int questionId = Integer.parseInt(questionIdField.getText().trim());
                deleteQuestion(questionId);  // Call the delete method
                questionIdField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid question ID.");
            }
        }
    }
}
