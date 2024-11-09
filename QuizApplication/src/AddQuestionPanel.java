import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;

public class AddQuestionPanel extends JPanel implements ActionListener {
    JButton AddQuestionbtn;
    Color Mainpurple = new Color(167, 41, 245);
    Color textColor = new Color(0, 14, 81);
    JTextArea questionText,option1,option2,option3,option4,correctAnswer;
    Connection con;
    int quiz_id;
    AddQuestionPanel(Connection con,int quiz_id,String title){
        this.quiz_id = quiz_id;
        this.con = con;
        setLayout(null);
        setBackground(Color.white);
        setBounds(200,0,800,700);

        JLabel jtitle = new JLabel(title);
        jtitle.setBounds(35,10,500,35);
        jtitle.setFont(new Font("rubik",Font.BOLD,30));
        add(jtitle);

        JLabel j1 = new JLabel("Enter the Question Text");
        j1.setBounds(100,50,400,50);
        j1.setFont(new Font("rubik",Font.BOLD,22));
        add(j1);

        questionText = new JTextArea();
        questionText.setBounds(100,100,600,175);
        questionText.setFont(new Font("rubik",Font.PLAIN,20));
        questionText.setLineWrap(true); // Wrap text to fit within the area
        questionText.setWrapStyleWord(true); // Wrap on word boundaries
        questionText.setMargin(new Insets(5, 5, 5, 5)); // Add padding if needed
        questionText.setCaretPosition(0); // Start caret from top-left
        questionText.setBorder(new LineBorder(Color.black));
        add(questionText);

        JLabel j3 = new JLabel("Enter Correct Answer");
        j3.setBounds(100,300,300,40);
        j3.setFont(new Font("rubik",Font.BOLD,22));
        add(j3);

        correctAnswer = new JTextArea();
        correctAnswer.setBounds(450,300,250,50);
        correctAnswer.setFont(new Font("rubik",Font.PLAIN,20));
        correctAnswer.setBorder(new LineBorder(Color.black));
        correctAnswer.setLineWrap(true); // Wrap text to fit within the area
        correctAnswer.setWrapStyleWord(true); // Wrap on word boundaries
        correctAnswer.setMargin(new Insets(5, 5, 5, 5)); // Add padding if needed
        correctAnswer.setCaretPosition(0);
        add(correctAnswer);

        JLabel j2 = new JLabel("Enter Options");
        j2.setBounds(100,375,400,40);
        j2.setFont(new Font("rubik",Font.BOLD,22));
        add(j2);

        option1 = getOptionArea(100,425,"1");
        option2 = getOptionArea(450,425,"2");
        option3 = getOptionArea(100,500,"3");
        option4 = getOptionArea(450,500,"4");
        add(option1);
        add(option2);
        add(option3);
        add(option4);

        AddQuestionbtn = new JButton("AddQuestion");
        AddQuestionbtn.setFont(new Font("Arial", Font.PLAIN, 24));
        AddQuestionbtn.setForeground(textColor); // Text color
        AddQuestionbtn.setHorizontalAlignment(SwingConstants.CENTER); // Align text to the left
        AddQuestionbtn.setFocusPainted(false);
        AddQuestionbtn.setOpaque(true);
        AddQuestionbtn.setBorder(new LineBorder(Mainpurple, 3));
        AddQuestionbtn.setBackground(Mainpurple);
        AddQuestionbtn.setBounds(300,590,200,50);
        AddQuestionbtn.addActionListener(this);
        add(AddQuestionbtn);

    }
    JTextArea getOptionArea(int x,int y,String opNo){
        JTextArea option = new JTextArea();
        option.setBounds(x,y,250,50);
        option.setFont(new Font("rubik",Font.PLAIN,20));
        option.setBorder(new LineBorder(Color.black));
        option.setLineWrap(true); // Wrap text to fit within the area
        option.setWrapStyleWord(true); // Wrap on word boundaries
        option.setMargin(new Insets(5, 5, 5, 5)); // Add padding if needed
        option.setCaretPosition(0);
        String placeholder = "Option "+opNo;
        option.setText(placeholder);
        option.setForeground(Color.GRAY);
        option.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (option.getText().equals(placeholder)) {
                    option.setText("");
                    option.setForeground(textColor);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (option.getText().isEmpty()) {
                    option.setText(placeholder);
                    option.setForeground(Color.GRAY);
                }
            }
        });
        return option;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String qnText,op1,op2,op3,op4,correctAns;
        qnText = questionText.getText();
        op1 = option1.getText();
        op2 = option2.getText();
        op3 = option3.getText();
        op4 = option4.getText();
        correctAns = correctAnswer.getText();
        if(e.getSource() == AddQuestionbtn && validation()){
            if(new QuizService(con).addQuestion(quiz_id,qnText,correctAns,op1,op2,op3,op4)){
                JOptionPane.showMessageDialog(this, "Question Added Successfully.");
                resetFields();
            }
        }
    }
    boolean validation() {
        String placeholder1 = "Option 1";
        String placeholder2 = "Option 2";
        String placeholder3 = "Option 3";
        String placeholder4 = "Option 4";

        // Check if question text is empty
        if (questionText.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the question text.","Success",JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        // Check if each option is empty or still contains the placeholder text
        if (option1.getText().trim().isEmpty() || option1.getText().equals(placeholder1)) {
            JOptionPane.showMessageDialog(this, "Please enter Option 1.");
            return false;
        }
        if (option2.getText().trim().isEmpty() || option2.getText().equals(placeholder2)) {
            JOptionPane.showMessageDialog(this, "Please enter Option 2.");
            return false;
        }
        if (option3.getText().trim().isEmpty() || option3.getText().equals(placeholder3)) {
            JOptionPane.showMessageDialog(this, "Please enter Option 3.");
            return false;
        }
        if (option4.getText().trim().isEmpty() || option4.getText().equals(placeholder4)) {
            JOptionPane.showMessageDialog(this, "Please enter Option 4.");
            return false;
        }

        // Check if correct answer is empty
        if (correctAnswer.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the correct answer.");
            return false;
        }

        // Check if correct answer matches any of the options
        String correctAns = correctAnswer.getText().trim();
        if (!correctAns.equals(option1.getText().trim()) &&
                !correctAns.equals(option2.getText().trim()) &&
                !correctAns.equals(option3.getText().trim()) &&
                !correctAns.equals(option4.getText().trim())) {
            JOptionPane.showMessageDialog(this, "The correct answer must match one of the options.");
            return false;
        }
        return true;
    }
    private void resetFields() {
        questionText.setText("");
        correctAnswer.setText("");

        option1.setText("Option 1");
        option1.setForeground(Color.GRAY);

        option2.setText("Option 2");
        option2.setForeground(Color.GRAY);

        option3.setText("Option 3");
        option3.setForeground(Color.GRAY);

        option4.setText("Option 4");
        option4.setForeground(Color.GRAY);
    }

}
