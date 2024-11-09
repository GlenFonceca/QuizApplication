import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class EditQuestionPanel extends JPanel implements ActionListener {
    Color Mainpurple = new Color(167, 41, 245);
    Color textColor = new Color(0, 14, 81);
    private Connection con;
    private JTextArea questionListArea;
    private JTextField questionIdField, questionTextField, correctAnswerField, option1Field, option2Field, option3Field, option4Field;
    private JButton submitButton, fetchButton;
    private int quizId;

    // Constructor
    EditQuestionPanel(Connection con, int quizId) {
        this.con = con;
        this.quizId = quizId;  // Store quizId passed from SelectQuizPanel
        setLayout(null);
        setBackground(Color.white);
        setBounds(200, 0, 800, 700);

        // Title Label
        JLabel titleLabel = new JLabel("Edit Questions");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(300, 10, 200, 30);
        add(titleLabel);

        // Scrollable Area for Listing Questions
        questionListArea = new JTextArea();
        questionListArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(questionListArea);
        scrollPane.setBounds(50, 50, 700, 200);
        add(scrollPane);

        // Label and Text Field to input Question ID
        JLabel idLabel = new JLabel("Enter Question ID to Edit:");
        idLabel.setBounds(50, 270, 200, 25);
        add(idLabel);

        questionIdField = new JTextField();
        questionIdField.setBounds(250, 270, 100, 25);
        add(questionIdField);

        // Fetch button to load question details
        fetchButton = new JButton("Fetch Question");
        fetchButton.setBounds(370, 270, 150, 25);
        fetchButton.setFont(new Font("Arial", Font.PLAIN, 18));
        fetchButton.setBackground(Mainpurple);
        fetchButton.setOpaque(true);
        fetchButton.setForeground(Color.white);
        fetchButton.setFocusPainted(false);
        fetchButton.setBorder(new LineBorder(Mainpurple, 4));
        fetchButton.addActionListener(this);
        add(fetchButton);

        JLabel questionTextLabel = new JLabel("Question Text:");
        questionTextLabel.setBounds(50, 310, 200, 25);
        add(questionTextLabel);

        questionTextField = new JTextField();
        questionTextField.setBounds(250, 310, 500, 25);
        add(questionTextField);

        JLabel correctAnswerLabel = new JLabel("Correct Answer:");
        correctAnswerLabel.setBounds(50, 350, 200, 25);
        add(correctAnswerLabel);

        correctAnswerField = new JTextField();
        correctAnswerField.setBounds(250, 350, 500, 25);
        add(correctAnswerField);

        JLabel option1Label = new JLabel("Option 1:");
        option1Label.setBounds(50, 390, 200, 25);
        add(option1Label);

        option1Field = new JTextField();
        option1Field.setBounds(250, 390, 500, 25);
        add(option1Field);

        JLabel option2Label = new JLabel("Option 2:");
        option2Label.setBounds(50, 430, 200, 25);
        add(option2Label);

        option2Field = new JTextField();
        option2Field.setBounds(250, 430, 500, 25);
        add(option2Field);

        JLabel option3Label = new JLabel("Option 3:");
        option3Label.setBounds(50, 470, 200, 25);
        add(option3Label);

        option3Field = new JTextField();
        option3Field.setBounds(250, 470, 500, 25);
        add(option3Field);

        JLabel option4Label = new JLabel("Option 4:");
        option4Label.setBounds(50, 510, 200, 25);
        add(option4Label);

        option4Field = new JTextField();
        option4Field.setBounds(250, 510, 500, 25);
        add(option4Field);

        submitButton = new JButton("Submit");
        submitButton.setBounds(370, 550, 150, 40);
        submitButton.setFont(new Font("Arial", Font.PLAIN, 22));
        submitButton.setBackground(Mainpurple);
        submitButton.setOpaque(true);
        submitButton.setForeground(Color.white);
        submitButton.setFocusPainted(false);
        submitButton.setBorder(new LineBorder(Mainpurple, 4));
        submitButton.addActionListener(this);
        add(submitButton);

        // Initially load questions for the given quiz_id
        displayQuestions();
    }

    // Display questions filtered by quiz_id
    private void displayQuestions() {
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT question_id, question_text FROM questions WHERE quiz_id = ?");
            stmt.setInt(1, quizId);
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

    // Fetch question details when the ID is entered
    private void fetchQuestionDetails(int questionId) {
        try {
            PreparedStatement stmt = con.prepareStatement(
                    "SELECT question_text, correct_answer FROM questions WHERE question_id = ? AND quiz_id = ?");
            stmt.setInt(1, questionId);
            stmt.setInt(2, quizId);  // Ensure the question belongs to the selected quiz
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                questionTextField.setText(rs.getString("question_text"));
                correctAnswerField.setText(rs.getString("correct_answer"));

                // Fetch the options for the question
                PreparedStatement optionsStmt = con.prepareStatement(
                        "SELECT option_text FROM options WHERE question_id = ?");
                optionsStmt.setInt(1, questionId);
                ResultSet optionsRs = optionsStmt.executeQuery();

                int optionIndex = 1;
                while (optionsRs.next()) {
                    if (optionIndex == 1) option1Field.setText(optionsRs.getString("option_text"));
                    else if (optionIndex == 2) option2Field.setText(optionsRs.getString("option_text"));
                    else if (optionIndex == 3) option3Field.setText(optionsRs.getString("option_text"));
                    else if (optionIndex == 4) option4Field.setText(optionsRs.getString("option_text"));
                    optionIndex++;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Question ID not found for this quiz.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching question details.");
        }
    }

    private void updateQuestion(int questionId) {
        try {
            // Update the question text and correct answer
            PreparedStatement updateQuestionStmt = con.prepareStatement(
                    "UPDATE questions SET question_text = ?, correct_answer = ? WHERE question_id = ? AND quiz_id = ?");
            updateQuestionStmt.setString(1, questionTextField.getText());
            updateQuestionStmt.setString(2, correctAnswerField.getText());
            updateQuestionStmt.setInt(3, questionId);
            updateQuestionStmt.setInt(4, quizId);
            int rowsAffected = updateQuestionStmt.executeUpdate();

            // Fetch the option ids for the given question_id
            PreparedStatement fetchOptionIdsStmt = con.prepareStatement(
                    "SELECT option_id FROM options WHERE question_id = ? ORDER BY option_id ASC");
            fetchOptionIdsStmt.setInt(1, questionId);
            ResultSet rs = fetchOptionIdsStmt.executeQuery();

            // List to hold the option ids in order
            List<Integer> optionIds = new ArrayList<>();
            while (rs.next()) {
                optionIds.add(rs.getInt("option_id"));
            }

            // Now update each option sequentially based on the option_ids
            PreparedStatement updateOptionStmt = con.prepareStatement(
                    "UPDATE options SET option_text = ? WHERE question_id = ? AND option_id = ?");

            // Sequentially update the option text from fields
            if (optionIds.size() > 0) updateOptionStmt.setString(1, option1Field.getText());
            if (optionIds.size() > 1) updateOptionStmt.setString(1, option2Field.getText());
            if (optionIds.size() > 2) updateOptionStmt.setString(1, option3Field.getText());
            if (optionIds.size() > 3) updateOptionStmt.setString(1, option4Field.getText());

            // For each option_id, update the option_text
            for (int i = 0; i < optionIds.size(); i++) {
                // Sequentially update the option text from fields
                if (i == 0) updateOptionStmt.setString(1, option1Field.getText());
                if (i == 1) updateOptionStmt.setString(1, option2Field.getText());
                if (i == 2) updateOptionStmt.setString(1, option3Field.getText());
                if (i == 3) updateOptionStmt.setString(1, option4Field.getText());
                updateOptionStmt.setInt(2, questionId);  // Set the question_id
                updateOptionStmt.setInt(3, optionIds.get(i));  // Set the option_id
                updateOptionStmt.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Question and options updated successfully.");
            resetFields();
            displayQuestions();  // Refresh the question list

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating question.");
        }
    }


    // A helper function to map option fields dynamically to the correct option_id (this is a simple approach, modify as needed)
    private int getOptionIdFromField(JTextField optionField) {
        // these fields have some predefined or known mapping.
        if (optionField == option1Field) {
            return 1;  // Replace with actual logic to get option_id for option1Field
        } else if (optionField == option2Field) {
            return 2;  // Replace with actual logic to get option_id for option2Field
        } else if (optionField == option3Field) {
            return 3;  // Replace with actual logic to get option_id for option3Field
        } else if (optionField == option4Field) {
            return 4;  // Replace with actual logic to get option_id for option4Field
        }
        return -1;  // Default case if no match found
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fetchButton) {
            try {
                int questionId = Integer.parseInt(questionIdField.getText().trim());
                fetchQuestionDetails(questionId);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid question ID.");
            }
        } else if (e.getSource() == submitButton) {
            try {
                int questionId = Integer.parseInt(questionIdField.getText().trim());

                // Check if the correct answer is valid
                String correctAnswer = correctAnswerField.getText().trim();
                if (!isCorrectAnswerValid(correctAnswer)) {
                    JOptionPane.showMessageDialog(this, "Correct answer must be one of the options.");
                    return; // Stop further processing if the correct answer is invalid
                }

                updateQuestion(questionId);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid question ID.");
            }
        }
    }

    private boolean isCorrectAnswerValid(String correctAnswer) {
        // Get the text from the option fields
        String option1 = option1Field.getText().trim();
        String option2 = option2Field.getText().trim();
        String option3 = option3Field.getText().trim();
        String option4 = option4Field.getText().trim();

        // Check if the correct answer matches any of the options
        return correctAnswer.equals(option1) || correctAnswer.equals(option2) || correctAnswer.equals(option3) || correctAnswer.equals(option4);
    }
    private void resetFields() {
        // Reset all text fields
        questionIdField.setText("");
        questionTextField.setText("");
        option1Field.setText("");
        option2Field.setText("");
        option3Field.setText("");
        option4Field.setText("");
        correctAnswerField.setText("");
    }


}
