import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class Register extends JPanel implements ActionListener {
    Connection conn;
    Color Mainpurple = new Color(167, 41, 245);
    JTextField username;
    JPasswordField confirmpasswordField,passwordField;
    JButton sumitButton,loginButton;
    private WelcomePage welcomePage;
    Register(Connection con,WelcomePage welcomePage) {
        this.welcomePage = welcomePage;
        conn = con;
        setLayout(null);
        setBackground(Color.white);
        setBounds(575, 100, 350, 500);
        JLabel signUpText = new JLabel("SignUp");
        signUpText.setBounds(125, 25, 125, 50);
        signUpText.setFont(new Font("arial", Font.BOLD, 29));
        add(signUpText);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("arial", Font.PLAIN, 18)); // Simple label font
        userLabel.setBounds(25, 125, 100, 25);
        add(userLabel);

        username = new JTextField();
        username.setBounds(25, 150, 300, 35);
        add(username);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("arial", Font.PLAIN, 18)); // Simple label font
        passwordLabel.setBounds(25, 200, 100, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(25, 225, 300, 35);
        add(passwordField);

        JLabel confirmpasswordLabel = new JLabel("ConfirmPassword:");
        confirmpasswordLabel.setFont(new Font("arial", Font.PLAIN, 18)); // Simple label font
        confirmpasswordLabel.setBounds(25, 275, 200, 25);
        add(confirmpasswordLabel);

        confirmpasswordField = new JPasswordField();
        confirmpasswordField.setBounds(25, 300, 300, 35);
        add(confirmpasswordField);

        sumitButton = new JButton("Submit");
        sumitButton.setFont(new Font("arial", Font.BOLD, 20));
        sumitButton.setBounds(105, 360, 150, 30);
        sumitButton.setBackground(Mainpurple);
        sumitButton.setForeground(Color.white);
        sumitButton.addActionListener(this);
        add(sumitButton);

        JLabel loginText = new JLabel("Already Have Account?");
        loginText.setFont(new Font("serif", Font.BOLD, 12)); // Simple label font
        loginText.setBounds(25, 440, 160, 25);
        add(loginText);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("arial",Font.BOLD,16));
        loginButton.setForeground(Mainpurple);
        loginButton.setBounds(185,440,100,25);
        loginButton.setBackground(null);
        loginButton.setBorder(null);
        loginButton.addActionListener(this);
        add(loginButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String userName = username.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmpasswordField.getPassword());

        if(e.getSource() == loginButton){
            welcomePage.regToLogin();
        }
        else if(e.getSource() == sumitButton && validateRegForm(userName,password,confirmPassword)){
            try {
                new AuthenticationService(conn).registerUser(userName,password);
                JOptionPane.showMessageDialog(null,"Registered Successfully");
            } catch (Exception ex) {
                ex.getMessage();
            }
            welcomePage.goToHomePage(userName);
        }
    }
    boolean validateRegForm(String username ,String pass,String cmPass){
        //This condition will Not allow any new user to be a Admin
//        if (username.equalsIgnoreCase("Admin")) {
//            JOptionPane.showMessageDialog(null, "Username cannot be 'Admin'!", "Error", JOptionPane.ERROR_MESSAGE);
//            return false;
//        }
        if (pass.equals(cmPass)) {
            return true; // Passwords match
        } else {
            // Show an error dialog if passwords do not match
            JOptionPane.showMessageDialog(null, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Passwords do not match
        }
    }
}

