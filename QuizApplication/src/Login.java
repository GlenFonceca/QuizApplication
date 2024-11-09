
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class Login extends JPanel implements ActionListener {
    Connection conn;
    WelcomePage welcomePage;
    String CurrentUser;
    Color Mainpurple = new Color(167, 41, 245);
    JTextField username;
    JPasswordField passwordField;
    JButton submitButton;

    Login(Connection conn,WelcomePage welcomePage){
        this.welcomePage = welcomePage;
        this.conn = conn;
        setLayout(null);
        setBackground(Color.white);
        setBounds(575,125,350,450);

        JLabel loginText = new JLabel("Login");
        loginText.setBounds(135,50,100,50);
        loginText.setFont(new Font("arial", Font.BOLD, 29));
        add(loginText);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("arial", Font.BOLD, 18)); // Simple label font
        userLabel.setBounds(25, 150, 100, 25);
        add(userLabel);

        username = new JTextField();
        username.setBounds(25,175,300,35);
        add(username);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("arial", Font.BOLD, 18)); // Simple label font
        passwordLabel.setBounds(25, 225, 100, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(25,250,300,35);
        add(passwordField);

        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("arial",Font.BOLD,20));
        submitButton.setBounds(105,350,150,30);
        submitButton.setBackground(Mainpurple);
        submitButton.setForeground(Color.white);
        submitButton.addActionListener(this);
        add(submitButton);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        CurrentUser = username.getText();
        String password = new String(passwordField.getPassword());
        boolean valid;
        if(e.getSource() == submitButton){
            try {
                valid =  new AuthenticationService(conn).authenticateUser(CurrentUser,password);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            if (valid) {
                if (CurrentUser.equalsIgnoreCase("Admin")) {
                    JOptionPane.showMessageDialog(null, "Welcome, Admin!", "Admin Access", JOptionPane.INFORMATION_MESSAGE);
                    welcomePage.goToAdminPage();
                } else {
                    welcomePage.goToHomePage(CurrentUser);
                    JOptionPane.showMessageDialog(null, "Login Successful .Welcome,"+CurrentUser, "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Username Or Password is Wrong", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
