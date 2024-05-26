import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ConnectionClass {
    Connection com;
    Statement stm;
    ConnectionClass()
    {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            com = DriverManager.getConnection("jdbc:mysql://localhost:3306/car_rental","root","pakchinA1.2");
            stm = com.createStatement();
            if(com.isClosed())
            {
                System.out.println("yes");
            }
            else {
                System.out.println("NO");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
