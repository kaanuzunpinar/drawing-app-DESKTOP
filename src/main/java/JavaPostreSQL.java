import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JavaPostreSQL {
    public static void writeToDatabase(){
        String url="jdbc:postgresql://localhost:5432/postgres";
        String user="postgres";
        String password="root";

        String name="WORKED";
        String query="INSERT INTO person(first_name) VALUES (?)";

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection= DriverManager.getConnection(url,user,password);
            PreparedStatement pst= connection.prepareStatement(query);
            pst.setString(1,name);
            pst.executeUpdate();
            System.out.println("success");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
