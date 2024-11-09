import java.sql.Connection;

public class Main{
    public static Connection con= DatabaseConnection.connect() ;

    public static void main(String[] args) {
        WelcomePage wc = new WelcomePage(con);
    }
}
