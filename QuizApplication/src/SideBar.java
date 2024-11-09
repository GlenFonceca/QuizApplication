import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.sql.Connection;

public class SideBar extends JPanel implements ActionListener {

    Color Mainpurple = new Color(167, 41, 245);
    private AdminFrame adminFrame; // Reference to AdminFrame to navigate between panels
    Connection con;
    SideBar(AdminFrame adminFrame,Connection con) {
        this.adminFrame = adminFrame;
        this.con = con;
        setLayout(null);
        setBounds(0, 0, 200, 700);
        setBackground(Mainpurple);

        //Top Icon (Logo or Image)
        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("images/boy.png")).getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH))); // Set your top icon
        iconLabel.setBounds(35, 25, 130, 130);
        add(iconLabel);


        // Add question button
        JButton addQuestionButton = createSidebarButton("Add Question");
        addQuestionButton.setBounds(20, 220, 160, 50);
        addQuestionButton.setActionCommand("AddQuestion");
        add(addQuestionButton);

        // Delete question button
        JButton deleteQuestionButton = createSidebarButton("Delete Question");
        deleteQuestionButton.setBounds(20, 280, 160, 50);
        deleteQuestionButton.setActionCommand("DeleteQuestion");
        add(deleteQuestionButton);

        // Edit question button
        JButton editQuestionButton = createSidebarButton("Edit Question");
        editQuestionButton.setBounds(20, 340, 160, 50);
        editQuestionButton.setActionCommand("EditQuestion");
        add(editQuestionButton);

        // Logout button
        JButton logoutButton = createSidebarButton("Logout");
        logoutButton.setBounds(20, 600, 160, 50);
        logoutButton.setActionCommand("Logout");
        add(logoutButton);
    }

    // Method to create a button with text only
    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setForeground(Color.white);
        button.setBackground(Mainpurple);
        button.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add some padding to the button text

        // Set hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(125, 0, 192)); // Darker shade of purple on hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Mainpurple); // Reset to original color
            }
        });
        button.addActionListener(this);

        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        // Switch between different actions based on the action command
        switch (actionCommand) {
            case "AddQuestion":
                adminFrame.switchPanel(new SelectQuizPanel(con, adminFrame,1)); // OperationNo = 1 for Add Question
                break;
            case "DeleteQuestion":
                adminFrame.switchPanel(new SelectQuizPanel(con, adminFrame,2)); // OperationNo = 2 for Delete Question
                break;
            case "EditQuestion":
                adminFrame.switchPanel(new SelectQuizPanel(con, adminFrame,3)); // OperationNo = 3 for Edit Question
                break;
            case "Logout":
                adminFrame.dispose();
                new WelcomePage(con).regToLogin();
                break;
            default:
                break;
        }
    }
}
