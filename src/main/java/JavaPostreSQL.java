import java.sql.*;

public class JavaPostreSQL {
    public static boolean writeToDatabase(String username,String password){
        String url="jdbc:postgresql://localhost:5432/postgres";
        String query="SELECT * FROM users WHERE username= ? AND password=?";

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection= DriverManager.getConnection(url,"postgres","root");
            PreparedStatement pst= connection.prepareStatement(query);
            pst.setString(1,username);
            pst.setString(2,password);
            ResultSet resultSet=pst.executeQuery();
            if(!resultSet.next()){
                return false;
            }
            else{
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
