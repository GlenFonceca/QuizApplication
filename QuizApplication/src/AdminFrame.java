import javax.swing.*;
import java.awt.*;

import java.sql.Connection;

public class AdminFrame extends JFrame {

    //Admin Password  = admin123

    public Connection con;
    DashboardPanel dashboardPanel;
    Color Mainpurple = new Color(167, 41, 245);
    JPanel Panel;
    SideBar sideBar;

    AdminFrame(Connection con) {
        this.con = con;
        dashboardPanel = new DashboardPanel(con);
        sideBar = new SideBar(this,con);
        setLayout(null);
        setSize(1000, 700);
        setLocationRelativeTo(null); //To make the Frame to Open in the Center of Screen
        setTitle("Quiz Central");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Panel = new JPanel(null);
        Panel.setBackground(new Color(245, 246, 255));
        setContentPane(Panel);

        add(dashboardPanel);
        add(sideBar);
        setVisible(true);
    }

public void switchPanel(JPanel newPanel) {
    Panel.removeAll();
    // Add the sidebar back to the panel
    Panel.add(sideBar);
    // Add the new panel to the content pane
    Panel.add(newPanel);
    // Revalidate and repaint to refresh the frame
    Panel.revalidate();
    Panel.repaint();
}



}

