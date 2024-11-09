import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class ResultDisplay extends JFrame implements ActionListener {
    Connection conn;
    Color Mainpurple = new Color(167, 41, 245);
    Color textColor = new Color(0, 14, 81);
    JPanel Panel;
    JPanel scorePanel;
    JButton playAgain,seeHistory;
    int finalScore=0;
    ResultDisplay(Connection conn ,int score) {
        this.conn = conn;
        finalScore = score;
        setLayout(null);
        setSize(1000,700);
        setLocationRelativeTo(null); //To make the Frame to Open in the Center of Screen
        setTitle("Quiz Central");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Panel = new JPanel(null);
        setContentPane(Panel);
        Panel.setBackground(new Color(245, 246, 255));

        JLabel resultText1 = new JLabel("Quiz completed ");
        resultText1.setBounds(60,100,400,60);
        resultText1.setFont(new Font("rubik", Font.PLAIN, 40));
        resultText1.setForeground(textColor);
        JLabel resultText2 = new JLabel("You scored...");
        resultText2.setBounds(40,180,450,70);
        resultText2.setFont(new Font("rubik", Font.BOLD, 55));
        resultText2.setForeground(Mainpurple);
        Panel.add(resultText1);
        Panel.add(resultText2);

        ImageIcon welcomeBanner = new ImageIcon(ClassLoader.getSystemResource("images/Score.png"));
        ImageIcon scaledBanner = new ImageIcon(
                welcomeBanner.getImage().getScaledInstance(400, 300, java.awt.Image.SCALE_SMOOTH)
        );
        JLabel imgresult = new JLabel(scaledBanner);
        imgresult.setBounds(50,275,400,300);
        Panel.add(imgresult);

        scorePanel = new JPanel();
        scorePanel.setBackground(new Color(255, 255, 255));
        scorePanel.setLayout(null);
        scorePanel.setBounds(550,120,400,325);

        JLabel Score = new JLabel(""+finalScore);
        Score.setBounds(150,100,150,150);
        Score.setForeground(textColor);
        Score.setFont(new Font("rubik",Font.BOLD,150));
        scorePanel.add(Score);

        JLabel MaxScore = new JLabel("out of 5");
        MaxScore.setBounds(165,265,150,20);
        MaxScore.setForeground(textColor);
        MaxScore.setFont(new Font("serif",Font.ITALIC,18));
        scorePanel.add(MaxScore);

        playAgain = getCustomButton("Play Again",Color.white);
        playAgain.setBackground(Mainpurple);
        playAgain.setBounds(550,470,400,50);
        playAgain.addActionListener(this);

        seeHistory = getCustomButton("See History",Mainpurple);
        seeHistory.setBackground(Color.white);
        seeHistory.setBounds(550,545,400,50);
        seeHistory.addActionListener(this);

        Panel.add(playAgain);
        Panel.add(seeHistory);
        Panel.add(scorePanel);
        setVisible(true);
    }

    public JButton getCustomButton(String btnText,Color textColor){
        JButton customButton = new JButton(btnText);
        customButton.setFont(new Font("Arial", Font.PLAIN, 24));
        customButton.setForeground(textColor); // Text color
        customButton.setHorizontalAlignment(SwingConstants.CENTER); // Align text to the left
        customButton.setFocusPainted(false);
        customButton.setOpaque(true);
        customButton.setBorder(new LineBorder(Mainpurple, 3));
        return customButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == playAgain){
            this.dispose();
            new HomePage(conn,HomePage.CurrentUser);
        } else if (e.getSource() == seeHistory) {
            this.dispose();
            new UserAttemptsFrame(HomePage.CurrentUser,conn);
        }
    }
}
