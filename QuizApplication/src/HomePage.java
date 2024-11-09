import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class HomePage extends JFrame implements ActionListener{
    Connection conn;
    public static String CurrentUser;
    Color Mainpurple = new Color(167, 41, 245);
    Color textColor = new Color(0, 14, 81);
    JPanel Panel;
    JButton topic1,topic2,topic3,topic4,logoutBtn;
    HomePage(Connection conn,String userName){
        CurrentUser = userName;
        this.conn = conn;
        setLayout(null);
        setSize(1000,700);
        setLocationRelativeTo(null); //To make the Frame to Open in the Center of Screen
        setTitle("Quiz Central");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Panel = new JPanel(null);
        setContentPane(Panel);
        Panel.setBackground(new Color(245, 246, 255));

        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("images/businessman.png")).getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH))); // Set your top icon
        iconLabel.setBounds(10, 20, 35, 35);
        add(iconLabel);

        JLabel userText = new JLabel("Hello "+CurrentUser);
        userText.setBounds(55,19,250,40);
        userText.setFont(new Font("arial", Font.BOLD, 25));
        userText.setForeground(Mainpurple);
        Panel.add(userText);

        //Welcome Text
        JLabel welcomeText2 = new JLabel("Quiz Central!",SwingConstants.CENTER);
        welcomeText2.setBounds(0,0,1000,60);
        welcomeText2.setBackground(Mainpurple);
        welcomeText2.setFont(new Font("arial", Font.BOLD, 40));
        welcomeText2.setForeground(Mainpurple);
        Panel.add(welcomeText2);

        logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(800,10,125,35);
        logoutBtn.setFont(new Font("Arial", Font.PLAIN, 22));
        logoutBtn.setBackground(Mainpurple);
        logoutBtn.setOpaque(true);
        logoutBtn.setForeground(Color.white);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(new LineBorder(Mainpurple, 4));
        logoutBtn.addActionListener(e -> {
            this.dispose();
            new WelcomePage(conn).regToLogin();
        });
        Panel.add(logoutBtn);

        JLabel selectTopicText= new JLabel("Select a Topic to Start Quiz");
        selectTopicText.setBounds(40,200,500,25);
        selectTopicText.setFont(new Font("serif", Font.BOLD, 25));
        selectTopicText.setForeground(textColor);
        Panel.add(selectTopicText);


        //Image Icons
        ImageIcon gn,sc,sp,hs;
        gn = new ImageIcon(ClassLoader.getSystemResource("images/generalKnowledge.jpeg"));
        ImageIcon gnImg =getScaledImage(gn,200,200);
        sc = new ImageIcon(ClassLoader.getSystemResource("images/science.jpg"));
        ImageIcon scImg = getScaledImage(sc,200,200);
        sp = new ImageIcon(ClassLoader.getSystemResource("images/sports.jpg"));
        ImageIcon spImg = getScaledImage(sp,200,200);
        hs = new ImageIcon(ClassLoader.getSystemResource("images/history.jpg"));
        ImageIcon hsImg = getScaledImage(hs,200,200);

        topic1 = getTopicButton(gnImg,"General Knowledge",40,250);
        topic2 = getTopicButton(scImg,"Science",280,250);
        topic3 = getTopicButton(hsImg,"History",760,250);
        topic4 = getTopicButton(spImg,"Sports",520,250);
        topic1.addActionListener(this);
        topic2.addActionListener(this);
        topic3.addActionListener(this);
        topic4.addActionListener(this);
        Panel.add(topic1);
        Panel.add(topic2);
        Panel.add(topic3);
        Panel.add(topic4);
        setVisible(true);
    }

    ImageIcon getScaledImage(ImageIcon ic,int width,int height){
        ImageIcon scaledImg = new ImageIcon(
                ic.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH)
        );
        return scaledImg;
    }
    JButton getTopicButton(ImageIcon ic,String text,int x,int y){
        JButton button = new JButton(text,ic);
        button.setBounds(x,y,200,235);
        button.setBorder(null);
        button.setBackground(Color.white);
        button.setForeground(textColor);
        button.setFont(new Font("arial",Font.BOLD,20));
        button.setHorizontalTextPosition(SwingConstants.CENTER); // Center horizontally
        button.setVerticalTextPosition(SwingConstants.BOTTOM);   // Place text below the icon
        //Adjust icon and text gap
        button.setIconTextGap(5);

        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int quiz_id=1;
        if(e.getSource() == topic1){
            quiz_id =1;
        } else if (e.getSource() == topic2) {
            quiz_id = 2;
        } else if(e.getSource() == topic3) {
            quiz_id =3;
        } else if (e.getSource() == topic4) {
            quiz_id = 4;
        }
        if(e.getSource() == topic1 ||e.getSource() == topic2||e.getSource() == topic3||e.getSource() == topic4){
            this.setVisible(false);
            new QuizPage(conn,quiz_id);
        }
    }
}
