import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class WelcomePage extends JFrame {
    Connection con;
    Color Mainpurple = new Color(167, 41, 245);
    JPanel Panel;
    Login loginPanel;
    Register registerPanel;
    WelcomePage(Connection con){
        this.con = con;
        loginPanel = new Login(con,this);
        registerPanel = new Register(con,this);
        setLayout(null);
        setSize(1000,700);
        setLocationRelativeTo(null); //To make the Frame to Open in the Center of Screen
        setTitle("Quiz Central");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Panel = new JPanel(null);
        Panel.setBackground(new Color(245, 246, 255));
        setContentPane(Panel);


        //Welcome Banner Image
        ImageIcon welcomeBanner = new ImageIcon(ClassLoader.getSystemResource("images/Welcome.png"));
        ImageIcon scaledBanner = new ImageIcon(
                welcomeBanner.getImage().getScaledInstance(400, 300, java.awt.Image.SCALE_SMOOTH)
        );
        JLabel banner = new JLabel(scaledBanner);
        banner.setBounds(100,300,400,300);
        Panel.add(banner);

        //Welcome Text
        JLabel welcomeText1 = new JLabel("Welcome to ");
        welcomeText1.setBounds(120,75,400,120);
        welcomeText1.setFont(new Font("arial", Font.PLAIN, 60));
        welcomeText1.setForeground(Color.black);
        JLabel welcomeText2 = new JLabel("Quiz Central!");
        welcomeText2.setBounds(70,180,500,120);
        welcomeText2.setFont(new Font("arial", Font.BOLD, 69));
        welcomeText2.setForeground(Mainpurple);
        Panel.add(welcomeText1);
        Panel.add(welcomeText2);

        Panel.add(registerPanel);
        setVisible(true);
    }
    public void regToLogin(){
        Panel.remove(registerPanel);
        Panel.add(loginPanel);
        Panel.revalidate(); // Refresh the panel to show changes
        Panel.repaint(); // Repaint the panel to show new content
    }
    public void goToHomePage(String UserName){
        this.dispose();
        new HomePage(con,UserName);
    }
    public void goToAdminPage(){
        this.dispose();
        new AdminFrame(con);
    }
}
