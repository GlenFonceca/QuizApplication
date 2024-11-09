
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Collections;
import java.util.List;

public class QuizPage extends JFrame implements ActionListener {
    Connection conn;
    List<Question> questions;
    List<QuizOption> options;
    QuizController quizController;
    QuizService quizService;
    int score=0,maxQns=5;
    Color correctColor = new Color(0x26D782);
    Color wrongColor = new Color(0xEE5454);
    Color Mainpurple = new Color(167, 41, 245);
    Color textColor = new Color(0, 14, 81);
    JPanel Panel;
    JLabel questionText,QnNo;
    JButton optionA, optionB, optionC, optionD, nextQuestion;
    int quiz_id,Qno=1;
    int currentQuestionIndex = 0;


    QuizPage(Connection conn, int quiz_id) {
        this.conn = conn;
        this.quiz_id = quiz_id;
        quizController = new QuizController(conn);
        quizService = new QuizService(conn);

        setLayout(null);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setTitle("Quiz Central");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Panel = new JPanel(null);
        Panel.setBackground(new Color(245, 246, 255));
        setContentPane(Panel);

        QnNo = new JLabel("");
        QnNo.setBounds(50,100,450,20);
        QnNo.setForeground(textColor);
        QnNo.setFont(new Font("arial",Font.ITALIC,15));
        Panel.add(QnNo);

        questionText = new JLabel("");
        questionText.setBounds(50, 125, 450, 450);
        questionText.setForeground(textColor);
        questionText.setFont(new Font("arial", Font.BOLD, 40));
        questionText.setVerticalAlignment(SwingConstants.TOP);
        questionText.setHorizontalAlignment(SwingConstants.LEFT);
        Panel.add(questionText);



        optionA = getOptionButton( 550, 125);
        optionB = getOptionButton( 550, 220);
        optionC = getOptionButton( 550, 310);
        optionD = getOptionButton(550, 400);
        optionA.addActionListener(this);
        optionB.addActionListener(this);
        optionC.addActionListener(this);
        optionD.addActionListener(this);

        Panel.add(optionA);
        Panel.add(optionB);
        Panel.add(optionC);
        Panel.add(optionD);

        questions = quizController.getQuizQuestions(quiz_id);
        Collections.shuffle(questions);
        displayQuestion(currentQuestionIndex); // Display the first question

        nextQuestion = new JButton("Next Question");
        nextQuestion.setBounds(550, 495, 400, 65);
        nextQuestion.setFont(new Font("Arial", Font.PLAIN, 22));
        nextQuestion.setBackground(Mainpurple);
        nextQuestion.setOpaque(true);
        nextQuestion.setForeground(Color.white);
        nextQuestion.setFocusPainted(false);
        nextQuestion.setBorder(new LineBorder(Mainpurple, 4));
        nextQuestion.addActionListener(e -> {
            if(currentQuestionIndex == maxQns-2){
                nextQuestion.setText("Finish");
            }
            if (currentQuestionIndex < maxQns-1) {
                currentQuestionIndex++;
                displayQuestion(currentQuestionIndex);
                resetButtonColors();
            } else {
                quizService.saveQuizAttempt(HomePage.CurrentUser,quiz_id,score);
                goToResult();
            }
        });

        Panel.add(nextQuestion);
        setVisible(true);
    }

    private void displayQuestion(int index) {
        Question question = questions.get(index);
        QnNo.setText("Question "+ Qno++ +" out of 5");
        questionText.setText("<html><div style='width:350px;'>"+question.getQuestionText()+"</html>");

        options = quizService.getOptionsForQuestion(question.getQuestionId());
        optionA.setText("  A: " + options.get(0).getOptionText());
        optionB.setText("  B: " + options.get(1).getOptionText());
        optionC.setText("  C: " + options.get(2).getOptionText());
        optionD.setText("  D: " + options.get(3).getOptionText());

        enableOptionButtons(true);//TO again enable the button for next Question
    }
    private void resetButtonColors() {
        optionA.setBorder(new LineBorder(Mainpurple, 4));
        optionB.setBorder(new LineBorder(Mainpurple, 4));
        optionC.setBorder(new LineBorder(Mainpurple, 4));
        optionD.setBorder(new LineBorder(Mainpurple, 4));
        optionA.setForeground(textColor);
        optionB.setForeground(textColor);
        optionC.setForeground(textColor);
        optionD.setForeground(textColor);
    }

    JButton getOptionButton(int x, int y) {
        JButton customButton = new JButton();
        customButton.setBounds(x, y, 400, 65);
        customButton.setFont(new Font("Arial", Font.PLAIN, 22));
        customButton.setForeground(textColor);
        customButton.setHorizontalAlignment(SwingConstants.LEFT);
        customButton.setContentAreaFilled(false);
        customButton.setFocusPainted(false);
        customButton.setBorder(new LineBorder(Mainpurple, 4));
        customButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return customButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        Question currentQuestion = questions.get(currentQuestionIndex);
        String correctAnswer = currentQuestion.getCorrectAnswer();

        if (clickedButton == optionA) {
            checkAnswer(clickedButton, options.get(0), correctAnswer);
        } else if (clickedButton == optionB) {
            checkAnswer(clickedButton, options.get(1), correctAnswer);
        } else if (clickedButton == optionC) {
            checkAnswer(clickedButton, options.get(2), correctAnswer);
        } else if (clickedButton == optionD) {
            checkAnswer(clickedButton, options.get(3), correctAnswer);
        }
    }

    private void enableOptionButtons(boolean enable) {
        optionA.setEnabled(enable);
        optionB.setEnabled(enable);
        optionC.setEnabled(enable);
        optionD.setEnabled(enable);
    }

    private void checkAnswer(JButton clickedButton, QuizOption selectedOption, String correctAnswer) {
        if (selectedOption.getOptionText().equals(correctAnswer)) {
             // Correct answer
            clickedButton.setForeground(correctColor);
            clickedButton.setBorder(new LineBorder(correctColor, 4));
            score++; // Increment score
            enableOptionButtons(false);
        } else {
            clickedButton.setBorder(new LineBorder(wrongColor, 4));
            clickedButton.setForeground(wrongColor);// Incorrect answer
            // Highlight the correct answer
            highlightCorrectAnswer(correctAnswer);
        }
    }

    private void highlightCorrectAnswer(String correctAnswer) {
        List<QuizOption> options = quizService.getOptionsForQuestion(questions.get(currentQuestionIndex).getQuestionId());
        for (QuizOption option : options) {
            if (option.getOptionText().equals(correctAnswer)) {
                if (option == options.get(0)){
                    optionA.setForeground(correctColor);
                    optionA.setBorder(new LineBorder(correctColor, 4));
                }
                else if (option == options.get(1)){
                    optionB.setForeground(correctColor);
                    optionB.setBorder(new LineBorder(correctColor, 4));
                }
                else if (option == options.get(2)){
                    optionC.setForeground(correctColor);
                    optionC.setBorder(new LineBorder(correctColor, 4));
                }
                else if (option == options.get(3)){
                    optionD.setForeground(correctColor);
                    optionD.setBorder(new LineBorder(correctColor, 4));
                }
            }
        }
        enableOptionButtons(false); //To disable the buttons so user can choose only once
    }
    void goToResult(){
        this.dispose();
        new ResultDisplay(conn,score);
    }
}

