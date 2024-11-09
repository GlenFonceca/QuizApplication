import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;

public class SelectQuizPanel extends JPanel implements ActionListener {
    AdminFrame adminFrame;
    JButton topic1,topic2,topic3,topic4;
    Color Mainpurple = new Color(167, 41, 245);
    Color textColor = new Color(0, 14, 81);
    int operationNO;
    Connection con;
    SelectQuizPanel(Connection con, AdminFrame adminFrame,int operationNO){
        this.con = con;
        this.adminFrame = adminFrame;
        this.operationNO = operationNO;
        setLayout(null);
        setBackground(Color.white);
        setBounds(200,0,800,700);

        JLabel selectText = new JLabel("Select a Quiz");
        selectText.setBounds(100,50,300,50);
        selectText.setFont(new Font("rubik",Font.BOLD,30));
        add(selectText);

        topic1 = getCustomButton(150,150,"General Knowledge");
        topic2 = getCustomButton(150,400,"Science");
        topic3 = getCustomButton(400,150,"History");
        topic4 = getCustomButton(400,400,"Sports");

        add(topic1);
        add(topic2);
        add(topic3);
        add(topic4);

    }
    JButton getCustomButton(int x, int y,String title) {
        JButton customButton = new JButton("<html>"+title+"</html>");
        customButton.setBounds(x, y, 200, 200);
        customButton.setFont(new Font("Arial", Font.PLAIN, 22));
        customButton.setForeground(textColor);
        customButton.setContentAreaFilled(false);
        customButton.setFocusPainted(false);
        customButton.setBorder(new LineBorder(Mainpurple, 6));
        customButton.setHorizontalAlignment(SwingConstants.CENTER);
        customButton.addActionListener(this);
        customButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                customButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                customButton.setForeground(Color.white);
                customButton.setBackground(Mainpurple);
                customButton.setOpaque(true);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                customButton.setForeground(textColor);
                customButton.setBackground(null);
                customButton.setOpaque(false);
            }
        });
        return customButton;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        int quiz_id=1;
        String quiz_title="";
        if(e.getSource() == topic1){
            quiz_id =1;
            quiz_title = "General Knowledge";
        } else if (e.getSource() == topic2) {
            quiz_id = 2;
            quiz_title = "Science";
        } else if(e.getSource() == topic3) {
            quiz_id =3;
            quiz_title = "History";
        } else if (e.getSource() == topic4) {
            quiz_id = 4;
            quiz_title = "Sports";
        }
        if(e.getSource() == topic1 ||e.getSource() == topic2||e.getSource() == topic3||e.getSource() == topic4){
            switch (operationNO) {
                case 1:
                    adminFrame.switchPanel(new AddQuestionPanel(con, quiz_id, quiz_title));
                    break;
                case 2:
                    adminFrame.switchPanel(new DeleteQuestionPanel(con, quiz_id));
                    break;
                case 3:
                    adminFrame.switchPanel(new EditQuestionPanel(con, quiz_id));
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Invalid operation");
                    break;
            }
        }
    }

}
